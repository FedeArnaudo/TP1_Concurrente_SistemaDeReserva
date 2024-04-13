import java.util.ArrayList;

public class ProcesoDeReserva implements Runnable{
    private Vuelo vuelo;
    public ProcesoDeReserva(Vuelo vuelo){
        this.vuelo = vuelo;
    }

    @Override
    public void run() {
        while (vuelo.getMatrizDeAsientos().getCantidadDeAsientosLibres() >= 0){
            Asiento asiento = vuelo.getMatrizDeAsientos().getAsientoRandom();
            asiento.setIdThread(Thread.currentThread().getName());
            if(asiento.getIdThread() != null && asiento.getIdThread().equals(Thread.currentThread().getName()) && asiento.getEstado().equals(ESTADO.LIBRE)){
                reservar(asiento);
                asiento.setIdThread(Thread.currentThread().getName());
                System.out.println("Asiento número: " + asiento.getNumeroAsiento());
            }
        }
        /*int i = 0;
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
        }*/
    }
    public void reservar(Asiento asiento){
        vuelo.getRegistros().getReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).addReserva(new Reserva(asiento, TIPO_DE_RESERVA.PENDIENTE_DE_PAGO));
        asiento.setEstado(ESTADO.OCUPADO);
    }
}
