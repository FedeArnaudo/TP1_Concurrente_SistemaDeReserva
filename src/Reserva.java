import java.util.Random;

public class Reserva {
    private Asiento asiento;
    private boolean available;
    private boolean checked;
    private final Random random;
    private final int PROBABILIDAD_PAGO = 90;
    private final int PROBABILIDAD_DE_CANCELACION = 10;
    public Reserva(Asiento asiento){
        this.asiento = asiento;
        random = new Random();
        available = true;
        checked = false;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public synchronized boolean isAvailable() {
        return available;
    }

    public synchronized void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean getProbabiliadadDePago() {
        return getProbabilidad() < PROBABILIDAD_PAGO;
    }

    public boolean getPROBABILIDAD_DE_CANCELACION() {
        return getProbabilidad() < PROBABILIDAD_DE_CANCELACION;
    }
    private int getProbabilidad(){
        return (random.nextInt(100));
    }
    public synchronized void setChecked (boolean checked) {
        this.checked = checked;
        this.available = true;
    }
    public synchronized boolean isChecked () {
        return checked;
    }
}
