import java.util.Random;

public class MatrizDeAsientos {
    private Asiento[][] matrizDeAsientos;
    private final int CANTIDAD_DE_FILAS = 31;
    private final int CANTIDAD_DE_COLUMNAS = 6;
    private final int CANTIDAD_MAX_ASIENTOS = 31 * 6;
    private Random random;
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

    public Asiento[][] getMatrizDeAsientos() {
        return matrizDeAsientos;
    }

    public Asiento getAsiento(){
        int i = random.nextInt(CANTIDAD_DE_FILAS);
        int j = random.nextInt(CANTIDAD_DE_COLUMNAS);

        return matrizDeAsientos[i][j];

    }
}
