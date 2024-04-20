public class Asiento {
    private ESTADO estado;
    private int numeroAsiento;
    private String idThread;
    public Asiento(){
    }
    public Asiento(int numeroAsiento, ESTADO estado){
        this.numeroAsiento = numeroAsiento;
        this.estado = estado;
        idThread = null;
    }

    public synchronized ESTADO getEstado() {
        return estado;
    }

    public synchronized void setEstado(ESTADO estado) {
        this.estado = estado;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public String getIdThread() {
        return idThread;
    }
    public void setIdThread(String idThread){
        this.idThread = idThread;
    }
}
