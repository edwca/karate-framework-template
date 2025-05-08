function fun() {
  var env = karate.env || 'qa';
  karate.log('🏷️ Karate env:', env);

  var parsedEnv = {};
  try {
    var resourcePath = 'classpath:.env.' + env;
    karate.log('📂 Buscando archivo en:', resourcePath);
    var envText = karate.read(resourcePath);
    var lines = envText.split('\n');

    lines.forEach(function (line) {
      line = line.trim();
      if (line && line.indexOf('=') !== -1) {
        var parts = line.split('=');
        var key = parts[0].trim();
        var value = parts[1].trim().replace(/^["']|["']$/g, '');
        parsedEnv[key] = value;
      }
    });
  } catch (e) {
    karate.log('⚠️ No se pudo leer el archivo .env.' + env + ':', e.message);
  }

  // Logging de variables críticas
  karate.log('📧 Email:', parsedEnv['reqresLoginEmail']);
  karate.log('🔒 Password:', parsedEnv['reqresLoginPassword']);
  karate.log('🌐 Base URL:', parsedEnv['BASE_URL']);

  // Configuración de base de datos
  var DbUtils = Java.type("utils.DbUtils");
  var dbConfig = {
    user: env === "qa" ? "qa_user" : "dev_user",
    password: env === "qa" ? "qa_pass" : "dev_pass",
    server: "localhost",
    database: "karate_test_db",
    encrypt: true,
    trustServerCertificate: true,
  };

  // Configuración final exportada
  return {
    BaseUrl: parsedEnv['BASE_URL'] || 'https://reqres.in/',
    reqresLoginEmail: parsedEnv['reqresLoginEmail'],
    reqresLoginPassword: parsedEnv['reqresLoginPassword'],
    db: new DbUtils(dbConfig),
    // Puedes agregar más propiedades globales aquí si las necesitas
  };
}
