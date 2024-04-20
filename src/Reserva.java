import java.util.Random;

public class Reserva {
    private Asiento asiento;
    private boolean available;
    private final Random random;
    private final int PROBABILIDAD_PAGO = 90;
    private final int PROBABILIDAD_DE_CANCELACION = 10;
    public Reserva(Asiento asiento){
        this.asiento = asiento;
        random = new Random();
        available = true;
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
}
