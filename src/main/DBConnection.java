package main;

import Materia.Asignatura;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn = null;
    private static final String DB_PATH = "database/skillbridge.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    // ------------------------------
    // Clase interna Materia
    // ------------------------------
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

    // ------------------------------
    // Conexión
    // ------------------------------
    public static Connection getConnection() {
        try {
            File dbDir = new File("database");
            if (!dbDir.exists()) {
                dbDir.mkdir();
                System.out.println("📁 Carpeta 'database' creada.");
            }

            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
                conn.createStatement().execute("PRAGMA busy_timeout = 5000;");
                System.out.println("✅ Conexión establecida con la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar: " + e.getMessage());
        }
        return conn;
    }

    // ------------------------------
    // Cerrar conexión
    // ------------------------------
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔒 Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Error al cerrar: " + e.getMessage());
        }
    }

    // ------------------------------
    // Registrar asignatura en tabla materias
    // ------------------------------
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
                System.out.println("✔ Asignatura registrada en materias.");
                return new Asignatura(id, nombre, descripcion);
            }
        } catch (Exception e) {
            System.out.println("❌ Error registrando asignatura: " + e.getMessage());
        }
        return null;
    }

    // ------------------------------
    // Guardar asignatura en tabla docente
    // ------------------------------
    public static void guardarAsignaturaDocente(String idDocente, String nombre, String descripcion) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO docente (idDocente, asignatura, descripcion) VALUES (?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idDocente);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);

            stmt.executeUpdate();

            System.out.println("✅ Asignatura asociada al docente: " + idDocente);

        } catch (SQLException e) {
            System.out.println("❌ Error guardando asignatura en docente: " + e.getMessage());
        }
    }

    // ------------------------------
    // Eliminar asignatura SOLO del docente
    // ------------------------------
    public static void eliminarAsignaturaDocente(String idDocente, String nombre, String descripcion) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "DELETE FROM docente WHERE idDocente = ? AND asignatura = ? AND descripcion = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idDocente);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);

            stmt.executeUpdate();

            System.out.println("🗑 Asignatura eliminada SOLO del docente: " + idDocente);

        } catch (SQLException e) {
            System.out.println("❌ Error eliminando asignatura del docente: " + e.getMessage());
        }
    }

}
