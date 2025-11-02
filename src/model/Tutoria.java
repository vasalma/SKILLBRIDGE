package model;

public class Tutoria {
    private int id;
    private int materiaId;
    private int monitorId;
    private String fecha;
    private String sala;
    private String video;

    public Tutoria() {}

    public Tutoria(int id, int materiaId, int monitorId, String fecha, String sala, String video) {
        this.id = id;
        this.materiaId = materiaId;
        this.monitorId = monitorId;
        this.fecha = fecha;
        this.sala = sala;
        this.video = video;
    }

    public Tutoria(int materiaId, int monitorId, String fecha, String sala, String video) {
        this.materiaId = materiaId;
        this.monitorId = monitorId;
        this.fecha = fecha;
        this.sala = sala;
        this.video = video;
    }

    // 🔹 Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMateriaId() { return materiaId; }
    public void setMateriaId(int materiaId) { this.materiaId = materiaId; }

    public int getMonitorId() { return monitorId; }
    public void setMonitorId(int monitorId) { this.monitorId = monitorId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public String getVideo() { return video; }
    public void setVideo(String video) { this.video = video; }

    @Override
    public String toString() {
        return "Tutoria [Materia ID=" + materiaId + ", Fecha=" + fecha + "]";
    }
}

