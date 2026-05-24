import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcesadorOptimizado {

    public static void procesar(String rutaArchivo) {

        Map<String, List<Cliente>> campanias = new HashMap<>();

        long inicio = System.currentTimeMillis();
        int totalProcesados = 0;


        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            reader.readLine(); // Omitir cabecera

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",", 8);
                if (partes.length < 8) continue;

                int id = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                double ingreso = Double.parseDouble(partes[2]);
                String segmento = partes[3];
                String region = partes[4];
                int score = Integer.parseInt(partes[5]);
                double deuda = Double.parseDouble(partes[6]);
                String jsonData = partes[7];

                Cliente cliente = new Cliente(id, nombre, ingreso, segmento, region, score, deuda, jsonData);
                totalProcesados++;

                String tipoCampania = determinarCampania(cliente);

                campanias.computeIfAbsent(tipoCampania, k -> new ArrayList<>()).add(cliente);
            }
        } catch (IOException e) {
            System.out.println("Error en procesamiento optimizado: " + e.getMessage());
        }

        long fin = System.currentTimeMillis();

        System.out.println("\nResumen de campañas generadas (VERSION OPTIMIZADA):");
        System.out.println("Total de campañas diferentes: " + campanias.size());

        for (Map.Entry<String, List<Cliente>> entrada : campanias.entrySet()) {
            System.out.println(entrada.getKey() + ": " + entrada.getValue().size());
        }

        System.out.println("\nTiempo de procesamiento optimizado (HashMap + Stream): " + (fin - inicio) + " ms");
        System.out.println("Total de registros mapeados correctamente: " + totalProcesados);
    }

    private static String determinarCampania(Cliente cliente) {
        String nivelIngreso = (cliente.getIngreso() >= 25000) ? "INGRESO_ALTO" :
                (cliente.getIngreso() >= 15000) ? "INGRESO_MEDIO" :
                        (cliente.getIngreso() >= 10000) ? "INGRESO_BAJO" : "NO_APLICA";

        String nivelScore = (cliente.getScore() >= 800) ? "SCORE_EXCELENTE" :
                (cliente.getScore() >= 600) ? "SCORE_BUENO" :
                        (cliente.getScore() >= 400) ? "SCORE_REGULAR" : "SCORE_RIESGO";

        String nivelDeuda = (cliente.getDeuda() >= 7000) ? "DEUDA_ALTA" :
                (cliente.getDeuda() >= 3000) ? "DEUDA_MEDIA" : "DEUDA_BAJA";

        return cliente.getSegmento() + "_" + cliente.getRegion() + "_" + nivelIngreso + "_" + nivelScore + "_" + nivelDeuda;
    }
}