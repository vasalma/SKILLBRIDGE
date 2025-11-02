package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // 🔗 Ruta de tu base de datos SQLite
    private static final String URL = "jdbc:sqlite:C:/Users/Mi PC/Desktop/BASE  DE DATOS (SKILLBRIDGE)/skillbridge.db";
    private static Connection connection = null;

    // 🔹 Constructor privado (para patrón Singleton)
    private DBConnection() {}

    // 🔹 Método para obtener la conexión
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
                System.out.println("✅ Conexión establecida con la base de datos SQLite.");
            } catch (SQLException e) {
                System.out.println("❌ Error al conectar con la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    // 🔹 Método opcional para cerrar conexión
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.out.println("⚠ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}

