package vistas.GestionRep;

import modelos.GestionRep.PresupuestoModelo;
import java.util.*;

public class PresupuestosVista {
	
	private List<PresupuestoModelo> listaPresupuestos = new ArrayList<>();
	private PresupuestoModelo presupuestoSeleccionado;
	private String filtroCodigoOrden;
	
	public PresupuestosVista(List<PresupuestoModelo> listaPresupuestos, 
			PresupuestoModelo presupuestoSeleccionado,
			String filtroCodigoOrden) {
		
		this.listaPresupuestos = listaPresupuestos;
		this.presupuestoSeleccionado = presupuestoSeleccionado;
		this.filtroCodigoOrden = filtroCodigoOrden;
	}
	
	
	public void opcionCrearPresupuesto() {
		
	}
	
	public void mostrarMensaje(String mensaje) {
		System.out.println("Mensaje: " + mensaje);
	}
	
	public void seleccionarPresupuesto(String presupuestoId) {
		
	}
	
	//Getters and setters:
	
	public List<PresupuestoModelo> getListaPresupuestos() {
		return listaPresupuestos;
	}
	
	public void setListaPresupuestos(List<PresupuestoModelo> listaPresupuestos) {
		this.listaPresupuestos = listaPresupuestos;
	}
	
	public PresupuestoModelo getPresupuestoSeleccionado() {
		return presupuestoSeleccionado;
	}
	
	public void setPresupuestoSeleccionado(PresupuestoModelo presupuestoSeleccionado) {
		this.presupuestoSeleccionado = presupuestoSeleccionado;
	}	
	
	public String getFiltroCodigoOrden() {
		return filtroCodigoOrden;
	}
	
	public void setFiltroCodigoOrden(String filtroCodigoOrden) {
		this.filtroCodigoOrden = filtroCodigoOrden;
	}
	

}
