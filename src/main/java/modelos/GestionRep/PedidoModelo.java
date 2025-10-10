package modelos.GestionRep;

import java.time.LocalDateTime;

public class PedidoModelo {
	
	private String pedidoId;
	private String ordenId = null;
	private LocalDateTime fecha;
	private String repuestos;
	private String adminId;
	
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
	
}
