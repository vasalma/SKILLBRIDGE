package crearLllave;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Privada {

    // Método para obtener la conexión a la base de datos
    private static Connection conectar() throws Exception {
        String url = "jdbc:sqlite:database/skillbridge.db";
        return DriverManager.getConnection(url);
    }

    // Método para insertar la llave en la base de datos
    public static void insertarLlave(String id, String nombre, String llave) {
        String sqlVerificar = "SELECT * FROM llaves_acceso WHERE id = ? OR llave = ?";
        String sqlInsertar = "INSERT INTO llaves_acceso (id, nombre, llave) VALUES (?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement verificar = conn.prepareStatement(sqlVerificar); PreparedStatement insertar = conn.prepareStatement(sqlInsertar)) {

            // Verificar si ya existe el ID o la llave
            verificar.setString(1, id);
            verificar.setString(2, nombre);
            verificar.setString(3, llave);
            ResultSet rs = verificar.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "⚠️ Ya existe una llave o ID con esos datos. Intente con otros valores.");
                return;
            }

            // Insertar si no hay duplicados
            insertar.setString(1, id);
            insertar.setString(3, llave);

            int filasAfectadas = insertar.executeUpdate();
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
        String id = JOptionPane.showInputDialog("Ingrese el ID del profesor o monitor:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ El ID no puede estar vacío.");
            return;
        }

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

        insertarLlave(id, nombre, llave);
    }
}
