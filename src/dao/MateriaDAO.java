package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.DBConnection;
import model.Materia;

public class MateriaDAO {
    private Connection conn;

    public MateriaDAO() {
        conn = DBConnection.getConnection();
    }

    // 🔹 Insertar materia
    public boolean insertarMateria(Materia materia) {
        String sql = "INSERT INTO materias (nombre) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, materia.getNombre());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar materia: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Listar materias
    public List<Materia> listarMaterias() {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT * FROM materias";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Materia(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar materias: " + e.getMessage());
        }
        return lista;
    }
}

