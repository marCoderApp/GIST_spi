package vistas.GestionRep;

import java.util.ArrayList;
import java.util.List;
import modelos.GestionRep.OrdenTrabajoModelo;
import modelos.GestionRep.PedidoModelo;

public class FormClientesVista {
	private String clienteId;
	private String nombre;
	private String empresa;
	private int cantidadOrdenesCliente;
	private String dni;
	private List<OrdenTrabajoModelo> listaOrdenesCliente = new ArrayList<>();

    //METODO CONSTRUCTOR
	public FormClientesVista(String clienteId, String nombre, String empresa, int cantidadOrdenesCliente, 
			String dni, List<OrdenTrabajoModelo> listaOrdenesCliente) {
        
		this.clienteId = clienteId;
		this.nombre = nombre;
		this.empresa = empresa;
		this.cantidadOrdenesCliente = cantidadOrdenesCliente;
		this.dni = dni;
		this.listaOrdenesCliente = listaOrdenesCliente;
    }
	
	public PedidoModelo ingresarPedido() {
		return new PedidoModelo(clienteId, null, clienteId, clienteId, null);
	}
	
	public void abrirForm() {
		System.out.println("Abriendo formulario para el cliente: " + nombre);
		System.out.println("Empresa: " + empresa);
		System.out.println("DNI: " + dni);
		System.out.println("Cantidad de órdenes: " + cantidadOrdenesCliente);
		System.out.println("Lista de órdenes:");
		for (OrdenTrabajoModelo orden : listaOrdenesCliente) {
			System.out.println("- Orden ID: " + orden.getOrdenId() + ", Descripción: " + orden.getDescripcion_falla());
		}
	}	
	
	public List<OrdenTrabajoModelo> obtenerOrdenesCliente(String clienteId) {
		return listaOrdenesCliente;
	}
	
	//GETTERS Y SETTERS
	
	public String getClienteId() {
		return clienteId;
	}
	
	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}
	
	public String getNombre() {
		return nombre;
	}	
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public int getCantidadOrdenesCliente() {
		return cantidadOrdenesCliente;
	}
	
	public void setCantidadOrdenesCliente(int cantidadOrdenesCliente) {
		this.cantidadOrdenesCliente = cantidadOrdenesCliente;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public List<OrdenTrabajoModelo> getListaOrdenesCliente() {
		return listaOrdenesCliente;
	}
	
	public void setListaOrdenesCliente(List<OrdenTrabajoModelo> listaOrdenesCliente) {
		this.listaOrdenesCliente = listaOrdenesCliente;
	}
	
}
