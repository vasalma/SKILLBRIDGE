package back;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.DBConnection;

public class Usuario {

    // 🔹 Atributos
    protected String id; // 🔸 AHORA ES STRING
    protected String nombre;
    protected String apellido;
    protected String correo;
    protected String contraseña;
    protected String rol;
    protected String telefono;

    // 🔹 Constructor vacío
    public Usuario() {}

    // 🔹 Constructor completo
    public Usuario(String id, String nombre, String apellido, String correo, String contraseña, String rol, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
        this.telefono = telefono;
    }

    // 🔹 Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    // 🔹 Verificar si el correo ya existe en la base
    public boolean existeCorreo(String correo) {
        String sql = "SELECT id FROM usuarios WHERE correo = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // true si encuentra el correo

        } catch (SQLException e) {
            System.err.println("⚠️ Error al verificar correo: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Registrar un usuario en SQLite
    public boolean registrarUsuarioSQLite(String id, String nombre, String apellido, String correo, String contraseña, String rol, String telefono) {
        String sql = "INSERT INTO usuarios (id, nombre, apellido, correo, contraseña, rol, telefono) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido);
            pstmt.setString(4, correo);
            pstmt.setString(5, contraseña);
            pstmt.setString(6, rol);
            pstmt.setString(7, telefono);
            
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Validar login usando ID y contraseña (ambos tipo TEXT)
    public boolean validarLoginPorID(String idTexto, String contraseña) {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND contraseña = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idTexto); // ✅ Ya no convertimos a número
            pstmt.setString(2, contraseña);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Guardar datos del usuario logueado
                this.id = rs.getString("id");
                this.nombre = rs.getString("nombre");
                this.rol = rs.getString("rol");
                System.out.println("✅ Login exitoso: " + nombre + " (" + rol + ")");
                return true;
            } else {
                System.out.println("⚠️ Credenciales incorrectas (ID o contraseña)");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al validar login: " + e.getMessage());
            return false;
        }
    }
}
