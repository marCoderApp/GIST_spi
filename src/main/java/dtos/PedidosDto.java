package dtos;

import modelos.GestionRep.EstadoPedido;

import java.time.LocalDateTime;

public class PedidosDto {

    public String id;
    public LocalDateTime fecha;
    public String adminId;
    public String estado = EstadoPedido.PENDIENTE.toString();

    public PedidosDto(String id, LocalDateTime fecha, String adminId, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.adminId = adminId;
        this.estado = estado;
    }


    //GETTERS Y SETTERS
    public String getId() {
        return id;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public String getAdminId() {
        return adminId;
    }
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = LocalDateTime.now();
    }

    public void setId(String pedidoId) {
        this.id = pedidoId;
    }


}
