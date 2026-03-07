package vistas.Personal;


import controladores.PersonalControl;
import modelos.GestionRep.Credenciales;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginVista {
	 private Stage stage;
	    private TextField txtUsuario;
	    private PasswordField txtContrasenia;
	    private Label lblMensaje;

	    public LoginVista(Stage stage) {
	        this.stage = stage;
	    }

	    //FORM PARA INGRESAR credenciales.
	    public boolean ingresarCredenciales(Credenciales credenciales) {
	        Stage loginStage = new Stage();
	        loginStage.setTitle("Inicio de Sesión - GIST");

	        Label lblTitulo = new Label("🔐 Iniciar Sesión");
	        lblTitulo.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;");

	        txtUsuario = new TextField();
	        txtUsuario.setPromptText("Usuario");

	        txtContrasenia = new PasswordField();
	        txtContrasenia.setPromptText("Contraseña");

	        lblMensaje = new Label();

            //BOTONES
	        Button btnIngresar = new Button("Ingresar");
	        Button btnCancelar = new Button("Cancelar");

	        HBox botones = new HBox(10, btnIngresar, btnCancelar);
	        botones.setAlignment(Pos.CENTER);

	        VBox root = new VBox(15, lblTitulo, txtUsuario, txtContrasenia, botones, lblMensaje);
	        root.setAlignment(Pos.CENTER);
	        root.setStyle("-fx-padding: 20; -fx-background-color: #f7f7f7;");

	        Scene scene = new Scene(root, 300, 250);
	        loginStage.setScene(scene);

	        final boolean[] autenticado = {false};

	        btnIngresar.setOnAction(e -> {
	            String usuario = txtUsuario.getText();
	            String contrasenia = txtContrasenia.getText();

	            // Cargar credenciales
	            credenciales.setUsuario(usuario);
	            credenciales.setContrasena(contrasenia);

	            // Usar el controlador de Personal
	            PersonalControl control = new PersonalControl(null, false, false, null);
	            boolean valido = control.validarCredenciales(credenciales);

	            if (valido) {
	                lblMensaje.setText("✅ Inicio de sesión exitoso");
	                autenticado[0] = true;
	                loginStage.close();
	            } else {
	                lblMensaje.setText("❌ Usuario o contraseña incorrectos");
	            }
	        });

	        btnCancelar.setOnAction(e -> {
	            autenticado[0] = false;
	            loginStage.close();
	        });

	        loginStage.showAndWait();
	        return autenticado[0];
	    }
	
}
