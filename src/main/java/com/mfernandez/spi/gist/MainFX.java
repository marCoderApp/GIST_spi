package com.mfernandez.spi.gist;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modelos.GestionRep.Credenciales;
import vistas.GestionRep.*;
import vistas.Personal.*;
import vistas.Notificacion.*;
import conexion.ConexionDB;
import java.sql.Connection;


public class MainFX extends Application {
	
	  @Override
	    public void start(Stage stage) {
		  OrdenTrabajoVista ordenVista = new OrdenTrabajoVista(null, null, null, null, null, null, null, null, null);
		  // Conexión y credenciales
	        Credenciales credenciales = new Credenciales(null, null, null, null, null, null);
	        LoginVista loginVista = new LoginVista(null);
	        Connection conn = ConexionDB.conectar();

	        if (conn != null) {
	            System.out.println("La conexión está funcionando correctamente");
	        } else {
	            System.out.println("La conexión falló");
	        }

	        // INICIO DE SESIÓN
	        if (!loginVista.ingresarCredenciales(credenciales)) {
	            System.out.println("Credenciales inválidas. Por favor, intentalo de nuevo.");
	            return;
	        }

	     // --- Título --
	        Text titulo = new Text("💻 GIST");
	        titulo.setFont(Font.font("Readex Pro", FontWeight.BOLD, 36));

	       
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(20);
	        grid.setVgap(15);
	        grid.setPadding(new Insets(20));

	        // --- Botones con sus acciones originales ---
	        Button btn1 = new Button("📋 Registrar nueva orden de trabajo");
	        btn1.setOnAction(e -> ordenVista.opcionCrearOrden());

	        Button btn2 = new Button("🧾 Listar órdenes de trabajo");
	        btn2.setOnAction(e -> ordenVista.mostrarLista());

	        Button btn3 = new Button("⚙️ Registrar entrega de máquina/s");
	        btn3.setOnAction(e -> ordenVista.navegar());

	        Button btn4 = new Button("🧑‍ Gestionar clientes");
	        ClientesVista clientesVista = new ClientesVista();
	        btn4.setOnAction(e -> clientesVista.mostrarMenuClientes());

	        Button btn5 = new Button("👷🏻 Gestionar Usuarios");
	        UsuarioVista usuarioVista = new UsuarioVista(null);
	        btn5.setOnAction(e ->  usuarioVista.mostrarMenuUsuarios());

	        Button btn6 = new Button("💡 Gestionar novedades");
	        NovedadesVista novedadesVista = new NovedadesVista(null, null, null, null, null);
	        btn6.setOnAction(e -> novedadesVista.mostrarMenuNovedades());

	        Button btn7 = new Button("💵 Gestionar presupuestos");
	        PresupuestosVista presupuestoVista = new PresupuestosVista(null, null, null);
	        btn7.setOnAction(e -> presupuestoVista.mostrarMenuPresupuestos());

	        Button btn8 = new Button("🚚 Gestionar pedidos");
			PedidosVista pedidosVista = new PedidosVista(null, null, null, null, 0);
	        btn8.setOnAction(e -> pedidosVista.mostrarMenuPedidos());

	        Button btn9 = new Button("🔨 Gestionar órdenes de trabajo");
	        btn9.setOnAction(e -> ordenVista.mostrarMenuOrdenesTrabajo());

	        Button btn10 = new Button("🔚 Salir");
	        btn10.setOnAction(e -> stage.close());

	        // --- Estilo visual uniforme para los botones ---
	        Button[] botones = {btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10};
	        for (Button b : botones) {
	            b.setPrefWidth(280);
	            b.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
	            b.setStyle(
	                "-fx-background-color: white;" +
	                "-fx-text-fill: gray;" +
	                "-fx-border-color: black;" +
	                "-fx-border-width: 1.5;" +
	                "-fx-background-radius: 8;" +
	                "-fx-border-radius: 8;"
	            );
	            b.setOnMouseEntered(e -> b.setStyle(
	                "-fx-background-color: #f5851c;" +
	                "-fx-text-fill: black;" +
	                "-fx-border-color: black;" +
	                "-fx-border-width: 1.5;" +
	                "-fx-background-radius: 8;" +
	                "-fx-border-radius: 8;"
	            ));
	            b.setOnMouseExited(e -> b.setStyle(
	                "-fx-background-color: white;" +
	                "-fx-text-fill: black;" +
	                "-fx-border-color: black;" +
	                "-fx-border-width: 1.5;" +
	                "-fx-background-radius: 8;" +
	                "-fx-border-radius: 8;"
	            ));
	        }

	        // --- Distribución en cuadrícula 2x5 ---
	        int cols = 2;
	        for (int i = 0; i < botones.length; i++) {
	            grid.add(botones[i], i % cols, i / cols);
	        }

	        // --- Layout principal ---
	        VBox root = new VBox(25);
	        root.setAlignment(Pos.TOP_CENTER);
	        root.setPadding(new Insets(30));
	        root.getChildren().addAll(titulo, grid);
	        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");

	        // --- Escena principal ---
	        Scene scene = new Scene(root, 650, 520);
	        stage.setTitle("Menú Principal - GIST");
	        stage.setScene(scene);
	        stage.show();
	    }

	    public static void main(String[] args) {
	    	 try {
	    	        launch(args);
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
	    	            javafx.scene.control.Alert.AlertType.ERROR, 
	    	            "Error: " + e.getMessage()
	    	        );
	    	        alert.showAndWait();
	    	    }
	    }

}
