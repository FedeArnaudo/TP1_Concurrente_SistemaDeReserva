public class Main {
    public static void main(String[] args) {
        Vuelo vuelo = new Vuelo();

        ProcesoDeReserva procesoDeReserva = new ProcesoDeReserva(vuelo);
        ProcesoDePago procesoDePago = new ProcesoDePago(vuelo);
        ProcesoDeCancelacionValidacion procesoDeCancelacionValidacion = new ProcesoDeCancelacionValidacion(vuelo);



        /**
         * Hilos del proceso 1 - Proceso De Reserva
         */
        Thread thread1 = new Thread(procesoDeReserva, "Thread 1-1");
        Thread thread2 = new Thread(procesoDeReserva, "Thread 1-2");
        Thread thread3 = new Thread(procesoDeReserva, "Thread 1-3");
        Thread thread21 = new Thread(procesoDePago, "Thread 2-1");
        Thread thread22 = new Thread(procesoDePago, "Thread 2-2");
        Thread thread31 = new Thread(procesoDeCancelacionValidacion, "Thread 3-1");
        Thread thread32 = new Thread(procesoDeCancelacionValidacion, "Thread 3-2");
        Thread thread33 = new Thread(procesoDeCancelacionValidacion, "Thread 3-3");
        Thread tread41 = new Thread(procesoDeVerificacion, "Thread 4-1");
        Thread thread41 = new Thread(procesoDeVerificacion, "Thread 4-2");

        thread1.start();
        thread2.start();
        thread3.start();
        thread21.start();
        thread22.start();
        thread31.start();
        thread32.start();
        thread33.start();
        thread41.start();
        thread42.start();
        long inicio = System.currentTimeMillis(); // Registrar el tiempo inicial

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread21.join();
            thread22.join();
            thread31.join();
            thread32.join();
            thread33.join();
            thread41.join();
            thread41.join();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        long fin = System.currentTimeMillis(); // Registrar el tiempo final
        double timeEjection = fin - inicio; // Calcular la diferencia de tiempo

        System.out.println("Tiempo de ejecución: " + (timeEjection / 1000) + " segundos");

        int pendientes = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.PENDIENTE_DE_PAGO).getSize();
        int confirmadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CONFIRMADAS).getSize();
        int canceladas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.CANCELADAS).getSize();
        int verificadas = vuelo.getRegistroDeReservas().getBufferDeReservas(TIPO_DE_RESERVA.VERIFICADAS).getSize();
        int listachecked = procesoDeCancelacionValidacion.cantdechecked();

        System.out.println(listachecked);
        System.out.println("Reservas pendientes: " + pendientes);
        System.out.println("Reservas confirmadas: " + confirmadas);
        System.out.println("Reservas canceladas: " + canceladas);
        System.out.println("Reservas verificadas: " + verificadas);
        System.out.println("Reservas pendientes + confirmadas + canceladas + verificadas: " + (pendientes + confirmadas + canceladas + verificadas));
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
