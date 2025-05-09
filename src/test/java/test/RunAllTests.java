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

class RunAllTests {

  static Results result;

  @Test
  void testAll() {
    result = Runner.path(
        "classpath:apiTemplateName/login/post/features/login.feature",
        "classpath:apiTemplateName/user/post/features/create-user.feature").parallel(1);
  }

  @AfterAll
  static void resumenFinal() throws IOException {
    System.out.println("\n✅ Finalizó la ejecución de pruebas Karate.");
    System.out.printf("✔️  Features pasadas: %d\n", result.getFeaturesPassed());
    System.out.printf("❌ Features fallidas: %d\n", result.getFeaturesFailed());
    System.out.printf("✔️  Escenarios pasados: %d\n", result.getScenariosPassed());
    System.out.printf("❌ Escenarios fallidos: %d\n", result.getScenariosFailed());

    // Mostrar detalles de escenarios fallidos
    for (FeatureResult fr : result.getFeatureResults().toList()) {
      for (ScenarioResult sr : fr.getScenarioResults()) {
        if (sr.isFailed()) {
          System.out.println("\n🚨 ******* Escenario fallido *******:");
          System.out.println("   📌 Nombre: ---->  " + sr.getScenario().getName());
          System.out.println("   🗂  Ubicacion: ----> " + fr.getFeature().getResource().getRelativePath());

          // Uso de utilidad para simplificar mensajes de error
          String errorMessage = sr.getError().getMessage();
          String relevantErrorLine = KarateErrorUtils.extractRelevantJsErrorLine(errorMessage);
          if (relevantErrorLine != null) {
            System.out.println("   ❗ Error resumido: " + relevantErrorLine);
          } else {
            System.out.println("   ❗ Error resumido: " + errorMessage);
          }

        }
      }
    }

    System.out.println("\n📄 Ver reporte: target/karate-reports/karate-summary.html");

    if (!System.getenv().containsKey("GITHUB_ACTIONS")) {
      File report = new File("target/karate-reports/karate-summary.html");
      if (report.exists() && Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(report.toURI());
      }
    }
  }
}
