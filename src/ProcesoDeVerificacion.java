public class ProcesoDeVerificacion implements Runnable{
    private Vuelo vuelo;
    public ProcesoDeVerificacion(Vuelo vuelo){
        this.vuelo = vuelo;
    }

    @Override
    public void run() {

        int cantidadReservas = 0;
        while(vuelo.getMatrizDeAsientos().getCantidadDeAsientosLibres() > 0
                || vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).getSize() > 0
                || vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getSize() > 0){
            cantidadReservas = cantReservas();
            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if(reserva == null) continue;
            if(reserva.isChecked()){
                verificar(reserva);
            }
        }
    }
    public void verificar(Reserva reserva){
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).deleteReserva(reserva);
        vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).addReserva(reserva);
    }
    public int cantReservas(){
        return vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize() + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getSize() + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize();
    }
}
