package modelos.Notificacion;

import java.time.LocalDateTime;

public class TareasModelo {
	
	private String tareaId;
	private String titulo;
	private String descripcion;
	private LocalDateTime fechaCreacion;
	private String tipo;
	private String estado;
	
	public TareasModelo(String titulo, String descripcion, LocalDateTime fechaCreacion, String tipo) {
		this.tareaId = generarTareaId();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.tipo = tipo;
	}
	
	public String generarTareaId() {
		// Lógica para generar un ID único para la tarea
		return "TAREA-" + System.currentTimeMillis();
	}
	
	public void listarTareas() {
		// Lógica para listar tareas
	}
	
	public void actualizarEstadoTarea(String nuevoEstado) {
		this.estado = nuevoEstado;
	}
	
	public void agregarTarea(TareasModelo datos) {
		// Lógica para agregar una nueva tarea
	}
	
	public void finalizarTarea() {
		this.estado = "Finalizada";
	}
	
	//Getters y Setters
	
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
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
