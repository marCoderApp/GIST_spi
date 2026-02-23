package vistas.GestionRep;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controladores.GestionRepControl;
import daos.GestioRep.PedidosDao;
import daos.Personal.AdminDao;
import dtos.PedidosDto;
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
			System.out.println("Buscar pedido");
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
                null,
                null);
        pedidoDto.setPedidoId(pedidoIdCreado);
        pedidoDto.setFecha(LocalDate.now().toString());
        pedidoDto.setRepuestos("");
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


            items.add(new RepuestoModelo(null, nombre, cantidad, precio, destinatario));

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
