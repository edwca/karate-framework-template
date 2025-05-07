function() {
  var env = karate.env || 'qa';
  karate.log('Active environment:', env);

  var config = {
    dbUser: karate.properties['DB_USER'],
    dbPassword: karate.properties['DB_PASSWORD'],
    dbName: karate.properties['DB_NAME'],
    dbHost: karate.properties['DB_HOST']
  };

  if (!config.dbUser || !config.dbPassword || !config.dbHost) {
    karate.log('❌ Faltan variables de entorno requeridas para DB');
    throw 'Configuración de base de datos incompleta';
  }

  return config;
}
