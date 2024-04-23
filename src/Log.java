import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log implements Runnable {
    private final Vuelo vuelo;

    public Log(Vuelo vuelo){
        this.vuelo = vuelo;
    }

    @Override
    public void run() {
        long inicio = 0L;
        long fin = 0L;
        inicio = System.currentTimeMillis(); // Registrar el tiempo inicial

        String directorioActual = System.getProperty("user.dir");

        String nombreArchivo = "log.txt";

        File archivo = new File(directorioActual, nombreArchivo);

        try {
            if (!archivo.exists()) {
                if (archivo.createNewFile()) {
                    System.out.println("Archivo creado: " + archivo.getAbsolutePath());
                } else {
                    System.out.println("No se pudo crear el archivo.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error al crear o escribir en el archivo: " + e.getMessage());
        }

        // Escribir en el archivo
        FileWriter escritor = null;
        try {
            escritor = new FileWriter(archivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (!isProcessActive()){

            try {
                escritor.write("Cantidad de reservas canceladas" + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize() + "\n");
                escritor.write("Cantidad de reservas aprobadas" + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize() + "\n\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                int periodo = 200;
                Thread.sleep(periodo);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            fin = (long) (System.currentTimeMillis() - inicio);
            escritor.write("Cantidad de reservas canceladas: " + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize() + "\n");
            escritor.write("Cantidad de reservas aprobadas: " + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize() + "\n");
            escritor.write("Cantidad de reservas totales: " +
                    "" + (vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize() + vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize()) + "\n");
            escritor.write("tiempo total: " + (fin / 1000) + "s.");
            escritor.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isProcessActive() {
        int cantProcesadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize();
        cantProcesadas += vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize();
        return cantProcesadas == vuelo.getMatrizDeAsientos().getCANTIDAD_MAX_ASIENTOS();
    }
}
