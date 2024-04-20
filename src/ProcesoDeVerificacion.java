public class ProcesoDeVerificacion implements Runnable{
    private Vuelo vuelo;
    public ProcesoDeVerificacion(Vuelo vuelo){
        this.vuelo = vuelo;
    }

    @Override
    public void run() {
        int cantidadReservas = 0;
        while(cantidadReservas != vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS() || vuelo.getRegistros().getReservas(TIPO_DE_RESERVA.CONFIRMADAS).getSize() > 0   ){
            cantidadReservas = vuelo.getRegistros().getReservas(TIPO_DE_RESERVA.CANCELADAS).getSize() + vuelo.getRegistros().getReservas(TIPO_DE_RESERVA.CONFIRMADAS).getSize() + vuelo.getRegistros().getReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize();
            Reserva reserva = vuelo.getRegistros().getReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if(reserva == null) continue;
            if(checked(reserva)){
                if(vuelo.getRegistros().getReservas(TIPO_DE_RESERVA.CONFIRMADAS).deleteReserva(reserva)) {
                    vuelo.getRegistros().getReservas(TIPO_DE_RESERVA.VERIFICADAS).addReserva(reserva);
                }
            }
        }
    }
}
