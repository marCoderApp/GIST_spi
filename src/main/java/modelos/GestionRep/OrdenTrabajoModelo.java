package modelos.GestionRep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import controladores.GestionRepControl;
//import modelos.GestionRep.EstadoOrden;


public class OrdenTrabajoModelo {
	
	private String ordenId;
	private String cliente_id;
	private List<MaquinaModelo> maquinas = new ArrayList<>();
	private String descripcion_falla;
	private String detalleRepId;
	private LocalDate fechaIngreso;
	private LocalDateTime fechaRetiro = null;
	private String adminId;
	private String presupuesto_id;
	private EstadoOrden estado = EstadoOrden.PENDIENTE;
	private String tecnicoId;
	private String observaciones;
	
	public OrdenTrabajoModelo(
			String cliente_id,
			List<MaquinaModelo> maquinas, 
			String descripcion_falla,
			String detalleRepId,
			LocalDate fechaIngreso,
			LocalDateTime fechaRetiro,
			String adminId, String presupuesto_id,
			EstadoOrden estado,
			String tecnicoId
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
	    this.estado = estado;
	    this.tecnicoId = setTecnicoId(tecnicoId);
	}
	
	public String generarNumeroOrden() {
		String sql = "SELECT orden_trabajo_id FROM orden_de_trabajo ORDER BY orden_trabajo_id DESC LIMIT 1";
	    String ultimoId = null;
	    
	    try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            ultimoId = rs.getString("orden_trabajo_id");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    int siguienteNumero = 1;

	    if (ultimoId != null && ultimoId.startsWith("ORDEN")) {
	        try {
	            int numeroActual = Integer.parseInt(ultimoId.substring(5));
	            System.out.println(numeroActual);
	            siguienteNumero = numeroActual + 1;
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }

	    return String.format("ORDEN%05d", siguienteNumero);
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

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate localDate) {
        this.fechaIngreso = localDate;
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

	public EstadoOrden getEstado() {
		return estado;
	}

	public void setEstado(EstadoOrden estado) {
		this.estado = estado;
	}

	public String getTecnicoId() {
		return tecnicoId;
	}

	public String setTecnicoId(String tecnicoId) {
		return this.tecnicoId = tecnicoId;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

    
    }
    

