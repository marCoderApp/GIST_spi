package vistas.Notificacion;

import java.time.LocalDateTime;
import modelos.Notificacion.TareasModelo;

public class TareasVista {

	private String tareaId;
	private String titulo;
	private String descripcion;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaVencimiento;
	private String estado = "Pendiente";
	private String prioridad;
	private String tipo;
	
	public TareasVista(String tareaId, String titulo, String descripcion, LocalDateTime fechaCreacion,
			LocalDateTime fechaVencimiento, String estado, String prioridad, String tipo) {
		// Constructor por defecto
		
		this.tareaId = tareaId;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.fechaVencimiento = fechaVencimiento;
		this.estado = estado;
		this.prioridad = prioridad;
		this.tipo = tipo;
		
	}
	
	public void listarTareas() {
		
	}
	
	public void agregarTarea(TareasModelo datos) {
		
	}
	
	public void finalizarTarea(String tareaId) {

	}
	
	//Getters and Setters
	
	public String getTareaId() {
		return tareaId;
	}
	
	public void setTareaId(String tareaId) {
		this.tareaId = tareaId;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public LocalDateTime getFechaVencimiento() {
		return fechaVencimiento;
	}
	
	public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getPrioridad() {
		return prioridad;
	}
	
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
