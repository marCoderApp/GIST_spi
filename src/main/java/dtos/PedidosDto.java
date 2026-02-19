package dtos;

import modelos.GestionRep.EstadoPedido;

public class PedidosDto {

    public String pedidoId;
    public String fecha;
    public String repuestos;
    public String adminId;
    public String estado = EstadoPedido.PENDIENTE.toString();

    public PedidosDto(String pedidoId, String fecha, String repuestos, String adminId, String estado) {
        this.pedidoId = pedidoId;
        this.fecha = fecha;
        this.repuestos = repuestos;
        this.adminId = adminId;
        this.estado = estado;
    }


    //GETTERS Y SETTERS
    public String getPedidoId() {
        return pedidoId;
    }
    public String getFecha() {
        return fecha;
    }
    public String getRepuestos() {
        return repuestos;
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

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setRepuestos(String repuestos) {
        this.repuestos = repuestos;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }


}
