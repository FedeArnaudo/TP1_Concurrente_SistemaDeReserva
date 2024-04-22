import java.util.HashMap;

public class ProcesoDePago implements Runnable{
    private final Object locker = new Object();
    private final Vuelo vuelo;
    private final HashMap<String, Integer> registroDeHilos;
    private final int reservasPorHilo;
    private final float sleepTime;
    public ProcesoDePago(Vuelo vuelo){
        this.vuelo = vuelo;
        registroDeHilos = new HashMap<>();
        reservasPorHilo = vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS() / 2;
        sleepTime = (float) (10 * 1000) / reservasPorHilo;
    }

    @Override
    public void run() {
        addReserva();
        while(reservasPorHilo(Thread.currentThread().getName()) < reservasPorHilo) {
            long inicio = 0L;
            long fin = 0L;
            inicio = System.currentTimeMillis(); // Registrar el tiempo inicial

            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).getReserva();

            if (reserva == null) continue;
            if (reserva.getAsiento().getEstado().equals(ESTADO.OCUPADO)) { //Si fue tomada por un solo hilo del registero de Pendientes, que otro estado podria tener?
                if (reserva.getProbabilidadDePago()) {
                    pagar(reserva);
                } else {
                    cancelar(reserva);
                }
                addReserva();

                synchronized (locker){
                    String numeroAsiento = "";
                    if(reserva.getAsiento().getNumeroAsiento() < 10){
                        numeroAsiento = "00" + reserva.getAsiento().getNumeroAsiento();
                    }
                    else if(reserva.getAsiento().getNumeroAsiento() < 100){
                        numeroAsiento = "0" + reserva.getAsiento().getNumeroAsiento();
                    }
                    else {
                        numeroAsiento = "" + reserva.getAsiento().getNumeroAsiento();
                    }
                    System.out.println(Thread.currentThread().getName() + " - " +
                            "" + numeroAsiento +" - " +
                            "" + reserva.getAsiento().getEstado() + " - " +
                            "" + reserva.getProbabilidadDePago());
                }

                reserva.setAvailable(true);

                //  Algoritmo de sleep
                fin = (long) (sleepTime - (System.currentTimeMillis() - inicio));
                if(fin > 0){
                    try {
                        Thread.sleep(fin);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void pagar(Reserva reserva){
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).deleteReserva(reserva);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).addReserva(reserva);
    }

    private void cancelar(Reserva reserva) {
        reserva.getAsiento().setEstado(ESTADO.DESCARTADO);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).deleteReserva(reserva);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).addReserva(reserva);
    }

    public Integer reservasPorHilo(String idThread){
        synchronized (this){
            return registroDeHilos.get(idThread);
        }
    }

    public void addReserva() {
        synchronized (this){
            String nameThread = "" + Thread.currentThread().getName();
            int numeroDeReserva = 0;
            if(!registroDeHilos.containsKey(nameThread)){
                registroDeHilos.put(nameThread, numeroDeReserva);
            }
            else {
                numeroDeReserva = registroDeHilos.get(nameThread) + 1;
                registroDeHilos.put(nameThread, numeroDeReserva);
            }
        }
    }
}
