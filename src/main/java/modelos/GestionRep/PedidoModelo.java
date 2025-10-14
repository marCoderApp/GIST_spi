package modelos.GestionRep;

import java.time.LocalDateTime;
import modelos.GestionRep.EstadoPedido;

public class PedidoModelo {
	
	private String pedidoId;
	private String ordenId = null;
	private LocalDateTime fecha;
	private String repuestos;
	private String adminId;
	private EstadoPedido estado = EstadoPedido.PENDIENTE;
	
	public PedidoModelo(String ordenId, LocalDateTime fecha,
			String repuestos, String adminId,
			EstadoPedido estado) {
		this.pedidoId = generarPedidoId();
		this.ordenId = ordenId;
		this.fecha = fecha;
		this.repuestos = repuestos;
		this.adminId = adminId;
		this.estado = estado;
	}
	
	public String generarPedidoId() {
		return "PED" + System.currentTimeMillis();
	}
	
	public Boolean guardarPedido(PedidoModelo datos) {
		return true;
	}
	
	public void eliminarPedido(String pedidoId) {

	}
	
	public void modificarPedido(String pedidoId) {

	}
	
	public void verpedido(String pedidoId) {

	}
	
	// Getters
	
	public String getPedidoId() {
		return pedidoId;
	}
	
	public String getOrdenId() {
		return ordenId;
	}
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	
	public String getRepuestos() {
		return repuestos;
	}
	
	public String getAdminId() {
		return adminId;
	}
	
	// Setters
	
	public void setPedidoId(String pedidoId) {
		this.pedidoId = pedidoId;
	}
	
	public void setOrdenId(String ordenId) {
		this.ordenId = ordenId;
	}
	
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}	
	
	public void setRepuestos(String repuestos) {
		this.repuestos = repuestos;
	}	
	
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public EstadoPedido getEstado() {
		return estado;
	}

	public void setEstado(EstadoPedido estado) {
		this.estado = estado;
	}
	
}
