public class Vuelo {
    private MatrizDeAsientos matrizDeAsientos;
    private RegistroDeReservas registroDeReservas;
    public Vuelo(){
        this.matrizDeAsientos = new MatrizDeAsientos();
        this.registroDeReservas = new RegistroDeReservas();
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
