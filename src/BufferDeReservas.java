import java.util.ArrayList;
import java.util.Random;

public class BufferDeReservas {
    private final ArrayList<Reserva> reservas;
    private Random random;

    public BufferDeReservas(){
        reservas = new ArrayList<Reserva>();
        random = new Random();
    }

    public void addReserva(Reserva reserva) {
        synchronized (this) {
            reservas.add(reserva);
            reserva.setAvailable(true);
        }
    }

    public Reserva getReserva() {
        Reserva reserva = null;
        synchronized (this) {
            if (!reservas.isEmpty()) {
                reserva = reservas.get(random.nextInt(reservas.size()));
                if (reserva.isAvailable()) {
                    reserva.setAvailable(false);
                    return reserva;
                }
            }
            return reserva;
        }
    }

    public Reserva getReserva(int numReserva) {
        return reservas.get(numReserva);
    }

    public void deleteReserva(Reserva reserva) {
        synchronized (this) {
            reservas.remove(reserva);
        }
    }

    public int getSize() {
        synchronized (this){
            return reservas.size();
        }
    }
}
