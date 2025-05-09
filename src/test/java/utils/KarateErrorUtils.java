package utils;

public class KarateErrorUtils {

    /**
     * Extrae el mensaje relevante de un error de Karate que contenga código JS fallido.
     * @param errorMessage mensaje completo de error (getMessage())
     * @return línea con formato "01: read(...)" o mensaje corto por defecto
     */
    public static String extractRelevantJsErrorLine(String errorMessage) {
        if (errorMessage == null || errorMessage.isEmpty()) {
            return "❗ Error vacío o desconocido";
        }

        String[] lines = errorMessage.split("\n");

        for (String line : lines) {
            if (line.trim().matches("^\\d{2}:\\s.*")) { // ej: "01: read(...)"
                return line.trim();
            }
        }

        return errorMessage.length() > 100
                ? "❗ Error resumido: " + errorMessage.substring(0, 100) + "..."
                : "❗ Error: " + errorMessage;
    }
}