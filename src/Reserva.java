import java.util.Random;

public class Reserva {
    private Asiento asiento;
    private boolean available;
    private boolean checked;
    private final Random random = new Random();
    private final double probabilidadDePago = random.nextDouble() * 100;
    private final double probabilidadDeCancelacion = random.nextDouble() * 100;

    public Reserva(Asiento asiento){
        this.asiento = asiento;
        available = true;
        checked = false;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean getProbabilidadDePago() {
        int PROBABILIDAD_PAGO = 90;
        return probabilidadDePago < PROBABILIDAD_PAGO;
    }

    public boolean getProbabilidadDeCancelacion() {
        int PROBABILIDAD_DE_CANCELACION = 10;
        return probabilidadDeCancelacion < PROBABILIDAD_DE_CANCELACION;
    }
    public void setChecked (boolean checked) {
        this.checked = checked;
        this.available = true;
    }
    public synchronized boolean isChecked () {
        return checked;
    }
}
