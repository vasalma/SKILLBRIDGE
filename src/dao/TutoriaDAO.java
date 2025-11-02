package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.DBConnection;
import model.Tutoria;

public class TutoriaDAO {
    private Connection conn;

    public TutoriaDAO() {
        conn = DBConnection.getConnection();
    }

    // 🔹 Insertar tutoría
    public boolean insertarTutoria(Tutoria tutoria) {
        String sql = "INSERT INTO tutorias (materia_id, monitor_id, fecha, sala, video) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tutoria.getMateriaId());
            stmt.setInt(2, tutoria.getMonitorId());
            stmt.setString(3, tutoria.getFecha());
            stmt.setString(4, tutoria.getSala());
            stmt.setString(5, tutoria.getVideo());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar tutoría: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Listar tutorías por materia
    public List<Tutoria> listarTutoriasPorMateria(int materiaId) {
        List<Tutoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM tutorias WHERE materia_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, materiaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tutoria t = new Tutoria(
                    rs.getInt("id"),
                    rs.getInt("materia_id"),
                    rs.getInt("monitor_id"),
                    rs.getString("fecha"),
                    rs.getString("sala"),
                    rs.getString("video")
                );
                lista.add(t);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar tutorías: " + e.getMessage());
        }
        return lista;
    }
}

