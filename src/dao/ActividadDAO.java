package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.DBConnection;
import model.Actividad;

public class ActividadDAO {
    private Connection conn;

    public ActividadDAO() {
        conn = DBConnection.getConnection();
    }

    // 🔹 Insertar actividad
    public boolean insertarActividad(Actividad actividad) {
        String sql = "INSERT INTO actividades (materia_id, titulo, descripcion, archivo, monitor_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, actividad.getMateriaId());
            stmt.setString(2, actividad.getTitulo());
            stmt.setString(3, actividad.getDescripcion());
            stmt.setString(4, actividad.getArchivo());
            stmt.setInt(5, actividad.getMonitorId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar actividad: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Listar actividades por materia
    public List<Actividad> listarActividadesPorMateria(int materiaId) {
        List<Actividad> lista = new ArrayList<>();
        String sql = "SELECT * FROM actividades WHERE materia_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, materiaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Actividad a = new Actividad(
                    rs.getInt("id"),
                    rs.getInt("materia_id"),
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    rs.getString("archivo"),
                    rs.getInt("monitor_id")
                );
                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar actividades: " + e.getMessage());
        }
        return lista;
    }
}

