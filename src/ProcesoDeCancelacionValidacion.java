import java.util.ArrayList;
import java.util.HashMap;

public class ProcesoDeCancelacionValidacion implements Runnable {
    private final Vuelo vuelo;
    private float registroDeTiempo;
    private long sleepTime;
    public ProcesoDeCancelacionValidacion(Vuelo vuelo) {
        this.vuelo = vuelo;
        registroDeTiempo = 0;
        sleepTime = 20 * 1000;
    }

    @Override
    public void run() {
        long inicio = 0L;
        long fin = 0L;
        inicio = System.currentTimeMillis(); // Registrar el tiempo inicial
        while (isProcessActive()){
            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();

            if(reserva != null && !reserva.isChecked()){
                if (reserva.getProbabilidadDeCancelacion()) {
                    cancelar(reserva);
                } else {
                    validar(reserva);
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //fin = System.currentTimeMillis() - inicio;
                //registrarTiempo((long)fin);
            }
            else if(reserva != null && reserva.isChecked()){
                reserva.setAvailable(true);
            }
        }

        fin = System.currentTimeMillis() - inicio;

        /*if(Thread.currentThread().getName().equals("Thread 3-1")) {
            System.out.println("Tiempo acumulado 3: " + (float)(getRegistroDeTiempo() / 3));
            sleepTime -= (getRegistroDeTiempo() / 3);
        }*/
        System.out.println("" + Thread.currentThread().getName() + ": " + fin);
        if ((sleepTime - fin) > 0) {
            sleepTime -= fin;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cancelar(Reserva reserva){
        reserva.getAsiento().setEstado(ESTADO.DESCARTADO);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).deleteReserva(reserva);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).addReserva(reserva);
    }

    public void validar(Reserva reserva){
        reserva.setChecked(true);
    }

    public boolean isProcessActive() {
        int cantProcesadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize();
        cantProcesadas += vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize();
        return cantProcesadas < vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS();
    }

    private void registrarTiempo(long tiempo){
        synchronized (this){
            registroDeTiempo += tiempo;
        }
    }

    private float getRegistroDeTiempo() {
        return registroDeTiempo;
    }
}

