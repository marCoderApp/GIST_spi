package vistas.GestionRep;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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

    //MUESTRA FORMULARIO PARA CREAR PRESUPUESTO
    public static void mostrarFormCrearPresupuesto(String maquinaId, Runnable callback) {
            FormPresupVista.ingresarPresupuesto(maquinaId, callback);
    }

    //MOSTRA MENU EN PRESPUESTO
    public void mostrarMenuPresupuestos() {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(null);
        alerta.setContentText("Módulo en construcción");
        alerta.showAndWait();


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
