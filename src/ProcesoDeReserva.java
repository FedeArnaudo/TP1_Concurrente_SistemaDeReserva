import java.util.HashMap;

public class ProcesoDeReserva implements Runnable{
    private final Object locker = new Object();
    private final Vuelo vuelo;
    private final HashMap<String, Integer> registroDeHilos;
    private final int asientosPorHilo;
    private final float sleepTime;
    private int contador;
    public ProcesoDeReserva(Vuelo vuelo){
        this.vuelo = vuelo;
        registroDeHilos = new HashMap<>();
        asientosPorHilo = (vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS() / 3);
        sleepTime = (float) ((5 * 1000.0 ) / asientosPorHilo);   // tiempo (s) * 1000ms / cantidadDeHilos
        contador = 1; // Eliminar
    }

    @Override
    public void run() {
        addReserva();
        while (reservasPorHilo(Thread.currentThread().getName()) < asientosPorHilo){
            long inicio = 0L;
            long fin = 0L;
            inicio = System.currentTimeMillis(); // Registrar el tiempo inicial

            Asiento asiento = vuelo.getMatrizDeAsientos().getAsientoRandom();
            if(asiento != null && asiento.getEstado().equals(ESTADO.LIBRE)){
                reservar(asiento);

                /*synchronized (this){
                    String numeroAsiento = "";
                    if(asiento.getNumeroAsiento() < 10){
                        numeroAsiento = "00" + asiento.getNumeroAsiento();
                    }
                    else if(asiento.getNumeroAsiento() < 100){
                        numeroAsiento = "0" + asiento.getNumeroAsiento();
                    }
                    else {
                        numeroAsiento = "" + asiento.getNumeroAsiento();
                    }
                    System.out.println(Thread.currentThread().getName() + " - " +
                            "" + contador + " - " +
                            "" + numeroAsiento +" - " +
                            "" + asiento.getEstado());
                    contador ++;
                }*/

                asiento.setIdThread(null); // Libero el asiento del hilo

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
    public void reservar(Asiento asiento){
        asiento.setEstado(ESTADO.OCUPADO);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).addReserva(new Reserva(asiento));
        addReserva();
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
    public Integer reservasPorHilo(String idThread){
        synchronized (this){
            return registroDeHilos.get(idThread);
        }
    }
}
