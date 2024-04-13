public class Main {
    public static void main(String[] args) {
        MatrizDeAsientos matrizDeAsientos = new MatrizDeAsientos();
        Registros registros = new Registros();
        Vuelo vuelo = new Vuelo(matrizDeAsientos, registros);

        ProcesoDeReserva procesoDeReserva = new ProcesoDeReserva(vuelo);
        ProcesoDePago procesoDePago = new ProcesoDePago(vuelo);
        ProcesoDeCancelacionValidacion procesoDeCancelacionValidacion = new ProcesoDeCancelacionValidacion(vuelo);
        ProcesoDeVerificacion procesoDeVerificacion = new ProcesoDeVerificacion(vuelo);


        /**
         * Hilos del proceso 1 - Proceso De Reserva
         */
        Thread thread1 = new Thread(procesoDeReserva, "Thread 1-1");
        Thread thread2 = new Thread(procesoDeReserva, "Thread 1-2");
        Thread thread3 = new Thread(procesoDeReserva, "Thread 1-3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /*
    // Thread por Procesos
        int procesoUno = 3;
        //int procesoDos = 2;
        //int procesoTres = ;
        //int procesoCuatro = ;
        int threadTotales = procesoUno;

        Thread[] allThread = new Thread[threadTotales];

        int i = 0;
        for(; i < procesoUno; i++){
            String name = "Thread 1-" + (i+1);
            Thread thread = new Thread(procesoDeReserva, name);
            allThread[i] = thread;
        }
        /*
        for(; i < (procesoUno + procesoDos); i++){
            String name = "Thread 2-" + (i+1);
            Thread thread = new Thread(procesoDePago, name);
            allThread[i] = thread;
        }
        for(i = 0; i < threadTotales; i++) {
        allThread[i].start();
    }

    long inicio = System.currentTimeMillis(); // Registrar el tiempo inicial

        for(i = 0; i < threadTotales; i++){
        try {
            allThread[i].join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    long fin = System.currentTimeMillis(); // Registrar el tiempo final
    double timeEjection = fin - inicio; // Calcular la diferencia de tiempo

        System.out.println("Tiempo de ejecución: " + (timeEjection / 1000) + " segundos");
     */
}