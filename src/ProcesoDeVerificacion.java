public class ProcesoDeVerificacion implements Runnable{
    private Vuelo vuelo;
    public ProcesoDeVerificacion(Vuelo vuelo){
        this.vuelo = vuelo;
    }

    @Override
    public void run() {
        while(isProcessActive()){
            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if(reserva == null) continue;
            if(reserva.isChecked()){
                verificar(reserva);
            } else {
                reserva.setAvailable(true);
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
}
