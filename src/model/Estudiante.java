package model;

public class Estudiante extends Usuario {

    public Estudiante() {
    }

    public Estudiante(int id, String nombre, String correo, String contraseña) {
        super(id, nombre, correo, contraseña, "estudiante");
    }

    public Estudiante(String nombre, String correo, String contraseña) {
        super(nombre, correo, contraseña, "estudiante");
    }

    // 🔹 Aquí podrías añadir métodos específicos del estudiante más adelante
}
