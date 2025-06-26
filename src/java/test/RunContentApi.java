package test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.core.FeatureResult;
import com.intuit.karate.core.ScenarioResult;
import utils.KarateErrorUtils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class RunContentApi {

    static Results result;

    @Test
    void runAllFeatures() {
        // Ejecuta todos los .feature en cualquier carpeta que termine en -api/**/features/
        result = Runner
            .path( "classpath:content-manager-api") // puedes añadir más si agregas otras APIs
            .tags("~@ignore") // opcional: ignora tests con @ignore
            .parallel(1);
    }

    @AfterAll
    static void resumenFinal() throws IOException {
        System.out.println("\n✅ Finalizó la ejecución de pruebas USER.");
        System.out.printf("✔️  Features pasadas: %d\n", result.getFeaturesPassed());
        System.out.printf("❌ Features fallidas: %d\n", result.getFeaturesFailed());
        System.out.printf("✔️  Escenarios pasados: %d\n", result.getScenariosPassed());
        System.out.printf("❌ Escenarios fallidos: %d\n", result.getScenariosFailed());

        for (FeatureResult fr : result.getFeatureResults().toList()) {
            for (ScenarioResult sr : fr.getScenarioResults()) {
                if (sr.isFailed()) {
                    System.out.println("\n🚨 ******* Escenario fallido *******:");
                    System.out.println("   📌 Nombre: ---->  " + sr.getScenario().getName());
                    System.out.println("   🗂  Ubicación: ----> " + fr.getFeature().getResource().getRelativePath());

                    String errorMessage = sr.getError().getMessage();
                    String relevant = KarateErrorUtils.extractRelevantJsErrorLine(errorMessage);
                    System.out.println("   ❗ Error resumido: " + relevant);
                }
            }
        }

        System.out.println("\n📄 Ver reporte: target/karate-reports/karate-summary.html");
        File report = new File("target/karate-reports/karate-summary.html");
        if (report.exists() && Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(report.toURI());
        }
    }
}
