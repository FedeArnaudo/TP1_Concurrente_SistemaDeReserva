public class Vuelo {
    private MatrizDeAsientos matrizDeAsientos;
    private RegistroDeReservas registroDeReservas;
    public Vuelo(MatrizDeAsientos matrizDeAsientos, RegistroDeReservas registroDeReservas){
        this.matrizDeAsientos = matrizDeAsientos;
        this.registroDeReservas = registroDeReservas;
    }

    public MatrizDeAsientos getMatrizDeAsientos() {
        return matrizDeAsientos;
    }

    public void setMatrizDeAsientos(MatrizDeAsientos matrizDeAsientos) {
        this.matrizDeAsientos = matrizDeAsientos;
    }

    public RegistroDeReservas getRegistros() {
        return registroDeReservas;
    }

    public void setRegistros(RegistroDeReservas registroDeReservas) {
        this.registroDeReservas = registroDeReservas;
    }
}
