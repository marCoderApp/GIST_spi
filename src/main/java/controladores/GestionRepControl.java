package controladores;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modelos.GestionRep.DetalleReparacionModelo;
import modelos.GestionRep.OrdenTrabajoModelo;
import modelos.GestionRep.PedidoModelo;
import modelos.GestionRep.PresupuestoModelo;

public class GestionRepControl {

	private boolean ordenCreada;
	private List<OrdenTrabajoModelo> ordenesTrabajo = new ArrayList<>();
	
	public GestionRepControl() {
		this.setOrdenCreada(false);
	}

	
	public boolean registrarOrden(OrdenTrabajoModelo orden) {
        this.ordenesTrabajo.add(orden);
        this.setOrdenCreada(true);
        return true;
    }
	
	public OrdenTrabajoModelo buscarOrdenCriterio(String criterio) {
		for (OrdenTrabajoModelo orden : ordenesTrabajo) {
			if (orden.getOrdenId().equalsIgnoreCase(criterio) || orden.getCliente_id().equalsIgnoreCase(criterio)
					|| orden.getAdminId().equalsIgnoreCase(criterio)) {
				return orden; // Retorna la orden si se encuentra una coincidencia
			}
		}
		return null; // Retorna null si no se encuentra la orden
	}
	
	public DetalleReparacionModelo registrarDetalle(DetalleReparacionModelo detalle) {
		// Lógica para registrar el detalle de reparación
		return detalle; // Retorna el detalle registrado (simulado)
	}
	
	public void crearPresupuesto(PresupuestoModelo presupuesto) {
		// Lógica para crear un presupuesto
	}
	
	public void registrarPedido(PedidoModelo pedido) {
		// Lógica para registrar un pedido
	}
	
	public void obtenerListaOrdenes(PedidoModelo pedido) {
		// Lógica para obtener la lista de órdenes de trabajo
	}
	
	public void obtenerListaOrdenes() {
		// Lógica para obtener la lista de órdenes de trabajo
	}	
	
	public void registrarEntrega(LocalDateTime fechaEntrega, String ordenId) {
		// Lógica para registrar la entrega de una orden de trabajo
	}
	
	//Getters and Setters
	public boolean isOrdenCreada() {
		return ordenCreada;
	}

	public void setOrdenCreada(boolean ordenCreada) {
		this.ordenCreada = ordenCreada;
	}

	public List<OrdenTrabajoModelo> getOrdenesTrabajo() {
		return ordenesTrabajo;
	}

	public void setOrdenesTrabajo(List<OrdenTrabajoModelo> ordenesTrabajo) {
		this.ordenesTrabajo = ordenesTrabajo;
	}
	
	
}
