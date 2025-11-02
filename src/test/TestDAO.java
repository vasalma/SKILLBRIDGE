package test;

import dao.UsuarioDAO;
import model.Usuario;

public class TestDAO {
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        // 🔹 Prueba de inserción (registro)
        Usuario nuevo = new Usuario(0, "Valeria", "valeria@correo.com", "12345", "estudiante");
        boolean insertado = dao.insertarUsuario(nuevo);
        System.out.println(insertado ? "✅ Insertado correctamente" : "❌ Error al insertar");

        // 🔹 Prueba de autenticación (login)
        Usuario usuario = dao.autenticar("valeria@correo.com", "12345");
        if (usuario != null) {
            System.out.println("✅ Login correcto. Bienvenida, " + usuario.getNombre());
        } else {
            System.out.println("❌ Credenciales incorrectas.");
        }
    }
}

