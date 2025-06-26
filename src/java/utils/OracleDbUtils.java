package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OracleDbUtils {

    private final String url;
    private final String user;
    private final String password;

    public OracleDbUtils(java.util.Map<String, Object> config) {
        // 🔄 Establecer ruta TNS_ADMIN de forma dinámica según entorno
        String tnsAdminEnv = System.getenv("TNS_ADMIN");

        if (tnsAdminEnv != null && !tnsAdminEnv.isEmpty()) {
            System.setProperty("oracle.net.tns_admin", tnsAdminEnv);
            System.out.println("🔐 TNS_ADMIN (Linux/CI): " + tnsAdminEnv);
        } else {
            String fallbackPath = "C:\\ODAC1025_64\\network\\admin";
            System.setProperty("oracle.net.tns_admin", fallbackPath);
            System.out.println("💻 TNS_ADMIN (Windows local): " + fallbackPath);
        }

        this.url = "jdbc:oracle:thin:@" + config.get("connectString"); // Ej: TISCTOS.world
        this.user = config.get("user").toString();
        this.password = config.get("password").toString();

        System.out.println("🧪 ORACLE JDBC URL via TNS: " + this.url);
    }

    public List<Map<String, Object>> query(String sql) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        }
        return results;
    }

}
