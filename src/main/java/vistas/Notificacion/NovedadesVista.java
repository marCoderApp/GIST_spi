package vistas.Notificacion;

import java.time.LocalDateTime;

import modelos.Notificacion.NovedadModelo;
import java.util.*;
import modelos.GestionRep.OrdenTrabajoModelo;


public class NovedadesVista {

	
	private String novedadId;
	private String titulo;
	private String descripcion;
	private LocalDateTime fechaCreacion;
	private List<OrdenTrabajoModelo> ordenesAsociadas = new ArrayList<>();
	 
	public NovedadesVista(String novedadId, String titulo, String descripcion, LocalDateTime fechaCreacion,
			List<OrdenTrabajoModelo> ordenesAsociadas) {
		// Constructor por defecto

		this.novedadId = novedadId;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.setOrdenesAsociadas(ordenesAsociadas);

	}
	
	public void crearTareaAPartirDeNovedad(NovedadModelo novedad) {
		// Lógica para crear una tarea a partir de una novedad
	}
	
	
	//Getters and Setters
	
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
	

	public List<OrdenTrabajoModelo> getOrdenesAsociadas() {
		return ordenesAsociadas;
	}

	public void setOrdenesAsociadas(List<OrdenTrabajoModelo> ordenesAsociadas) {
		this.ordenesAsociadas = ordenesAsociadas;
	}
	
	
}
