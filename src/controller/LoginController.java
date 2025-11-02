package controller;

import dao.UsuarioDAO;
import model.Usuario;
import model.Monitor;
import model.Estudiante;
import ui.dashboard;
//import ui.docente;
//import utils.DialogUtil;

public class LoginController {
    private UsuarioDAO usuarioDAO;

    public LoginController() {
        usuarioDAO = new UsuarioDAO();
    }

    /**
     * 🔹 Método principal de login.
     * Valida credenciales y abre la interfaz correspondiente.
     */
    public void login(String correo, String contraseña) {
        Usuario usuario = usuarioDAO.autenticar(correo, contraseña);

        if (usuario != null) {
            System.out.println("✅ Usuario autenticado: " + usuario.getNombre() + " (" + usuario.getRol() + ")");

            // Mostrar ventana correspondiente según el rol
            if (usuario instanceof Monitor) {
                abrirDashboardMonitor((Monitor) usuario);
            } else if (usuario instanceof Estudiante) {
                abrirDashboardEstudiante((Estudiante) usuario);
            } else {
                //DialogUtil.mostrarError("Rol no reconocido. Verifica la base de datos.");
            }

        } else {
            //DialogUtil.mostrarError("❌ Credenciales incorrectas. Inténtalo nuevamente.");
        }
    }

    /**
     * 🔹 Abre la interfaz para estudiantes
     */
    private void abrirDashboardEstudiante(Estudiante estudiante) {
        //dashboard dash = new dashboard(estudiante);
        //dash.setVisible(true);
        System.out.println("🎓 Se abrió el panel de estudiante.");
    }

    /**
     * 🔹 Abre la interfaz para monitores/docentes
     */
    private void abrirDashboardMonitor(Monitor monitor) {
        //docente doc = new docente(monitor);
        //doc.setVisible(true);
        System.out.println("👨‍🏫 Se abrió el panel de docente.");
    }
}


