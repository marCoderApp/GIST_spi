package vistas.GestionRep;

import java.time.LocalDateTime;

import controladores.PersonalControl;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import 	modelos.GestionRep.PresupuestoModelo;

public class FormPresupVista {
	
	private String presupuestoId;
	private LocalDateTime fechaCreacion;
	private String detalleReparacion;
	private String ordenTrabajoId;
	private static float total;
	
	public FormPresupVista(String presupuestoId, LocalDateTime fechaCreacion, String detalleReparacion,
			String ordenTrabajoId, float total) {
		super();
		this.presupuestoId = presupuestoId;
		this.fechaCreacion = fechaCreacion;
		this.detalleReparacion = detalleReparacion;
		this.ordenTrabajoId = ordenTrabajoId;
		this.total = total;
	}
	
	public static void ingresarPresupuesto(String maquinaId) {
        Stage ventana = new Stage();
        ventana.setTitle("Crear Presupuesto");

        // TÍTULO
        Label lblTitulo = new Label("Crear Presupuesto para Máquina - " + maquinaId);
        lblTitulo.setStyle("-fx-font-weight: bold");

        // INPUTS

        TextField txtMonto = new TextField();
        txtMonto.setPromptText("Monto total");

        CheckBox chkFactura = new CheckBox("Con factura");

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(e -> {
            try {
                float totalMonto = Float.parseFloat(txtMonto.getText());
                boolean conFactura = chkFactura.isSelected();
                String adminId = PersonalControl.adminIdPersonalControl != null ? PersonalControl.adminIdPersonalControl :
                        PersonalControl.tecnicoIdPersonalControl;

                PresupuestoModelo nuevo = new PresupuestoModelo(adminId, maquinaId, totalMonto, conFactura);
                boolean exito = PresupuestoModelo.ingresarPresupuestoBD(nuevo);

                if (exito) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Éxito");
                    alerta.setHeaderText("Presupuesto creado");
                    alerta.setContentText("El presupuesto fue guardado correctamente.");
                    alerta.showAndWait();
                    ventana.close();
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("No se pudo crear el presupuesto");
                    alerta.setContentText("Verifique los datos ingresados.");
                    alerta.showAndWait();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("No se pudo crear el presupuesto");
                alerta.setContentText("Verifique los datos ingresados.");
                alerta.showAndWait();
            }
        });

        VBox layout = new VBox(10, lblTitulo, txtMonto, chkFactura, btnGuardar);
        layout.setPadding(new Insets(15));

        Scene scene = new Scene(layout, 400, 300);
        ventana.setScene(scene);
        ventana.show();


    }

    public void abrirFormulario() {
		// Lógica para abrir el formulario de presupuesto
	}
	
	public void mostrarPresupuesto(float total) {
		// Lógica para mostrar el presupuesto
	}
	
	public boolean confirmarPresupuesto() {
		// Lógica para confirmar el presupuesto
		return true;
	}
	
	//Getters y Setters
	
	public String getPresupuestoId() {
		return presupuestoId;
	}
	
		
	public void setPresupuestoId(String presupuestoId) {
		this.presupuestoId = presupuestoId;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getDetalleReparacion() {
		return detalleReparacion;
	}
	
	public void setDetalleReparacion(String detalleReparacion) {
		this.detalleReparacion = detalleReparacion;
	}
	
	public String getOrdenTrabajoId() {
		return ordenTrabajoId;
	}
	
	public void setOrdenTrabajoId(String ordenTrabajoId) {
		this.ordenTrabajoId = ordenTrabajoId;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	

}
