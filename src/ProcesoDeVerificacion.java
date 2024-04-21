public class ProcesoDeVerificacion implements Runnable{
    private Vuelo vuelo;
    public ProcesoDeVerificacion(Vuelo vuelo){
        this.vuelo = vuelo;
    }

    @Override
    public void run() {

        int cantidadReservas = 0;
        while(cantidadReservas != vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS() || vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getSize() > 0){
            cantidadReservas = cantReservas();
            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if(reserva == null) continue;
            if(reserva.getAsiento().getEstado().equals(ESTADO.CHECKED)){
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
