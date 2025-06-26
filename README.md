# Proyecto de Automatización de API con Karate DSL

Este proyecto automatiza pruebas de servicios REST utilizando [Karate DSL](https://github.com/karatelabs/karate), empleando Java, JUnit5 y Maven.

---

## 🔧 Requisitos

- **Java 17 o superior** (Karate no es compatible con Java 24 actualmente --> https://adoptium.net/en-GB/temurin/releases/?version=17&os=any&arch=any)
- **Apache Maven 3.8+** (https://maven.apache.org/download.cgi)
- **El proyecto utiliza y configura jUnit5**
- **Node.js** *(opcional, si usas JS en hooks o scripts complementarios)*
- **Visual Studio Code** (recomendado) con extensiones para Java
- **Karate 1.1.0** Recomendado por dependencias del proyecto
- **karate-junit5** Recomendado por dependencias del proyecto
- **Crear variables de sitema** de entorno %JAVA_HOME%\bin, %MAVEN_HOME%\bin, TNS_ADMIN(con la ubicacion del tsnames)
- **Agregar rutas de instlacion en path de sistema**
- **Agregar extension XML / karate (opcional ideal para la versión pagada)**
- **Agregar variable de entorno con key para encryptar y decencryptar "ENV_SECRET_KEY" en entorno de deploy**
- **Este proyecto ocupa conexion a db oracle y sql para el caso del primero subir tsnames.ora como archivo en el caso de azure pipelina "Pipelines > Library > Secure Files" y otorgar permisos**

# Comprobar instalaciones
```
- java: "java --version"
- maven: "mvn -v"
```
---

## 📁 Estructura del Proyecto
```
## 📁 Estructura del Proyecto Karate

```bash
.
├── .github
│   └── workflows
│       └── templates
├── .vscode
├── scripts
│   ├── input
│   └── out-put
├── src
│   ├── java
│   │   ├── test
│   │   └── utils
│   ├── resources
│   │   └── files
│   └── test
│       ├── common
│       ├── content-manager-api
│       │   └── document/post
│       │       ├── auth
│       │       ├── data/payload
│       │       ├── features
│       │       └── schemas
│       └── template-api
│           ├── database/get/features
│           ├── login/post
│           │   ├── data
│           │   ├── features
│           │   └── schemas
│           └── user/post
│               ├── data
│               ├── features
│               └── schemas
├── target
│   ├── karate-reports              # Reportes principales
│   ├── karate-reports_*            # Reportes históricos por timestamp
│   ├── surefire-reports            # Resultados de test
│   └── test-classes                # Clases compiladas
├── .gitignore
├── dockerfile
├── pom.xml
├── karate-config.js
├── KaratePipelineWin.yml
├── karatePipeLinux.yml
├── README.md
├── estructura-proyecto.txt
└── archivos .enc de entorno (env.qa.json.enc, env.devel.json.enc, ...)
```

> Esta estructura organiza el proyecto de pruebas API con Karate DSL, separando claramente la lógica de entorno, los recursos compartidos, y las suites por microservicio/módulo.


```

---
# 🔐 Gestión segura de archivos de entorno en ApiTesting

Este proyecto utiliza archivos `.json` con credenciales y configuraciones sensibles como:

- `env.qa.json`
- `env.devel.json`
- `env.pre-prod.json`

Para proteger esta información sensible y cumplir con buenas prácticas de seguridad y normativas de protección de datos, **estos archivos se encriptan** antes de subirse al repositorio.

# 🔐 Método de encriptación utilizado
En tu proyecto estamos utilizando el siguiente esquema:

AES-256-CBC
AES: Advanced Encryption Standard.

256: Tamaño de clave de 256 bits (clave fuerte).

CBC: Cipher Block Chaining — un modo que encadena bloques y usa un IV (vector de inicialización) aleatorio para cada cifrado.

¿Cómo se aplica?
La clave (ENV_SECRET_KEY) se convierte en una clave binaria de 256 bits con SHA-256.

Se genera un IV aleatorio por cada archivo cifrado.

El IV se adjunta al inicio del archivo .enc, permitiendo el descifrado sin almacenamiento externo de IV.

---

## 📦 Archivos encriptados

Solo los siguientes archivos deben ser versionados (subidos a Git):

- `env.qa.json.enc`
- `env.devel.json.enc`
- `env.pre-prod.json.enc`

Los `.json` planos deben agregarse al `.gitignore`.

---

## ⚙️ Requisitos

- Node.js `>= 16`
- Tener definidos los scripts:
  - `scripts/encrypt-env.js`
  - `scripts/decrypt-env.js`
- Clave segura definida como variable de entorno `ENV_SECRET_KEY`.

---

## 🛠️ Comandos para cifrado y descifrado

| Acción            | Entorno       | Comando                                                                 |
|-------------------|---------------|-------------------------------------------------------------------------|
| **Cifrar archivos**     | PowerShell    | `$env:ENV_SECRET_KEY = "clave-secreta"; node scripts/encrypt-env.js`  |
|                       | Bash / Linux | `ENV_SECRET_KEY="clave-secreta" node scripts/encrypt-env.js`          |
| **Descifrar archivos** | PowerShell    | `$env:ENV_SECRET_KEY = "clave-secreta"; node scripts/decrypt-env.js`  |
|                       | Bash / Linux | `ENV_SECRET_KEY="clave-secreta" node scripts/decrypt-env.js`          |

> 📝 Reemplaza `"clave-secreta"` por tu clave real del entorno (`qa`, `devel`, `pre-prod`).

> 📝 !Importante, se debe ejecutar una parte del comando primero y luego la siguiente:

    > 1.- `$env:ENV_SECRET_KEY = "clave-secreta"; `
    > 2.-  `node scripts/encrypt-env.js `


📌 La clave **nunca debe subirse al repositorio**. Debe definirse como variable local o en Azure DevOps como `ENV_SECRET_KEY` protegida.


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

## Crear schemas para validar en los escenarios

```bash
# 1.- Debemos copiar nuestro response a convertir en
scripts\input\input-response.json

# 2.- Ejecutar segun terminal

# powershell
scripts/generate-schema.bat   

# bash
bash scripts/generate-schema.sh

# 3.- Validar la salida del schema en:
scripts\out-put\schema.json

# 4.- llevar el archivo de salida a la api que se desea trabajar
# Renombrar el archivo segun sea el caso
ej: 

"src\test\content-manager-api\document\post\schemas\sucessful-response.json"
```


## Ejecuciones Manuales desde CLI powerShell

```bash
# Todos los tests - "-Dsurefire.printSummary=false" imprime en consola un log mas limpio
mvn test "-Dtest=test.RunAllTests" "-Dkarate.env=qa" "-Dsurefire.printSummary=false" 

# Solo ContentApi
mvn test "-Dtest=test.RunContentApi" "-Dkarate.env=devel" "-Dsurefire.printSummary=false" 

# Ejecutar ContentApi mediante un TAG
mvn test "-Dtest=test.RunContentApi" "-Dkarate.env=devel" "-Dkarate.options=--tags @CONTENT-MANAGER" "-Dsurefire.printSummary=false"

# Solo Template Api
mvn test "-Dtest=test.RunTemplateApi" "-Dkarate.env=qa" "-Dsurefire.printSummary=false" 


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
- Azure Devops

Simplemente ejecuta los comandos Maven dentro del job correspondiente.

---

## 👨‍💻 Autor

Proyecto desarrollado y mantenido por **GCM**.