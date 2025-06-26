function fn() {
  var env = karate.env || 'qa';
  karate.log('🏷️ Ambiente ejecutado env:', env);
  var parsedEnv = karate.read('classpath:env.' + env + '.json');

  var OracleDbUtils = Java.type("utils.OracleDbUtils");
  var SqlServerUtils = Java.type("utils.SqlServerUtils");

  var oracleDb = new OracleDbUtils({
    user: parsedEnv.ORACLE_USER,
    password: parsedEnv.ORACLE_PASSWORD,
    connectString: parsedEnv.ORACLE_CONNECTION_STRING
  });

  var sqlDb = new SqlServerUtils({
    user: parsedEnv.SQL_DB_USER,
    password: parsedEnv.SQL_DB_PASSWORD,
    server: parsedEnv.SQL_DB_HOST,
    database: parsedEnv.SQL_DB_NAME,
    encrypt: true,
    trustServerCertificate: true
  });

  return {
    BaseUrl: parsedEnv.BASE_URL,
    BaseUrlContent: parsedEnv.BASE_URL_CONTENT,
    ContentRedPathFile: parsedEnv.CONTENT_READ_FILE,
    reqresLoginEmail: parsedEnv.reqresLoginEmail,
    reqresLoginPassword: parsedEnv.reqresLoginPassword,
    databases: {
      oracle: oracleDb,
      sqlserver: sqlDb
    }
  };
}
