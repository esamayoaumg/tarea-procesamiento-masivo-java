import java.util.List;

public class Main {

    public static void main(String[] args) {
        // TODO: REEMPLAZA CON TU NOMBRE REAL AQUÍ PARA LA FIRMA VISIBLE EN CONSOLA
        String nombreEstudiante = "Elder Geovani Samayoa Esquivel";
        String archivo = "clientes.csv";

        // REDUCCIÓN POR CONTINGENCIA: Pasamos de 2,000,000 a 100,000 para evitar llenar tu disco C:
        int cantidadClientes = 100_000;


        System.out.println("LABORATORIO: Procesamiento masivo de clientes");
        System.out.println("ESTUDIANTE:" + nombreEstudiante.toUpperCase());
        System.out.println("NOTA: Dataset reducido a 100K por espacio en disco.");
        System.out.println("___________________________________________________________");


        System.out.println("\n[1] Generando archivo de datos...");
        long inicioGeneracion = System.currentTimeMillis();
        GeneradorClientes.generarArchivo(archivo, cantidadClientes);
        long finGeneracion = System.currentTimeMillis();
        System.out.println("Tiempo de generación: " + (finGeneracion - inicioGeneracion) + " ms");
        mostrarMemoria();

        System.out.println("\n___________________________________________________");
        System.out.println("[2] PROCESAMIENTO EN VERSIÓN INEFICIENTE");
        System.out.println("\n___________________________________________________");
        try {
            System.out.println("Cargando TODOS los clientes en memoria...");
            long inicioCarga = System.currentTimeMillis();
            List<Cliente> clientes = ProcesadorMalo.cargarTodosLosClientes(archivo);
            long finCarga = System.currentTimeMillis();

            System.out.println("Clientes cargados en memoria: " + clientes.size());
            System.out.println("Tiempo de carga: " + (finCarga - inicioCarga) + " ms");
            mostrarMemoria();

            System.out.println("\nProcesando con estructura INEFICIENTE (Búsqueda Lineal)...");
            ProcesadorIneficiente.procesar(clientes);
            mostrarMemoria();


            clientes = null;
            System.gc();
        } catch (OutOfMemoryError oom) {
            System.err.println("\n⚠️ ¡COLAPSO! java.lang.OutOfMemoryError en la versión ineficiente.");
            System.err.println("La JVM colapsó por retención masiva de JSONs en la lista.");
        }


        System.out.println("\n--------------------------------------------------");
        System.out.println("[3] PROCESAMIENTO EN VERSIÓN OPTIMIZADA");
        System.out.println("--------------------------------------------------");
        mostrarMemoria();


        ProcesadorOptimizado.procesar(archivo);

        mostrarMemoria();

        System.out.println("\n___________________________________________________");
        System.out.println("FIN DEL PROGRAMA - REVISAR CARPETA /EVIDENCIA");
        System.out.println("ESTUDIANTE: " + nombreEstudiante.toUpperCase());
        System.out.println("\n___________________________________________________");
    }

    private static void mostrarMemoria() {
        Runtime runtime = Runtime.getRuntime();
        long memoriaUsada = runtime.totalMemory() - runtime.freeMemory();
        long memoriaTotal = runtime.totalMemory();
        long memoriaMaxima = runtime.maxMemory();

        System.out.println("Memoria JVM -> Usada: " + (memoriaUsada / 1024 / 1024)
                + " MB | Total: " + (memoriaTotal / 1024 / 1024)
                + " MB | Máxima: " + (memoriaMaxima / 1024 / 1024) + " MB");
    }
}