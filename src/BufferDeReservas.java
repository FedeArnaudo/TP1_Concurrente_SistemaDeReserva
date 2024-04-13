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
        }
    }

    public Reserva getReserva() {
        int index = random.nextInt(reservas.size());
        return reservas.get(index);
    }

    public void deleteReserva(Reserva reserva) {
        synchronized (this) {
            reservas.remove(reserva);
        }
    }
}
