import java.util.ArrayList;
import java.util.HashMap;

public class ProcesoDeCancelacionValidacion implements Runnable {
    private final Object locker = new Object();
    private final Vuelo vuelo;
    private long registroDeTiempo;
    private long sleepTime;
    public ProcesoDeCancelacionValidacion(Vuelo vuelo) {
        this.vuelo = vuelo;
        registroDeTiempo = 0;
        sleepTime = 30 * 1000;
    }

    @Override
    public void run() {
        while (isProcessActive()){
            long inicio = 0L;
            long fin = 0L;
            inicio = System.currentTimeMillis(); // Registrar el tiempo inicial
            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();

            if(reserva != null && !reserva.isChecked()){
                if (reserva.getProbabilidadDeCancelacion()) {
                    cancelar(reserva);
                } else {
                    validar(reserva);
                }

                reserva.setAvailable(true);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                fin = System.currentTimeMillis() - inicio;
                registrarTiempo(fin);
            }
        }
        if(Thread.currentThread().getName() == "Thread 3-1"){
            sleepTime -= getRegistroDeTiempo();

            try {
                Thread.sleep((long)sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void cancelar(Reserva reserva){
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).deleteReserva(reserva);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).addReserva(reserva);
        reserva.getAsiento().setEstado(ESTADO.DESCARTADO);
    }

    public void validar(Reserva reserva){
        reserva.setChecked(true);
    }

    public boolean isProcessActive() {
        int cantProcesadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize();
        cantProcesadas += vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize();
        return cantProcesadas < vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS();
    }

    public void registrarTiempo(long tiempo){
        synchronized (this){
            registroDeTiempo += tiempo;
        }
    }

    public long getRegistroDeTiempo() {
        synchronized (this){
            return registroDeTiempo;
        }
    }
}

