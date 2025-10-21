package modelos.GestionRep;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PresupuestoModelo {

	private String presupuestoId;
	private String ordenId;
	private LocalDateTime fechaCreacion;
	private String adminId;
	private LocalDateTime fechaEstimada;
	private float total;
	private String estado;
	private List<RepuestoModelo> repuestos = new ArrayList<>();


	public PresupuestoModelo(
			String ordenId,
			LocalDateTime fechaCreacion,
			String adminId,
			String detalleRepId,
			LocalDateTime fechaEstimada,
			float total,
			String estado, List<RepuestoModelo> repuestos
			) {
		
		this.presupuestoId = generarPresupuestoId();
		this.ordenId = ordenId;
		this.adminId = adminId;
		this.fechaCreacion = fechaCreacion;
		this.fechaEstimada = fechaEstimada;
		this.total = total;
		this.estado = estado;	
		this.repuestos = repuestos;
	}
	
	public String generarPresupuestoId() {
		return "PRE" + Math.random();
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
