package vistas.GestionRep;

import controladores.GestionRepControl;
import dtos.PresupuestosDTO;
import javafx.beans.property.ReadOnlyStringWrapper;
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
    public static void mostrarFormCrearPresupuesto(String ordenId, String maquinaId, Runnable callback) {
            FormPresupVista.ingresarPresupuesto(ordenId, maquinaId, callback);
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
            mostrarListaOrdenes();
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

    //MOSTRAR LISTA DE ORDENES
    public static void mostrarListaOrdenes() {
        // VENTANA DE LISTAR ORDENES DE TRABAJO
        Stage ventana = new Stage();
        ventana.setTitle("Lista de Órdenes de Trabajo");

        //TABLA DE FILAS DE STRING
        TableView<ObservableList<String>> tabla = new TableView<>();

        //LISTA DE FILAS, CADA FILA ES UNA LISTA DE STRINGS
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();

        String[] nombresCampos = {
                "Orden_trabajo_id", "Fecha_ingreso", "Estado",
                "Cliente", "Maquina_id",
                "Tipo", "Marca", "Modelo", "Descripcion_Falla",
                "Observaciones", "Activo"
        };

        //AGREGAR NOMBRES DE COLUMNAS DINÁMICAMENTE
        for(String nombreCampo : nombresCampos){
            final int colIndex = tabla.getColumns().size();
            TableColumn<ObservableList<String>, String> columna =
                    new TableColumn<>(nombreCampo);

            columna.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                    (param.getValue().size() > colIndex) ?
                            param.getValue().get(colIndex) : ""));
            columna.setPrefWidth(switch(nombreCampo) {
                case "Orden_trabajo_id" -> 100;
                case "Fecha_ingreso" -> 120;
                case "Cliente" -> 150;
                case "Estado" -> 150;
                case "Maquina_id" -> 100;
                case "Tipo" -> 150;
                case "Marca" -> 120;
                case "Modelo" -> 150;
                case "Descripcion_Falla" -> 200;
                case "Observaciones" -> 200;
                case "Activo" -> 100;
                default -> 120;
            });
            tabla.getColumns().add(columna);
        }

        //CARGAR DATOS DE ORDENES
        OrdenTrabajoVista.cargarDatosDeOrdenes(tabla, datos);

        //BOTONES
        Button btnAgregarPresupuesto = new Button("Agregar Presupuesto");
        btnAgregarPresupuesto.setPrefWidth(150);
        btnAgregarPresupuesto.setOnAction(e -> {
            String ordenId = tabla.getSelectionModel().getSelectedItem().getFirst();
            String maquinaId = tabla.getSelectionModel().getSelectedItem().get(4);

            System.out.println("Orden seleccionada: " + ordenId);
            System.out.println("Maquina seleccionada: "+ maquinaId);

            FormPresupVista.ingresarPresupuesto(ordenId, maquinaId,()->{});

        });

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setPrefWidth(150);
        btnCerrar.setOnAction(e -> ventana.close());

        //LAYOUT
        HBox botonesBox = new HBox(10, btnAgregarPresupuesto,
                btnCerrar
               );

        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.setPadding(new Insets(10));

        VBox layout = new VBox(10, tabla, botonesBox);
        layout.setPadding(new Insets(10));

        Scene escena = new Scene(layout);
        ventana.setScene(escena);
        ventana.show();
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

        TableColumn<PresupuestosDTO, String> colNombreCliente = new TableColumn<>("Nombre Cliente");
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colNombreCliente.setPrefWidth(150);

        TableColumn<PresupuestosDTO, String> colApellidoCliente = new TableColumn<>("Apellido Cliente");
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<>("apellidoCliente"));
        colApellidoCliente.setPrefWidth(150);

        TableColumn<PresupuestosDTO, String> colNombreMaquina = new TableColumn<>("Nombre Maquina");
        colNombreMaquina.setCellValueFactory(new PropertyValueFactory<>("nombreMaquina"));
        colNombreMaquina.setPrefWidth(150);

		TableColumn<PresupuestosDTO, String> colMaquinaId = new TableColumn<>("Maquina ID");
		colMaquinaId.setCellValueFactory(new PropertyValueFactory<>("maquinaId"));
		colMaquinaId.setPrefWidth(150);

		TableColumn<PresupuestosDTO, String> colFechaCreacion = new TableColumn<>("Fecha creación");
		colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));
		colFechaCreacion.setPrefWidth(150);

		TableColumn<PresupuestosDTO, String> colAdminId = new TableColumn<>("Admin ID");
		colAdminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));
		colAdminId.setPrefWidth(150);

        TableColumn<PresupuestosDTO, String> colOrdenId = new TableColumn<>("Orden ID");
        colOrdenId.setCellValueFactory(new PropertyValueFactory<>("ordenId"));
        colOrdenId.setPrefWidth(150);

		tabla.getColumns().addAll(colPresupuestoId,
                colTotal,
                colConFactura,
                colNombreCliente,
                colApellidoCliente,
                colNombreMaquina,
                colMaquinaId,
                colFechaCreacion,
                colAdminId,
                colOrdenId);

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
