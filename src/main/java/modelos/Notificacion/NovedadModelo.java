package modelos.Notificacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import controladores.GestionRepControl;

public class NovedadModelo {
	
	private String novedadId;
	private String titulo;
	private String descripcion;
	private LocalDateTime fechaCreacion;
	private String ordenId;
    private GestionRepControl gestionRepControl = new GestionRepControl();
	
	public NovedadModelo(String titulo, String descripcion, LocalDateTime fechaCreacion,
			String ordenId) {
		this.novedadId = generarNovedadId();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.ordenId = ordenId;
	}
	
	public String generarNovedadId() {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String idNovedad = "NVD-" + uuid; // Ej: MAQ-3F9A1B2C
        return idNovedad;
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
