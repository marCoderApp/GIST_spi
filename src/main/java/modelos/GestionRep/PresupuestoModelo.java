package modelos.GestionRep;

import java.time.LocalDateTime;

public class PresupuestoModelo {

	private String presupuestoId;
	private String ordenId;
	private LocalDateTime fechaCreacion;
	private String detalleRepId;
	private String adminId;
	private LocalDateTime fechaEstimada;
	private float total;
	private String estado;

	public PresupuestoModelo(String presupuestoId,
			String ordenId,
			LocalDateTime fechaCreacion,
			String adminId,
			String detalleRepId,
			LocalDateTime fechaEstimada,
			float total,
			String estado
			) {
		
		this.presupuestoId = presupuestoId;
		this.ordenId = ordenId;
		this.adminId = adminId;
		this.fechaCreacion = fechaCreacion;
		this.detalleRepId = detalleRepId;
		this.fechaEstimada = fechaEstimada;
		this.total = total;
		this.estado = estado;		
	}
	
	public String getPresupuestoId() {
        return presupuestoId;
    }

    public void setPresupuestoId(String presupuestoId) {
        this.presupuestoId = presupuestoId;
    }

    public String getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(String ordenId) {
        this.ordenId = ordenId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getDetalleRepId() {
        return detalleRepId;
    }

    public void setDetalleRepId(String detalleRepId) {
        this.detalleRepId = detalleRepId;
    }

    public LocalDateTime getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(LocalDateTime fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Boolean guardarPresupuesto(PresupuestoModelo presupuesto) {
    	
    	return true;
    	
    }
    
    public Double calcularTotal() {
    	return 1.000001;
    }
    
    public void aprobar() {
    	
    }
    
    public void rechazar() {
    	
    }
    
    public Boolean estaAprobado() {
    	return true;
    }
    
    public String obtenerResumen() {
    	return "Hello World";
    }
	
}
