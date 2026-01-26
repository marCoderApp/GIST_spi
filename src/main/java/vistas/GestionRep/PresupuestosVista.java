package vistas.GestionRep;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
			System.out.println("Listar Presupuestos");
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
