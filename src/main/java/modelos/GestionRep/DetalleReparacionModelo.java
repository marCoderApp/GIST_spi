package modelos.GestionRep;

import java.time.LocalDateTime;

public class DetalleReparacionModelo {
	
	private String detalleRepId;
	private String descripcion;
	private String repuestos; 
	private String tecnicoId;
	private LocalDateTime fecha;
	private String ordenId;
	private boolean ordenAsociada;
	
	public DetalleReparacionModelo(String detalleRepId, String descripcion,
			String repuestos, String tecnicoId,
			LocalDateTime fecha,
			String ordenId, boolean ordenAsociada) {
		this.detalleRepId = generarDetalleRepId();
		this.descripcion = descripcion;
		this.repuestos = repuestos;
		this.tecnicoId = tecnicoId;
		this.fecha = fecha;
		this.ordenId = ordenId;
		this.ordenAsociada = ordenAsociada;
	}
	
	public String generarDetalleRepId() {
		return "DR" + System.currentTimeMillis();
	}
	
	public Boolean guardarDetalles(DetalleReparacionModelo datos) {
		return true;
	}
	
	public void eliminarDetalle(String detalleRepId) {
		
	}
	
	public void modificarDetalle(String detalleRepId) {
		
	}
	

	// Getters
	public String getDetalleRepId() {
	    return detalleRepId;
	}

	public String getRepuestos() {
	    return repuestos;
	}

	public String getTecnicoId() {
	    return tecnicoId;
	}

	public LocalDateTime getFecha() {
	    return fecha;
	}

	public String getOrdenId() {
	    return ordenId;
	}

	public boolean isOrdenAsociada() {
	    return ordenAsociada;
	}

	// Setters
	public void setDetalleRepId(String detalleRepId) {
	    this.detalleRepId = detalleRepId;
	}

	public void setRepuestos(String repuestos) {
	    this.repuestos = repuestos;
	}

	public void setTecnicoId(String tecnicoId) {
	    this.tecnicoId = tecnicoId;
	}

	public void setFecha(LocalDateTime fecha) {
	    this.fecha = fecha;
	}

	public void setOrdenId(String ordenId) {
	    this.ordenId = ordenId;
	}

	public void setOrdenAsociada(boolean ordenAsociada) {
	    this.ordenAsociada = ordenAsociada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	
	

}
