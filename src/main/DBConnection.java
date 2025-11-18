package main;

import Materia.Asignatura;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private static Connection conn = null;
    private static final String DB_PATH = "database/skillbridge.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

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
    // Registrar asignatura en tabla materias (CATÁLOGO)
    // ------------------------------
    public static Asignatura registrarAsignaturaEnBD(String id, String nombre, String descripcion) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO materias (id, nombre, descripcion) VALUES (?, ?, ?)"
        )) {

            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("✔ Asignatura registrada en materias (Catálogo).");
                return new Asignatura(id, nombre, descripcion);
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("❌ Error: La asignatura con ID " + id + " ya existe en el catálogo.");
            } else {
                System.out.println("❌ Error registrando asignatura: " + e.getMessage());
            }
        }
        return null;
    }

    // ------------------------------
    // Guardar asignatura en tabla docente (ASIGNACIÓN)
    // ------------------------------
    /**
     * Inserta la asignación de una materia a un docente.
     *
     * @param idDocente ID del docente.
     * @param nombre Nombre de la materia (usado como FK implícita).
     * @param descripcion Descripción de la materia.
     */
    public static void guardarAsignaturaDocente(String idDocente, String nombre, String descripcion) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO docente (idDocente, asignatura, descripcion) VALUES (?, ?, ?)"
        )) {

            stmt.setString(1, idDocente);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);

            stmt.executeUpdate();

            System.out.println("✅ Asignatura asociada al docente: " + idDocente + " (Nombre: " + nombre + ")");

        } catch (SQLException e) {
            System.out.println("❌ Error guardando asignatura en docente: " + e.getMessage());
        }
    }

    // ------------------------------
    // Eliminar asignatura SOLO del docente (DESASIGNAR)
    // ------------------------------
    /**
     * Elimina el vínculo de la asignatura de la tabla 'docente' (Asignación).
     *
     * @param idDocente ID del docente.
     * @param nombre Nombre de la materia.
     * @param descripcion Descripción de la materia.
     */
    public static void eliminarAsignaturaDocente(String idDocente, String nombre, String descripcion) {
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM docente WHERE idDocente = ? AND asignatura = ? AND descripcion = ?"
        )) {

            stmt.setString(1, idDocente);
            stmt.setString(2, nombre);
            stmt.setString(3, descripcion);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("🗑 Asignatura eliminada SOLO del docente " + idDocente + " (Nombre: " + nombre + ").");
            } else {
                System.out.println("⚠️ Advertencia: No se encontró la asignación para eliminar.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error eliminando asignatura del docente: " + e.getMessage());
        }
    }

    // ------------------------------
    // OBTENER ASIGNATURAS ASIGNADAS AL DOCENTE ACTUAL (¡FILTRADO CORREGIDO!)
    // ------------------------------
    /**
     * Recupera solo las asignaturas que han sido asignadas al ID de docente
     * especificado. La consulta se corrige para unir por Nombre Y Descripción,
     * resolviendo el problema de las asignaturas homónimas.
     *
     * @param idDocente ID del docente logueado.
     * @return Lista de Asignaturas.
     */
    public static List<Asignatura> obtenerAsignaturasDocente(String idDocente) {
        List<Asignatura> asignaturas = new ArrayList<>();

        // 🚀 CONSULTA CORREGIDA: Se añade la DESCRIPCIÓN a la cláusula ON para asegurar que solo una asignatura 
        // de la tabla 'materias' coincida con la asignación del docente.
        String sql = "SELECT m.id, m.nombre, m.descripcion FROM materias m "
                + "INNER JOIN docente d ON m.nombre = d.asignatura AND m.descripcion = d.descripcion "
                + "WHERE d.idDocente = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idDocente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");
                    asignaturas.add(new Asignatura(id, nombre, descripcion));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al obtener asignaturas del docente: " + e.getMessage());
        }
        return asignaturas;
    }
    
        // ... (MÉTODOS EXISTENTES SE MANTIENEN IGUAL) ...

    // ------------------------------
    // ✅ NUEVO MÉTODO: Ejecutar UPDATE genérico
    // ------------------------------
    public static boolean ejecutarUpdate(String sql, Object... params) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Establecer parámetros
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error en ejecutarUpdate: " + e.getMessage());
            return false;
        }
    }

    // ------------------------------
    // ✅ NUEVO MÉTODO: Insertar actividad
    // ------------------------------
    public static boolean insertarActividad(String id, String titulo, String descripcion, String idMateria) {
        String sql = "INSERT INTO actividades (id, titulo, descripcion, idMateria) VALUES (?, ?, ?, ?)";
        return ejecutarUpdate(sql, id, titulo, descripcion, idMateria);
    }

    // ------------------------------
    // ✅ NUEVO MÉTODO: Insertar video
    // ------------------------------
    public static boolean insertarVideo(String id, String titulo, String descripcion, String idMateria) {
        String sql = "INSERT INTO videos (id, titulo, description, idMateria) VALUES (?, ?, ?, ?)";
        return ejecutarUpdate(sql, id, titulo, descripcion, idMateria);
    }

    // ------------------------------
    // ✅ NUEVO MÉTODO: Obtener actividades por materia
    // ------------------------------
    public static List<String[]> obtenerActividadesPorMateria(String idMateria) {
        List<String[]> actividades = new ArrayList<>();
        String sql = "SELECT id, titulo, descripcion FROM actividades WHERE idMateria = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, idMateria);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String[] actividad = {
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("descripcion")
                };
                actividades.add(actividad);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo actividades: " + e.getMessage());
        }
        return actividades;
    }

    // ------------------------------
    // ✅ NUEVO MÉTODO: Obtener videos por materia
    // ------------------------------
    public static List<String[]> obtenerVideosPorMateria(String idMateria) {
        List<String[]> videos = new ArrayList<>();
        String sql = "SELECT id, titulo, description FROM videos WHERE idMateria = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, idMateria);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String[] video = {
                    rs.getString("id"),
                    rs.getString("titulo"),
                    rs.getString("description")
                };
                videos.add(video);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error obteniendo videos: " + e.getMessage());
        }
        return videos;
    }
}

