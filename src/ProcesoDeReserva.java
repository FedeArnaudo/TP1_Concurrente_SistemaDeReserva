import java.util.HashMap;

public class ProcesoDeReserva implements Runnable{
    private final Object locker = new Object();
    private final Vuelo vuelo;
    private final HashMap<String, Integer> hiloReserva;
    private final int maxReservas;
    private final float sleepTime;
    public ProcesoDeReserva(Vuelo vuelo){
        this.vuelo = vuelo;
        hiloReserva = new HashMap<>();
        maxReservas = (vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS()/3);
        sleepTime = (float) ((10.0 * 1000.0 ) / maxReservas);
    }

    @Override
    public void run() {
        addReserva();
        while (reservasPorHilo(Thread.currentThread().getName()) < maxReservas){
            long inicio = 0L;
            long fin = 0L;
            inicio = System.currentTimeMillis(); // Registrar el tiempo inicial

            Asiento asiento = vuelo.getMatrizDeAsientos().getAsientoRandom();
            if(asiento != null && asiento.getEstado().equals(ESTADO.LIBRE)){
                reservar(asiento);
                vuelo.getMatrizDeAsientos().decrementarCantidadDeAsientosLibres();

                synchronized (locker){
                    System.out.println(Thread.currentThread().getName() + " - " + asiento.getNumeroAsiento());
                }

                asiento.setIdThread(null);
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
            int numRes = 0;
            if(!hiloReserva.containsKey(nameThread)){
                hiloReserva.put(nameThread, numRes);
            }
            else {
                numRes = hiloReserva.get(nameThread) + 1;
                hiloReserva.put(nameThread, numRes);
            }
        }
    }

    public Integer reservasPorHilo(String idThread){
        synchronized (this){
            return hiloReserva.get(idThread);
        }
    }
}
