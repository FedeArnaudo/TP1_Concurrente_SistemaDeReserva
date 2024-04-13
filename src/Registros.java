import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.Random;

public class Registros {
    private final ArrayList<Reserva> reservasPendientes;
    private final ArrayList<Reserva> reservasConfirmadas;
    private final ArrayList<Reserva> reservasCanceladas;
    private final ArrayList<Reserva> reservasVerificadas;
    private final Object keyPendientes = new Object();
    private final Object keyConfirmadas = new Object();
    private final Object keyCanceladas = new Object();
    private final Object keyVerificadas = new Object();
    private Random random;
    public Registros(){
        reservasPendientes = new ArrayList<>();
        reservasConfirmadas = new ArrayList<>();
        reservasCanceladas = new ArrayList<>();
        reservasVerificadas = new ArrayList<>();
        random = new Random();
    }

    public void addReserva(Reserva reserva){
        switch (reserva.getRegistro()){
            case PENDIENTE_DE_PAGO:
                synchronized (keyPendientes){
                    reservasPendientes.add(reserva);
                    break;
                }
            case CONFIRMADAS:
                reservasConfirmadas.add(reserva);
                break;
            case CANCELADAS:
                reservasCanceladas.add(reserva);
                break;
            case VERIFICADAS:
                reservasVerificadas.add(reserva);
                break;
        }

    }
    public Reserva getReserva(REGISTRO registro){
        switch (registro){
            case PENDIENTE_DE_PAGO:
                synchronized (keyPendientes){
                    if(!reservasPendientes.isEmpty()){
                        return reservasPendientes.get(random.nextInt(reservasPendientes.size()));
                    }
                }
            case CONFIRMADAS:
                if(!reservasConfirmadas.isEmpty()){
                    return reservasConfirmadas.get(random.nextInt(reservasConfirmadas.size()));
                }
            case CANCELADAS:
                if(!reservasCanceladas.isEmpty()){
                    return reservasCanceladas.get(random.nextInt(reservasCanceladas.size()));
                }
            case VERIFICADAS:
                if(!reservasVerificadas.isEmpty()){
                    return reservasVerificadas.get(random.nextInt(reservasVerificadas.size()));
                }
        }
        return null;
    }

    public void deleteReserva(Reserva reserva){
        switch (reserva.getRegistro()){
            case PENDIENTE_DE_PAGO:
                synchronized (keyPendientes){
                    reservasPendientes.remove(reserva);
                    break;
                }
            case CONFIRMADAS:
                reservasConfirmadas.remove(reserva);
                break;
            case CANCELADAS:
                reservasCanceladas.remove(reserva);
                break;
            case VERIFICADAS:
                reservasVerificadas.remove(reserva);
                break;
        }
    }
}
