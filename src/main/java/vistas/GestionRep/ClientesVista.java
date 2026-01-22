package vistas.GestionRep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controladores.GestionRepControl;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelos.GestionRep.ClienteModelo;

import static vistas.GestionRep.OrdenTrabajoVista.*;

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

    //MOSTRAR EL MENU DE LOS CLIENTES Y LAS OPCIONES DISPONIBLES.
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

        //IMPUTS
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
    public void buscarCliente(){
        //VENTANA
        Stage ventana = new Stage();
        ventana.setTitle("Busqueda de Cliente");

        //TITULO
        Label titulo = new Label("Elija criterio de búsqueda");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //DROPDOWN
        ComboBox<String> criterioOpciones = new ComboBox<>();
        criterioOpciones.getItems().addAll("Cliente_ID",
                "Apellido",
                "Nombre",
                "Empresa",
                "CUIT",
                "DNI",
                "Telefono");
        criterioOpciones.setPromptText("Seleccione un criterio");
        criterioOpciones.setPrefWidth(200);

        //BOTONES
        Button botonBuscar = new Button("Aceptar");
        Button botonCancelar = new Button("Cancelar");

        botonBuscar.setOnAction(e-> {
            String criterioSeleccionado = criterioOpciones.getValue();

            if(criterioSeleccionado == null){
                mostrarAdvertencia("Debe seleccionar un criterio de busqueda para continuar!!");
                return;
            }

            String criterio = switch (criterioSeleccionado) {
                case "CLIENTE ID" -> "CLIENTE_ID";
                case "Nombre" -> "NOMBRE";
                case "Apellido" -> "APELLIDO";
                case "Empresa" -> "EMPRESA";
                case "CUIT" -> "CUIT";
                case "DNI" -> "MARCA";
                case "Telefono" -> "TELEFONO";
                default -> null;
            };

            if(criterio == null) {
                mostrarAdvertencia("Criterio de búsqueda no reconocido: " + criterioSeleccionado);
                return;
            }

            mostrarFormBusquedaCliente(criterio, criterioSeleccionado);

        });
        botonBuscar.setStyle("-fx-base: #008000;");

        botonCancelar.setOnAction(e -> ventana.close());
        botonCancelar.setStyle("-fx-base: #FF0000;");

        //BOTON LAYOUT
        HBox botonLayout = new HBox(10, botonBuscar, botonCancelar);
        botonLayout.setAlignment(Pos.CENTER);

        //LAYOUT
        VBox layout = new VBox(10, titulo, criterioOpciones, botonLayout);
        layout.setPadding(new Insets(10));

        Scene escena = new Scene(layout);
        ventana.setScene(escena);
        ventana.show();
    }

    //MOSTAR FORM BUSQUEDA DE CLIENTE
    public void mostrarFormBusquedaCliente(String criterio, String criterioSeleccionado){
        Stage ventana = new Stage();
        ventana.setTitle("Busqueda de orden avanzada por:");
        ventana.initModality(Modality.APPLICATION_MODAL);

        //TITULO
        Label titulo = new Label("Busqueda de orden avanzada");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //INPUT
        TextField inputDato = new TextField();
        inputDato.setPromptText("Ingrese dato de busqueda");
        inputDato.setPrefWidth(20);

        //BOTON BUSCAR
        Button botonBuscar = new Button("Buscar");
        botonBuscar.setOnAction(e -> {
                    String dato = inputDato.getText();
                    ObservableList<ObservableList<String>> resultado = GestionRepControl.buscarClientePorCriterio(criterio, dato);
                    if (resultado == null || resultado.isEmpty()) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "No se encontraron resultados.");
                        a.setHeaderText("Búsqueda");
                        a.showAndWait();
                        return;
                    }

                    //TABLA DE RESULTADOS DE BUSQUEDA
                    TableView<ObservableList<String>> tabla =
                            new TableView<>();
                    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                    String[] nombresCampos = {
                            "Cliente_ID",
                            "Nombre",
                            "Apellido",
                            "Empresa",
                            "Telefono",
                            "CUIT",
                            "DNI",
                    };

                    //AGREGAR NOMBRES A COLUMNAS DINAMICAMENTE
                    for(String nombreCampo : nombresCampos) {
                        final int colIndex = tabla.getColumns().size();
                        TableColumn<ObservableList<String>, String> columna = new TableColumn<>(nombreCampo);

                        columna.setCellValueFactory(param ->
                                new ReadOnlyStringWrapper(param.getValue().size() > colIndex ?
                                        param.getValue().get(colIndex) : ""));
                        columna.setPrefWidth(switch(nombreCampo){
                            case "Cliente_ID" -> 100;
                            case "Nombre" -> 100;
                            case "Apellido" -> 100;
                            case "Telefono" -> 100;
                            case "Empresa" -> 100;
                            case "Maquina_ID" -> 100;
                            case "CUIT" -> 100;
                            case "DNI" -> 100;
                            default -> 120;
                        });
                        tabla.getColumns().add(columna);
                    }

                    //AGREGAR DATOS A LA TABLA
                    tabla.setItems(resultado);

                    // Ventana resultados
                    Stage ventanaResultados = new Stage();
                    ventanaResultados.setTitle("Resultados de búsqueda");

                    Label tituloResultados = new Label("Resultados para: " + criterioSeleccionado + " = " + dato);
                    tituloResultados.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 14));

                    Button btnOrdenesRelacionadas = new Button(" Ordenes relacionadas");
                    btnOrdenesRelacionadas.setOnAction(event->{
                      String cliente_id = String.valueOf(tabla.getItems().get(0).get(0));

                      mostrarListaOrdenesByClienteId(cliente_id);
                    });

                    Button btnCerrar = new Button("Cerrar");
                    btnCerrar.setOnAction(ev -> ventanaResultados.close());

                    HBox barra = new HBox(10,btnOrdenesRelacionadas, btnCerrar);
                    barra.setAlignment(Pos.CENTER_RIGHT);
                    barra.setPadding(new Insets(10));

                    VBox layoutResultados = new VBox(10, tituloResultados, tabla, barra);
                    layoutResultados.setPadding(new Insets(10));

                    ventanaResultados.setScene(new Scene(layoutResultados, 700, 400));
                    ventanaResultados.initOwner(ventana);
                    ventanaResultados.show();
    });

        botonBuscar.setPrefWidth(100);
        botonBuscar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button botonCancelar = new Button("Cancelar");
        botonCancelar.setOnAction(e -> ventana.close());
        botonCancelar.setPrefWidth(100);
        botonCancelar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        //LAYOUT
        HBox layoutBotones = new HBox(10, botonBuscar, botonCancelar);
        layoutBotones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, titulo, inputDato, layoutBotones);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Scene escena = new Scene(layout, 300, 150);
        ventana.setScene(escena);
        ventana.show();
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
        Button btnOrdenesRelacionadas = new Button("Ordenes relacionadas");
        Button btnEliminar = new Button("️ Eliminar");
        Button btnCerrar = new Button(" Cerrar");

        btnOrdenesRelacionadas.setOnAction(e -> {
            ClienteModelo seleccionado = tabla.getSelectionModel().getSelectedItem();
            if(seleccionado != null){
                String cliente_id = seleccionado.getClienteId();
                    mostrarListaOrdenesByClienteId(cliente_id);
            }else{
                mostrarAdvertencia("Debe seleccionar un cliente!");
            }
        });

        btnEditar.setOnAction(e -> {
            ClienteModelo seleccionado = tabla.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
            	String cliente_id = seleccionado.getClienteId();
                editarClientePorId(seleccionado, ventana, cliente_id);
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
            	String cliente_id = seleccionado.getClienteId();
              
                
            	Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
        				"¿Eliminar el cliente con ID " + cliente_id + "?",
        				ButtonType.YES, ButtonType.NO);
        		confirm.showAndWait().ifPresent(respuesta -> {
        			if(respuesta == ButtonType.YES) {
        				eliminarClientePorId(seleccionado, ventana, cliente_id);
        				datos.remove(seleccionado);
        				}
        		});	  
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
        botonesBox.getChildren().addAll(btnEditar, btnEliminar, btnOrdenesRelacionadas, btnCerrar);

        // Layout principal
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(tabla, botonesBox);

        Scene escena = new Scene(layout, 900, 500);
        ventana.setScene(escena);
        ventana.show();
	}

    //MOSTRAR LISTA DE ORDENES BY CLIENTE_ID
    public static void mostrarListaOrdenesByClienteId(String cliente_id){
        String consultaSQL = "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.ESTADO, "
                + "C.NOMBRE, C.APELLIDO, OM.MAQUINA_ID, M.TIPO, M.MARCA, M.MODELO, " +
                "M.DESCRIPCION_FALLA, M.OBSERVACIONES, O.ACTIVO "
                + "FROM ORDEN_DE_TRABAJO O "
                + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
                + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
                + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
                + "WHERE O.ACTIVO = TRUE AND C.CLIENTE_ID = ? "
                + "ORDER BY FECHA_INGRESO ASC";

        // VENTANA DE LISTAR ORDENES DE TRABAJO
        Stage ventana = new Stage();
        ventana.setTitle("Lista de Órdenes de Trabajo");

        //TABLA DE FILAS DE STRING
        TableView<ObservableList<String>> tabla = new TableView<>();

        //LISTA DE FILAS, CADA FILA ES UNA LISTA DE STRINGS
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();

        String[] nombresCampos = {
                "Orden_trabajo_id", "Fecha_ingreso", "Estado",
                "Cliente", "Maquina_id",
                "Tipo", "Marca", "Modelo", "Descripcion_Falla",
                "Observaciones", "Activo"
        };

        //AGREGAR NOMBRES DE COLUMNAS DINÁMICAMENTE
        for(String nombreCampo : nombresCampos){
            final int colIndex = tabla.getColumns().size();
            TableColumn<ObservableList<String>, String> columna =
                    new TableColumn<>(nombreCampo);

            columna.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                    (param.getValue().size() > colIndex) ?
                            param.getValue().get(colIndex) : ""));
            columna.setPrefWidth(switch(nombreCampo) {
                case "Orden_trabajo_id" -> 100;
                case "Fecha_ingreso" -> 120;
                case "Cliente" -> 150;
                case "Estado" -> 150;
                case "Maquina_id" -> 100;
                case "Tipo" -> 150;
                case "Marca" -> 120;
                case "Modelo" -> 150;
                case "Descripcion_Falla" -> 200;
                case "Observaciones" -> 200;
                case "Activo" -> 100;
                default -> 120;
            });
            tabla.getColumns().add(columna);
        }

        //HACER CONSULTA Y CARGAR LAS FILAS
        try (PreparedStatement consultaPreparada = GestionRepControl.conexion.prepareStatement(consultaSQL)) {
            consultaPreparada.setString(1, cliente_id);
            ResultSet resultado = consultaPreparada.executeQuery();

            while (resultado.next()) {
                ObservableList<String> fila =
                        FXCollections.observableArrayList();

                String orden_trabajo_id = resultado.getString("orden_trabajo_"
                        + "id");
                String fecha_ingreso = resultado.getString("fecha_ingreso");
                String estado = resultado.getString("estado");
                String cliente = resultado.getString("nombre") + " " +
                        resultado.getString("apellido");
                String maquina_id = resultado.getString("maquina_id");
                String tipo = resultado.getString("tipo");
                String marca = resultado.getString("marca");
                String modelo = resultado.getString("modelo");
                String descripcion_falla = resultado.getString("descripcion_falla");
                String observaciones = resultado.getString("observaciones");
                Boolean activo = resultado.getBoolean("activo");

                if(maquina_id == null) {
                    maquina_id = "X";
                    tipo = "Sin";
                    marca = "Máquina";
                    modelo = "Asociada";
                }

                //INSERTAR VARIABLES EN FILA
                fila.addAll(orden_trabajo_id, fecha_ingreso, estado,
                        cliente, maquina_id, tipo,
                        marca, modelo, descripcion_falla,
                        observaciones, activo.toString());

                //INSERTAR CADA FILA EN DATOS
                datos.add(fila);
            }

            //INSERTAR TODAS LAS FILAS EN LA TABLA DE FILAS DE STRING
            tabla.setItems(datos);

            //BOTONES
            Button botonDarDeBaja = new Button("Dar de Baja");
            Button botonCerrar = new Button("Cerrar");
            Button botonVer = new Button("Ver");
            Button botonCambiarEstado = new Button("Cambiar Estado");
            Button botonBuscarOrden = new Button("Buscar Orden");
            Button botonOrdenesInactivas = new Button("Ordenes inactivas");

            //BOTON BUSCAR ORDEN
            botonBuscarOrden.setOnAction(e -> {
                //ESTE METODO VA A FILTRAR EL TIPO DE BUSQUEDA QUE SE NECESITE.
                OrdenTrabajoVista.buscarOrdenDeTrabajo();
            });

            //BOTON VER
            botonVer.setOnAction(e -> {
                ObservableList<String> seleccionado = tabla.getSelectionModel().getSelectedItem();

                if(seleccionado != null) {
                    String ordenId = seleccionado.get(0);

                    OrdenTrabajoVista.verOrdentrabajo(ordenId);
                }else {
                    mostrarAdvertencia("Debe seleccionar una orden para ver.");
                }
            });

            //BOTON CAMBIAR DE ESTADO
            botonCambiarEstado.setOnAction(e -> {
                ObservableList<String> seleccionado = tabla.getSelectionModel().getSelectedItem();

                if(seleccionado != null) {
                    String ordenId = seleccionado.get(0);

                    OrdenTrabajoVista.cambiarEstadoOrden(tabla, tabla.getItems(), ordenId);
                }else{
                    mostrarAdvertencia("Debe seleccionar una orden para cambiar su estado.");
                }
            });

            //BOTON DAR DE BAJA
            botonDarDeBaja.setOnAction(e -> {
                ObservableList<String> seleccionado =
                        tabla.getSelectionModel()
                                .getSelectedItem();

                if(seleccionado != null) {
                    String ordenId = seleccionado.get(0);

                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                            "¿Eliminar la orden con ID " + ordenId + "?",
                            ButtonType.YES, ButtonType.NO);
                    confirm.showAndWait().ifPresent(respuesta -> {
                        if(respuesta == ButtonType.YES) {
                            Boolean dadaDeBaja = GestionRepControl.darDeBajaOrden(ordenId);

                            if(dadaDeBaja) {
                                mostrarAlertaExito("Desactivar orden", "Se ha dado de baja la orden");
                                datos.remove(seleccionado);
                            }else{
                                mostrarAdvertencia("No se ha podido dar de baja la orden.");
                            }
                        }
                    });
                }else {
                    mostrarAdvertencia("Debe seleccionar una orden para"
                            + "eliminar");
                }

            });

            //BOTON CERRAR
            botonCerrar.setOnAction(e ->
                    ventana.close());

            //BOTON DE LISTAR ORDEN INACTICAS
            botonOrdenesInactivas.setOnAction(e -> {
                OrdenTrabajoVista.listarOrdenesInactivasVista();
            });

            //LAYOUT
            HBox botonesBox = new HBox(10,
                    botonBuscarOrden,
                    botonVer,
                    botonCambiarEstado, botonDarDeBaja, botonOrdenesInactivas,
                    botonCerrar);

            botonesBox.setAlignment(Pos.CENTER);
            botonesBox.setPadding(new Insets(10));

            VBox layout = new VBox(10, tabla, botonesBox);
            layout.setPadding(new Insets(10));

            Scene escena = new Scene(layout);
            ventana.setScene(escena);
            ventana.show();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Error al mostrar lista: " + e.getMessage());
            alerta.showAndWait();
        }
    }
	
	//EDITAR CLIENTE
	private static void editarClientePorId(ClienteModelo cliente,
			Stage ventanaPadre, String cliente_id) {
		
	}
	
	//ELIMINAR CLIENTE
	private static void eliminarClientePorId(ClienteModelo cliente,
			Stage ventanaPadre, String cliente_id) {
		
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
