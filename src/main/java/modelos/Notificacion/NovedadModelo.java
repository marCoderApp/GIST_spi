package modelos.Notificacion;

import java.time.LocalDateTime;

public class NovedadModelo {
	
	private String novedadId;
	private String titulo;
	private String descripcion;
	private LocalDateTime fechaCreacion;
	private String ordenId;
	
	public NovedadModelo(String novedadId, String titulo, String descripcion, LocalDateTime fechaCreacion,
			String ordenId) {
		this.novedadId = novedadId;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.ordenId = ordenId;
	}
	
	public void crearTareaAPartirDeNovedad() {
		// Lógica para crear una tarea a partir de la novedad
	}
	
	//Getters y Setters
	
	public String getNovedadId() {
		return novedadId;
	}
	
	public void setNovedadId(String novedadId) {
		this.novedadId = novedadId;
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
	
	public String getOrdenId() {
		return ordenId;
	}
	
	public void setOrdenId(String ordenId) {
		this.ordenId = ordenId;
	}
	
}
