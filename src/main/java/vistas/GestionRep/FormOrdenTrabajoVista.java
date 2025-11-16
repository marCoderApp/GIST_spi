package vistas.GestionRep;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import controladores.GestionRepControl;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import modelos.GestionRep.ClienteModelo;

public class FormOrdenTrabajoVista {
	
	private GestionRepControl gestionRepControl = new GestionRepControl();
	private String ordenId;
	
	public FormOrdenTrabajoVista(String ordenId) {
		this.setOrdenId(ordenId);
	}

    // ABRIR FORM REGISTRAR RETIRO DE ORDEN.
	public void abrirForm(String ordenIdParam, String adminId) {
		GestionRepControl gestionRepControl = new GestionRepControl();

	    Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
	    alertaConfirmacion.setTitle("Confirmar Retiro de Orden");
	    alertaConfirmacion.setHeaderText("Confirmar retiro de la Orden ID = " + ordenIdParam);
	    alertaConfirmacion.setContentText("Seleccione una opción:");

	    ButtonType botonSi = new ButtonType("Sí", ButtonData.YES);
	    ButtonType botonNo = new ButtonType("No", ButtonData.NO);

	    alertaConfirmacion.getButtonTypes().setAll(botonSi, botonNo);

	    Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

	    if (resultado.isPresent()) {
	        if (resultado.get().getButtonData() == ButtonData.YES) {
	            // Confirmó el retiro
	            boolean entregado = gestionRepControl.registrarEntrega(ordenIdParam, adminId);

	            if (entregado) {
	                // Mostrar mensaje de éxito
	                Alert exito = new Alert(Alert.AlertType.INFORMATION);
	                exito.setTitle("Entrega Registrada");
	                exito.setHeaderText("✅ La orden se ha retirado correctamente");
	                exito.setContentText("Orden ID: " + ordenIdParam);
	                exito.showAndWait();
	            } else {
	                Alert error = new Alert(Alert.AlertType.ERROR);
	                error.setTitle("Error al Retirar Orden");
	                error.setHeaderText("❌ No se pudo registrar el retiro");
	                error.setContentText("Verifique los datos e intente nuevamente.");
	                error.showAndWait();
	            }

	        } else if (resultado.get().getButtonData() == ButtonData.NO) {
	        } else {
	            Alert error = new Alert(Alert.AlertType.ERROR);
	            error.setTitle("Opción inválida");
	            error.setHeaderText("Opción inválida seleccionada");
	            error.setContentText("Por favor, intente de nuevo.");
	            error.showAndWait();
	        }
	    }
	}
	
	public String getOrdenId() {
		return ordenId;
	}

	public void setOrdenId(String ordenId) {
		this.ordenId = ordenId;
	}

}
