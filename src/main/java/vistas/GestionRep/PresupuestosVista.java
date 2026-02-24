package vistas.GestionRep;

import controladores.GestionRepControl;
import dtos.PresupuestosDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
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

		Stage ventanaPresupuestos = new Stage();
		ventanaPresupuestos.setTitle("Presupuestos");

		Label titulo = new Label("Gestion de Presupuestos");
		titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		//botones
		Button btnCrearPresupuestos = new Button("Crear presupuesto");
		Button btnListarPresupuestos = new Button("Listar presupuestos");
		Button btnBuscarPresupuestos = new Button("Buscar presupuestos");
		Button btnCerrar = new Button("Cerrar");

		btnListarPresupuestos.setPrefWidth(250);
		btnCrearPresupuestos.setPrefWidth(250);
		btnBuscarPresupuestos.setPrefWidth(250);
		btnCerrar.setPrefWidth(250);

		btnListarPresupuestos.setOnAction(e->{
			listarPresupuestos();
		});

		btnCrearPresupuestos.setOnAction(e->{
			System.out.println("Crear presupuesto");
		});

		btnBuscarPresupuestos.setOnAction(e->{
			System.out.println("Buscar presupuesto");
		});

		btnCerrar.setOnAction(e->{
			ventanaPresupuestos.close();
		});

		// Estilos
		String estiloBoton =
				"-fx-background-color: white;" +
						"-fx-text-fill: black;" +
						"-fx-border-color: black;" +
						"-fx-border-width: 1.5;" +
						"-fx-background-radius: 8;" +
						"-fx-border-radius: 8;" +
						"-fx-font-size: 14px;";

		btnListarPresupuestos.setStyle(estiloBoton);
		btnCrearPresupuestos.setStyle(estiloBoton);
		btnBuscarPresupuestos.setStyle(estiloBoton);
		btnCerrar.setStyle(estiloBoton);

		// Hover effects
		Button[] botones = {btnListarPresupuestos, btnCrearPresupuestos,
				btnBuscarPresupuestos, btnCerrar};
		for (Button btn : botones) {
			btn.setOnMouseEntered(e -> btn.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
			btn.setOnMouseExited(e -> btn.setStyle(estiloBoton));
		}

		VBox layout = new VBox(15);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(30));
		layout.getChildren().addAll(titulo,
				btnListarPresupuestos,
				btnCrearPresupuestos,
				btnBuscarPresupuestos,
				btnCerrar);
		layout.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");


		//Escena
		Scene scene = new Scene(layout, 400, 450);
		ventanaPresupuestos.setScene(scene);
		ventanaPresupuestos.show();

    }
	
	public void mostrarMensaje(String mensaje) {
		System.out.println("Mensaje: " + mensaje);
	}
	
	public void seleccionarPresupuesto(String presupuestoId) {
		
	}

	//LISTAR PRESUPUESTOS
	public void listarPresupuestos() {
		Stage ventana = new Stage();
		ventana.setTitle("Lista de Presupuestos");
		ventana.initModality(Modality.APPLICATION_MODAL);

		Label titulo = new Label("Lista de Presupuestos");
		titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		List<PresupuestosDTO> listaPresupuestos = GestionRepControl.listarPresupuestos();
		ObservableList<PresupuestosDTO> items = FXCollections.observableArrayList(listaPresupuestos);

		TableView<PresupuestosDTO> tabla = new TableView<>(items);
		tabla.setEditable(false);
		tabla.setPrefWidth(400);
		tabla.setPrefHeight(300);

		TableColumn<PresupuestosDTO, String> colPresupuestoId = new TableColumn<>("Presupuesto ID");
		colPresupuestoId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colPresupuestoId.setPrefWidth(150);

		TableColumn<PresupuestosDTO, Float> colTotal = new TableColumn<>("Total");
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		colTotal.setPrefWidth(150);

		TableColumn<PresupuestosDTO, Boolean> colConFactura = new TableColumn<>("Factura");
		colConFactura.setCellValueFactory(new PropertyValueFactory<>("conFactura"));
		colConFactura.setPrefWidth(150);

		TableColumn<PresupuestosDTO, String> colMaquinaId = new TableColumn<>("Maquina ID");
		colMaquinaId.setCellValueFactory(new PropertyValueFactory<>("maquinaId"));
		colMaquinaId.setPrefWidth(150);

		TableColumn<PresupuestosDTO, String> colFechaCreacion = new TableColumn<>("Fecha creación");
		colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
		colFechaCreacion.setPrefWidth(150);

		TableColumn<PresupuestosDTO, String> colAdminId = new TableColumn<>("Admin ID");
		colAdminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));
		colAdminId.setPrefWidth(150);

		tabla.getColumns().addAll(colPresupuestoId, colTotal, colConFactura, colMaquinaId, colFechaCreacion, colAdminId);

		Button btnCerrar = new Button("Cerrar");
		btnCerrar.setPrefWidth(150);
		btnCerrar.setAlignment(Pos.CENTER);
		btnCerrar.setOnAction(e -> ventana.close());

		HBox barra = new HBox(10, btnCerrar);
		barra.setAlignment(Pos.CENTER);
		barra.setPadding(new Insets(10));

		VBox layout = new VBox(15);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(30));
		layout.getChildren().addAll(titulo, tabla, barra);
		layout.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #84b6ef);");

		Scene escena = new Scene(layout, 1000, 450);
		ventana.setScene(escena);
		ventana.show();

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
