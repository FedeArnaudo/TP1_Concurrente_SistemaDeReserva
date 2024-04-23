import java.util.HashMap;

public class ProcesoDePago implements Runnable{
    private final Vuelo vuelo;
    private final HashMap<String, Integer> registroDeHilos;
    private final int reservasPorHilo;
    private final float sleepTime;
    private int contador;

    public ProcesoDePago(Vuelo vuelo){
        this.vuelo = vuelo;
        registroDeHilos = new HashMap<>();
        reservasPorHilo = vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS() / 2;
        sleepTime = (float) (15 * 1000) / reservasPorHilo;
        contador = 1; // Eliminar
    }

    @Override
    public void run() {
        addReserva();
        while(reservasPorHilo(Thread.currentThread().getName()) < reservasPorHilo) {
            long inicio = 0L;
            long fin = 0L;
            inicio = System.currentTimeMillis(); // Registrar el tiempo inicial

            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).getReserva();

            if (reserva != null) { //Si fue tomada por un solo hilo del registero de Pendientes, que otro estado podria tener?
                if (reserva.getProbabilidadDePago()) {
                    pagar(reserva);
                } else {
                    cancelar(reserva);
                }
                addReserva();

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

    private Integer reservasPorHilo(String idThread){
        synchronized (this){
            return registroDeHilos.get(idThread);
        }
    }

    private void addReserva() {
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
