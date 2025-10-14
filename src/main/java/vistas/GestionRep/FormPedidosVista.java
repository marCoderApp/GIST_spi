package vistas.GestionRep;

import java.time.LocalDateTime;

import modelos.GestionRep.EstadoPedido;
import modelos.GestionRep.PedidoModelo;

public class FormPedidosVista {

	private String presupuestoId;
	private LocalDateTime fechaCreacion;
	private String repuestos;
	private String ordenTrabajoId;
	private String adminNombre;
	private float total;
	private String estado;
	
	public FormPedidosVista(String presupuestoId, LocalDateTime fechaCreacion, String repuestos, String ordenTrabajoId,
			String adminNombre, float total, String estado) {
		// Constructor
		this.presupuestoId = presupuestoId;
		this.fechaCreacion = fechaCreacion;
		this.repuestos = repuestos;
		this.ordenTrabajoId = ordenTrabajoId;
		this.adminNombre = adminNombre;
		this.total = total;
		this.estado = estado;
	}
	
	//Getters y Setters
	
	public String getPresupuestoId() {
		return presupuestoId;
	}
	
	public void setPresupuestoId(String presupuestoId) {
		this.presupuestoId = presupuestoId;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getRepuestos() {
		return repuestos;
	}
	
	public void setRepuestos(String repuestos) {
		this.repuestos = repuestos;
	}
	
	public String getOrdenTrabajoId() {
		return ordenTrabajoId;
	}
	
	public void setOrdenTrabajoId(String ordenTrabajoId) {
		this.ordenTrabajoId = ordenTrabajoId;
	}
	
	public String getAdminNombre() {
		return adminNombre;
	}
	
	public void setAdminNombre(String adminNombre) {
		this.adminNombre = adminNombre;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public String getEstado() {
        return estado;
    }
	
	public void setEstado(String estado) {
		this.estado = estado;
}
	
public PedidoModelo ingresarPedido() {
	// Lógica para ingresar un nuevo pedido
	return new PedidoModelo(presupuestoId, fechaCreacion, repuestos, ordenTrabajoId, null);
}

public void abrirForm() {
	// Lógica para abrir el formulario de pedidos
	System.out.println("Abriendo formulario de pedidos...");
}

public void modificarPedido(PedidoModelo pedido) {
    // Lógica para modificar un pedido existente
    System.out.println("Modificando pedido: " + pedido);
}

public void cambiarEstado(PedidoModelo pedido, EstadoPedido nuevoEstado) {
    // Lógica para cambiar el estado de un pedido
    pedido.setEstado(nuevoEstado);
    System.out.println("Cambiando estado del pedido: " + pedido + " a " + nuevoEstado);
}

	
}
