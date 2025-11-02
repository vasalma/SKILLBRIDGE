package model;

public class Monitor extends Usuario {
    private String materiaAsignada;
    private int horasSemanales;

    public Monitor() {
        super();
        this.rol = "Monitor";
    }

    public Monitor(int id, String nombre, String correo, String contraseña, String materiaAsignada, int horasSemanales) {
        super(id, nombre, correo, contraseña, "Monitor");
        this.materiaAsignada = materiaAsignada;
        this.horasSemanales = horasSemanales;
    }

    public String getMateriaAsignada() { return materiaAsignada; }
    public void setMateriaAsignada(String materiaAsignada) { this.materiaAsignada = materiaAsignada; }

    public int getHorasSemanales() { return horasSemanales; }
    public void setHorasSemanales(int horasSemanales) { this.horasSemanales = horasSemanales; }
}


