package modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OrdenTrabajoModelo {
	
	private static int contador = 500;
	
	private String ordenId;
	private String cliente_id;
	private List<MaquinaModelo> maquinas = new ArrayList<>();
	private String descripcion_falla;
	private String detalleRepId;
	private LocalDateTime fechaIngreso;
	private LocalDateTime fechaRetiro = null;
	private String adminId;
	private String presupuesto_id;
	
	public OrdenTrabajoModelo(
			String ordenId, String cliente_id,
			List<MaquinaModelo> maquinas, 
			String descripcion_falla,
			String detalleRepId,
			LocalDateTime fechaIngreso,
			LocalDateTime fechaRetiro,
			String adminId, String presupuesto_id
			) {
		
		this.ordenId = generarNumeroOrden();
		this.cliente_id = cliente_id;
		this.maquinas = maquinas;
		this.descripcion_falla = descripcion_falla;
	    this.detalleRepId = detalleRepId;
	    this.fechaIngreso = fechaIngreso;
	    this.fechaRetiro = fechaRetiro;
	    this.adminId = adminId;
	    this.presupuesto_id = presupuesto_id;
	}
	
	public String generarNumeroOrden() {
		int bloque = 10 + ((contador - 1) / 1000);
		int numeroDentroBloque = ((contador - 1) % 1000) + 1;
		String codigo = String.format("ORDEN%d%03d", bloque, numeroDentroBloque);
		contador++;
		return codigo;
	}
	
	// 🔹 Getters y Setters
    public String getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(String ordenId) {
        this.ordenId = ordenId;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public List<MaquinaModelo> getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(List<MaquinaModelo> maquinas) {
        this.maquinas = maquinas;
    }

    public String getDescripcion_falla() {
        return descripcion_falla;
    }

    public void setDescripcion_falla(String descripcion_falla) {
        this.descripcion_falla = descripcion_falla;
    }

    public String getDetalleRepId() {
        return detalleRepId;
    }

    public void setDetalleRepId(String detalleRepId) {
        this.detalleRepId = detalleRepId;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDateTime fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPresupuesto_id() {
        return presupuesto_id;
    }

    public void setPresupuesto_id(String presupuesto_id) {
        this.presupuesto_id = presupuesto_id;
    }
    
    public Boolean crearOrden(OrdenTrabajoModelo orden) {
    	return true;
    }
    
    public List<OrdenTrabajoModelo> filtrarPorCriterio(String criterio){
    	
    	List<OrdenTrabajoModelo> listaDeOrdenes = new ArrayList<>();
    	return listaDeOrdenes;
    }
    
    public Boolean asociarOrden(String ordenId) {
    	return true;
    }
    
    public List<OrdenTrabajoModelo> listarOrdenes(){
    	List<OrdenTrabajoModelo> listarOrdenes = new ArrayList<>();
    	return listarOrdenes;
    }
    
    public Boolean actualizarEstado(LocalDateTime fechaRetiro, String adminId, String estado) {
    	return true;
    }
    
    public Boolean agregarMaquinaAlista(MaquinaModelo maquina) {
    	return true;
    }
    
    }
    

