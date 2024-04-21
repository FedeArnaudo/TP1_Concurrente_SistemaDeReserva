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
        while (vuelo.getMatrizDeAsientos().getCantidadDeAsientosLibres() > 0
                || vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).getSize() > 0
                || vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getSize() > 0){
            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if (reserva == null) continue;
            if (reserva.isChecked()) continue;
            if (reserva.getPROBABILIDAD_DE_CANCELACION()) {
                vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).deleteReserva(reserva);
                vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).addReserva(reserva);
            } else {
                reserva.setChecked(true);
                reserva.setAvailable(true);
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

