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
        BufferDeReservas pendientesDePago = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO);
        BufferDeReservas confirmadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS);
        BufferDeReservas canceladas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS);
        BufferDeReservas verificadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS);
        int cantProcesadas = canceladas.getSize() + verificadas.getSize();
        return cantProcesadas < vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS();
    }
}
