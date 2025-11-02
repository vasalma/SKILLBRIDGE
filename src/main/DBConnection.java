package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class DBConnection {
    private static Connection conn = null;
    private static final String DB_PATH = "database/skillbridge.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection getConnection() {
        try {
            // Crear carpeta si no existe
            File dbDir = new File("database");
            if (!dbDir.exists()) {
                dbDir.mkdir();
                System.out.println("📁 Carpeta 'database' creada.");
            }

            // Crear archivo si no existe (SQLite lo genera automáticamente al conectarse)
            File dbFile = new File(DB_PATH);
            boolean dbNuevo = !dbFile.exists();

            // Conexión
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
                conn.createStatement().execute("PRAGMA busy_timeout = 5000;");
                System.out.println("✅ Conexión establecida con la base de datos SQLite.");

                // ⚙️ Crear tablas solo si es nuevo o vacío
                if (dbNuevo) {
                    createTables();
                } else {
                    // También verificamos si existen las tablas por si el archivo estaba vacío
                    createTables();
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar con la base de datos: " + e.getMessage());
        }

        return conn;
    }

    // 🔹 Crear tablas
    private static void createTables() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    correo TEXT UNIQUE NOT NULL,
                    contraseña TEXT NOT NULL,
                    rol TEXT CHECK(rol IN ('estudiante','monitor')) NOT NULL
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS materias (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS tutorias (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    id_monitor INTEGER,
                    id_materia INTEGER,
                    fecha TEXT,
                    hora TEXT,
                    FOREIGN KEY(id_monitor) REFERENCES usuarios(id),
                    FOREIGN KEY(id_materia) REFERENCES materias(id)
                );
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS actividades (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo TEXT NOT NULL,
                    descripcion TEXT,
                    fecha TEXT,
                    id_tutoria INTEGER,
                    FOREIGN KEY(id_tutoria) REFERENCES tutorias(id)
                );
            """);

            System.out.println("🧩 Tablas creadas/verificadas correctamente.");
        } catch (SQLException e) {
            System.out.println("⚠️ Error al crear tablas: " + e.getMessage());
        }
    }

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
}



