import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

class HiloLector extends Thread {
    private String nombreArchivo;
    private LinkedList<Persona> listaCompartida;

    public HiloLector(String nombreArchivo, LinkedList<Persona> listaCompartida) {
        this.nombreArchivo = nombreArchivo;
        this.listaCompartida = listaCompartida;
    }

    @Override
    public void run() {
        System.out.println("Iniciando hilo: "+Thread.currentThread().getName()+ " para archivo: " + nombreArchivo);

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int contadorLineas = 0;

            while ((linea = br.readLine()) != null) {// Este bucle lee cada línea del archivo secuencialmente hasta que alcanza el final del archivo. Cada línea se almacena en la variable linea.
                // Lógica para procesar cada cadena y agregarla a la lista compartida
                contadorLineas++;
                System.out.println("Hilo " + Thread.currentThread().getName() + " para archivo " + nombreArchivo + " - Procesando línea " + contadorLineas);


                // Lógica para procesar cada cadena y agregarla a la lista compartida
                int longitud = linea.length();
                String iniciales = obtenerIniciales(linea);
                Persona persona = new Persona(longitud, iniciales, linea);

                /*La lista compartida listaCompartida está siendo modificada en varios hilos simultáneamente.
                El bloque sincronizado asegura que solo un hilo a la vez pueda agregar una persona a la lista, evitando posibles problemas de concurrencia.*/
                synchronized (listaCompartida) {
                    listaCompartida.add(persona);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Hilo para archivo "+ Thread.currentThread().getName() + nombreArchivo +  " terminado.");
    }


    private String obtenerIniciales(String cadena) {
        // Lógica para obtener las iniciales de la cadena
        String[] palabras = cadena.split(" ");
        StringBuilder iniciales = new StringBuilder();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                iniciales.append(palabra.charAt(0));
            }
        }
        return iniciales.toString();
    }
}
