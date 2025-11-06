package crearLllave;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class Privada {

    // Método para obtener la conexión a la base de datos
    private static Connection conectar() throws Exception {
        String url = "jdbc:sqlite:database/skillbridge.db";
        return DriverManager.getConnection(url);
    }

    // Método para insertar la sllave en la base de datos
    public static void insertarLlave(String nombre, String llave) {
        String sql = "INSERT INTO llaves_acceso (nombre, llave) VALUES (?, ?)";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, llave);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "✅ Llave de acceso insertada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "❌ Error al insertar la llave de acceso.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error: " + e.getMessage());
        }
    }

    // Método principal para solicitar datos y ejecutar la inserción
    public static void main(String[] args) {
        String nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Nombre no puede estar vacío.");
            return;
        }

        String llave = JOptionPane.showInputDialog("Ingrese la llave de acceso:");
        if (llave == null || llave.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Llave no puede estar vacía.");
            return;
        }

        insertarLlave(nombre, llave);
    }
}

