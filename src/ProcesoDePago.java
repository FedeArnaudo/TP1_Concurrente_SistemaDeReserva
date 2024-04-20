public class ProcesoDePago implements Runnable{
    private Vuelo vuelo;
    public ProcesoDePago(Vuelo vuelo){
        this.vuelo = vuelo;
    }

    @Override
    public void run() {
        while(vuelo.getMatrizDeAsientos().getCantidadDeAsientosLibres() > 0 || vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).getSize() > 0) {
            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).getReserva();
            if (reserva == null) continue;
            if (reserva.getAsiento().getEstado().equals(ESTADO.OCUPADO)) {
                if (reserva.getProbabiliadadDePago()) {
                    pagar(reserva);
                } else {
                    cancelar(reserva);
                }
            }
        }
    }

    private void pagar(Reserva reserva){
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).deleteReserva(reserva);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).addReserva(reserva);
    }

    private void cancelar(Reserva reserva) {
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).deleteReserva(reserva);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).addReserva(reserva);
        reserva.getAsiento().setEstado(ESTADO.DESCARTADO);
    }
}
