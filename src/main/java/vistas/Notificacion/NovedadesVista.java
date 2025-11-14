package vistas.Notificacion;

import java.time.LocalDateTime;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import modelos.Notificacion.NovedadModelo;
import java.util.*;
import modelos.GestionRep.OrdenTrabajoModelo;


public class NovedadesVista {

	
	private String novedadId;
	private String titulo;
	private String descripcion;
	private LocalDateTime fechaCreacion;
	private List<OrdenTrabajoModelo> ordenesAsociadas = new ArrayList<>();
	 
	public NovedadesVista(String novedadId, String titulo, String descripcion, LocalDateTime fechaCreacion,
			List<OrdenTrabajoModelo> ordenesAsociadas) {
		// Constructor por defecto

		this.novedadId = novedadId;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.setOrdenesAsociadas(ordenesAsociadas);

	}

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

    public void mostrarFormCrearNovedades(){
        System.out.println("MENU DE NOVEDADES");
    }

    public void mostrarListaNovedades(){
        System.out.println("LISTA DE NOVEDADES");
    }
	
	public void crearTareaAPartirDeNovedad(NovedadModelo novedad) {
		// Lógica para crear una tarea a partir de una novedad
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
