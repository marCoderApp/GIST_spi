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
import java.util.concurrent.atomic.AtomicReference;

import modelos.GestionRep.OrdenTrabajoModelo;
import vistas.GestionRep.OrdenTrabajoVista;

import static vistas.GestionRep.OrdenTrabajoVista.mostrarAdvertencia;


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
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
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

            //Boton vincular orden
            Button btnVincularOrden = new Button("Vincular orden");
            btnVincularOrden.setPrefWidth(250);
            btnVincularOrden.setStyle(estiloBoton);

            btnVincularOrden.setOnAction(event -> {

                String orden_id = buscarOrdenParaVincular();

                if (orden_id != null){
                    btnVincularOrden.setUserData(orden_id);
                    btnVincularOrden.setText(orden_id);
                }else{
                    mostrarError("Debe seleccionar una orden!", "Orden no seleccionada!");
                }
            });

            HBox itemBox = new HBox(10, txtComentarioItem, btnVincularOrden);
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
                            }else if(child instanceof Button btnVincularOrden){
                                ordenId = btnVincularOrden.getUserData() != null ?
                                        btnVincularOrden.getUserData().toString() : "";
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

        //layout
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
                " LEFT JOIN NOVEDAD_ITEM I ON N.ID = I.NOVEDADID" +
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
            ObservableList<String> seleccionado = table.getSelectionModel()
                    .getSelectedItem();
            String nov_id = seleccionado.get(0);
                if (nov_id != null || nov_id.isEmpty()){
                    verNovedad(nov_id);
                }else{
                    mostrarAdvertencia("Debe seleccionar una novedad para ver");
                }
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

        Stage ventanaVerNovedad = new Stage();
        ventanaVerNovedad.setTitle("Novedad: " + nov_id);

        //TITULO
        Label titulo = new Label("Novedad - " + nov_id);
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //CONTENEDOR
        VBox contenedor = new VBox(10);
        contenedor.setPadding(new Insets(15));
        contenedor.getChildren().add(titulo);

        //Metodo para construir la vista de novedad
        construirVistaNovedad(contenedor, datos);

        ScrollPane scrollPane = new ScrollPane(contenedor);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500);

        Scene scene = new Scene(scrollPane, 600, 650);
        ventanaVerNovedad.setScene(scene);
        ventanaVerNovedad.show();
    }

    //CONSTRUIR VISTA DE NOVEDAD
    public static void construirVistaNovedad(VBox contenedor,
                                             List<Map<String, Object>> datos) {
        //AQUI VA EL RESTO PARA PODER VER LA ORDEN
        contenedor.getChildren().clear();
        int contador = 1;
        for (Map<String, Object> fila : datos) {
            Label numItem = new Label("Item número: " + contador);
            numItem.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            Label lblFecha = new Label("Fecha creación: " + (fila.get("FECHA")));
            Label lblComentario = new Label("Comentario: ");
            TextArea comentarioArea = new TextArea((String) fila.get("COMENTARIO"));
            comentarioArea.setWrapText(true);
            comentarioArea.setPromptText("Sin comentario");
            comentarioArea.setEditable(false);
            comentarioArea.setPrefRowCount(5);
            Label lblAdminID = new Label("Admin ID: " + fila.get("ADMIN_ID"));
            Label lblItemID = new Label("Item ID: " + fila.get("ITEMID"));
            Label lblOrdenID = new Label("Orden ID: " + fila.get("OrdenID"));

            contenedor.getChildren().addAll(
                   numItem,
                    lblFecha,
                    new Separator(),
                    lblComentario, comentarioArea,
                    lblAdminID, lblItemID, lblOrdenID,
                    new Separator(),
                    new Separator()
            );
            contador++;
        }
    }
	//BUSCAR ORDEN PARA VINCULAR
    public static String buscarOrdenParaVincular(){
        AtomicReference<String> orden_id = new AtomicReference<>();

        //VENTANA
        Stage ventana = new Stage();
        ventana.setTitle("Busqueda de Orden");

        //TITULO
        Label titulo = new Label("Elija criterio de búsqueda");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //DROPDOWN
        ComboBox<String> criterioOpciones = new ComboBox<>();
        criterioOpciones.getItems().addAll("Orden ID",
                "Apellido Cliente",
                "Estado",
                "Fecha ingreso",
                "Empresa",
                "Tipo de máquina",
                "Marca",
                "Telefono");
        criterioOpciones.setPromptText("Seleccione un criterio");
        criterioOpciones.setPrefWidth(200);

        //BOTONES
        Button botonBuscar = new Button("Aceptar");
        Button botonCancelar = new Button("Cancelar");

        botonBuscar.setOnAction(e-> {
            String criterioSeleccionado = criterioOpciones.getValue();

            if(criterioSeleccionado == null){
                mostrarAdvertencia("Debe seleccionar un criterio de busqueda para continuar!!");
                return;
            }

            String criterio = switch (criterioSeleccionado) {
                case "Orden ID" -> "O.ORDEN_TRABAJO_ID";
                case "Apellido Cliente" -> "C.APELLIDO";
                case "Estado" -> "O.ESTADO";
                case "Fecha ingreso" -> "O.FECHA_INGRESO";
                case "Empresa" -> "EMPRESA";
                case "Tipo de máquina" -> "M.TIPO";
                case "Marca" -> "MARCA";
                case "Telefono" -> "TELEFONO";
                default -> null;
            };

            if(criterio == null) {
                mostrarAdvertencia("Criterio de búsqueda no reconocido: " + criterioSeleccionado);
                return;
            }

            orden_id.set(mostrarFormBusquedaParaVincularOrden(criterio, criterioSeleccionado,
                    ventana));

        });
        botonBuscar.setStyle("-fx-base: #008000;");

        botonCancelar.setOnAction(e -> ventana.close());
        botonCancelar.setStyle("-fx-base: #FF0000;");

        //BOTON LAYOUT
        HBox botonLayout = new HBox(10, botonBuscar, botonCancelar);
        botonLayout.setAlignment(Pos.CENTER);

        //LAYOUT
        VBox layout = new VBox(10, titulo, criterioOpciones, botonLayout);
        layout.setPadding(new Insets(10));

        Scene escena = new Scene(layout);
        ventana.setScene(escena);
        ventana.showAndWait();
        return orden_id.get();
    }


    //MOSTRAR FORM DE BUSQUEDA PARA VINCULAR.
    public static String mostrarFormBusquedaParaVincularOrden(String criterio,
                                                              String seleccionado,
                                                              Stage ventanaSeleccionada){
        AtomicReference<String> orden_id = new AtomicReference<>();
        Stage ventana = new Stage();
        ventana.setTitle("Busqueda de orden avanzada por:");
        ventana.initModality(Modality.APPLICATION_MODAL);

        //TITULO
        Label titulo = new Label("Busqueda de orden avanzada");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //INPUT
        TextField inputDato = new TextField();
        inputDato.setPromptText("Ingrese dato de busqueda");
        inputDato.setPrefWidth(20);

        //BOTON BUSCAR
        Button botonBuscar = new Button("Buscar");
        botonBuscar.setOnAction(e -> {
            String dato = inputDato.getText();
            ObservableList<ObservableList<String>> resultado = GestionRepControl.buscarOrdenCriterio(criterio, dato);
            if (resultado == null || resultado.isEmpty()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "No se encontraron resultados.");
                a.setHeaderText("Búsqueda");
                a.showAndWait();
                return;
            }

            //TABLA DE RESULTADOS DE BUSQUEDA
            TableView<ObservableList<String>> tabla =
                    new TableView<>();
            tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            String[] nombresCampos = {
                    "Orden_Trabajo_ID",
                    "Fecha_Ingreso",
                    "Descripcion_Fallas",
                    "Estado",
                    "Cliente",
                    "Empresa",
                    "Telefono",
                    "Maquina_ID",
                    "Tipo",
                    "Marca",
                    "Modelo",
                    "Descripcion_falla"
            };

            //AGREGAR NOMBRES A COLUMNAS DINAMICAMENTE
            for(String nombreCampo : nombresCampos) {
                final int colIndex = tabla.getColumns().size();
                TableColumn<ObservableList<String>, String> columna = new TableColumn<>(nombreCampo);

                columna.setCellValueFactory(param ->
                        new ReadOnlyStringWrapper(param.getValue().size() > colIndex ?
                                param.getValue().get(colIndex) : ""));
                columna.setPrefWidth(switch(nombreCampo){
                    case "Orden_trabajo_id" -> 100;
                    case "Fecha_ingreso" -> 100;
                    case "Descripcion_falla" -> 100;
                    case "Estado" -> 100;
                    case "Cliente" -> 100;
                    case "Empresa" -> 100;
                    case "Telefono" -> 100;
                    case "Maquina_ID" -> 100;
                    case "Tipo" -> 100;
                    case "Marca" -> 100;
                    case "Modelo" -> 100;
                    default -> 120;
                });
                tabla.getColumns().add(columna);
            }

            //AGREGAR DATOS A LA TABLA
            tabla.setItems(resultado);

            // Ventana resultados
            Stage ventanaResultados = new Stage();
            ventanaResultados.setTitle("Resultados de búsqueda");

            Label tituloResultados = new Label("Resultados para: " + seleccionado + " = " + dato);
            tituloResultados.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            Button btnSeleccionarOrden = new Button("Seleccionar");
            Button btnCerrar = new Button("Cerrar");
            btnSeleccionarOrden.setOnAction(event->{
                    ObservableList<ObservableList<String>> ordenSeleccionada =
                            tabla.getSelectionModel().getSelectedItems();

                    if (ordenSeleccionada != null && !ordenSeleccionada.isEmpty()){
                        orden_id.set(ordenSeleccionada.get(0).get(0));
                        ventanaResultados.close();
                        ventanaSeleccionada.close();
                        ventana.close();
                    }else{
                        mostrarError("Debe seleccionar una orden de trabajo.",
                                "Orden no seleccioanda!");
                    }

            });
            btnCerrar.setOnAction(ev -> ventanaResultados.close());

            //Barra de botones.
            HBox barra = new HBox(10,btnSeleccionarOrden,
                    btnCerrar);
            barra.setAlignment(Pos.CENTER_RIGHT);
            barra.setPadding(new Insets(10));

            //Layout
            VBox layoutResultados = new VBox(10, tituloResultados, tabla, barra);
            layoutResultados.setPadding(new Insets(10));

            ventanaResultados.setScene(new Scene(layoutResultados, 700, 400));
            ventanaResultados.initOwner(ventana);
            ventanaResultados.showAndWait();
        });

        botonBuscar.setPrefWidth(100);
        botonBuscar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button botonCancelar = new Button("Cancelar");
        botonCancelar.setOnAction(e -> ventana.close());
        botonCancelar.setPrefWidth(100);
        botonCancelar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        //LAYOUT
        HBox layoutBotones = new HBox(10, botonBuscar, botonCancelar);
        layoutBotones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, titulo, inputDato, layoutBotones);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Scene escena = new Scene(layout, 300, 150);
        ventana.setScene(escena);
        ventana.showAndWait();

    return orden_id.get();
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
