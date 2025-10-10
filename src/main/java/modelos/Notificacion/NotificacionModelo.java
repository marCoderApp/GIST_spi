package modelos.Notificacion;

import java.time.LocalDateTime;

public class NotificacionModelo {
	
	private String notificacionId;
	private String titulo;
	private String descripcion;
	private String tareaId;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaObjetivo;
	
	public NotificacionModelo(String titulo, String descripcion, String tareaId,
			LocalDateTime fechaCreacion, LocalDateTime fechaObjetivo) {
		this.notificacionId = generarNotificacionId();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.tareaId = tareaId;
		this.fechaCreacion = fechaCreacion;
		this.fechaObjetivo = fechaObjetivo;
	}
	
	public String generarNotificacionId() {
		return "NOTIF-" + System.currentTimeMillis();
	}
	
	
	public String listarNotificacion() {
		return "NotificacionModelo [notificacionId=" + notificacionId + ", titulo=" + titulo + ", descripcion="
				+ descripcion + ", tareaId=" + tareaId + ", fechaCreacion=" + fechaCreacion + ", fechaObjetivo="
				+ fechaObjetivo + "]";
	}
	
	public Boolean estaVencida() {
		return LocalDateTime.now().isAfter(fechaObjetivo);
	}
	
	//Getters
	
	public String getNotificacionId() {
		return notificacionId;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}	
	
	public String getTareaId() {
		return tareaId;
	}	
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public LocalDateTime getFechaObjetivo() {
		return fechaObjetivo;
	}	
	
	//Setters
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public void setFechaObjetivo(LocalDateTime fechaObjetivo) {
		this.fechaObjetivo = fechaObjetivo;
	}
	
	public void setTareaId(String tareaId) {
		this.tareaId = tareaId;
	}
	
	public void setNotificacionId(String notificacionId) {
		this.notificacionId = notificacionId;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}	
	
}
