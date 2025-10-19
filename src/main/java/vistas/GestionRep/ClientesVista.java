package vistas.GestionRep;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controladores.GestionRepControl;
import modelos.GestionRep.ClienteModelo;
import modelos.GestionRep.PedidoModelo;

public class ClientesVista {
	
	private String clienteId;
	private List<ClienteModelo> clientes = new ArrayList<>();
	private ClienteModelo clienteSeleccionado;
	private String filtroCodigoOrden;
	private int cantidadTotalOrdenes;
	
	public ClientesVista() {
		this.clienteId = "";
		this.clientes = new ArrayList<>();
		this.clienteSeleccionado = null;
		this.filtroCodigoOrden = "";
		this.cantidadTotalOrdenes = 0;
	}
	
	public void mostrarMenuClientes() {
		// TODO Auto-generated method stub
		System.out.println("=== Gestión de Clientes ===");
		System.out.println("1. Listar Clientes");
		System.out.println("2. Seleccionar Cliente");
		System.out.println("3. Crear Pedido");
		System.out.println("4. Filtrar Órdenes por Código");
		System.out.println("5. Salir");
	}
	
	public ClienteModelo crearNuevoCliente() {
		
		GestionRepControl gestionRepControl = new GestionRepControl();
		Scanner scanner = new Scanner(System.in);

	    System.out.println("=== Crear nuevo cliente ===");
	    System.out.print("Nombre: ");
	    String nombre = scanner.nextLine();
	    System.out.print("Apellido: ");
	    String apellido = scanner.nextLine();
	    System.out.print("Empresa: ");
	    String empresa = scanner.nextLine();
	    System.out.print("Teléfono: ");
	    String telefono = scanner.nextLine();
	    System.out.print("DNI: ");
	    String dni = scanner.nextLine();
	    System.out.print("CUIT: ");
	    String cuit = scanner.nextLine();
	    
	    ClienteModelo nuevoCliente = new ClienteModelo(nombre, apellido, empresa, telefono, dni, cuit);
	    
	    boolean guardado = gestionRepControl.registrarCliente(nuevoCliente);
		
		if (guardado) {
			mostrarMensaje("Cliente creado exitosamente.");
			return nuevoCliente;
		} else {
			mostrarMensaje("Error al crear el cliente.");
		}
		return null;
	}
	
	public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
	
	public static void mostrarListaClientes(List<ClienteModelo> clientes) {
		System.out.println("Lista de Clientes:");
		for (ClienteModelo cliente : clientes) {
			 System.out.println("───────────────────────────────");
			    System.out.println("ID Cliente: " + cliente.getClienteId());
			    System.out.println("Nombre:     " + cliente.getNombre());
			    System.out.println("Apellido:   " + cliente.getApellido());
			    System.out.println("Empresa:    " + cliente.getEmpresa());
			    System.out.println("Teléfono:   " + cliente.getTelefono());
			    System.out.println("DNI:        " + cliente.getDni());
			    System.out.println("CUIT:       " + cliente.getCuit());
				 System.out.println("───────────────────────────────");
		}
	}
	
	//Getters
	
	public String getClienteId() {
		return clienteId;
	}
	
	public List<ClienteModelo> getClientes() {
		return clientes;
	}
	
	public ClienteModelo getClienteSeleccionado() {
		return clienteSeleccionado;
	}
	
	public String getFiltroCodigoOrden() {
		return filtroCodigoOrden;
	}
	
	public int getCantidadTotalOrdenes() {
		return cantidadTotalOrdenes;
	}
	
	//Setters
	
	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}
	
	public void setClientes(List<ClienteModelo> clientes) {
		this.clientes = clientes;
	}
	
	public void setClienteSeleccionado(ClienteModelo clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}
	
	public void setFiltroCodigoOrden(String filtroCodigoOrden) {
		this.filtroCodigoOrden = filtroCodigoOrden;
	}
	
	public void setCantidadTotalOrdenes(int cantidadTotalOrdenes) {
		this.cantidadTotalOrdenes = cantidadTotalOrdenes;
	}
	
}
