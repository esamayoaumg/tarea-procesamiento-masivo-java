import java.util.ArrayList;
import java.util.List;

public class ProcesadorIneficiente {

    public static void procesar(List<Cliente> clientes) {
        List<String> tiposCampania = new ArrayList<>();
        List<List<Cliente>> grupos = new ArrayList<>();

        long inicio = System.currentTimeMillis();

        for (Cliente cliente : clientes) {
            String tipo = determinarCampania(cliente);
            int index = -1;

            // BÚSQUEDA LINEAL INEFICIENTE: Recorre una lista para validar duplicados
            for (int i = 0; i < tiposCampania.size(); i++) {
                if (tiposCampania.get(i).equals(tipo)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                tiposCampania.add(tipo);
                List<Cliente> nuevaLista = new ArrayList<>();
                nuevaLista.add(cliente);
                grupos.add(nuevaLista);
            } else {
                grupos.get(index).add(cliente);
            }
        }

        long fin = System.currentTimeMillis();

        System.out.println("\nResumen de campañas generadas (VERSION INEFICIENTE):");
        System.out.println("Total de campañas diferentes: " + tiposCampania.size());

        for (int i = 0; i < tiposCampania.size(); i++) {
            System.out.println(tiposCampania.get(i) + ": " + grupos.get(i).size());
        }

        System.out.println("\nTiempo de procesamiento ineficiente: " + (fin - inicio) + " ms");
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
