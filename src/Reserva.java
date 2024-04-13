import java.util.Random;

public class Reserva {
    private Asiento asiento;
    private String idThread;
    private final Random random;
    private final int PROBABILIDAD_PAGO = 90;
    private final int PROBABILIDAD_DE_CANCELACION = 10;
    public Reserva(Asiento asiento){
        this.asiento = asiento;
        random = new Random();
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public String getIdThread() {
        return idThread;
    }

    public void setIdThread(String idThread) {
        this.idThread = idThread;
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
