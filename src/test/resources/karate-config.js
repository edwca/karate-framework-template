function fn() {
  var env = karate.env || 'qa';
  karate.log('🏷️ Karate env:', env);

  var configJsonPath = 'classpath:env.' + env + '.json';
  var parsedEnv = karate.read(configJsonPath);

  karate.log('📧 Email:', parsedEnv.reqresLoginEmail);
  karate.log('🔒 Password:', parsedEnv.reqresLoginPassword);
  karate.log('🌐 Base URL:', parsedEnv.BASE_URL);

  var DbUtils = Java.type("utils.DbUtils");
  var dbConfig = {
    user: parsedEnv.DB_USER,
    password: parsedEnv.DB_PASSWORD,
    server: parsedEnv.DB_HOST,
    database: parsedEnv.DB_NAME,
    encrypt: true,
    trustServerCertificate: true
  };

  return {
    BaseUrl: parsedEnv.BASE_URL,
    reqresLoginEmail: parsedEnv.reqresLoginEmail,
    reqresLoginPassword: parsedEnv.reqresLoginPassword,
    db: new DbUtils(dbConfig)
  };
}
