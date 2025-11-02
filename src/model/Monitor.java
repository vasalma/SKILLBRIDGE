package model;

public class Monitor extends Usuario {

    public Monitor() {}

    public Monitor(int id, String nombre, String correo, String contraseña) {
        super(id, nombre, correo, contraseña, "monitor");
    }

    public Monitor(String nombre, String correo, String contraseña) {
        super(nombre, correo, contraseña, "monitor");
    }

    // 🔹 Aquí podrías añadir métodos o atributos específicos del monitor
}

