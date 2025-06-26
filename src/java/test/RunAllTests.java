package test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import com.intuit.karate.core.ScenarioResult;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class RunAllTests {

    static Results result;

    @Test
    void runAllFeatures() {
        result = Runner
                .path("classpath:template-api", "classpath:content-manager-api")
                .tags("~@ignore")
                .parallel(1);

        if (result.getFailCount() > 0) {
            System.err.println("❌ Escenarios fallidos detectados:");

            result.getFeatureResults().forEach(fr -> {
                String path = fr.getFeature().getResource().getRelativePath();
                fr.getScenarioResults().forEach(sr -> {
                    if (sr.getError() != null) {
                        System.err.println("📁 Feature: " + path);
                        System.err.println("  ❌ Escenario: " + sr.getScenario().getName());
                        System.err.println("  🧪 Error: " + sr.getError().getMessage());
                    }
                });
            });

            // Detiene el build y entrega mensaje claro en CI/CD
            fail("❌ Karate report contiene errores. Ver detalles arriba o en el artefacto karate-report.zip");
        }

        System.out.println("✅ Todas las pruebas pasaron exitosamente.");
    }

    private static Map<String, Set<String>> detectarTodosLosIgnorados() {
        Map<String, Set<String>> ignoradosPorFeature = new HashMap<>();

        try {
            Files.walk(Paths.get("src/test"))
                    .filter(path -> path.toString().endsWith(".feature"))
                    .forEach(path -> {
                        Set<String> ignorados = new HashSet<>();
                        try {
                            List<String> lines = Files.readAllLines(path);
                            Pattern scenarioPattern = Pattern.compile("^\\s*(Scenario|Scenario Outline):\\s*(.+)");

                            for (int i = 0; i < lines.size(); i++) {
                                Matcher matcher = scenarioPattern.matcher(lines.get(i));
                                if (matcher.find()) {
                                    String scenarioName = matcher.group(2).trim();
                                    int j = i - 1;
                                    while (j >= 0 && lines.get(j).trim().startsWith("@")) {
                                        if (lines.get(j).contains("@ignore")) {
                                            ignorados.add(scenarioName);
                                            break;
                                        }
                                        j--;
                                    }
                                }
                            }
                            if (!ignorados.isEmpty()) {
                                String relativePath = Paths.get("src/test").relativize(path)
                                        .toString().replace("\\", "/");
                                ignoradosPorFeature.put(relativePath, ignorados);
                            }
                        } catch (IOException e) {
                            System.err.println("⚠️ No se pudo leer el archivo: " + path);
                        }
                    });
        } catch (IOException e) {
            System.err.println("⚠️ No se pudo explorar src/test para features");
        }

        return ignoradosPorFeature;
    }

    @AfterAll
    static void resumenFinal() throws IOException {
        AtomicInteger totalScenarios = new AtomicInteger(0);
        AtomicInteger passedScenarios = new AtomicInteger(0);
        AtomicInteger failedScenarios = new AtomicInteger(0);
        AtomicInteger ignoredScenarios = new AtomicInteger(0);
        Set<String> printedFeatures = new HashSet<>();

        Map<String, List<String>> detalleScenarios = new LinkedHashMap<>();
        Map<String, Set<String>> ignoradosGlobal = detectarTodosLosIgnorados();

        // ✅ Mostrar solo features con escenarios realmente ignorados
        System.out.println("\n🧪 Escenarios ignorados detectados:");
        ignoradosGlobal.forEach((ruta, escenarios) -> {
            if (!escenarios.isEmpty()) {
                System.out.println("\ud83d\udcc1 " + ruta);
                escenarios.forEach(esc -> System.out.println("   \u23ed\ufe0f " + esc));
            }
        });

        result.getFeatureResults().forEach(fr -> {
            String featurePath = fr.getFeature().getResource().getRelativePath().replace("\\", "/");
            Set<String> ignorados = ignoradosGlobal.getOrDefault(featurePath, Collections.emptySet());

            List<ScenarioResult> scenarios = fr.getScenarioResults();
            List<String> detalles = new ArrayList<>();

            int passedLocal = 0;
            int failedLocal = 0;

            for (ScenarioResult sr : scenarios) {
                String nombre = sr.getScenario().getName();

                if (ignorados.contains(nombre)) {
                    detalles.add("\u001B[90m⏭\ufe0f " + nombre + "\u001B[0m");
                    ignoredScenarios.incrementAndGet();
                } else {
                    String status = sr.getError() == null ? "✅" : "❌";
                    detalles.add("   " + status + " " + nombre);
                    if (sr.getError() == null) {
                        passedLocal++;
                    } else {
                        failedLocal++;
                    }
                }
            }

            // Agregar ignorados no ejecutados
            Set<String> yaListados = new HashSet<>();
            for (String detalle : detalles) {
                yaListados.add(detalle.replaceAll("^\\s*(✅|❌|⏭\ufe0f)\\s*", "").trim());
            }
            for (String ign : ignorados) {
                boolean yaExiste = yaListados.stream().anyMatch(e -> e.startsWith(ign));
                if (!yaExiste) {
                    detalles.add("\u001B[90m⏭\ufe0f " + ign + "\u001B[0m");
                    ignoredScenarios.incrementAndGet();
                }
            }

            totalScenarios.addAndGet(passedLocal + failedLocal);
            passedScenarios.addAndGet(passedLocal);
            failedScenarios.addAndGet(failedLocal);
            detalleScenarios.put(featurePath, detalles);
        });

        System.out.println("\n✅ Finalizó la ejecución de pruebas.");

        int total = totalScenarios.get();
        int passed = passedScenarios.get();
        int failed = failedScenarios.get();

        double percentPassed = total > 0 ? (passed * 100.0) / total : 0.0;
        double percentFailed = total > 0 ? (failed * 100.0) / total : 0.0;

        System.out.printf("   ✔️ %% Pruebas pasadas:   %.2f%%\n", percentPassed);
        System.out.printf("   ❌ %% Pruebas fallidas:  %.2f%%\n", percentFailed);

        System.out.println("\n📊 Tabla resumen por Feature:");
        System.out.printf("%-25s %-15s %-35s %-11s %-10s %-10s\n", "API", "Endpoint", "Feature", "Escenarios",
                "✔️ Pasados", "❌ Fallidos");
        System.out.println("=".repeat(110));

        result.getFeatureResults().forEach(fr -> {
            String path = fr.getFeature().getResource().getRelativePath().replace("\\", "/");
            if (!printedFeatures.add(path))
                return;

            String[] parts = path.split("/");
            String api = parts.length > 0 ? parts[0] : "-";
            String endpoint = parts.length > 1 ? parts[1] : "-";
            String feature = parts[parts.length - 1];

            List<ScenarioResult> scenarios = fr.getScenarioResults();
            int passedF = (int) scenarios.stream().filter(sr -> sr.getError() == null).count();
            int failedF = (int) scenarios.stream().filter(sr -> sr.getError() != null).count();
            int totalF = passedF + failedF;

            if (totalF > 0) {
                System.out.printf("%-25s %-15s %-35s %-11d %-10d %-10d\n",
                        api, endpoint, feature, totalF, passedF, failedF);
            }
        });
        System.out.println("\n\ud83e\uddea Tabla de escenarios por Feature:");
        System.out.printf("%-5s %-90s %-80s\n", "ID", "Feature", "Escenarios");
        System.out.println("=".repeat(180));

        AtomicInteger idCounter = new AtomicInteger(1);
        detalleScenarios.forEach((featurePath, escenarios) -> {
            int id = idCounter.getAndIncrement();
            System.out.printf("%-5d %-90s\n", id, featurePath);

            escenarios.forEach(nombreEscenario -> {
                System.out.printf("%-5s %-90s %-80s\n", "", "", nombreEscenario.trim());
            });

            System.out.println("-".repeat(180));
        });
        ;

    }

}
