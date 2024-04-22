public class RegistroDeReservas {
    private final BufferDeReservas Pendientes;
    private final BufferDeReservas Confirmadas;
    private final BufferDeReservas Canceladas;
    private final BufferDeReservas Verificadas;
    public RegistroDeReservas(){
        Pendientes = new BufferDeReservas();
        Confirmadas = new BufferDeReservas();
        Canceladas = new BufferDeReservas();
        Verificadas = new BufferDeReservas();
    }

    public BufferDeReservas getBufferDeReservas(TIPO_DE_RESERVA tipoDeReserva){
        switch (tipoDeReserva) {
            case PENDIENTE_DE_PAGO: return Pendientes;
            case CONFIRMADAS: return Confirmadas;
            case CANCELADAS: return Canceladas;
            case VERIFICADAS: return Verificadas;
            default: return null;
        }
    }

    public void getTotal(){

    }
}
