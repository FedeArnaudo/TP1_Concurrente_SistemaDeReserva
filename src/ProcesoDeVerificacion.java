public class ProcesoDeVerificacion implements Runnable{
    private final Vuelo vuelo;
    private long registroDeTiempo;
    private long sleepTime;
    private int contador;
    public ProcesoDeVerificacion(Vuelo vuelo){
        this.vuelo = vuelo;
        registroDeTiempo = 0;
        sleepTime = 16 * 1000;
        contador = 1;
    }

    @Override
    public void run() {
        long inicio = 0L;
        long fin = 0L;
        inicio = System.currentTimeMillis(); // Registrar el tiempo inicial
        while(isProcessActive()){

            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if(reserva != null && reserva.isChecked()){
                verificar(reserva);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                fin = System.currentTimeMillis() - inicio;
                registrarTiempo(fin);
            }
            else if(reserva != null && !reserva.isChecked()){
                reserva.setAvailable(true);
            }
        }
        fin = System.currentTimeMillis() - inicio;
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
    public void verificar(Reserva reserva){
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).deleteReserva(reserva);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).addReserva(reserva);
    }
    public boolean isProcessActive() {
        int cantProcesadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize();
        cantProcesadas += vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize();
        return cantProcesadas < vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS();
    }

    private void registrarTiempo(long tiempo){
        if(Thread.currentThread().getName() == "Thread 4-1") {
            registroDeTiempo += tiempo;
        }
    }

    private long getRegistroDeTiempo() {
        return registroDeTiempo;
    }
}
