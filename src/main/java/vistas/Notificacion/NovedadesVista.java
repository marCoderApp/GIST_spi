package vistas.Notificacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import controladores.GestionRepControl;
import controladores.NotificacionesControlador;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelos.Notificacion.NovedadItem;
import modelos.Notificacion.NovedadModelo;
import java.util.*;
import modelos.GestionRep.OrdenTrabajoModelo;
import vistas.GestionRep.OrdenTrabajoVista;


public class NovedadesVista {
	
	private String novedadId;
	private String titulo;
	private String descripcion;
	private LocalDateTime fechaCreacion;
	private List<OrdenTrabajoModelo> ordenesAsociadas = new ArrayList<>();

    //METODO CONSTRUCTOR
	public NovedadesVista(String novedadId, String titulo, String descripcion, LocalDateTime fechaCreacion,
			List<OrdenTrabajoModelo> ordenesAsociadas) {
		this.novedadId = novedadId;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.setOrdenesAsociadas(ordenesAsociadas);

	}

    //MOSTRAR MENU NOVEDADES
    public void mostrarMenuNovedades(){

        //VENTANA
        Stage ventanaMenuNovedades = new Stage();
        ventanaMenuNovedades.setTitle("Gestion de Novedades");

        //TITULO
        Label titulo = new Label("Gestión de Novedades - GIST");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        //BOTONES
        Button btnCrearNovedad = new Button("Crear Novedad");
        btnCrearNovedad.setPrefWidth(250);

        Button btnListarNovedades = new Button("Listar Novedades");
        btnListarNovedades.setPrefWidth(250);

        Button btnVolver = new Button("<- Volver");
        btnVolver.setPrefWidth(250);

        //ESTILOS DE BOTONES

        String estiloBoton =
                "-fx-background-color: white;" +
                        "-fx-text-fill: black;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-font-size: 14px;";

        btnCrearNovedad.setStyle(estiloBoton);
        btnListarNovedades.setStyle(estiloBoton);
        btnVolver.setStyle(estiloBoton);

        //ACCIONES DE BOTONES

        btnCrearNovedad.setOnAction(e -> {
            mostrarFormCrearNovedades();
        });
        btnListarNovedades.setOnAction(e->{
            mostrarListaNovedades();
        });
        btnVolver.setOnAction(e->{ventanaMenuNovedades.close();});

        //HOVER EFECTOS
        Button[] botones = {btnCrearNovedad, btnListarNovedades, btnVolver};
        for (Button btn : botones) {
            btn.setOnMouseEntered(e -> btn.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
            btn.setOnMouseExited(e -> btn.setStyle(estiloBoton));
        }

        //LAYOUT
        VBox layout = new VBox(15);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(30));
        layout.getChildren().addAll(
          titulo,
          btnListarNovedades,
          btnCrearNovedad,
          btnVolver
        );
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");

        Scene escena = new Scene(layout, 400, 450);
        ventanaMenuNovedades.setScene(escena);
        ventanaMenuNovedades.show();
    };

    //MOSTRAR FORM CREAR NOVEDADES
    public void mostrarFormCrearNovedades(){

        Stage ventanaFormNovedades = new Stage();
        ventanaFormNovedades.setTitle("Crear Novedad");

        //TITULO
        Label titulo = new Label("Crear Novedad - GIST");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        //CONTENEDOR DE ITEMS
        VBox contenedorItems = new VBox(20);

        //SCROLLPANE
        ScrollPane scrollPaneItems = new ScrollPane(contenedorItems);
        scrollPaneItems.setFitToWidth(true);
        scrollPaneItems.setPrefHeight(250);
        scrollPaneItems.setStyle("-fx-background-color:transparent;");

        //BOTONES
        Button btnAgregarItem = new Button("+ Agregar ítem");
        btnAgregarItem.setPrefWidth(250);

        Button btnCrearNovedad = new Button("Crear Novedad");
        btnCrearNovedad.setPrefWidth(250);

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefWidth(250);

        //ESTILOS
        String estiloBoton =
                "-fx-background-color: white;" +
                        "-fx-text-fill: black;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-font-size: 14px;";

        btnCrearNovedad.setStyle(estiloBoton);
        btnCancelar.setStyle(estiloBoton);
        btnAgregarItem.setStyle(estiloBoton);
        btnAgregarItem.setOnMouseEntered(e -> btnAgregarItem.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
        btnAgregarItem.setOnMouseExited(e -> btnAgregarItem.setStyle(estiloBoton));


        //HOVER EFFECTS
        Button[] botones = {btnCrearNovedad, btnCancelar};
        for (Button btn : botones) {
            btn.setOnMouseEntered(e -> btn.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
            btn.setOnMouseExited(e -> btn.setStyle(estiloBoton));
        }

        //ACCIONES DE BOTONES

        btnAgregarItem.setOnAction(e -> {

            if (contenedorItems.getChildren().size() >= 5) {
                mostrarError("Solo se permiten hasta 5 ítems por novedad.",
                        "Advertencia");
                return;
            }

            TextArea txtComentarioItem = new TextArea();
            txtComentarioItem.setPromptText("Comentario del ítem...");
            txtComentarioItem.setPrefRowCount(5);
            txtComentarioItem.setWrapText(true);

            Button btnSeleccionarOrden = new Button("Vincular orden");
            btnSeleccionarOrden.setPrefWidth(250);
            btnSeleccionarOrden.setStyle(estiloBoton);

            btnSeleccionarOrden.setOnAction(event -> {

                OrdenTrabajoVista.obtenerOrdenesDisponibles(idSeleccionado -> {
                    btnSeleccionarOrden.setUserData(idSeleccionado);
                    btnSeleccionarOrden.setText(idSeleccionado);
                    btnSeleccionarOrden.setText("Orden: " + idSeleccionado);
                });
            });

            HBox itemBox = new HBox(10, txtComentarioItem, btnSeleccionarOrden);
            contenedorItems.getChildren().add(itemBox);
            itemBox.setAlignment(Pos.CENTER);

        });

        btnCrearNovedad.setOnAction(e-> {
                List<NovedadItem> items = new ArrayList<>();

                for(Node node : contenedorItems.getChildren()){
                    if(node instanceof HBox itemBox){
                        TextArea comentarioArea = null;
                        String ordenId = "";

                        for(Node child : itemBox.getChildren()){
                            if(child instanceof TextArea area){
                                comentarioArea = area;
                            }else if(child instanceof Button btnSeleccionarOrden){
                                ordenId = btnSeleccionarOrden.getUserData() != null ?
                                        btnSeleccionarOrden.getUserData().toString() : "";
                                System.out.println("ORDEN ID EN BOTÓN: " + ordenId);
                            }
                        }

                        if(comentarioArea != null || !comentarioArea.getText().trim().isEmpty()){
                            items.add(new NovedadItem(ordenId, comentarioArea.getText().trim()));
                        }

                    }
                }
            if(items.isEmpty()){
                mostrarError("Debe agregar al menos un item a la novedad",
                        "Ocurrió error");
            }

            NovedadModelo nuevaNovedad = new NovedadModelo(
                    LocalDateTime.now(),
                    items);

            boolean guardado = NotificacionesControlador.crearNovedad(nuevaNovedad);

            if(guardado){
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Novedad guardada");
                alerta.setHeaderText(null);
                alerta.setContentText("La novedad se ha guardado correctamente.");
                alerta.showAndWait();

                ventanaFormNovedades.close();

            }else{
                mostrarError("No se pudo guardar la novedad.", "Ocurrió" +
                        " un error");
            }


        });

        btnCancelar.setOnAction(e->{ventanaFormNovedades.close();});

        VBox botonesBox = new VBox(10,
                btnAgregarItem, btnCancelar);

        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.setPadding(new Insets(10));

        VBox layout = new VBox(25, titulo, botonesBox, scrollPaneItems, btnCrearNovedad);
        layout.setPadding(new Insets(15));
        layout.setPrefWidth(600);
        layout.setAlignment(Pos.CENTER);

        Scene escena = new Scene(layout);
        ventanaFormNovedades.setScene(escena);
        ventanaFormNovedades.sizeToScene();
        ventanaFormNovedades.setResizable(true);
        ventanaFormNovedades.show();
        }

        //MOSTRAR NOVEDADES
    public void mostrarListaNovedades(){
        Stage ventana = new Stage();
        ventana.setTitle("Lista de novedades");
        ventana.initModality(Modality.APPLICATION_MODAL);

        TableView<ObservableList<String>> table = new TableView<>();

        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();

        String[] nombresCampos = {
                "Id", "Orden_id", "Comentario",
                "Fecha", "Admin_id"
        };

        for(String nombreCampo : nombresCampos){
            final int colIndex = table.getColumns().size();
            TableColumn<ObservableList<String>, String> columna =
                    new TableColumn<>(nombreCampo);

            columna.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                    (param.getValue().size() > colIndex) ?
                            param.getValue().get(colIndex) : ""));
            columna.setPrefWidth(switch(nombreCampo){
                case "Id" -> 100;
                case "Orden_id" -> 100;
                case "Comentario" -> 150;
                case "Fecha" -> 150;
                case "Admin_id" -> 120;
                default -> 120;
            });
            table.getColumns().add(columna);
        }

        String consultaSQL = "SELECT N.ID, I.ORDENID, I.COMENTARIOITEM, " +
                "N.FECHA, N.ADMIN_ID FROM NOVEDADES N" +
                " JOIN NOVEDAD_ITEM I ON N.ID = I.NOVEDADID" +
                " ORDER BY FECHA ASC";

        try (PreparedStatement consultaPreparada = GestionRepControl.conexion.prepareStatement(consultaSQL)) {
            ResultSet resultado = consultaPreparada.executeQuery();

            while (resultado.next()) {
                ObservableList<String> fila =
                        FXCollections.observableArrayList();

                String id= resultado.getString("Id");
                String orden_id = resultado.getString("OrdenId");
                String comentario = resultado.getString("ComentarioItem");
                String fecha = resultado.getString("Fecha");
                String admin_id = resultado.getString("Admin_id");

                if(admin_id == null) {
                 admin_id = "0";
                }

                //INSERTAR VARIABLES EN FILA
                fila.addAll(id, orden_id, comentario, fecha, admin_id.toString());

                //INSERTAR CADA FILA EN DATOS
                datos.add(fila);
            }

            //INSERTAR TODAS LAS FILAS EN LA TABLA DE FILAS DE STRING
            table.setItems(datos);
    }catch (SQLException e) {
        mostrarError("Error al listar items!", "Ocurrió un error");
        e.printStackTrace();
        }

        Button btnVerNovedad = new Button("Ver");
        Button btnEliminar = new Button("Eliminar");
        Button btnCerrar = new Button("Cerrar");

        btnVerNovedad.setOnAction(e->{

        });

        btnEliminar.setOnAction(e->{
            String nov_id = "";
                NotificacionesControlador.eliminarNovedad(nov_id);
        });

        btnCerrar.setOnAction(e -> {
            ventana.close();
        });

        //LAYOUT
        HBox botonesBox = new HBox(10,
                btnVerNovedad, btnEliminar, btnCerrar);

        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.setSpacing(10);

        VBox layout = new VBox(10, table, botonesBox);
        layout.setPadding(new Insets(10, 10, 10, 0));

        Scene scene = new Scene(layout);
        ventana.setScene(scene);
        ventana.show();
    }

    //MOSTRAR ERRORES
    private static void mostrarError(String mensaje, String header){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(header);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    //CREAR TAREA A PARTIR DE NOVEDAD
	public void crearTareaAPartirDeNovedad(NovedadModelo novedad) {
	}

    //VER UNA NOVEDAD
    public void verNovedad(String nov_id){
        List<Map<String, Object>> datos = NotificacionesControlador.obtenerNovedadPorId(nov_id);

        Stage ventanaVerOrden = new Stage();
        ventanaVerOrden.setTitle("Novedad: " + nov_id);

        //TITULO
        Label titulo = new Label("Novedad - " + nov_id);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //CONTENEDOR
        VBox contenedor = new VBox(10);
        contenedor.setPadding(new Insets(15));
        contenedor.getChildren().add(titulo);

        //AQUI VA EL CONSTRUIR VIDA DE NOVEDAD
    }

    public static void construirVistaNovedad(VBox contenedor,
                                             List<Map<String, Object>> datos,
                                             String nov_id) {
        //AQUI VA EL RESTO PARA PODER VER LA ORDEN
    }
	
	//Getters and Setters
	
	public String getNovedadId() {
		return novedadId;
	}
	
	public void setNovedadId(String novedadId) {
		this.novedadId = novedadId;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	

	public List<OrdenTrabajoModelo> getOrdenesAsociadas() {
		return ordenesAsociadas;
	}

	public void setOrdenesAsociadas(List<OrdenTrabajoModelo> ordenesAsociadas) {
		this.ordenesAsociadas = ordenesAsociadas;
	}
	
}
