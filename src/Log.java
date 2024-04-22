import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log implements Runnable {
    private Vuelo vuelo;
    private final int periodo = 200;
    public Log(Vuelo vuelo){
        this.vuelo = vuelo;
    }

    @Override
    public void run() {
        // Obtener la ubicación del directorio de trabajo actual
        String directorioActual = System.getProperty("user.dir");

        // Definir el nombre del archivo
        String nombreArchivo = "log.txt";

        // Crear una instancia de File para representar el archivo en el directorio actual
        File archivo = new File(directorioActual, nombreArchivo);

        try {
            // Verificar si el archivo ya existe
            if (!archivo.exists()) {
                // Crear el archivo si no existe
                if (archivo.createNewFile()) {
                    System.out.println("Archivo creado: " + archivo.getAbsolutePath());
                } else {
                    System.out.println("No se pudo crear el archivo.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error al crear o escribir en el archivo: " + e.getMessage());
        }

        int contador = 0;
        int cantidadCanceladas = 0;

        // Escribir en el archivo
        FileWriter escritor = null;
        try {
            escritor = new FileWriter(archivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (contador < 10){

            try {
                escritor.write("Cantidad de reservas canceladas" + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize() + "\n");
                escritor.write("Cantidad de reservas aprobadas" + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize() + "\n");
                contador++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(periodo);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            escritor.write("fin");
            escritor.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
