package model;

public class Estudiante extends Usuario {
    private String carrera;
    private int semestre;

    public Estudiante() {
        super();
        this.rol = "Estudiante";
    }

    public Estudiante(int id, String nombre, String correo, String contraseña, String carrera, int semestre) {
        super(id, nombre, correo, contraseña, "Estudiante");
        this.carrera = carrera;
        this.semestre = semestre;
    }

    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }
}

