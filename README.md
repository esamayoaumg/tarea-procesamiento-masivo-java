# Tarea: Procesamiento Masivo de clientes

**Estudiante:** Elder Geovani Samayoa Esquivel 
**Curso:** Programación III  
**Año:** 2026  

---

## 1. Descripción del Problema
El sistema bajo análisis requiere procesar y segmentar un volumen masivo de datos de clientes para la asignación de campañas de marketing dinámicas. Cada registro incluye atributos financieros, geográficos y un campo estructurado de gran peso (`jsonData`), el cual simula una carga real de base de datos (`CLOB` o documentos embebidos NoSQL).

La arquitectura e implementación inicial provista presentaba dos fallas críticas de diseño que impedían la escalabilidad del sistema:
1. **Carga Masiva Total en Memoria (Eager Loading):** Intentar instanciar y retener la totalidad de los objetos en una lista dinámica (`ArrayList`), provocando una alta presión en la Heap de la Java Virtual Machine (JVM).
2. **Clasificación mediante Búsqueda Lineal:** Realizar una comparación secuencial uno a uno dentro de bucles anidados para la agrupación de campañas, lo que elevaba exponencialmente el tiempo de cómputo.

---

## 2. Explicación de la Versión Ineficiente

### Carga de Clientes en Memoria
Al leer el archivo físico, la clase `ProcesadorMalo` mapea cada fila del CSV en un objeto `Cliente` y lo añade directamente a un `List<Cliente>`. Dado que la columna `jsonData` acarrea una gran cantidad de caracteres aleatorios (~4 KB por fila), la acumulación masiva de estas cadenas de texto satura la memoria RAM. Esto degrada el rendimiento de la computadora debido a la ejecución constante del Recolector de Basura (*Garbage Collection Overhead*) o detiene el programa por un error fatal de desbordamiento (`java.lang.OutOfMemoryError`).

### Búsqueda Lineal Ineficiente
Para clasificar cada cliente en una campaña de marketing específica, el software original recorre de forma anidada una lista de categorías preexistentes:
```java
for (int i = 0; i < tiposCampania.size(); i++) {
    if (tiposCampania.get(i).equals(tipo)) {
        index = i;
        break;
    }
}
