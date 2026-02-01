package com.mfernandez.spi.gist;


import conexion.PasswordHasher;
import daos.Personal.AdminDao;
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
import java.sql.SQLException;

//JAVA SECURITY IMPORTS
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.Base64;

public class MainFX extends Application {
	
	  @Override
	    public void start(Stage stage) throws SQLException {
		  OrdenTrabajoVista ordenVista = new OrdenTrabajoVista(null, null, null, null, null, null, null, null, null);
		  // Conexión y credenciales
	        Credenciales credenciales = new Credenciales(null, null, null, null, null, null);
	        LoginVista loginVista = new LoginVista(null);
	        Connection conn = ConexionDB.conectar();
			AdminDao adminDao = new AdminDao();

		  if (conn != null) {
			  System.out.println("La conexión está funcionando correctamente");
		  } else {
			  System.out.println("La conexión falló");
		  }

		  if (!adminDao.existeSuperAdmin()) {
			  String id = "ADM000";
			  String email = "superadmin@sistema.local"; // ideal: tomar de config
			  String pwd = System.getenv().get("SUPER_ADMIN_KEY");
			  String hash = PasswordHasher.hash(pwd.toCharArray());

			  adminDao.crearSuperAdmin(id, "Super", "Admin", email, hash);
			  System.out.println("[BOOT] SUPER_ADMIN creado: " + email);
		  }

	        // INICIO DE SESIÓN
	        if (!loginVista.ingresarCredenciales(credenciales)) {
	            System.out.println("Credenciales inválidas. Por favor, intentalo de nuevo.");
	            return;
	        }


	     //TITULO
	        Text titulo = new Text("💻 GIST");
	        titulo.setFont(Font.font("Readex Pro", FontWeight.BOLD, 36));

	       
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(20);
	        grid.setVgap(15);
	        grid.setPadding(new Insets(20));

	        //BOTONES DE ACCESO
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

	        Button btn9 = new Button("🔨 Módulo AI");
	        btn9.setOnAction(e -> ordenVista.mostrarMenuOrdenesTrabajo());

	        Button btn10 = new Button("🔚 Salir");
	        btn10.setOnAction(e -> stage.close());

	        //ESTILO PARA BOTONES
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

	        // GRID DE 2X5
	        int cols = 2;
	        for (int i = 0; i < botones.length; i++) {
	            grid.add(botones[i], i % cols, i / cols);
	        }

	        // LAYOUT
	        VBox root = new VBox(25);
	        root.setAlignment(Pos.TOP_CENTER);
	        root.setPadding(new Insets(30));
	        root.getChildren().addAll(titulo, grid);
	        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");

	        //ESCENA
	        Scene scene = new Scene(root, 650, 520);
	        stage.setTitle("Menú Principal - GIST");
	        stage.setScene(scene);
	        stage.show();
	    }

        //METODO MAIN
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
