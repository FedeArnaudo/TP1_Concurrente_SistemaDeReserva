import java.util.ArrayList;

public class ProcesoDeCancelacionValidacion implements Runnable {
    private final Vuelo vuelo;
    public final ArrayList<Reserva> reservasChecked;
    public ProcesoDeCancelacionValidacion(Vuelo vuelo) {
        this.vuelo = vuelo;
        reservasChecked = new ArrayList<>();
    }

    @Override
    public void run() {
        /*while (true){
            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if(reserva != null){

            }
        }*/

        if(vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getSize() >0){
            Reserva reservaConfirmada = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if(reservaConfirmada.getPROBABILIDAD_DE_CANCELACION()){
                if(vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).deleteReserva(reservaConfirmada)){
                    vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).addReserva(reservaConfirmada);
                    reservaConfirmada.getAsiento().setEstado(ESTADO.DESCARTADO);
                }
            }
            else {
                isChecked(reservaConfirmada);
                reservaConfirmada.getAsiento().setEstado(ESTADO.CHECKED);
            }
        }
    }

    public void cancelarValidar()
    {

    }    public void isChecked(Reserva reserva){
        synchronized (this){
            reservasChecked.add(reserva);
        }
    }
    public int cantdechecked (){
        return reservasChecked.size();
    }
    public boolean check(Reserva reserva){
        return reservasChecked.contains(reserva);
    }
}

