package dtos;

import java.time.LocalDateTime;

public class PresupuestosDTO {

    private String id;
    private float total;
    private Boolean conFactura;
    private String nombreMaquina;
    private String maquinaId;
    private String nombreCliente;
    private String apellidoCliente;
    private String adminId;
    private LocalDateTime fecha_creacion;
    private String ordenId;

    public PresupuestosDTO(String id,
                           float total,
                           Boolean conFactura,
                           String nombreMaquina,
                           String maquinaId,
                           String nombreCliente,
                           String apellidoCliente,
                           LocalDateTime fecha_creacion,
                           String adminId,
                           String ordenId) {
        this.id = id;
        this.total = total;
        this.conFactura = conFactura;
        this.nombreMaquina = nombreMaquina;
        this.maquinaId = maquinaId;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.fecha_creacion = fecha_creacion;
        this.adminId = adminId;
        this.ordenId = ordenId;
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

    public String getNombreMaquina() {
        return nombreMaquina;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
    public String getApellidoCliente() {
        return apellidoCliente;
    }
    public String getOrdenId() {
        return ordenId;
    }

    public void setNombreMaquina(String nombreMaquina) {
        this.nombreMaquina = nombreMaquina;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }
    public void setOrdenId(String ordenId) {
        this.ordenId = ordenId;
    }

}
