import java.util.Random;

public class MatrizDeAsientos {
    private final Asiento[][] matrizDeAsientos;
    private final int CANTIDAD_DE_FILAS = 31;
    private final int CANTIDAD_DE_COLUMNAS = 6;
    private final int CANTIDAD_MAX_ASIENTOS = 31 * 6;

    private final Random random;
    public MatrizDeAsientos(){
        matrizDeAsientos = new Asiento[CANTIDAD_DE_FILAS][CANTIDAD_DE_COLUMNAS];
        random = new Random();

        int k = 1;
        for (int i = 0; i < CANTIDAD_DE_FILAS; i ++){
            for (int j = 0; j < CANTIDAD_DE_COLUMNAS; j ++){
                matrizDeAsientos[i][j] = new Asiento(k, ESTADO.LIBRE);
                k++;
            }
        }
    }

    public int getCANTIDAD_MAX_ASIENTOS() {
        return CANTIDAD_MAX_ASIENTOS;
    }

    public Asiento getAsientoRandom(){
        int i = random.nextInt(CANTIDAD_DE_FILAS);
        int j = random.nextInt(CANTIDAD_DE_COLUMNAS);
        Asiento asiento = matrizDeAsientos[i][j];

        synchronized (this){
            if(asiento.getIdThread() == null){
                asiento.setIdThread("" + Thread.currentThread().getId());
                return asiento;
            }
        }
        return null;
    }
}
