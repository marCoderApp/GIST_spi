package vistas.GestionRep;

import java.util.ArrayList;
import java.util.List;

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
	
	public PedidoModelo opcionCrearPedido() {
		return new PedidoModelo(clienteId, null, clienteId, clienteId);
	}
	
	public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
	
	public void mostrarListaClientes(List<ClienteModelo> clientes) {
		System.out.println("Lista de Clientes:");
		for (ClienteModelo cliente : clientes) {
			System.out.println(cliente);
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
