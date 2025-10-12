package vistas.GestionRep;

import java.time.LocalDateTime;

import modelos.GestionRep.DetalleReparacionModelo;

public class DetalleRepVista {

	private String detalleRepId;
	private String descripcion;
	private String repuestos;
	private String tecnicoId;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaEstimada;
	private String ordenId;
	private int nivelService;
	private float presupuesto;
	private boolean ordenAsociada;
	
	//Constructor
	
	public DetalleRepVista(String detalleRepId, String descripcion, String repuestos, String tecnicoId,
			LocalDateTime fechaCreacion, LocalDateTime fechaEstimada, String ordenId, String ecnicoId, int nivelService,
			float presupuesto, boolean ordenAsociada) {
		super();
		this.detalleRepId = detalleRepId;
		this.descripcion = descripcion;
		this.repuestos = repuestos;
		this.tecnicoId = tecnicoId;
		this.fechaCreacion = fechaCreacion;
		this.fechaEstimada = fechaEstimada;
		this.ordenId = ordenId;
		this.nivelService = nivelService;
		this.presupuesto = presupuesto;
		this.ordenAsociada = ordenAsociada;
	}
	
	//Getters and Setters
	
	public String getDetalleRepId() {
		return detalleRepId;
	}
	
	public void setDetalleRepId(String detalleRepId) {
		this.detalleRepId = detalleRepId;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getRepuestos() {
		return repuestos;
	}
	
	public void setRepuestos(String repuestos) {
		this.repuestos = repuestos;
	}
	
	public String getTecnicoId() {
		return tecnicoId;
	}
	
	public void setTecnicoId(String tecnicoId) {
		this.tecnicoId = tecnicoId;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public LocalDateTime getFechaEstimada() {
		return fechaEstimada;
	}
	
	public void setFechaEstimada(LocalDateTime fechaEstimada) {
		this.fechaEstimada = fechaEstimada;
	}
	
	public String getOrdenId() {
		return ordenId;
	}
	
	public void setOrdenId(String ordenId) {
		this.ordenId = ordenId;
	}
	
	public int getNivelService() {
		return nivelService;
	}
	
	public void setNivelService(int nivelService) {
		this.nivelService = nivelService;
	}
	
	public float getPresupuesto() {
		return presupuesto;
	}
	
	public void setPresupuesto(float presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	public boolean isOrdenAsociada() {
		return ordenAsociada;
	}
	
	public void setOrdenAsociada(boolean ordenAsociada) {
		this.ordenAsociada = ordenAsociada;
	}
	
	
	public void abrirFormulario() {
		// TODO Auto-generated method stub

	}
	
	public DetalleReparacionModelo ingresarDetalleReparacion() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
