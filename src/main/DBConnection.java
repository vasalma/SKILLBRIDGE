package main;

import Materia.Asignatura;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static Connection conn = null;
    private static final String DB_PATH = "database/skillbridge.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    // Clase interna para manejar los datos de la Materia holiiiii
    public static class Materia {

        public int id;
        public String nombre;
        public String descripcion;

        public Materia(int id, String nombre, String descripcion) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
        }
    }

    public static Connection getConnection() {
        try {
            // Lógica para crear carpeta 'database' si no existe
            File dbDir = new File("database");
            if (!dbDir.exists()) {
                dbDir.mkdir();
                System.out.println("📁 Carpeta 'database' creada.");
            }

            // Conexión
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
                conn.createStatement().execute("PRAGMA busy_timeout = 5000;");
                System.out.println("✅ Conexión establecida con la base de datos SQLite.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con la base de datos: " + e.getMessage());
        }

        return conn;
    }

    // ----------------------------------------------------
    // (Método closeConnection() existente)
    // ----------------------------------------------------
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public static Asignatura registrarAsignaturaEnBD(String id, String nombre, String descripcion) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO materias (id, nombre, descripcion) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                return new Asignatura(id, nombre, descripcion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
