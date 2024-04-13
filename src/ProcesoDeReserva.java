import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ProcesoDeReserva implements Runnable{
    private Vuelo vuelo;
    private int cantidadDeReservas;
    private ArrayList<Integer> cantRes;
    public ProcesoDeReserva(Vuelo vuelo){
        this.vuelo = vuelo;
        cantidadDeReservas = vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS();
        cantRes = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true){
            Asiento asiento = vuelo.getMatrizDeAsientos().getAsiento();
            asiento.setIdThread(Thread.currentThread().getName());
            if(asiento.getIdThread() != null && asiento.getIdThread().equals(Thread.currentThread().getName()) && asiento.getEstado().equals(ESTADO.LIBRE)){
                reservar(asiento);
                setCantidadDeReservas(asiento);
                asiento.setIdThread(Thread.currentThread().getName());
            }
            if(getCantidadDeReservas() == 0){
                break;
            }
        }
        int i = 0;
        synchronized (this){
            i++;
            if(i == 1){
                Collections.sort(cantRes);
                // Crear un archivo de texto
                String nombreArchivo = "Pruebas.txt";

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
                    // Escribir los datos en el archivo
                    for (Integer numero : cantRes) {
                        String print = "";
                        if(numero < 10){
                            print = "00";
                        }
                        else if(numero > 9 && numero < 100){
                            print = "0";
                        }
                        writer.write(print + numero.toString() + " ");

                        if(numero == (i * 10)){
                            writer.newLine();
                            i++;
                        }
                    }

                } catch (IOException e) {
                    System.err.println("Error al escribir en el archivo: " + e.getMessage());
                }
            }
        }
    }
    public void reservar(Asiento asiento){
        vuelo.getRegistros().addReserva(new Reserva(asiento, REGISTRO.PENDIENTE_DE_PAGO));
        asiento.setEstado(ESTADO.OCUPADO);
    }
    public synchronized int getCantidadDeReservas(){
        return cantidadDeReservas;
    }
    public synchronized void setCantidadDeReservas(Asiento asiento){
        /*
        String print = "" + Thread.currentThread().getName() + " - ";
        if(asiento.getNumeroAsiento() < 10){
            print += "00";
        }
        else if(asiento.getNumeroAsiento() > 9 && asiento.getNumeroAsiento() < 100){
            print += "0";
        }
        System.out.println( print + asiento.getNumeroAsiento() + " - " + getCantidadDeReservas());
        */
        cantRes.add(asiento.getNumeroAsiento());
        cantidadDeReservas --;
    }
}
