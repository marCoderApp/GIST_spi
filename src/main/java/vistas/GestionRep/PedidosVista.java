package vistas.GestionRep;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import controladores.GestionRepControl;
import daos.GestioRep.PedidosDao;
import daos.Personal.AdminDao;
import dtos.PedidosDto;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import modelos.GestionRep.*;
import javafx.scene.*;
import javafx.stage.*;

public class PedidosVista {

	private String pedidoId;
	private List<PedidoModelo> listaPedidos = new ArrayList<>();
	private PedidoModelo pedidoSeleccionado;
	private String filtroCodigoOrden;
	private int cantidadTotal;

    //METODO CONSTRUCTOR
	public PedidosVista(String pedidoId, List<PedidoModelo> listaPedidos, PedidoModelo pedidoSeleccionado,
			String filtroCodigoOrden, int cantidadTotal) {
		this.pedidoId = pedidoId;
		this.listaPedidos = listaPedidos;
		this.pedidoSeleccionado = pedidoSeleccionado;
		this.filtroCodigoOrden = filtroCodigoOrden;
		this.cantidadTotal = cantidadTotal;
		
	}

    //CREAR PEDIDO
	public PedidoModelo opcionCrearPedido() {
		// Lógica para crear un nuevo pedidonew
        return null;
	}
	
	//MOSTRAR MENSAJE
	public void mostrarMensaje(String mensaje) {
		// Lógica para mostrar un mensaje al usuario
		System.out.println(mensaje);
	}

    //MOSTRAR LISTA DE PEDIDOS
	public void mostrarListaPedidos(List<PedidoModelo> listaPedidos) {
		// Lógica para mostrar la lista de pedidos
		for (PedidoModelo pedido : listaPedidos) {
			System.out.println(pedido);
		}
	}
	

    //MENU PARA REALIZAR FUNCIONALIDADES DE PEDIDOS.
	public void mostrarMenuPedidos() {
     		Stage ventanaPedidos = new Stage();
			 ventanaPedidos.setTitle("Pedidos");

			 Label titulo = new Label("Gestion de Pedidos");
		titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		//botones
		Button btnCrearPedido = new Button("Crear pedido");
		Button btnListarPedidos = new Button("Listar pedidos");
		Button btnBuscarPedidos = new Button("Buscar pedidos");
		Button btnCerrar = new Button("Cerrar");

		btnListarPedidos.setPrefWidth(250);
		btnCrearPedido.setPrefWidth(250);
		btnBuscarPedidos.setPrefWidth(250);
		btnCerrar.setPrefWidth(250);

		btnListarPedidos.setOnAction(e->{
			listarPedidos();
		});

		btnCrearPedido.setOnAction(e->{
			mostrarFormCrearPedidos();
		});

		btnBuscarPedidos.setOnAction(e->{
			buscarPedidos();
		});

		btnCerrar.setOnAction(e->{
			ventanaPedidos.close();
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

		btnListarPedidos.setStyle(estiloBoton);
		btnCrearPedido.setStyle(estiloBoton);
		btnBuscarPedidos.setStyle(estiloBoton);
		btnCerrar.setStyle(estiloBoton);

		// Hover effects
		Button[] botones = {btnListarPedidos, btnCrearPedido,
				btnBuscarPedidos, btnCerrar};
		for (Button btn : botones) {
			btn.setOnMouseEntered(e -> btn.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
			btn.setOnMouseExited(e -> btn.setStyle(estiloBoton));
		}

		VBox layout = new VBox(15);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(30));
		layout.getChildren().addAll(titulo,
				btnListarPedidos,
				btnCrearPedido,
				btnBuscarPedidos,
				btnCerrar);
		layout.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");

		Scene scene = new Scene(layout, 400, 450);
		ventanaPedidos.setScene(scene);
		ventanaPedidos.show();
    }

    //LISTAR PEDIDOS
    public void listarPedidos(){
        Stage ventana = new Stage();
        ventana.setTitle("Lista de pedidos");
        ventana.initModality(Modality.APPLICATION_MODAL);

        Label titulo = new Label("Lista de pedidos");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Obtener pedidos de la base de datos
        List<PedidosDto> pedidos = PedidosDao.listarTodosPedidos();
        ObservableList<PedidosDto> datosPedidos = FXCollections.observableArrayList(pedidos);

        // Crear tabla
        TableView<PedidosDto> tablaPedidos = new TableView<>();
        tablaPedidos.setItems(datosPedidos);
        tablaPedidos.setPrefHeight(400);

        // Columna ID
        TableColumn<PedidosDto, String> colId = new TableColumn<>("ID Pedido");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(150);

        // Columna Fecha
        TableColumn<PedidosDto, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFecha.setPrefWidth(120);

        // Columna Admin
        TableColumn<PedidosDto, String> colAdmin = new TableColumn<>("Admin ID");
        colAdmin.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        colAdmin.setPrefWidth(120);

        // Columna Estado
        TableColumn<PedidosDto, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setPrefWidth(120);

        // Columna Acciones con botón Ver
        TableColumn<PedidosDto, Void> colAcciones = new TableColumn<>("Acciones");
        colAcciones.setPrefWidth(100);

        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnVer = new Button("Ver");

            {
                btnVer.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                btnVer.setOnAction(event -> {
                    PedidosDto pedido = getTableView().getItems().get(getIndex());
                    mostrarDetallesPedido(pedido);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnVer);
                }
            }
        });

        tablaPedidos.getColumns().addAll(colId, colFecha, colAdmin, colEstado, colAcciones);

        // Botón cerrar
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setPrefWidth(150);
        btnCerrar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCerrar.setOnAction(e -> ventana.close());

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titulo, tablaPedidos, btnCerrar);

        Scene scene = new Scene(layout, 700, 550);
        ventana.setScene(scene);
        ventana.showAndWait();
    }

	//MOSTRAR FORM CREAR PEDIDOS
	public void mostrarFormCrearPedidos(){
        Stage ventana = new Stage();
        ventana.setTitle("Crear pedido");
        ventana.initModality(Modality.APPLICATION_MODAL);

        Label titulo = new Label("Crear pedido");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setPadding(new Insets(10, 0, 10, 0));

        // INPUTS (manual por texto)
        TextField txtNombreRepuesto = new TextField();
        txtNombreRepuesto.setPromptText("Nombre del repuesto");
        txtNombreRepuesto.setPrefWidth(250);

        TextField txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");
        txtCantidad.setPrefWidth(250);

        TextField txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio unitario");
        txtPrecio.setPrefWidth(250);

        TextField txtDestinatario = new TextField();
        txtDestinatario.setPromptText("Destinatario");
        txtDestinatario.setPrefWidth(250);

        Button btnAgregar = new Button("Agregar repuesto");
        btnAgregar.setPrefWidth(250);

        // TABLA (se mantiene)
        TableView<RepuestoModelo> tablaItems = new TableView<>();
        ObservableList<RepuestoModelo> items = FXCollections.observableArrayList();

        TableColumn<RepuestoModelo, String> colNombre = new TableColumn<>("Repuesto");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(220);

        TableColumn<RepuestoModelo, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCantidad.setPrefWidth(90);

        TableColumn<RepuestoModelo, Double> colPrecio = new TableColumn<>("Precio Unit.");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colPrecio.setPrefWidth(110);

        TableColumn<RepuestoModelo, String> colDest = new TableColumn<>("Destinatario");
        colDest.setCellValueFactory(new PropertyValueFactory<>("destinatario"));
        colDest.setPrefWidth(160);

        tablaItems.getColumns().addAll(colNombre, colCantidad, colPrecio, colDest);
        tablaItems.setItems(items);
        tablaItems.setPrefHeight(220);

        PedidosDao pedidosDao = new PedidosDao();

        final String pedidoIdCreado = "PED" + System.currentTimeMillis();

        PedidosDto pedidoDto = new PedidosDto(null,
                null,
                null,
                null
        );
        pedidoDto.setId(pedidoIdCreado);
        pedidoDto.setFecha(LocalDateTime.now());
        pedidoDto.setAdminId(AdminDao.adminActualId);
        pedidoDto.setEstado("Pendiente");

        boolean ok = PedidosDao.crearPedido(pedidoDto);
        if (!ok) {
            mostrarMensaje("No se pudo crear el pedido en la base de datos.");
            return;
        }

        btnAgregar.setOnAction(e -> {
            String nombre = txtNombreRepuesto.getText().trim();
            String destinatario = txtDestinatario.getText().trim();

            if (nombre.isEmpty()) {
                mostrarMensaje("Ingrese el nombre del repuesto.");
                return;
            }
            if (txtCantidad.getText().trim().isEmpty() || txtPrecio.getText().trim().isEmpty()) {
                mostrarMensaje("Ingrese cantidad y precio unitario.");
                return;
            }
            if(txtDestinatario.getText().trim().isEmpty()){
                mostrarMensaje("Ingrese destinatario.");
                return;
            }

            int cantidad;
            double precio;
            try {
                cantidad = Integer.parseInt(txtCantidad.getText().trim());
                precio = Double.parseDouble(txtPrecio.getText().trim());
            } catch (NumberFormatException ex) {
                mostrarMensaje("Cantidad o precio inválidos.");
                return;
            }

            String repuestoId = "REP" + System.currentTimeMillis();
            pedidosDao.agregarItemManualAPedido(pedidoIdCreado, repuestoId, nombre, cantidad, precio, destinatario);


            items.add(new RepuestoModelo(null, nombre, cantidad, precio, destinatario, false));

            txtNombreRepuesto.clear();
            txtCantidad.clear();
            txtPrecio.clear();
            txtDestinatario.clear();
        });

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setPrefWidth(120);
        btnGuardar.setOnAction(e ->
                ventana.close());

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefWidth(120);
        btnCancelar.setOnAction(e -> ventana.close());

        HBox barraBotones = new HBox(10, btnGuardar, btnCancelar);
        barraBotones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(12,
                titulo,
                txtNombreRepuesto,
                txtCantidad,
                txtPrecio,
                txtDestinatario,
                btnAgregar,
                tablaItems,
                barraBotones
        );
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene escena = new Scene(layout, 650, 520);
        ventana.setScene(escena);
        ventana.showAndWait();
	}

    //MOSTRAR DETALLES DEL PEDIDO
    private void mostrarDetallesPedido(PedidosDto pedido) {
        Stage ventanaDetalle = new Stage();
        ventanaDetalle.setTitle("Detalles del Pedido");
        ventanaDetalle.initModality(Modality.APPLICATION_MODAL);

        Label titulo = new Label("Detalles del Pedido: " + pedido.getId());
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // Información del pedido
        Label lblFecha = new Label("Fecha: " + pedido.getFecha());
        lblFecha.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Label lblAdmin = new Label("Admin ID: " + pedido.getAdminId());
        lblAdmin.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Label lblEstado = new Label("Estado: " + pedido.getEstado());
        lblEstado.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label lblRepuestos = new Label("Repuestos:");
        lblRepuestos.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblRepuestos.setPadding(new Insets(10, 0, 5, 0));

        // Obtener repuestos del pedido
        List<RepuestoModelo> repuestos = PedidosDao.obtenerRepuestosDePedido(pedido.getId());
        ObservableList<RepuestoModelo> datosRepuestos = FXCollections.observableArrayList(repuestos);

        // Tabla de repuestos
        TableView<RepuestoModelo> tablaRepuestos = new TableView<>();
        tablaRepuestos.setItems(datosRepuestos);
        tablaRepuestos.setPrefHeight(300);

        TableColumn<RepuestoModelo, String> colNombre = new TableColumn<>("Repuesto");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(200);

        TableColumn<RepuestoModelo, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCantidad.setPrefWidth(100);

        TableColumn<RepuestoModelo, Double> colPrecio = new TableColumn<>("Precio Unit.");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colPrecio.setPrefWidth(110);

        TableColumn<RepuestoModelo, String> colDestinatario = new TableColumn<>("Destinatario");
        colDestinatario.setCellValueFactory(new PropertyValueFactory<>("destinatario"));
        colDestinatario.setPrefWidth(150);

        TableColumn<RepuestoModelo, String> colRecibido = new TableColumn<>("Recibido");
        colRecibido.setCellValueFactory(new PropertyValueFactory<>("recibido"));
        colRecibido.setPrefWidth(100);

        TableColumn<RepuestoModelo, Void> colAccion = new TableColumn<>("Accion");
        colAccion.setPrefWidth(100);
        colAccion.setCellFactory(param -> new TableCell<>() {
            private final Button btnRecibir = new Button("Recibir");

            {
                btnRecibir.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
                btnRecibir.setOnAction(event -> {
                    modelos.GestionRep.RepuestoModelo repuesto = getTableView().getItems().get(getIndex());
                    Boolean esActualizado = PedidosDao.marcarRepuestoComoRecibido(repuesto);

                    if (esActualizado){
                        OrdenTrabajoVista.mostrarAlertaExito("Actualizado",
                                "El repuesto se ha marcado como recibido.");
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnRecibir);
                }
            }
        });

        tablaRepuestos.getColumns().addAll(colNombre,
                colCantidad,
                colPrecio,
                colDestinatario,
                colRecibido,
                colAccion);

        // Calcular total
        double total = repuestos.stream()
                .mapToDouble(r -> r.getCantidad() * r.getPrecioUnitario())
                .sum();

        Label lblTotal = new Label(String.format("Total: $%.2f", total));
        lblTotal.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblTotal.setStyle("-fx-text-fill: #2E7D32;");

        // Botónes
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setPrefWidth(120);
        btnCerrar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCerrar.setOnAction(e -> ventanaDetalle.close());

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                titulo,
                lblFecha,
                lblAdmin,
                lblEstado,
                lblRepuestos,
                tablaRepuestos,
                lblTotal,
                btnCerrar
        );

        Scene scene = new Scene(layout, 600, 550);
        ventanaDetalle.setScene(scene);
        ventanaDetalle.showAndWait();
    }

    //BUSCAR PEDIDO
    public void buscarPedidos(){
        Stage ventana = new Stage();
        ventana.setTitle("Buscar pedido");
        ventana.initModality(Modality.APPLICATION_MODAL);

        Label titulo = new Label("Elija criterio de búsqueda:");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        ComboBox<String> cbCriterio = new ComboBox<>();
        cbCriterio.getItems().addAll("Repuesto",
                "Fecha",
                "Destinatario");

        cbCriterio.setPromptText("Seleccione un criterio");
        cbCriterio.setPrefWidth(200);


        Button btnBuscar = new Button("Buscar");
        btnBuscar.setPrefWidth(100);
        btnBuscar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnBuscar.setOnAction(e -> {
            String criterioSeleccionado = cbCriterio.getValue();

            if (criterioSeleccionado == null){
                OrdenTrabajoVista.mostrarAdvertencia("Debe seleccionar un criterio para continuar");
                return;
            }

            String criterio = switch (criterioSeleccionado){
                case "Repuesto" -> "nombre_repuesto";
                case "Fecha" -> "fecha";
                case "Destinatario" -> "destinatario";
                default -> null;
            };

            if (criterio == null){
                OrdenTrabajoVista.mostrarAdvertencia("Criterio de busqueda no reconocido!");
                return;
            }

            mostrarFormBusquedaPedidos(criterio);
        });

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setPrefWidth(100);
        btnCerrar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCerrar.setOnAction(e -> ventana.close());

        HBox barraBotones = new HBox(10, btnBuscar, btnCerrar);
        barraBotones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, titulo, cbCriterio, barraBotones);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 350, 150);
        ventana.setScene(scene);
        ventana.showAndWait();

    }

    //MOSTRAR FORM DE BUSQUEDA DE PEDIDOS.
    public void mostrarFormBusquedaPedidos(String criterio){
        Stage ventana = new Stage();
        ventana.setTitle("Buscar pedido");
        ventana.initModality(Modality.APPLICATION_MODAL);

        Label titulo = new Label("Busqueda de pedido avanzada");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //INPUT
        TextField inputDatoBusqueda = new TextField();
        inputDatoBusqueda.setPrefWidth(200);
        inputDatoBusqueda.setPromptText("Ingrese dato de busqueda");

        Button btnBuscar = new Button("Buscar");
        btnBuscar.setPrefWidth(100);
        btnBuscar.setOnAction(e -> {
            String dato = inputDatoBusqueda.getText();
            ObservableList<ObservableList<String>> pedidos = GestionRepControl
                        .buscarPedidoPorCriterio(criterio, dato);

            if (pedidos == null || pedidos.isEmpty()){
                OrdenTrabajoVista.mostrarAdvertencia("No se encontraron resultados.");
                return;
            }

            TableView<ObservableList<String>> tablaResultados = new TableView<>();
            tablaResultados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            String [] nombresCampos = {
                    "Pedido ID",
                    "Fecha",
                    "Admin ID",
                    "Estado",
                    "Repuesto ID",
                    "Nombre Repuesto",
                    "Cantidad",
                    "Precio Unitario",
                    "Destinatario",
                    "Recibido"
            };

            for(String nombreCampo : nombresCampos){
                final int colIndex = tablaResultados.getColumns().size();
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(nombreCampo);

                column.setCellValueFactory(param ->
                        new ReadOnlyStringWrapper(param.getValue().size() > colIndex ? param.
                                getValue().get(colIndex): ""));
                column.setPrefWidth(switch (nombreCampo){
                    case "Pedido ID" -> 150;
                    case "Fecha" -> 150;
                    case "Admin ID" -> 150;
                    case "Estado" -> 150;
                    case "Repuesto ID" -> 150;
                    case "Nombre Repuesto" -> 150;
                    case "Cantidad" -> 100;
                    case "Precio Unitario" -> 150;
                    case "Destinatario" -> 150;
                    case "Recibido" -> 100;
                    default -> 0;
                });
                tablaResultados.getColumns().add(column);
            }

            tablaResultados.setItems(pedidos);
            tablaResultados.setPrefHeight(300);

            Stage ventanaResultados = new Stage();
            ventanaResultados.setTitle("Resultados de la busqueda");
            ventana.setMinWidth(800);
            Label tituloResultados = new Label("Resultados de la busqueda");
            tituloResultados.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            Button btnCerrar = new Button("Cerrar");
            btnCerrar.setOnAction(event->{
                ventanaResultados.close();
            });

            HBox barraBotones = new HBox(10, btnCerrar);
            barraBotones.setAlignment(Pos.CENTER);
            barraBotones.setPadding(new Insets(10, 0, 0, 0));

            VBox layout = new VBox(10);
            layout.setAlignment(Pos.CENTER);
            layout.getChildren().addAll(tituloResultados, tablaResultados, barraBotones);

            Scene escena = new Scene(layout, 600, 400);
            ventanaResultados.setScene(escena);
            ventanaResultados.showAndWait();

        });

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setOnAction(event->{
            ventana.close();
        });

        HBox barraBotones = new HBox(10, btnBuscar, btnCerrar);
        barraBotones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(titulo, inputDatoBusqueda, barraBotones);

        Scene escena = new Scene(layout, 350, 150);
        ventana.setScene(escena);
        ventana.showAndWait();
    }

    //GETTERS AND SETTERS
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



}
