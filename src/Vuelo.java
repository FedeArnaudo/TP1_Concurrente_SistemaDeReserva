public class Vuelo {
    private MatrizDeAsientos matrizDeAsientos;
    private Registros registros;
    public Vuelo(MatrizDeAsientos matrizDeAsientos, Registros registros){
        this.matrizDeAsientos = matrizDeAsientos;
        this.registros = registros;
    }

    public MatrizDeAsientos getMatrizDeAsientos() {
        return matrizDeAsientos;
    }

    public void setMatrizDeAsientos(MatrizDeAsientos matrizDeAsientos) {
        this.matrizDeAsientos = matrizDeAsientos;
    }

    public Registros getRegistros() {
        return registros;
    }

    public void setRegistros(Registros registros) {
        this.registros = registros;
    }
}
