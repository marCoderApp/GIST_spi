package vistas.GestionRep;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controladores.GestionRepControl;
import javafx.stage.Stage;
import modelos.GestionRep.ClienteModelo;
import modelos.GestionRep.PedidoModelo;

public class ClientesVista {
	
	private String clienteId;
	private List<ClienteModelo> clientes = new ArrayList<>();
	private ClienteModelo clienteSeleccionado;
	private String filtroCodigoOrden;
	private int cantidadTotalOrdenes;
	
	public ClientesVista() {
		this.clienteId = "";
		this.clientes = new ArrayList<>();
		this.clienteSeleccionado = null;
		this.filtroCodigoOrden = "";
		this.cantidadTotalOrdenes = 0;
	}
	
	public void mostrarMenuClientes() {
		// TODO Auto-generated method stub
		System.out.println("=== Gestión de Clientes ===");
		System.out.println("1. Listar Clientes");
		System.out.println("2. Seleccionar Cliente");
		System.out.println("3. Crear Pedido");
		System.out.println("4. Filtrar Órdenes por Código");
		System.out.println("5. Salir");
	}
	
	public ClienteModelo crearNuevoCliente() {

        GestionRepControl gestionRepControl = new GestionRepControl();

        Stage ventanaFormulario = new Stage();
        ventanaFormulario.setTitle("Crear nuevo cliente");
        ventanaFormulario.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        //Título
        javafx.scene.control.Label lblTitulo = new javafx.scene.control.Label("=== Crear nuevo cliente ===");
        lblTitulo.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 16));

        // Campos de texto con sus labels
        javafx.scene.control.Label lblNombre = new javafx.scene.control.Label("Nombre:*");
        javafx.scene.control.TextField txtNombre = new javafx.scene.control.TextField();
        txtNombre.setPromptText("Ingrese nombre");
        txtNombre.setPrefWidth(250);

        javafx.scene.control.Label lblApellido = new javafx.scene.control.Label("Apellido:*");
        javafx.scene.control.TextField txtApellido = new javafx.scene.control.TextField();
        txtApellido.setPromptText("Ingrese apellido");
        txtApellido.setPrefWidth(250);

        javafx.scene.control.Label lblEmpresa = new javafx.scene.control.Label("Empresa:");
        javafx.scene.control.TextField txtEmpresa = new javafx.scene.control.TextField();
        txtEmpresa.setPromptText("Ingrese empresa (opcional)");
        txtEmpresa.setPrefWidth(250);

        javafx.scene.control.Label lblTelefono = new javafx.scene.control.Label("Teléfono:");
        javafx.scene.control.TextField txtTelefono = new javafx.scene.control.TextField();
        txtTelefono.setPromptText("Ingrese teléfono (opcional)");
        txtTelefono.setPrefWidth(250);

        javafx.scene.control.Label lblDni = new javafx.scene.control.Label("DNI:");
        javafx.scene.control.TextField txtDni = new javafx.scene.control.TextField();
        txtDni.setPromptText("Ingrese DNI (opcional)");
        txtDni.setPrefWidth(250);

        javafx.scene.control.Label lblCuit = new javafx.scene.control.Label("CUIT:");
        javafx.scene.control.TextField txtCuit = new javafx.scene.control.TextField();
        txtCuit.setPromptText("Ingrese CUIT (opcional)");
        txtCuit.setPrefWidth(250);

        //Botones
        javafx.scene.control.Button btnGuardar = new javafx.scene.control.Button("Guardar");
        javafx.scene.control.Button btnCancelar = new javafx.scene.control.Button("Cancelar");

        //Almacenar los resultados
        final ClienteModelo[] resultado = new ClienteModelo[1];

        btnGuardar.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String empresa = txtEmpresa.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String dni = txtDni.getText().trim();
            String cuit = txtCuit.getText().trim();

            // Validación de campos obligatorios
            if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.WARNING
                );
                alerta.setTitle("Campos Requeridos");
                alerta.setHeaderText("Nombre, Apellido y teléfono son obligatorios");
                alerta.setContentText("Por favor, complete los campos marcados con *");
                alerta.showAndWait();
                return;
            }

            // Crear nuevo cliente
            ClienteModelo nuevoCliente = new ClienteModelo(
                    nombre,
                    apellido,
                    empresa.isEmpty() ? null : empresa,
                    telefono.isEmpty() ? null : telefono,
                    dni.isEmpty() ? null : dni,
                    cuit.isEmpty() ? null : cuit
            );

            // Intentar guardar
            ClienteModelo guardado = gestionRepControl.registrarCliente(nuevoCliente);

            if (guardado != null) {
                javafx.scene.control.Alert exitoAlert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION
                );
                exitoAlert.setTitle("Éxito");
                exitoAlert.setHeaderText("Cliente creado exitosamente ✅");
                exitoAlert.setContentText("ID: " + guardado.getClienteId());
                exitoAlert.showAndWait();

                resultado[0] = guardado;
                ventanaFormulario.close();
            } else {
                javafx.scene.control.Alert errorAlert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.ERROR
                );
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Error al crear el cliente");
                errorAlert.setContentText("No se pudo registrar el cliente en la base de datos.");
                errorAlert.showAndWait();
            }
        });

        btnCancelar.setOnAction(e -> {
            resultado[0] = null;
            ventanaFormulario.close();
        });

        btnGuardar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancelar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        btnGuardar.setPrefWidth(100);
        btnCancelar.setPrefWidth(100);

        // Layout
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(15);
        grid.setPadding(new javafx.geometry.Insets(20));

        grid.add(lblTitulo, 0, 0, 2, 1);
        grid.add(lblNombre, 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(lblApellido, 0, 2);
        grid.add(txtApellido, 1, 2);
        grid.add(lblEmpresa, 0, 3);
        grid.add(txtEmpresa, 1, 3);
        grid.add(lblTelefono, 0, 4);
        grid.add(txtTelefono, 1, 4);
        grid.add(lblDni, 0, 5);
        grid.add(txtDni, 1, 5);
        grid.add(lblCuit, 0, 6);
        grid.add(txtCuit, 1, 6);

        javafx.scene.layout.HBox botonesBox = new javafx.scene.layout.HBox(10);
        botonesBox.setAlignment(javafx.geometry.Pos.CENTER);
        botonesBox.getChildren().addAll(btnGuardar, btnCancelar);
        grid.add(botonesBox, 0, 7, 2, 1);

        javafx.scene.Scene escena = new javafx.scene.Scene(grid, 450, 450);
        ventanaFormulario.setScene(escena);
        ventanaFormulario.showAndWait();

        return resultado[0];
	}
	
	public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
	
	public static void mostrarListaClientes(List<ClienteModelo> clientes) {
		System.out.println("Lista de Clientes:");
		for (ClienteModelo cliente : clientes) {
			 System.out.println("───────────────────────────────");
			    System.out.println("ID Cliente: " + cliente.getClienteId());
			    System.out.println("Nombre:     " + cliente.getNombre());
			    System.out.println("Apellido:   " + cliente.getApellido());
			    System.out.println("Empresa:    " + cliente.getEmpresa());
			    System.out.println("Teléfono:   " + cliente.getTelefono());
			    System.out.println("DNI:        " + cliente.getDni());
			    System.out.println("CUIT:       " + cliente.getCuit());
				 System.out.println("───────────────────────────────");
		}
	}
	
	//Getters
	
	public String getClienteId() {
		return clienteId;
	}
	
	public List<ClienteModelo> getClientes() {
		return clientes;
	}
	
	public ClienteModelo getClienteSeleccionado() {
		return clienteSeleccionado;
	}
	
	public String getFiltroCodigoOrden() {
		return filtroCodigoOrden;
	}
	
	public int getCantidadTotalOrdenes() {
		return cantidadTotalOrdenes;
	}
	
	//Setters
	
	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}
	
	public void setClientes(List<ClienteModelo> clientes) {
		this.clientes = clientes;
	}
	
	public void setClienteSeleccionado(ClienteModelo clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}
	
	public void setFiltroCodigoOrden(String filtroCodigoOrden) {
		this.filtroCodigoOrden = filtroCodigoOrden;
	}
	
	public void setCantidadTotalOrdenes(int cantidadTotalOrdenes) {
		this.cantidadTotalOrdenes = cantidadTotalOrdenes;
	}
	
}
