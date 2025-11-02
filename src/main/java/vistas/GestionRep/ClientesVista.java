package vistas.GestionRep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import controladores.GestionRepControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

        //Ventana
        Stage gestionClientesVentana = new Stage();
        gestionClientesVentana.setTitle("Gestión Clientes");

        //Título
        Label titulo = new Label("Gestión de Clientes - GIST");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        //Botones
        Button botonCrearCliente = new Button("Crear nuevo cliente");
        Button botonListarClientes = new Button("Listar clientes");
        Button botonBuscarCliente = new Button("Buscar cliente");
        Button botonSalir = new Button("Salir");

        botonCrearCliente.setPrefWidth(250);
        botonListarClientes.setPrefWidth(250);
        botonBuscarCliente.setPrefWidth(250);
        botonSalir.setPrefWidth(250);

        botonCrearCliente.setOnAction(e -> {crearNuevoCliente();});
        botonListarClientes.setOnAction(e -> {mostrarListaClientes();});
        botonBuscarCliente.setOnAction(e -> {buscarCliente();});
        botonSalir.setOnAction(e -> gestionClientesVentana.close());
        
     // Estilos
     		String estiloBoton = 
     			"-fx-background-color: white;" +
     			"-fx-text-fill: black;" +
     			"-fx-border-color: black;" +
     			"-fx-border-width: 1.5;" +
     			"-fx-background-radius: 8;" +
     			"-fx-border-radius: 8;" +
     			"-fx-font-size: 14px;";
     		
     		botonCrearCliente.setStyle(estiloBoton);
     		botonListarClientes.setStyle(estiloBoton);
     		botonBuscarCliente.setStyle(estiloBoton);
     		botonSalir.setStyle(estiloBoton);
     		
     		// Hover effects
     		Button[] botones = {botonCrearCliente, botonListarClientes,
     				botonBuscarCliente, botonSalir};
     		for (Button btn : botones) {
     			btn.setOnMouseEntered(e -> btn.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
     			btn.setOnMouseExited(e -> btn.setStyle(estiloBoton));
     		}
     		
     		// Layout
     		VBox layout = new VBox(15);
     		layout.setAlignment(Pos.CENTER);
     		layout.setPadding(new Insets(30));
     		layout.getChildren().addAll(titulo,
     				botonCrearCliente,
     				botonListarClientes,
     				botonBuscarCliente,
     				botonSalir);
     		layout.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");
     		
     		Scene escena = new Scene(layout, 400, 450);
     		gestionClientesVentana.setScene(escena);
     		gestionClientesVentana.show();
        
	}

    //CREAR NUEVO CLIENTE
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


	//BUSCAR CLIENTE
    public ClienteModelo buscarCliente(){

        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Buscar cliente");
        dialogo.setHeaderText("Ingrese nombre o apellido del cliente");
        dialogo.setContentText("Busqueda:");

        Optional<String> resultado = dialogo.showAndWait();

        if(resultado.isPresent() && !resultado.get().trim().isEmpty()){
            String criterio = resultado.get().trim();

            List<ClienteModelo> clientesEncontrados = buscarClientesPorNombre(criterio);
            
            if(clientesEncontrados.isEmpty()) {
            	Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            	alerta.setTitle("No hay clientes - Lista vacía");
            	alerta.setHeaderText("No se han encontrado clientes");
            	alerta.showAndWait();
            }else {
            	mostrarResultadosBusqueda(clientesEncontrados);
            }
        }

        return null;
    }
    
    //BUSCAR CLIENTE POR NOMBRE
    private List<ClienteModelo> buscarClientesPorNombre(String criterio){
    	List<ClienteModelo> resultados = new ArrayList<>();
    	
    	String sqlBusqueda = "SELECT * FROM CLIENTE WHERE"
    			+ " LOWER(nombre) LIKE ? OR LOWER(apellido) LIKE ?";
    	
    	try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sqlBusqueda)){
    		ps.setString(1, "%" + criterio.toLowerCase() + "%");
    		ps.setString(2, "%" + criterio.toLowerCase() + "%");
    		
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			ClienteModelo cliente = new ClienteModelo(
    					rs.getString("nombre"),
    					rs.getString("apellido"),
    					rs.getString("empresa"),
    					rs.getString("telefono"),
    					rs.getString("cuit"),
    					rs.getString("dni")
    					);
    			cliente.setClienteId(rs.getString("cliente_id"));
    			resultados.add(cliente);
    		}
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return resultados;
    }
    
    //MOSTRAR RESULTADOS
    private void mostrarResultadosBusqueda(List<ClienteModelo> clientes) {
    	Stage ventanaResultados = new Stage();
    	ventanaResultados.setTitle("Resultados de la Búsqueda");
    	
    	TableView<ClienteModelo> tablaResultados = new TableView<>();
    	
    	ObservableList<ClienteModelo> datos = FXCollections.observableArrayList(clientes);
    	
    	//COLUMNAS
    	TableColumn<ClienteModelo, String> columnaId = new TableColumn<>("ID");
		columnaId.setCellValueFactory(new PropertyValueFactory<>("Cliente_id"));
		
		TableColumn<ClienteModelo, String> columnaNombre = new TableColumn<>("Nombre");
		columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		
		TableColumn<ClienteModelo, String> columnaApellido = new TableColumn<>("apellido");
		columnaApellido.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
		
		TableColumn<ClienteModelo, String> columnaEmpresa = new TableColumn<>("Empresa");
		columnaEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
		
		TableColumn<ClienteModelo, String> columnaCuit = new TableColumn<>("CUIT");
		columnaCuit.setCellValueFactory(new PropertyValueFactory<>("cuit"));
    	
		TableColumn<ClienteModelo, String> columnaDni = new TableColumn<>("DNI");
		columnaDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
    }

    //MOSTRAR MENSAJE
	public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    //MOSTRAR LISTA DE CLIENTES
	@SuppressWarnings("unchecked")
	public static void mostrarListaClientes() {
        Stage ventana = new Stage();
        ventana.setTitle("Lista de Clientes");

        // Obtener clientes de la base de datos
        List<ClienteModelo> listaClientes = ClienteModelo.listarClientes();

        if (listaClientes.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Sin Clientes");
            alerta.setHeaderText("No hay clientes registrados");
            alerta.setContentText("Cree un nuevo cliente para comenzar.");
            alerta.showAndWait();
            return;
        }

        // Crear tabla
        TableView<ClienteModelo> tabla = new TableView<>();
        ObservableList<ClienteModelo> datos = FXCollections.observableArrayList(listaClientes);

        // Columnas
        TableColumn<ClienteModelo, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("clienteId"));
        colId.setPrefWidth(80);

        TableColumn<ClienteModelo, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(120);

        TableColumn<ClienteModelo, String> colApellido = new TableColumn<>("Apellido");
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colApellido.setPrefWidth(120);

        TableColumn<ClienteModelo, String> colEmpresa = new TableColumn<>("Empresa");
        colEmpresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
        colEmpresa.setPrefWidth(150);

        TableColumn<ClienteModelo, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTelefono.setPrefWidth(120);

        TableColumn<ClienteModelo, String> colDni = new TableColumn<>("DNI");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colDni.setPrefWidth(100);

        TableColumn<ClienteModelo, String> colCuit = new TableColumn<>("CUIT");
        colCuit.setCellValueFactory(new PropertyValueFactory<>("cuit"));
        colCuit.setPrefWidth(120);

        tabla.getColumns().addAll(colId, colNombre, colApellido, colEmpresa, colTelefono, colDni, colCuit);
        tabla.setItems(datos);

        // Botones de acción
        Button btnEditar = new Button("✏️ Editar");
        Button btnEliminar = new Button("️ Eliminar");
        Button btnCerrar = new Button(" Cerrar");

        btnEditar.setOnAction(e -> {
            ClienteModelo seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                editarCliente(seleccionado, ventana);
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Sin Selección");
                alerta.setHeaderText("Debe seleccionar un cliente");
                alerta.showAndWait();
            }
        });

        btnEliminar.setOnAction(e -> {
            ClienteModelo seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                eliminarCliente(seleccionado, ventana);
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Sin Selección");
                alerta.setHeaderText("Debe seleccionar un cliente");
                alerta.showAndWait();
            }
        });

        btnCerrar.setOnAction(e -> ventana.close());

        // Layout de botones
        HBox botonesBox = new HBox(10);
        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.setPadding(new Insets(10));
        botonesBox.getChildren().addAll(btnEditar, btnEliminar, btnCerrar);

        // Layout principal
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(tabla, botonesBox);

        Scene escena = new Scene(layout, 900, 500);
        ventana.setScene(escena);
        ventana.show();
	}
	
	//EDITAR CLIENTE
	
	private static void editarCliente(ClienteModelo cliente,
			Stage ventanaPadre) {
		
	}
	
	//ELIMINAR CLIENTE
	private static void eliminarCliente(ClienteModelo cliente,
			Stage ventanaPadre) {
		
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
