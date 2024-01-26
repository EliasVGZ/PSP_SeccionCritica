import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Principal {
    public static void main(String[] args) {
        LinkedList<Persona> listaCompartida = new LinkedList<>();
        //La lista hilos se utiliza para almacenar las referencias a los hilos que se lanzan para procesar archivos de texto.
        LinkedList<Thread> hilos = new LinkedList<>();

        if(args.length < 1){
            System.out.println("Se necesita al menos un fichero a procesar");
            System.exit(-1);
        }

        // Lanzar hilos para cada archivo de texto
//        Por cada nombreArchivo en args, creas un nuevo objeto HiloLector, lo agregas a la lista de hilos hilos y luego llamas a start().
//        Esto iniciará un nuevo hilo y ejecutará el método run() de la instancia de HiloLector en ese hilo específico.
//        Cada hilo trabajará de forma independiente, procesando su propio archivo.

        for (String nombreArchivo : args) {
            HiloLector hiloLector = new HiloLector(nombreArchivo, listaCompartida);
            hilos.add(hiloLector);
            hiloLector.start();//todo  Esto invoca el método run del hilo.
        }


        // Esperar a que todos los hilos hayan terminado
        /*Se itera sobre la lista de hilos y se llama al método join() en cada hilo.
        Esto asegura que el hilo principal espere a que cada hilo termine su ejecución antes de continuar.*/

//        for (Thread hilo : hilos) {
//            try {
//                hilo.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


        //Todo, un while que trabaja mientras detecte que hay hilosVivos
        while (hayHilosVivos(hilos)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Ordenar la lista alfabéticamente por la cadena
        listaCompartida.sort((p1, p2) -> p1.cadena.compareTo(p2.cadena));

        // Lógica para volcar la lista en un nuevo archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("salida.txt"))) {
            for (Persona persona : listaCompartida) {
                writer.write(persona.longitud + " " + persona.iniciales + " " + persona.cadena);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Proceso completado. Lista ordenada y volcada en salida.txt.");
    }

    private static boolean hayHilosVivos(LinkedList<Thread> hilos) {
        Iterator<Thread> iterator = hilos.iterator();
        while (iterator.hasNext()) {
            Thread hilo = iterator.next();
            if (hilo.isAlive()) {
                return true; // Si hay al menos un hilo vivo, retorna true
            }
        }
        return false; // Si no hay hilos vivos, retorna false
    }
}
