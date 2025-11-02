package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.DBConnection;
import model.Usuario;
import model.Monitor;
import model.Estudiante;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() {
        conn = DBConnection.getConnection();
    }

    // 🔹 Registrar nuevo usuario
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, rol) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getContraseña());
            stmt.setString(4, usuario.getRol());
            stmt.executeUpdate();
            System.out.println("✅ Usuario insertado correctamente");
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Validar credenciales
    public Usuario autenticar(String correo, String contraseña) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // ⚠️ Intentamos leer tanto "id" como "id_usuario"
                int id;
                try {
                    id = rs.getInt("id");
                } catch (SQLException ex) {
                    id = rs.getInt("id_usuario");
                }

                String nombre = rs.getString("nombre");
                String rol = rs.getString("rol");

                if ("Monitor".equalsIgnoreCase(rol)) {
                    Monitor monitor = new Monitor();
                    monitor.setId(id);
                    monitor.setNombre(nombre);
                    monitor.setCorreo(correo);
                    monitor.setContraseña(contraseña);
                    monitor.setRol(rol);
                    monitor.setMateriaAsignada("No asignada");
                    monitor.setHorasSemanales(0);
                    return monitor;
                } else {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setId(id);
                    estudiante.setNombre(nombre);
                    estudiante.setCorreo(correo);
                    estudiante.setContraseña(contraseña);
                    estudiante.setRol(rol);
                    estudiante.setCarrera("Sin definir");
                    estudiante.setSemestre(1);
                    return estudiante;
                }
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
                int id;
                try {
                    id = rs.getInt("id");
                } catch (SQLException ex) {
                    id = rs.getInt("usuario_id");
                }

                Usuario u = new Usuario(
                    id,
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


