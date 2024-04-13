public class Asiento {
    private ESTADO estado;
    private int numeroAsiento;
    private String idThread;
    public Asiento(){
    }
    public Asiento(int numeroAsiento, ESTADO estado){
        this.numeroAsiento = numeroAsiento;
        this.estado = estado;
    }

    public ESTADO getEstado() {
        return estado;
    }

    public void setEstado(ESTADO estado) {
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

    public synchronized void setIdThread(String idThread) {
        if(getIdThread() == null){
            this.idThread = idThread;
        }
        else if(getIdThread().equals(idThread)){
            this.idThread = null;
        }

    }
}
