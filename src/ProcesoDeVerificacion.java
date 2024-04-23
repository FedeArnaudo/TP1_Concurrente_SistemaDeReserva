public class ProcesoDeVerificacion implements Runnable{
    private final Vuelo vuelo;
    private long registroDeTiempo;
    private long sleepTime;
    private int contador;
    public ProcesoDeVerificacion(Vuelo vuelo){
        this.vuelo = vuelo;
        registroDeTiempo = 0;
        sleepTime = 10 * 1000;
        contador = 1;
    }

    @Override
    public void run() {
        while(isProcessActive()){
            long inicio = 0L;
            long fin = 0L;
            inicio = System.currentTimeMillis(); // Registrar el tiempo inicial

            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if(reserva != null && reserva.isChecked()){
                verificar(reserva);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                /*fin = System.currentTimeMillis() - inicio;
                registrarTiempo(fin);

                synchronized (this){
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
                            "" + contador + " - " +
                            "" + numeroAsiento +" - " +
                            "" + reserva.getAsiento().getEstado() + " - " +
                            "" + reserva.getProbabilidadDePago() + " - " +
                            "" + reserva.getProbabilidadDeCancelacion() + " - " + reserva.isChecked());
                    contador ++;
                }*/
            }
            else if(reserva != null && !reserva.isChecked()){
                reserva.setAvailable(true);
            }
        }
        /*if(Thread.currentThread().getName() == "Thread 4-1"){
            sleepTime -= getRegistroDeTiempo();

            if(sleepTime > 0){
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
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

    public void registrarTiempo(long tiempo){
        if(Thread.currentThread().getName() == "Thread 4-1") {
            registroDeTiempo += tiempo;
        }
    }

    public long getRegistroDeTiempo() {
        return registroDeTiempo;
    }
}
