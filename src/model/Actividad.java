package model;

public class Actividad {
    private int id;
    private int materiaId;
    private String titulo;
    private String descripcion;
    private String archivo;
    private int monitorId;

    public Actividad() {}

    public Actividad(int id, int materiaId, String titulo, String descripcion, String archivo, int monitorId) {
        this.id = id;
        this.materiaId = materiaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.monitorId = monitorId;
    }

    public Actividad(int materiaId, String titulo, String descripcion, String archivo, int monitorId) {
        this.materiaId = materiaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.archivo = archivo;
        this.monitorId = monitorId;
    }

    // 🔹 Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMateriaId() { return materiaId; }
    public void setMateriaId(int materiaId) { this.materiaId = materiaId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getArchivo() { return archivo; }
    public void setArchivo(String archivo) { this.archivo = archivo; }

    public int getMonitorId() { return monitorId; }
    public void setMonitorId(int monitorId) { this.monitorId = monitorId; }

    @Override
    public String toString() {
        return titulo + " - " + descripcion;
    }
}

