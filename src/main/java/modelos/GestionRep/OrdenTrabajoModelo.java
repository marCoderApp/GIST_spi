package modelos.GestionRep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controladores.GestionRepControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	private String despacho;
	public OrdenTrabajoModelo(
			String cliente_id, List<MaquinaModelo> maquinas, String descripcion_falla,
			String detalleRepId, LocalDate fechaIngreso, LocalDateTime fechaRetiro,
			String adminId, String presupuesto_id, EstadoOrden estado, String tecnicoId, String despacho
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
	    this.setDespacho(despacho);
	}

    //GENERAR ID DE ORDEN REALIZANDO UNA CONSULTA A LA BASE DE DATOS.
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

    //ACTUALIZA EL ESTADO DE UNA ORDEN DE TRABAJO, RECIBE ESTADO Y ORDEN ID POR PARÁMETRO
    public static Boolean actualizarEstado(String ordenIdParam, EstadoOrden estado) {
    	String sqlActualizar = "UPDATE ORDEN_DE_TRABAJO SET estado = ?"
    			+ " WHERE ORDEN_TRABAJO_ID = ? AND ESTADO = 'Lista'";
    	
    	try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sqlActualizar)){
    		ps.setString(1, estado.getValor());
    		ps.setString(2, ordenIdParam);

			int filasAfectadas = ps.executeUpdate();

	        if (filasAfectadas > 0) {
	            System.out.println("\nEl estado fue actualizado correctamente .\n");
	        } else {
	            System.out.println("\nNo se encontró una orden LISTA con ese ID.\n");
	        }
    		
    	}catch(Exception e){
   		 System.out.println("Error al actualizar la Orden: " + e.getMessage());
		 e.printStackTrace(); 
    	}
    	
    	return true;
    }

    //OBTENER DATOS DE ORDEN BD, REALIZANDO UNA CONSULTA PERSONALIZADA
    public static List<Map<String, Object>> obtenerDatosOrdenBD(String ordenId) {
        List<Map<String,Object>> resultados = new ArrayList<>();
        String consultaSQL = "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.ESTADO, "
                + "C.NOMBRE, C.APELLIDO, OM.MAQUINA_ID, M.TIPO, M.MARCA, M.MODELO, "
                + "N.ID AS NOVEDAD_ID, N.FECHA AS FECHA_NOVEDAD, N.ADMIN_ID, "
                + "NI.ITEMID, NI.COMENTARIOITEM, P.total AS PRESUPUESTO_TOTAL, P.fecha_creacion " +
                "AS PRESUPUESTO_FECHA, P.PRESUPUESTO_ID, P.CON_FACTURA, M.ESTADO_MAQUINA, "
                + "DR.DESCRIPCION, DR.FECHA "
                + "FROM ORDEN_DE_TRABAJO O "
                + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
                + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
                + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
                + "LEFT JOIN NOVEDAD_ITEM NI ON NI.ORDENID = O.ORDEN_TRABAJO_ID "
                + "LEFT JOIN NOVEDADES N ON N.ID = NI.NOVEDADID "
                + "LEFT JOIN PRESUPUESTO P ON P.MAQUINA_ID = M.ID "
                + "LEFT JOIN DETALLEREPARACION DR ON DR.MAQUINA_ID= M.ID "
                + "WHERE O.ORDEN_TRABAJO_ID = ? "
                + "ORDER BY O.ORDEN_TRABAJO_ID";

        try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(consultaSQL)) {
            ps.setString(1, ordenId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> fila = new HashMap<>();
                    fila.put("ORDEN_TRABAJO_ID", rs.getString("ORDEN_TRABAJO_ID"));
                    fila.put("FECHA_INGRESO", rs.getDate("FECHA_INGRESO"));
                    fila.put("ESTADO", rs.getString("ESTADO"));
                    fila.put("NOMBRE", rs.getString("NOMBRE"));
                    fila.put("APELLIDO", rs.getString("APELLIDO"));
                    fila.put("MAQUINA_ID", rs.getString("MAQUINA_ID"));
                    fila.put("TIPO", rs.getString("TIPO"));
                    fila.put("MARCA", rs.getString("MARCA"));
                    fila.put("MODELO", rs.getString("MODELO"));
                    fila.put("ESTADO_MAQUINA", rs.getString("ESTADO_MAQUINA"));
                    fila.put("DESCRIPCION", rs.getString("DESCRIPCION"));
                    fila.put("NOVEDAD_ID", rs.getString("NOVEDAD_ID"));
                    fila.put("FECHA_NOVEDAD", rs.getDate("FECHA_NOVEDAD"));
                    fila.put("ADMIN_ID", rs.getString("ADMIN_ID"));
                    fila.put("ITEMID", rs.getString("ITEMID"));
                    fila.put("COMENTARIOITEM", rs.getString("COMENTARIOITEM"));
                    fila.put("PRESUPUESTO_ID", rs.getString("presupuesto_id"));
                    fila.put("PRESUPUESTO_TOTAL", rs.getDouble("PRESUPUESTO_TOTAL"));
                    fila.put("PRESUPUESTO_FECHA", rs.getTimestamp("PRESUPUESTO_FECHA"));
                    fila.put("CON_FACTURA", rs.getBoolean("CON_FACTURA"));
                    resultados.add(fila);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;

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

	public String getDespacho() {
		return despacho;
	}

	public void setDespacho(String despacho) {
		this.despacho = despacho;
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



}
    

