# Proyecto de Automatización de API con Karate DSL

Este proyecto automatiza pruebas de servicios REST utilizando [Karate DSL](https://github.com/karatelabs/karate), empleando Java, JUnit5 y Maven.

---

## 🔧 Requisitos

- **Java 17 o superior** (Karate no es compatible con Java 24 actualmente)
- **Apache Maven 3.8+**
- **Node.js** *(opcional, si usas JS en hooks o scripts complementarios)*
- **Visual Studio Code** (recomendado) con extensiones para Java

---

## 📁 Estructura del Proyecto

```
src/
├── test/
│   ├── java/
│   │   └── test/
│   │       └── RunAllTests.java
│   │       └── login/post/RunLoginTests.java
│   │       └── user/post/RunUserTests.java
│   ├── apiTemplateName/
│   │   ├── login/
│   │   │   └── post/
│   │   │       └── features/
│   │   │           └── login.feature
│   │   └── user/
│   │       └── post/
│   │           └── features/
│   │               └── create-user.feature
├── karate-config.js
└── README.md
```

---

### ✅ Comandos de Ejecución

Version de java a setear en CLI powerShell:

$env:JAVA_HOME="C:\Program Files\Java\jdk-17.0.12"

```bash
# Todos los tests
mvn test -D"test=test.RunAllTests" -D "karate.env=qa"

# Solo login
mvn test -Dtest=login.post.RunLoginTests -Dkarate.env=qa

# Solo user
mvn test -Dtest=user.post.RunUserTests -Dkarate.env=qa
```

> Asegúrate que las clases `RunLoginTests.java` y `RunUserTests.java` estén ubicadas correctamente bajo `src/test/java/login/post/` y `src/test/java/user/post/` respectivamente.

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