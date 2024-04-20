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

    public RegistroDeReservas getRegistroDeReservas() {
        return registroDeReservas;
    }

    public void setRegistroDeReservas(RegistroDeReservas registroDeReservas) {
        this.registroDeReservas = registroDeReservas;
    }
}
