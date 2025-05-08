package utils;

import java.sql.*;

public class DbUtils {

    private final String url;
    private final String user;
    private final String password;

    public DbUtils(java.util.Map<String, Object> config) {
        this.url = "jdbc:sqlserver://" + config.get("server") + ";databaseName=" + config.get("database")
                + ";encrypt=" + config.get("encrypt") + ";trustServerCertificate=" + config.get("trustServerCertificate");
        this.user = config.get("user").toString();
        this.password = config.get("password").toString();
    }

    public String query(String sql) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getString(1);
            }
        }
        return null;
    }
}
