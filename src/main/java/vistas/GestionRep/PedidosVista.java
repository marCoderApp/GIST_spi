package vistas.GestionRep;

import java.util.ArrayList;
import java.util.List;
import modelos.GestionRep.*;

public class PedidosVista {

	private String pedidoId;
	private List<PedidoModelo> listaPedidos = new ArrayList<>();
	private PedidoModelo pedidoSeleccionado;
	private String filtroCodigoOrden;
	private int cantidadTotal;
	
	public PedidosVista(String pedidoId, List<PedidoModelo> listaPedidos, PedidoModelo pedidoSeleccionado,
			String filtroCodigoOrden, int cantidadTotal) {
		// Constructor
		
		this.pedidoId = pedidoId;
		this.listaPedidos = listaPedidos;
		this.pedidoSeleccionado = pedidoSeleccionado;
		this.filtroCodigoOrden = filtroCodigoOrden;
		this.cantidadTotal = cantidadTotal;
		
	}
	
	public PedidoModelo opcionCrearPedido() {
		// Lógica para crear un nuevo pedido
		return new PedidoModelo(filtroCodigoOrden, null, filtroCodigoOrden, filtroCodigoOrden, null);
	}
	
	
	public void mostrarMensaje(String mensaje) {
		// Lógica para mostrar un mensaje al usuario
		System.out.println(mensaje);
	}
	
	public void mostrarListaPedidos(List<PedidoModelo> listaPedidos) {
		// Lógica para mostrar la lista de pedidos
		for (PedidoModelo pedido : listaPedidos) {
			System.out.println(pedido);
		}
	}
	
	//Getters y Setters
	
	public String getPedidoId() {
		return pedidoId;
	}
	
	public void setPedidoId(String pedidoId) {
		this.pedidoId = pedidoId;
	}
	
	public List<PedidoModelo> getListaPedidos() {
		return listaPedidos;
	}
	
	public void setListaPedidos(List<PedidoModelo> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}
	
	public PedidoModelo getPedidoSeleccionado() {
		return pedidoSeleccionado;
	}
	
	public void setPedidoSeleccionado(PedidoModelo pedidoSeleccionado) {
		this.pedidoSeleccionado = pedidoSeleccionado;
	}
	
	public String getFiltroCodigoOrden() {
		return filtroCodigoOrden;
	}
	
	public void setFiltroCodigoOrden(String filtroCodigoOrden) {
		this.filtroCodigoOrden = filtroCodigoOrden;
	}
	
	public int getCantidadTotal() {
		return cantidadTotal;
	}
	
	public void setCantidadTotal(int cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	public void mostrarMenuPedidos() {
		// TODO Auto-generated method stub
		System.out.println("Menú de Pedidos");
	}
	
}
