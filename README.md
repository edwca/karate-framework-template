# Proyecto de Automatización de API con Karate DSL

Este proyecto automatiza pruebas de servicios REST utilizando [Karate DSL](https://github.com/karatelabs/karate), empleando Java, JUnit5 y Maven.

---

## 🔧 Requisitos

- **Java 17 o superior** (Karate no es compatible con Java 24 actualmente)
- **Apache Maven 3.8+**
- **Node.js** *(opcional, si usas JS en hooks o scripts complementarios)*
- **Visual Studio Code** (recomendado) con extensiones para Java
- **Karate 1.1.0** Recomendado por dependencias del proyecto
- **karate-junit5** Recomendado por dependencias del proyecto
---

## 📁 Estructura del Proyecto
```
Listado de rutas de carpetas para el volumen OS
El n·mero de serie del volumen es 62C7-8631
C:.
ª   .gitignore
ª   dockerfile
ª   estructura.log
ª   pom.xml
ª   README.md
ª   
+---.github
ª   +---workflows
ª       ª   karate-tests.yml
ª       ª   
ª       +---templates
ª               karate-tests_docker.yml
ª               karate-tests_sh.yml
ª               
+---.vscode
ª       karate-snippets.code-snippets
ª       karate.code-snippets
ª       settings.json
ª       
+---logs
ª       run-login-tests.log
ª       
+---script
ª       run-login-tests.sh
ª       
+---src
ª   +---test
ª       +---apiTemplateName
ª       ª   +---database
ª       ª   ª   +---data
ª       ª   ª   +---feature
ª       ª   ª           all-databases.feature
ª       ª   ª           test-db.feature
ª       ª   ª           
ª       ª   +---login
ª       ª   ª   +---post
ª       ª   ª       +---data
ª       ª   ª       ª       dynamic-data.js
ª       ª   ª       ª       
ª       ª   ª       +---features
ª       ª   ª       ª       login.feature
ª       ª   ª       ª       
ª       ª   ª       +---schemas
ª       ª   ª               login-error.json
ª       ª   ª               login-successful.json
ª       ª   ª               login-unauthorized.json
ª       ª   ª               
ª       ª   +---user
ª       ª       +---post
ª       ª           +---features
ª       ª           ª       create-user.feature
ª       ª           ª       
ª       ª           +---schemas
ª       ª                   create-user-success.json
ª       ª                   
ª       +---auth
ª       ª       authorization-header.txt
ª       ª       
ª       +---java
ª       ª   +---test
ª       ª   ª       RunAllTests.java
ª       ª   ª       RunLoginTests.java
ª       ª   ª       RunUserTests.java
ª       ª   ª       
ª       ª   +---utils
ª       ª           DbUtils.java
ª       ª           KarateErrorUtils.java
ª       ª           
ª       +---resources
ª               env.qa.devel.json
ª               env.qa.json
ª               env.qa.pre-prod.json
ª               karate-config.js
ª               logback-test.xml
ª               simplelogger.properties
ª               
+---target
    +---classes
    +---generated-test-sources
    ª   +---test-annotations
    +---karate-reports
    ª   ª   apiTemplateName.user.post.features.create-user.html
    ª   ª   apiTemplateName.user.post.features.create-user.karate-json.txt
    ª   ª   favicon.ico
    ª   ª   karate-logo.png
    ª   ª   karate-logo.svg
    ª   ª   karate-progress-json.txt
    ª   ª   karate-summary-json.txt
    ª   ª   karate-summary.html
    ª   ª   karate-tags.html
    ª   ª   karate-timeline.html
    ª   ª   
    ª   +---res
    ª           bootstrap.min.css
    ª           bootstrap.min.js
    ª           jquery.min.js
    ª           jquery.tablesorter.min.js
    ª           karate-report.css
    ª           karate-report.js
    ª           
    +---karate-reports_1746812971037
    ª   ª   apiTemplateName.login.post.features.login.html
    ª   ª   apiTemplateName.login.post.features.login.karate-json.txt
    ª   ª   apiTemplateName.user.post.features.create-user.html
    ª   ª   apiTemplateName.user.post.features.create-user.karate-json.txt
    ª   ª   favicon.ico
    ª   ª   karate-logo.png
    ª   ª   karate-logo.svg
    ª   ª   karate-progress-json.txt
    ª   ª   karate-summary-json.txt
    ª   ª   karate-summary.html
    ª   ª   karate-tags.html
    ª   ª   karate-timeline.html
    ª   ª   
    ª   +---res
    ª           bootstrap.min.css
    ª           bootstrap.min.js
    ª           jquery.min.js
    ª           jquery.tablesorter.min.js
    ª           karate-report.css
    ª           karate-report.js
    ª           
    +---karate-reports_1746813133450
    ª   ª   apiTemplateName.login.post.features.login.html
    ª   ª   apiTemplateName.login.post.features.login.karate-json.txt
    ª   ª   apiTemplateName.user.post.features.create-user.html
    ª   ª   apiTemplateName.user.post.features.create-user.karate-json.txt
    ª   ª   favicon.ico
    ª   ª   karate-logo.png
    ª   ª   karate-logo.svg
    ª   ª   karate-progress-json.txt
    ª   ª   karate-summary-json.txt
    ª   ª   karate-summary.html
    ª   ª   karate-tags.html
    ª   ª   karate-timeline.html
    ª   ª   
    ª   +---res
    ª           bootstrap.min.css
    ª           bootstrap.min.js
    ª           jquery.min.js
    ª           jquery.tablesorter.min.js
    ª           karate-report.css
    ª           karate-report.js
    ª           
    +---karate-reports_1746813278651
    ª   ª   apiTemplateName.login.post.features.login.html
    ª   ª   apiTemplateName.login.post.features.login.karate-json.txt
    ª   ª   apiTemplateName.user.post.features.create-user.html
    ª   ª   apiTemplateName.user.post.features.create-user.karate-json.txt
    ª   ª   favicon.ico
    ª   ª   karate-logo.png
    ª   ª   karate-logo.svg
    ª   ª   karate-progress-json.txt
    ª   ª   karate-summary-json.txt
    ª   ª   karate-summary.html
    ª   ª   karate-tags.html
    ª   ª   karate-timeline.html
    ª   ª   
    ª   +---res
    ª           bootstrap.min.css
    ª           bootstrap.min.js
    ª           jquery.min.js
    ª           jquery.tablesorter.min.js
    ª           karate-report.css
    ª           karate-report.js
    ª           
    +---karate-reports_1746814833624
    ª   ª   apiTemplateName.login.post.features.login.html
    ª   ª   apiTemplateName.login.post.features.login.karate-json.txt
    ª   ª   apiTemplateName.user.post.features.create-user.html
    ª   ª   apiTemplateName.user.post.features.create-user.karate-json.txt
    ª   ª   favicon.ico
    ª   ª   karate-logo.png
    ª   ª   karate-logo.svg
    ª   ª   karate-progress-json.txt
    ª   ª   karate-summary-json.txt
    ª   ª   karate-summary.html
    ª   ª   karate-tags.html
    ª   ª   karate-timeline.html
    ª   ª   
    ª   +---res
    ª           bootstrap.min.css
    ª           bootstrap.min.js
    ª           jquery.min.js
    ª           jquery.tablesorter.min.js
    ª           karate-report.css
    ª           karate-report.js
    ª           
    +---karate-reports_1746815289161
    ª   ª   apiTemplateName.login.post.features.login.html
    ª   ª   apiTemplateName.login.post.features.login.karate-json.txt
    ª   ª   apiTemplateName.user.post.features.create-user.html
    ª   ª   apiTemplateName.user.post.features.create-user.karate-json.txt
    ª   ª   favicon.ico
    ª   ª   karate-logo.png
    ª   ª   karate-logo.svg
    ª   ª   karate-progress-json.txt
    ª   ª   karate-summary-json.txt
    ª   ª   karate-summary.html
    ª   ª   karate-tags.html
    ª   ª   karate-timeline.html
    ª   ª   
    ª   +---res
    ª           bootstrap.min.css
    ª           bootstrap.min.js
    ª           jquery.min.js
    ª           jquery.tablesorter.min.js
    ª           karate-report.css
    ª           karate-report.js
    ª           
    +---karate-reports_1746815378255
    ª   ª   apiTemplateName.login.post.features.login.html
    ª   ª   apiTemplateName.login.post.features.login.karate-json.txt
    ª   ª   favicon.ico
    ª   ª   karate-logo.png
    ª   ª   karate-logo.svg
    ª   ª   karate-progress-json.txt
    ª   ª   karate-summary-json.txt
    ª   ª   karate-summary.html
    ª   ª   karate-tags.html
    ª   ª   karate-timeline.html
    ª   ª   
    ª   +---res
    ª           bootstrap.min.css
    ª           bootstrap.min.js
    ª           jquery.min.js
    ª           jquery.tablesorter.min.js
    ª           karate-report.css
    ª           karate-report.js
    ª           
    +---karate-reports_1746815391617
    ª   ª   apiTemplateName.login.post.features.login.html
    ª   ª   apiTemplateName.login.post.features.login.karate-json.txt
    ª   ª   apiTemplateName.user.post.features.create-user.html
    ª   ª   apiTemplateName.user.post.features.create-user.karate-json.txt
    ª   ª   favicon.ico
    ª   ª   karate-logo.png
    ª   ª   karate-logo.svg
    ª   ª   karate-progress-json.txt
    ª   ª   karate-summary-json.txt
    ª   ª   karate-summary.html
    ª   ª   karate-tags.html
    ª   ª   karate-timeline.html
    ª   ª   
    ª   +---res
    ª           bootstrap.min.css
    ª           bootstrap.min.js
    ª           jquery.min.js
    ª           jquery.tablesorter.min.js
    ª           karate-report.css
    ª           karate-report.js
    ª           
    +---karate-reports_1746815404539
    ª   ª   apiTemplateName.login.post.features.login.html
    ª   ª   apiTemplateName.login.post.features.login.karate-json.txt
    ª   ª   favicon.ico
    ª   ª   karate-logo.png
    ª   ª   karate-logo.svg
    ª   ª   karate-progress-json.txt
    ª   ª   karate-summary-json.txt
    ª   ª   karate-summary.html
    ª   ª   karate-tags.html
    ª   ª   karate-timeline.html
    ª   ª   
    ª   +---res
    ª           bootstrap.min.css
    ª           bootstrap.min.js
    ª           jquery.min.js
    ª           jquery.tablesorter.min.js
    ª           karate-report.css
    ª           karate-report.js
    ª           
    +---maven-status
    ª   +---maven-compiler-plugin
    ª       +---testCompile
    ª           +---default-testCompile
    ª                   createdFiles.lst
    ª                   inputFiles.lst
    ª                   
    +---surefire-reports
    ª       TEST-test.RunAllTests.xml
    ª       TEST-test.RunLoginTests.xml
    ª       TEST-test.RunUserTests.xml
    ª       test.RunAllTests.txt
    ª       test.RunLoginTests.txt
    ª       test.RunUserTests.txt
    ª       
    +---test-classes
        ª   env.qa.devel.json
        ª   env.qa.json
        ª   env.qa.pre-prod.json
        ª   karate-config.js
        ª   logback-test.xml
        ª   simplelogger.properties
        ª   
        +---apiTemplateName
        ª   +---database
        ª   ª   +---feature
        ª   ª           all-databases.feature
        ª   ª           test-db.feature
        ª   ª           
        ª   +---login
        ª   ª   +---post
        ª   ª       +---data
        ª   ª       ª       dynamic-data.js
        ª   ª       ª       
        ª   ª       +---features
        ª   ª       ª       login.feature
        ª   ª       ª       
        ª   ª       +---schemas
        ª   ª               login-error.json
        ª   ª               login-successful.json
        ª   ª               login-unauthorized.json
        ª   ª               
        ª   +---user
        ª       +---post
        ª           +---features
        ª           ª       create-user.feature
        ª           ª       
        ª           +---schemas
        ª                   create-user-success.json
        ª                   
        +---auth
        ª       authorization-header.txt
        ª       
        +---test
        ª       RunAllTests.class
        ª       RunLoginTests.class
        ª       RunUserTests.class
        ª       
        +---utils
                DbUtils.class
                KarateErrorUtils.class
                
```

---

### ✅ Comandos de Ejecución

Antes de ejecutar debemos considerar tener la versión de java instalada 
si no existe su variable de entorno seteada podemos a través de powerShell usar:

```bash
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
```

## Importante

> Por defecto karate-config.js esta preparado para trabajar con 
un environment en este caso .qa por lo que cualqueir comando que
no incluya esta información se va a ejecutar contra QA, pero 
si queremos setear otro ambiente debemos usar el parametro
"-Dkarate.env=qa", "-Dkarate.env=devel", "-Dkarate.env=pre-prod"
archivos que podemos encontrar en 

```bash
src\test\resources\env.qa.json
```

## Ejecuciones Manuales desde CLI powerShell

```bash
# Todos los tests - "-Dsurefire.printSummary=false" imprime en consola un log mas limpio
mvn test "-Dtest=test.RunAllTests" "-Dkarate.env=qa" "-Dsurefire.printSummary=false" 

# Solo login
mvn test "-Dtest=test.RunLoginTests" "-Dkarate.env=qa" "-Dsurefire.printSummary=false" 

# Solo user
mvn test "-Dtest=test.RunUserTests" "-Dkarate.env=qa" "-Dsurefire.printSummary=false" 
```

> Asegúrate que las clases `RunLoginTests.java` y `RunUserTests.java` estén ubicadas correctamente bajo `src/test/java/login/post/` y `src/test/java/user/post/` respectivamente.


## Ejecuciones Manuales desde CLI gitBash con script
> En terminal bash ejecutar "bash script/run-login-tests.sh", archivo que contiene los comandos para correr todos los test


---

## ⚙️ Configuración por Entorno (`karate-config.js`)

```javascript
function fn() {
  var env = karate.env || 'qa';
  var config = {
    baseUrl: 'https://api-qa.example.com'
  };

  if (env === 'dev') {
    config.baseUrl = 'https://api-dev.example.com';
  } else if (env === 'prod') {
    config.baseUrl = 'https://api.example.com';
  }

  return config;
}
```

---

## 📄 Reportes de Ejecución

Karate genera reportes HTML automáticos por cada `.feature` en:

```
target/karate-reports/
```

También puedes integrarlo con **Allure**, **ExtentReports**, o exportar datos personalizados si lo necesitas.

---

## 📦 Dependencias en `pom.xml`

```xml
<dependencies>
  <dependency>
    <groupId>com.intuit.karate</groupId>
    <artifactId>karate-junit5</artifactId>
    <version>1.4.1</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <version>12.6.1.jre11</version>
  </dependency>
</dependencies>
```

---

## 🚀 Integración Continua

Este proyecto es apto para CI/CD con:

- GitHub Actions
- GitLab CI
- Jenkins

Simplemente ejecuta los comandos Maven dentro del job correspondiente.

---

## 👨‍💻 Autor

Proyecto desarrollado y mantenido por el equipo de QA de **Detacoop**.