package vistas.GestionRep;

import java.time.LocalDateTime;
import 	modelos.GestionRep.PresupuestoModelo;

public class FormPresupVista {
	
	private String presupuestoId;
	private LocalDateTime fechaCreacion;
	private String detalleReparacion;
	private String ordenTrabajoId;
	private float total;
	
	public FormPresupVista(String presupuestoId, LocalDateTime fechaCreacion, String detalleReparacion,
			String ordenTrabajoId, float total) {
		super();
		this.presupuestoId = presupuestoId;
		this.fechaCreacion = fechaCreacion;
		this.detalleReparacion = detalleReparacion;
		this.ordenTrabajoId = ordenTrabajoId;
		this.total = total;
	}
	
	public PresupuestoModelo ingresarPresupuesto() {
		return new PresupuestoModelo(presupuestoId, fechaCreacion, detalleReparacion, ordenTrabajoId, fechaCreacion, total, detalleReparacion);
	}
	
	public void abrirFormulario() {
		// Lógica para abrir el formulario de presupuesto
	}
	
	public void mostrarPresupuesto(float total) {
		// Lógica para mostrar el presupuesto
	}
	
	public boolean confirmarPresupuesto() {
		// Lógica para confirmar el presupuesto
		return true;
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
	
	public String getDetalleReparacion() {
		return detalleReparacion;
	}
	
	public void setDetalleReparacion(String detalleReparacion) {
		this.detalleReparacion = detalleReparacion;
	}
	
	public String getOrdenTrabajoId() {
		return ordenTrabajoId;
	}
	
	public void setOrdenTrabajoId(String ordenTrabajoId) {
		this.ordenTrabajoId = ordenTrabajoId;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	

}
