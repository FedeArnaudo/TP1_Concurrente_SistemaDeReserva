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
        synchronized (this) {
            if (!reservas.isEmpty()) {
                Reserva reserva = reservas.get(random.nextInt(reservas.size()));
                if (reserva.isAvailable()) {
                    reserva.setAvailable(false);
                    return reserva;
                }
            }
            return null;
        }
    }

    public boolean deleteReserva(Reserva reserva) {
        synchronized (this) {
            return reservas.remove(reserva);
        }
    }

    public int getSize() {
        return reservas.size();
    }
}
