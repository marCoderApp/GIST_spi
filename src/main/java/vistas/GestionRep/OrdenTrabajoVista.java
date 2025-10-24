package vistas.GestionRep;

import java.time.LocalDate;
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
		OrdenTrabajoModelo orden = new OrdenTrabajoModelo(null, null, null, null, null, null, null, null, null, null);
		
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
		
	    LocalDate fecha = (orden.getFechaIngreso() != null)
                ? orden.getFechaIngreso()
                : LocalDate.now();

		
		 System.out.println("\n=== DATOS DE LA ORDEN A INSERTAR ===");
	        System.out.println("Orden ID: " + orden.getOrdenId());
	        System.out.println("Cliente ID: " + GestionRepControl.clienteIdGestionRep);
	        System.out.println("Descripción: " + orden.getDescripcion_falla());
	        System.out.println("Fecha creación: " + fecha);
	        System.out.println("Estado: " + orden.getEstado().getValor());
	        System.out.println("Admin ID: " + PersonalControl.adminIdPersonalControl);
	        System.out.println("Técnico ID: " + PersonalControl.tecnicoIdPersonalControl);
	        System.out.println("=====================================\n");
		
		gestionRepControl.insertarOrdenBase(orden);
		
		System.out.println("Maquinas");
		List<MaquinaModelo> maquinas = gestionRepControl.seleccionarMaquinas(orden);
		for (MaquinaModelo maquina : maquinas) {
			System.out.println(" - " + maquina.getTipo() + " (ID: " + maquina.getMaquinaId() + ")");
		}
		
		
		
	}
	

	public void guardarCambios(OrdenTrabajoModelo orden) {
		
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
		abrirFormularioOrdenTrabajo();
	}
	
	public void abrirFormularioOrdenTrabajo() {
		seleccionarOrden(null);
	}
	
	
	
	public void seleccionarOrden(String ordenId) {
	}   
	
	
	public void mostrarConfirmaci(String mensaje) {
	}
	
	public void mostrarLista(List<OrdenTrabajoModelo> listaOrdenes) {
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
