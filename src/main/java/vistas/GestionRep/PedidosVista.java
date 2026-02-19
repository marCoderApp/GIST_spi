package vistas.GestionRep;

import java.util.ArrayList;
import java.util.List;

import controladores.GestionRepControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		// Lógica para crear un nuevo pedido
		return new PedidoModelo(filtroCodigoOrden, null, filtroCodigoOrden, filtroCodigoOrden, null);
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
			System.out.println("Listar pedidos");
		});

		btnCrearPedido.setOnAction(e->{
			System.out.println("Crear pedido");
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


		//Escena
		Scene scene = new Scene(layout, 400, 450);
		ventanaPedidos.setScene(scene);
		ventanaPedidos.show();
    }

	//MOSTRAR FORM CREAR PEDIDOS
	public void mostrarFormCrearPedidos(){
		Stage ventana = new Stage();
		ventana.setTitle("Crear pedido");
		ventana.initModality(Modality.APPLICATION_MODAL);

		Label titulo = new Label("Crear pedido");
		titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		titulo.setPadding(new Insets(10, 0, 10, 0));



		HBox layout = new HBox(10);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(10));

		layout.getChildren().addAll(titulo);

		Scene escena = new Scene(layout, 400, 100);
		ventana.setScene(escena);
		ventana.show();
	}

}
