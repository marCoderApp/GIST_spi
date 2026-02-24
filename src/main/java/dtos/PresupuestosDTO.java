package dtos;

import java.time.LocalDateTime;

public class PresupuestosDTO {

    private String id;
    private float total;
    private Boolean conFactura;
    private String maquinaId;
    private String adminId;
    private LocalDateTime fecha_creacion;

    public PresupuestosDTO(String id,
                           float total,
                           Boolean conFactura,
                           String maquinaId,
                           LocalDateTime fecha_creacion,
                           String adminId) {
        this.id = id;
        this.total = total;
        this.conFactura = conFactura;
        this.maquinaId = maquinaId;
        this.fecha_creacion = fecha_creacion;
        this.adminId = adminId;
    }

    public String getId() {
        return id;
    }

    public float getTotal() {
        return total;
    }

    public Boolean getConFactura() {
        return conFactura;
    }

    public String getMaquinaId() {
        return maquinaId;
    }

    public String getAdminId() {
        return adminId;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    public void setConFactura(Boolean conFactura) {
        this.conFactura = conFactura;
    }

    public void setMaquinaId(String maquinaId) {
        this.maquinaId = maquinaId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

}
