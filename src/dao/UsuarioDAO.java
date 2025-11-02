package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.DBConnection;
import model.Usuario;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() {
        conn = DBConnection.getConnection();
    }

    // 🔹 Crear nuevo usuario
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, rol) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContraseña());
            stmt.setString(4, usuario.getRol());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Buscar usuario por correo y contraseña (login)
    public Usuario autenticar(String correo, String contraseña) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contraseña"),
                    rs.getString("rol")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al autenticar: " + e.getMessage());
        }
        return null;
    }

    // 🔹 Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contraseña"),
                    rs.getString("rol")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }
}

