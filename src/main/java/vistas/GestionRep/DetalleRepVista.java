package vistas.GestionRep;

import java.time.LocalDateTime;

import controladores.GestionRepControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import modelos.GestionRep.DetalleReparacionModelo;

public class DetalleRepVista {

	private String detalleRepId;
	private String descripcion;
	private String repuestos;
	private String tecnicoId;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaEstimada;
	private String ordenId;
	private int nivelService;
	private float presupuesto;
	private boolean ordenAsociada;
	
	//Constructor
	
	public DetalleRepVista(String detalleRepId, String descripcion, String repuestos, String tecnicoId,
			LocalDateTime fechaCreacion, LocalDateTime fechaEstimada, String ordenId, String ecnicoId, int nivelService,
			float presupuesto, boolean ordenAsociada) {
		super();
		this.detalleRepId = detalleRepId;
		this.descripcion = descripcion;
		this.repuestos = repuestos;
		this.tecnicoId = tecnicoId;
		this.fechaCreacion = fechaCreacion;
		this.fechaEstimada = fechaEstimada;
		this.ordenId = ordenId;
		this.nivelService = nivelService;
		this.presupuesto = presupuesto;
		this.ordenAsociada = ordenAsociada;
	}

    //INGRESAR DATOS DE DETALLE REPARACION
    public static void ingresarDetalleRep(String ordenId){

        Stage ventanaDetalleRep = new Stage();
        ventanaDetalleRep.setTitle("Ingresar Detalle de Reparación");
        ventanaDetalleRep.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        //TITULO
        Label titulo = new Label("Detalle de Reparación");
        titulo.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));

        //INPUTS
        TextArea descripcionArea = new TextArea();
        descripcionArea.setPromptText("Ingrese una descripción detallada...");
        descripcionArea.setPrefRowCount(5);
        descripcionArea.setWrapText(true);

        TextArea repuestosArea = new TextArea();
        repuestosArea.setPromptText("Ingrese los repuestos que necesita para reparar...");
        repuestosArea.setPrefRowCount(5);
        repuestosArea.setWrapText(true);

        TextField txtTecnicoId = new TextField();
        txtTecnicoId.setPromptText("ID de técnico");
        txtTecnicoId.setPrefWidth(100);

        ComboBox<Integer> nivelServiceBox = new ComboBox<>();
        nivelServiceBox.getItems().addAll(1, 2, 3);

        //NUEVO DETALLE DE REPARACIÓN:
        DetalleReparacionModelo nuevoDetalleRep = new DetalleReparacionModelo(null,
                null,
                null,
                null,
                LocalDateTime.now(),
                null,
                0);

        //BOTONES
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #15bb15;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold;");
        btnGuardar.setOnAction(e -> {
            String descripcion = descripcionArea.getText().trim();
            String repuestos = repuestosArea.getText().trim();
            String tecnicoId = txtTecnicoId.getText().trim();
            int nivelService = nivelServiceBox.getValue() != null ? nivelServiceBox.getValue() : nuevoDetalleRep.getNivelService();

            if(descripcion == null || repuestos == null || tecnicoId == null){
                mostrarError("Todos los campos son obligatorios");
                return;
            }

            if (nivelService == 0) {
                mostrarError("Seleccioná un nivel de servicio antes de guardar.");
                return;
            }


            //CARGAR LOS DATOS EN EL BOTON
            nuevoDetalleRep.setDescripcion(descripcion);
            nuevoDetalleRep.setRepuestos(repuestos);
            nuevoDetalleRep.setTecnicoId(tecnicoId);
            nuevoDetalleRep.setNivelService(nivelService);

            boolean exito = GestionRepControl.cargarDetalleRep(nuevoDetalleRep, ordenId);

            if(exito){
                mostrarAlerta("Detalle de reparación guardado correctamente."
                , ventanaDetalleRep);
                btnGuardar.setDisable(true);
            }else{
                mostrarError("Verificá los datos e intentá nuevamente.");
            }

        });
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #da1d38;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold;");
        btnCancelar.setOnAction(e -> ventanaDetalleRep.close());



        //LAYOUR Y GRID
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.add(titulo, 0, 0, 2, 1);
        grid.add(new javafx.scene.control.Label("Descripción:"), 0, 1);
        grid.add(descripcionArea, 1, 1);
        grid.add(new javafx.scene.control.Label("Repuestos"), 0, 2);
        grid.add(repuestosArea, 1, 2);
        grid.add(new Label("ID de Técnico"), 0, 3);
        grid.add(txtTecnicoId, 1, 3);
        grid.add(new Label("Nivel de Servicio"), 0, 4);
        grid.add(nivelServiceBox, 1, 4);

        //LAYOUT HORIZONTAL DE BOTONES
        HBox hbox = new HBox(10, btnGuardar, btnCancelar);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        grid.add(hbox, 0, 5, 2, 1);

        //MOSTRAR ESCENA
        Scene escena = new Scene(grid);
        ventanaDetalleRep.setScene(escena);
        ventanaDetalleRep.show();

    }

    //MOSTRAR ALERTA EXITO
    private static void mostrarAlerta(String mensaje, Stage ventanaDetalleRep){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Éxito");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();

        ventanaDetalleRep.close();
    }

    private static void mostrarError(String mensaje){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("No se pudo guardar");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }


	//Getters and Setters
	
	public String getDetalleRepId() {
		return detalleRepId;
	}
	
	public void setDetalleRepId(String detalleRepId) {
		this.detalleRepId = detalleRepId;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getRepuestos() {
		return repuestos;
	}
	
	public void setRepuestos(String repuestos) {
		this.repuestos = repuestos;
	}
	
	public String getTecnicoId() {
		return tecnicoId;
	}
	
	public void setTecnicoId(String tecnicoId) {
		this.tecnicoId = tecnicoId;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public LocalDateTime getFechaEstimada() {
		return fechaEstimada;
	}
	
	public void setFechaEstimada(LocalDateTime fechaEstimada) {
		this.fechaEstimada = fechaEstimada;
	}
	
	public String getOrdenId() {
		return ordenId;
	}
	
	public void setOrdenId(String ordenId) {
		this.ordenId = ordenId;
	}
	
	public int getNivelService() {
		return nivelService;
	}
	
	public void setNivelService(int nivelService) {
		this.nivelService = nivelService;
	}
	
	public float getPresupuesto() {
		return presupuesto;
	}
	
	public void setPresupuesto(float presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	public boolean isOrdenAsociada() {
		return ordenAsociada;
	}
	
	public void setOrdenAsociada(boolean ordenAsociada) {
		this.ordenAsociada = ordenAsociada;
	}
	
}
