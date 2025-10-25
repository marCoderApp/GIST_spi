package vistas.GestionRep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import controladores.GestionRepControl;
import controladores.PersonalControl;
import modelos.Personal.TecnicoModelo;
import modelos.GestionRep.*;	


public class OrdenTrabajoVista {

	private String id;
	private LocalDateTime fechaCreacion;
	private String estado;
	private String descripcion;
	private TecnicoModelo tecnicoAsignado;
	private ClienteModelo cliente;
	private String detalleRep;
	private String mensajeExito;
	private String mensajeError;
	private FormOrdenTrabajoVista formOrdenTrabajoVista = new FormOrdenTrabajoVista(null);
	
	public OrdenTrabajoVista(String id, LocalDateTime fechaCreacion, String estado, String descripcion,
			TecnicoModelo tecnicoAsignado, ClienteModelo cliente, String detalleRep, String mensajeExito,
			String mensajeError) {
		this.id = "";
		this.fechaCreacion = LocalDateTime.now();
		this.estado = "";
		this.descripcion = "";
		this.tecnicoAsignado = null;
		this.cliente = null;
		this.detalleRep = "";
		this.mensajeExito = "";
		this.mensajeError = "";
	}
	
	public void opcionCrearOrden() {
		ingresarDatos();
	}
	
	public void ingresarDatos() {
		Scanner scanner = new Scanner(System.in);
		GestionRepControl gestionRepControl = new GestionRepControl();
		OrdenTrabajoModelo orden = new OrdenTrabajoModelo(null, null, null, null, null, null, null, null, null, null, null);
		
		System.out.println("-----------------------------");
		System.out.println("CREAR NUEVA ORDEN DE TRABAJO");
		System.out.println("-----------------------------");
		gestionRepControl.elegirCliente();
		String clienteId = GestionRepControl.clienteIdGestionRep;
		System.out.println("Ingrese la descripción de la falla");
		String descripcion_falla = scanner.nextLine();
		String tecnicoId = PersonalControl.tecnicoIdPersonalControl;
		String adminId = PersonalControl.adminIdPersonalControl;
		
		orden.setCliente_id(clienteId);
		orden.setDescripcion_falla(descripcion_falla);
		orden.setTecnicoId(tecnicoId);
		orden.setAdminId(adminId);
		orden.setEstado(EstadoOrden.PENDIENTE);
		
	    /*LocalDate fecha = (orden.getFechaIngreso() != null)
                ? orden.getFechaIngreso()
                : LocalDate.now();*/

		
		/* System.out.println("\n=== DATOS DE LA ORDEN A INSERTAR ===");
	        System.out.println("Orden ID: " + orden.getOrdenId());
	        System.out.println("Cliente ID: " + GestionRepControl.clienteIdGestionRep);
	        System.out.println("Descripción: " + orden.getDescripcion_falla());
	        System.out.println("Fecha creación: " + fecha);
	        System.out.println("Estado: " + orden.getEstado().getValor());
	        System.out.println("Admin ID: " + PersonalControl.adminIdPersonalControl);
	        System.out.println("Técnico ID: " + PersonalControl.tecnicoIdPersonalControl);
	        System.out.println("=====================================\n");*/
		
		gestionRepControl.insertarOrdenBase(orden);
		
		System.out.println("Maquinas");
		List<MaquinaModelo> maquinas = gestionRepControl.seleccionarMaquinas(orden);
		for (MaquinaModelo maquina : maquinas) {
			System.out.println(" - " + maquina.getTipo() + " - " + maquina.getMarca() + " (ID: " + maquina.getMaquinaId() + ")");
		}
		
			System.out.println("Ingrese Observaciones");
			String observaciones = scanner.nextLine();
			
			orden.setObservaciones(observaciones);
			
			guardarCambios(orden, observaciones);
			
			if(GestionRepControl.chequearIdOrden(orden.getOrdenId())) {
				System.out.println("La Orden de Trabajo ha sido agregada correctamente");
			}else {
				System.out.println("Se ha producido un error.");
			}	
	}
	

	public void guardarCambios(OrdenTrabajoModelo orden, String observaciones) {
		String sqlGuardarCambios = "UPDATE orden_de_trabajo SET observaciones = ? WHERE "
				+ "orden_trabajo_id = ? ";
		
		try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sqlGuardarCambios)){
			ps.setString(1, observaciones);
			ps.setString(2, orden.getOrdenId());
			
			ps.executeUpdate();
		}catch(Exception e) {
			 System.out.println("Error al insertar observaciones: " + e.getMessage());
		}
	}
	
	public void mostrarMensaje(String mensaje, boolean exito) {

	}
	
	public void ingresarCriterioBusqueda() {

	}
	
	public void mostrarResultado(List<OrdenTrabajoModelo> listaOrdenes) {

	}

	public void agregarDetalleReparacion() {

	}
	
	public void navegar() {
		seleccionarOrden();
	}
	
	public void seleccionarOrden() {
		    Scanner scanner = new Scanner(System.in);
		    String ordenIdParam;

		    String sqlConsultaOrdenes = 
		        "SELECT O.ORDEN_TRABAJO_ID, "
		        + "O.FECHA_INGRESO, O.DESCRIPCION_FALLA, O.ESTADO, "
		        + "C.NOMBRE, C.APELLIDO, C.EMPRESA, C.TELEFONO, "
		        + "C.DNI, C.CUIT, M.ID, M.TIPO, "
		        + "M.MARCA, M.MODELO, M.COLOR, O.DESPACHO "
		        + "FROM ORDEN_DE_TRABAJO O "
		        + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
		        + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
		        + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
		        + "WHERE O.ORDEN_TRABAJO_ID = ? AND ESTADO = 'Lista'";

		    System.out.println("Ingrese el ID de la Orden de Trabajo para entregar:");
		    String ordenId = scanner.nextLine();

		    try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sqlConsultaOrdenes)) {
		        ps.setString(1, ordenId);
		        ResultSet rs = ps.executeQuery();

		        boolean encontrada = false;
		        while (rs.next()) {
		            if (!encontrada) {
		                System.out.println("\n✅ ORDEN ENCONTRADA");
		                System.out.println("ID: " + rs.getString("orden_trabajo_id"));
		                System.out.println("Cliente: " + rs.getString("nombre") + " " + rs.getString("apellido"));
		                System.out.println("Teléfono: " + rs.getString("telefono") + " - DNI: " + rs.getString("dni"));
		                System.out.println("CUIT: " + rs.getString("cuit"));
		                System.out.println("Empresa: " + rs.getString("empresa"));
		                System.out.println("Descripción: " + rs.getString("descripcion_falla"));
		                System.out.println("Estado: " + rs.getString("estado"));
		                System.out.println("\nMáquinas asociadas:");
		                encontrada = true;
		                ordenIdParam = rs.getString("orden_trabajo_id");
		                formOrdenTrabajoVista.abrirForm(ordenIdParam, PersonalControl.adminIdPersonalControl);
		            }

		            String idMaquina = rs.getString("id");
		            if (idMaquina != null) {
		                System.out.println(" - ID: " + idMaquina 
		                        + " | " + rs.getString("tipo")
		                        + " | " + rs.getString("marca")
		                        + " | Modelo: " + rs.getString("modelo")
		                        + " | Color: " + rs.getString("color"));
		            }
		           
		        }

		        if (!encontrada) {
		            System.out.println("❌ No existe una orden LISTA con ese ID.");
		        }

		}catch(Exception e) {
			  System.out.println("Ocurrió un error en el proceso: " + e.getMessage());
		}
	}   
	
	
	public void mostrarConfirmacion(String mensaje) {
	}
	
	public void mostrarLista() {
		String sqlConsultaOrdenes = "SELECT O.ORDEN_TRABAJO_ID, "
				+ "O.FECHA_INGRESO, O.DESCRIPCION_FALLA, O.ESTADO, "
				+ "C.NOMBRE, C.APELLIDO, C.EMPRESA, M.ID, M.TIPO, "
				+ "M.MARCA, M.MODELO, M.COLOR FROM ORDEN_DE_TRABAJO O"
				+ " JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID"
				+ " LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID"
				+ " LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID"
				+ " ORDER BY O.ORDEN_TRABAJO_ID";
		try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sqlConsultaOrdenes)){
			ResultSet rs =ps.executeQuery();
			
			   String ordenActual = "";
		        boolean primeraLinea = true;

		        while (rs.next()) {
		            String ordenId = rs.getString("orden_trabajo_id");

		            if (!ordenId.equals(ordenActual)) {
		                if (!primeraLinea) System.out.println("-----------------------------------");
		                primeraLinea = false;

		                System.out.println("📌 ORDEN: " + ordenId);
		                System.out.println("Nombre: " + rs.getString("nombre"));
		                System.out.println("Apellido: " + rs.getString("apellido"));
		                System.out.println("Descripción: " + rs.getString("descripcion_falla"));
		                System.out.println("Fecha: " + rs.getDate("fecha_ingreso"));
		                System.out.println("Estado: " + rs.getString("estado"));
		                System.out.println("Máquinas:");
		                ordenActual = ordenId;
		            }

		            String maquinaId = rs.getString("id");

		            if (maquinaId != null) {
		                System.out.println("   - " + maquinaId + " | " +
		                    rs.getString("tipo") + " | " +
		                    rs.getString("marca") + " | " +
		                    rs.getString("modelo"));
		            } else {
		                System.out.println("   (Sin máquinas asociadas)");
		            }
		        }
		}catch(Exception e) {
			   System.out.println("Error al mostrar lista: " + e.getMessage());
		}
		
	}
	
	
	//Setters and getters
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public TecnicoModelo getTecnicoAsignado() {
		return tecnicoAsignado;
	}
	
	public void setTecnicoAsignado(TecnicoModelo tecnicoAsignado) {
		this.tecnicoAsignado = tecnicoAsignado;
	}
	
	public ClienteModelo getCliente() {
		return cliente;
	}
	
	public void setCliente(ClienteModelo cliente) {
		this.cliente = cliente;
	}
	
	public String getDetalleRep() {
		return detalleRep;
	}
	
	public void setDetalleRep(String detalleRep) {
		this.detalleRep = detalleRep;
	}
	
	public String getMensajeExito() {
		return mensajeExito;
	}
	
	public void setMensajeExito(String mensajeExito) {
		this.mensajeExito = mensajeExito;
	}
	
	public String getMensajeError() {
		return mensajeError;
	}
	
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public void mostrarMenuOrdenesTrabajo() {
		
		System.out.println("Menú de Órdenes de Trabajo");
		
	}
}
