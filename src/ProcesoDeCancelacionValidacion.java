import java.nio.Buffer;
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
        while (isProcessActive()){
            System.out.println("Verificacion "
                    + vuelo.getMatrizDeAsientos().getCantidadDeAsientosLibres() + " "
                    + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).getSize() + " "
                    + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getSize() + " "
                    + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize() + " "
                    + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize());
            Reserva reserva = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getReserva();
            if (reserva == null) continue;
            //System.out.println("Reserva: " + reserva.isChecked() + " Numero: " + reserva.getAsiento().getNumeroAsiento());
            if (!reserva.isChecked()) {
                if (reserva.getPROBABILIDAD_DE_CANCELACION()) {
                    vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).deleteReserva(reserva);
                    vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).addReserva(reserva);
                    reserva.getAsiento().setEstado(ESTADO.DESCARTADO);
                } else {
                    reserva.setChecked(true);
                }
            } else {
                reserva.setAvailable(true);
            }
        }
    }

    public boolean isProcessActive() {
        BufferDeReservas pendientesDePago = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO);
        BufferDeReservas confirmadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS);
        BufferDeReservas canceladas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS);
        BufferDeReservas verificadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS);
        int cantProcesadas = confirmadas.getSize() + canceladas.getSize() + verificadas.getSize();
        return cantProcesadas < vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS();
    }
}

