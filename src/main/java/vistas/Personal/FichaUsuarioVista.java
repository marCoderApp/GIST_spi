package vistas.Personal;

import javafx.stage.Stage;
import modelos.Personal.TecnicoModelo;
import modelos.Notificacion.TareasModelo;
import java.util.List;

public class FichaUsuarioVista {

	
	private String usuarioId;
	private String nombre;
	private String apellido;
	private String especialidad;
	private int cantidadTareas;
	
	public FichaUsuarioVista(String usuarioId, String nombre, String apellido, String especialidad,
                             int cantidadTareas) {
		super();
		this.usuarioId = usuarioId;
		this.nombre = nombre;
		this.apellido = apellido;
		this.especialidad = especialidad;
		this.cantidadTareas = cantidadTareas;
	}

    //MENÚ ADMINISTRADORES
    public void mostrarMenuAdministradores(){
        System.out.println("MOSTRAR MENU ADMINISTRADORES");
    }

    //MENÚ TECNICOS
    public void mostrarMenuTecnicos(){

        // Ventana
        Stage gestionTecnicosVentana = new Stage();
        gestionTecnicosVentana.setTitle("Gestión de Técnicos");

        // Título
        javafx.scene.control.Label titulo = new javafx.scene.control.Label("Gestión de Técnicos - GIST");
        titulo.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 24));

        // Botones
        javafx.scene.control.Button botonCrearTecnico = new javafx.scene.control.Button("➕ Crear nuevo técnico");
        javafx.scene.control.Button botonListarTecnicos = new javafx.scene.control.Button("📋 Listar técnicos");
        javafx.scene.control.Button botonBuscarTecnico = new javafx.scene.control.Button("🔍 Buscar técnico");
        javafx.scene.control.Button botonVolver = new javafx.scene.control.Button("🔙 Volver");

        botonCrearTecnico.setPrefWidth(250);
        botonListarTecnicos.setPrefWidth(250);
        botonBuscarTecnico.setPrefWidth(250);
        botonVolver.setPrefWidth(250);

        botonCrearTecnico.setOnAction(e -> opcionCrearTecnico());
        botonListarTecnicos.setOnAction(e -> mostrarLista());
        botonBuscarTecnico.setOnAction(e -> buscarTecnico());
        botonVolver.setOnAction(e -> gestionTecnicosVentana.close());

        // Estilos
        String estiloBoton =
                "-fx-background-color: white;" +
                        "-fx-text-fill: black;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-font-size: 14px;";

        botonCrearTecnico.setStyle(estiloBoton);
        botonListarTecnicos.setStyle(estiloBoton);
        botonBuscarTecnico.setStyle(estiloBoton);
        botonVolver.setStyle(estiloBoton);

        // Hover effects
        javafx.scene.control.Button[] botones = {botonCrearTecnico, botonListarTecnicos, botonBuscarTecnico, botonVolver};
        for (javafx.scene.control.Button btn : botones) {
            btn.setOnMouseEntered(e -> btn.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
            btn.setOnMouseExited(e -> btn.setStyle(estiloBoton));
        }

        // Layout
        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(15);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(30));
        layout.getChildren().addAll(titulo, botonCrearTecnico, botonListarTecnicos, botonBuscarTecnico, botonVolver);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");

        javafx.scene.Scene escena = new javafx.scene.Scene(layout, 400, 450);
        gestionTecnicosVentana.setScene(escena);
        gestionTecnicosVentana.show();

    }


    //OPCIÓN CREAR TÉCNICO
    public void opcionCrearTecnico() {
        System.out.println("CREANDO TECNICO");
    }
	
	public void abrirFicha() {
                // Lógica para abrir la ficha del técnico
    }
	
	public void mostrarFichaDatos(TecnicoModelo tecnico) {
		// Lógica para mostrar los datos del técnico
	}
	
	public void mostrarLista() {
		// Lógica para mostrar la lista de técnicos
	}

    private void buscarTecnico() {
        javafx.scene.control.TextInputDialog dialogo = new javafx.scene.control.TextInputDialog();
        dialogo.setTitle("Buscar técnico");
        dialogo.setHeaderText("Ingrese nombre o apellido del técnico");
        dialogo.setContentText("Búsqueda:");

        java.util.Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent() && !resultado.get().trim().isEmpty()) {
            String criterio = resultado.get().trim();

            // Aquí implementarás la lógica de búsqueda según tu base de datos
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.INFORMATION
            );
            alerta.setTitle("Búsqueda");
            alerta.setHeaderText("Buscando técnico: " + criterio);
            alerta.setContentText("Función de búsqueda en desarrollo...");
            alerta.showAndWait();
        }
    }
	
	public void actualizarCantidadTareas(int nuevaCantidad) {
		this.cantidadTareas = nuevaCantidad;
	}
	
	//Getters y Setters
	
	public String getusuarioId() {
		return usuarioId;
	}
	
	public void setusuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getEspecialidad() {
		return especialidad;
	}
	
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}	
	
	public int getCantidadTareas() {
		return cantidadTareas;
	}
	
	public void setCantidadTareas(int cantidadTareas) {
		this.cantidadTareas = cantidadTareas;
	}
}
