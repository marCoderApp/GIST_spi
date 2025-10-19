package vistas.GestionRep;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

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
		
		System.out.println("-----------------------------");
		System.out.println("CREAR NUEVA ORDEN DE TRABAJO");
		System.out.println("-----------------------------");
		elegirCliente();
		System.out.println("Descripción de la falla:");
		String descripcionFalla = scanner.nextLine();
		
		
		
	}
	
	public String elegirCliente() {
		Scanner scanner = new Scanner(System.in);
		ClientesVista clientesVista = new ClientesVista();
		
		System.out.println("Seleccione una opción:");
		System.out.println("1. Elegir cliente existente");
		System.out.println("2. Crear nuevo cliente");
		 int opcion = scanner.nextInt();
		    scanner.nextLine(); // limpiar buffer
		    
			if (opcion == 1) {
				List<ClienteModelo> clientes = ClienteModelo.listarClientes();
				ClientesVista.mostrarListaClientes(clientes);
				
				System.out.println("Ingrese el ID del cliente: ");
				String clienteId = scanner.nextLine();
				System.out.println("Cliente seleccionado: " + clienteId);
				return clienteId;
			} else if (opcion == 2) {
				System.out.println("Creando nuevo cliente...");
				
				clientesVista.crearNuevoCliente();
				return ""; // Retornar el ID del nuevo cliente
			} else {
				System.out.println("Opción inválida. Por favor, intente de nuevo.");
			}
		
		return "";
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
