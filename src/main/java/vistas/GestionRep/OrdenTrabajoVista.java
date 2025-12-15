package vistas.GestionRep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.time.LocalDate;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

import controladores.GestionRepControl;
import controladores.PersonalControl;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import modelos.Personal.TecnicoModelo;
import modelos.GestionRep.*;	


public class OrdenTrabajoVista {

	private String id;
	private LocalDateTime fechaCreacion;
	private String estado;
	private String descripcion;
	private TecnicoModelo tecnicoAsignado;
	private ClienteModelo cliente;
	private String detalleRep;
	private String mensajeExito;
	private String mensajeError;
	private FormOrdenTrabajoVista formOrdenTrabajoVista = new FormOrdenTrabajoVista(null);
	
	public OrdenTrabajoVista(String id, LocalDateTime fechaCreacion, String estado, String descripcion,
			TecnicoModelo tecnicoAsignado, ClienteModelo cliente, String detalleRep, String mensajeExito,
			String mensajeError) {
		this.id = "";
		this.fechaCreacion = LocalDateTime.now();
		this.estado = "";
		this.descripcion = "";
		this.tecnicoAsignado = null;
		this.cliente = null;
		this.detalleRep = "";
		this.mensajeExito = "";
		this.mensajeError = "";
	}
	
	//OPCION CREAR ORDEN
	public void opcionCrearOrden() {
		ingresarDatos();
	}
	
	//INGRESAR DATOS DE LA ORDEN
	public void ingresarDatos() {
		GestionRepControl gestionRepControl = new GestionRepControl();
	    OrdenTrabajoModelo orden = new OrdenTrabajoModelo(null, null, null, null, null, null, null, null, null, null, null);

	    // Ventana para seleccionar cliente
	    gestionRepControl.elegirCliente();
	    String clienteId = GestionRepControl.clienteIdGestionRep;

	    if (clienteId == null) {
	        Alert alerta = new Alert(Alert.AlertType.WARNING, "Debe seleccionar un cliente antes de continuar.");
	        alerta.showAndWait();
	        return;
	    }

	    // Solicitar descripción de falla
	    TextInputDialog dialogoDescripcion = new TextInputDialog();
	    dialogoDescripcion.setTitle("Nueva Orden de Trabajo");
	    dialogoDescripcion.setHeaderText("Descripción de la falla");
	    dialogoDescripcion.setContentText("Ingrese la descripción:");

	    Optional<String> descripcionResultado = dialogoDescripcion.showAndWait();
	    if (!descripcionResultado.isPresent() || descripcionResultado.get().trim().isEmpty()) {
	        return;
	    }

	    String descripcion_falla = descripcionResultado.get();
	    String tecnicoId = PersonalControl.tecnicoIdPersonalControl;
	    String adminId = PersonalControl.adminIdPersonalControl;

	    orden.setCliente_id(clienteId);
	    orden.setDescripcion_falla(descripcion_falla);
	    orden.setTecnicoId(tecnicoId);
	    orden.setAdminId(adminId);
	    orden.setEstado(EstadoOrden.PENDIENTE);

	    gestionRepControl.insertarOrdenBase(orden);

	    // Selección de máquinas usando
	    List<MaquinaModelo> maquinas = gestionRepControl.seleccionarMaquinas(orden);

	    // Mostrar lista de máquinas elegidas
	    StringBuilder maquinasTexto = new StringBuilder("Máquinas seleccionadas:\n\n");
	    for (MaquinaModelo maquina : maquinas) {
	        maquinasTexto.append(" - ")
	        .append(maquina.getTipo()).append(" - ")
	        .append(maquina.getMarca()).append(" (ID: ")
	        .append(maquina.getMaquinaId()).append(")\n");
	    }

	    Alert maquinasAlert = new Alert(Alert.AlertType.INFORMATION, maquinasTexto.toString());
	    maquinasAlert.setHeaderText("Máquinas asociadas a la orden");
	    maquinasAlert.showAndWait();

	    // Pedir Observaciones
	    TextInputDialog dialogoObservaciones = new TextInputDialog();
	    dialogoObservaciones.setTitle("Observaciones");
	    dialogoObservaciones.setHeaderText("Ingrese observaciones para la orden");
	    dialogoObservaciones.setContentText("Observaciones:");

	    Optional<String> observacionResultado = dialogoObservaciones.showAndWait();
	    String observaciones = observacionResultado.isPresent() ? observacionResultado.get() : "";

	    orden.setObservaciones(observaciones);
	    guardarCambios(orden, observaciones);

	    // Verificación final
	    if (GestionRepControl.chequearIdOrden(orden.getOrdenId())) {
	        Alert ok = new Alert(Alert.AlertType.INFORMATION, "✅ La Orden de Trabajo ha sido agregada correctamente");
	        ok.setHeaderText("Registro Exitoso");
	        ok.showAndWait();
	    } else {
	        Alert error = new Alert(Alert.AlertType.ERROR, "❌ Se ha producido un error al registrar la orden.");
	        error.setHeaderText("Error");
	        error.showAndWait();
	    }
	}
	

	//GUARDAR CAMBIOS
	public void guardarCambios(OrdenTrabajoModelo orden, String observaciones) {
		String sqlGuardarCambios = "UPDATE orden_de_trabajo SET observaciones = ? WHERE "
				+ "orden_trabajo_id = ? ";
		
		try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sqlGuardarCambios)){
			ps.setString(1, observaciones);
			ps.setString(2, orden.getOrdenId());
			
			ps.executeUpdate();
		}catch(Exception e) {
			 System.out.println("Error al insertar observaciones: " + e.getMessage());
		}
	}
	
	
	public void mostrarMensaje(String mensaje, boolean exito) {

	}
	
	public void ingresarCriterioBusqueda() {

	}
	
	public void mostrarResultado(List<OrdenTrabajoModelo> listaOrdenes) {

	}

	public void agregarDetalleReparacion() {

	}
	
	public void navegar() {
		seleccionarOrden();
	}
	
	//SELECCIONAR UNA ORDEN
	public void seleccionarOrden() {
		 TextInputDialog dialogo = new TextInputDialog();
		    dialogo.setTitle("Entrega de Orden");
		    dialogo.setHeaderText("Ingrese el ID de la Orden LISTA para entregar");
		    dialogo.setContentText("ID Orden:");

		    Optional<String> resultadoDialogo = dialogo.showAndWait();
		    if (!resultadoDialogo.isPresent() || resultadoDialogo.get().isEmpty()) {
		        return;
		    }

		    String ordenId = resultadoDialogo.get();
		    String ordenIdParam;

		    String sqlConsultaOrdenes =
		            "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.DESCRIPCION_FALLA, O.ESTADO, "
		            + "C.NOMBRE, C.APELLIDO, C.EMPRESA, C.TELEFONO, "
		            + "C.DNI, C.CUIT, M.ID, M.TIPO, "
		            + "M.MARCA, M.MODELO, M.COLOR, O.DESPACHO "
		            + "FROM ORDEN_DE_TRABAJO O "
		            + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
		            + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
		            + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
		            + "WHERE O.ORDEN_TRABAJO_ID = ? AND ESTADO = 'Lista'";

		    try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sqlConsultaOrdenes)) {
		        ps.setString(1, ordenId);
		        ResultSet rs = ps.executeQuery();

		        boolean encontrada = false;
		        StringBuilder texto = new StringBuilder();
		        texto.append("✅ ORDEN LISTA PARA ENTREGA\n\n");

		        while (rs.next()) {
		            if (!encontrada) {
		            	ordenIdParam = rs.getString("orden_trabajo_id");
		                formOrdenTrabajoVista.abrirForm(ordenIdParam, PersonalControl.adminIdPersonalControl);
		                
		                encontrada = true;
		                
		            }

		            
		        }

		        if (!encontrada) {
		            Alert alerta = new Alert(Alert.AlertType.WARNING, "❌ No existe una orden LISTA con ese ID.");
		            alerta.setHeaderText("Orden no encontrada");
		            alerta.showAndWait();
		            return;
		        }

		        TextArea areaTexto = new TextArea(texto.toString());
		        areaTexto.setEditable(false);
		        areaTexto.setWrapText(true);

		        Stage ventana = new Stage();
		        ventana.setTitle("Entrega de Orden");
		        ventana.setScene(new Scene(new StackPane(areaTexto), 600, 450));
		        ventana.show();

		    } catch (Exception e) {
		        Alert alerta = new Alert(Alert.AlertType.ERROR, "⚠️ Error en el proceso: " + e.getMessage());
		        alerta.setHeaderText("Error");
		        alerta.showAndWait();
		    }
	}   
	
	
	public void mostrarConfirmacion(String mensaje) {
	}
	
	//MOSTRAR LISTA DE ORDENES.
	public void mostrarLista() {
		 String consultaSQL = "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.DESCRIPCION_FALLA, O.ESTADO, "
                + "C.NOMBRE, C.APELLIDO, OM.MAQUINA_ID, M.TIPO, M.MARCA, M.MODELO "
                + "FROM ORDEN_DE_TRABAJO O "
                + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
                + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
                + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
                + "ORDER BY O.ORDEN_TRABAJO_ID";

        // VENTANA DE LISTAR ORDENES DE TRABAJO
        Stage ventana = new Stage();
        ventana.setTitle("Lista de Órdenes de Trabajo");

        //TABLA DE FILAS DE STRING
        TableView<ObservableList<String>> tabla = new TableView<>();

        //LISTA DE FILAS, CADA FILA ES UNA LISTA DE STRINGS
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();

        String[] nombresCampos = {
                "Orden_trabajo_id", "Fecha_ingreso",
                "Descripcion_falla", "Estado",
                "Cliente", "Maquina_id",
                "Tipo", "Marca", "Modelo"
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
                case "Descripcion_falla" -> 200;
                case "Cliente" -> 150;
                case "Estado" -> 150;
                case "Maquina_id" -> 100;
                case "Tipo" -> 150;
                case "Marca" -> 120;
                case "Modelo" -> 150;
                default -> 120;
            });
            tabla.getColumns().add(columna);
        }

        //HACER CONSULTA Y CARGAR LAS FILAS
        try (PreparedStatement consultaPreparada = GestionRepControl.conexion.prepareStatement(consultaSQL)) {
            ResultSet resultado = consultaPreparada.executeQuery();

            while (resultado.next()) {
                ObservableList<String> fila =
                        FXCollections.observableArrayList();

                String orden_trabajo_id = resultado.getString("orden_trabajo_"
                        + "id");
                String fecha_ingreso = resultado.getString("fecha_ingreso");
                String descripcion_falla = resultado.getString("descripcion_falla");
                String estado = resultado.getString("estado");
                String cliente = resultado.getString("nombre") + " " +
                        resultado.getString("apellido");
                String maquina_id = resultado.getString("maquina_id");
                String tipo = resultado.getString("tipo");
                String marca = resultado.getString("marca");
                String modelo = resultado.getString("modelo");

                if(maquina_id == null) {
                    maquina_id = "X";
                    tipo = "Sin";
                    marca = "Máquina";
                    modelo = "Asociada";
                }

                //INSERTAR VARIABLES EN FILA
                fila.addAll(orden_trabajo_id, fecha_ingreso,
                        descripcion_falla, estado,
                        cliente, maquina_id, tipo,
                        marca, modelo);

                //INSERTAR CADA FILA EN DATOS
                datos.add(fila);
            }

            //INSERTAR TODAS LAS FILAS EN LA TABLA DE FILAS DE STRING
            tabla.setItems(datos);

            //BOTONES
            Button botonEditar = new Button("✏️ Editar");
            Button botonEliminar = new Button("Eliminar");
            Button botonCerrar = new Button("Cerrar");
            Button botonVer = new Button("Ver");
            Button botonCambiarEstado = new Button("Cambiar Estado");
            Button botonBuscarOrden = new Button("Buscar Orden");
            Button botonIngresarDetalleRep = new Button("Ingresar Detalle de Reparación");

            botonBuscarOrden.setOnAction(e -> {

                //ESTE METODO VA A FILTRAR EL TIPO DE BUSQUEDA QUE SE NECESITE.
                buscarOrdenDeTrabajo();
            });

            botonIngresarDetalleRep.setOnAction(e -> {
                ObservableList<String> seleccionado = tabla.getSelectionModel().getSelectedItem();

                if(seleccionado != null){
                    String ordenId = seleccionado.get(0);
                    mostrarFormDetalleRep(ordenId);
                }else{
                    mostrarAdvertencia("Debe seleccionar una orden para ingresar detalle de reparación");
                }

            });

            botonVer.setOnAction(e -> {
                ObservableList<String> seleccionado = tabla.getSelectionModel().getSelectedItem();

                if(seleccionado != null) {
                    String ordenId = seleccionado.get(0);

                    verOrdentrabajo(ordenId);
                }else {
                    mostrarAdvertencia("Debe seleccionar una orden para ver.");
                }
            });

            botonCambiarEstado.setOnAction(e -> {
                ObservableList<String> seleccionado = tabla.getSelectionModel().getSelectedItem();

                if(seleccionado != null) {
                    String ordenId = seleccionado.get(0);

                    cambiarEstadoOrden(ordenId);
                }else{
                    mostrarAdvertencia("Debe seleccionar una orden para cambiar su estado.");
                }
            });

            botonEditar.setOnAction(e -> {
                ObservableList<String> seleccionado =
                        tabla.getSelectionModel().getSelectedItem();

                if(seleccionado != null) {
                    String ordenId = seleccionado.get(0);
                    editarOrdenPorId(ordenId);
                }else {
                    mostrarAdvertencia("Debe seleccionar una orden para editar.");
                }


            });

            botonEliminar.setOnAction(e -> {
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
                            eliminarOrdenPorId(ordenId);
                            datos.remove(seleccionado);
                        }
                    });
                }else {
                    mostrarAdvertencia("Debe seleccionar una orden para"
                            + "eliminar");
                }

            });

            botonCerrar.setOnAction(e ->
                    ventana.close());

            //LAYOUT

            HBox botonesBox = new HBox(10,
                    botonBuscarOrden,
                    botonVer,
                    botonCambiarEstado,
                    botonIngresarDetalleRep,
                    botonEditar, botonEliminar,
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
	
	//MOSTRAR ADVERTENCIA
	public static void mostrarAdvertencia(String mensaje) {
	    Alert alerta = new Alert(Alert.AlertType.WARNING);
	    alerta.setTitle("Advertencia");
	    alerta.setHeaderText(null);
	    alerta.setContentText(mensaje);
	    alerta.showAndWait();
	}

    //INGRESAR DETALLE DE REPARACIÓN
    private void mostrarFormDetalleRep(String ordenId) {

    DetalleRepVista.ingresarDetalleRep(ordenId);

}

    //BUSCAR UNA ORDEN DE TRABAJO
    private void buscarOrdenDeTrabajo() {
        System.out.println("BUSCAR ORDEN");

        //VENTANA
        Stage ventana = new Stage();
        ventana.setTitle("Busqueda de Orden");

        //TITULO
        Label titulo = new Label("Elija criterio de búsqueda");
        titulo.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));

        //DROPDOWN
        ComboBox<String> criterioOpciones = new ComboBox<>();
        criterioOpciones.getItems().addAll("Orden ID",
                "Cliente",
                "Estado",
                "Fecha ingreso",
                "Empresa",
                "Telefono");
        criterioOpciones.setPromptText("Seleccione un criterio");
        criterioOpciones.setPrefWidth(200);

        //BOTONES
        Button botonBuscar = new Button("Aceptar");
        Button botonCancelar = new Button("Cancelar");

        botonBuscar.setOnAction(e-> {
            String criterioSeleccionado = criterioOpciones.getValue();
            String criterio = "";
            if(criterioSeleccionado.equals("Orden ID")) {
                criterio = "ORDEN_TRABAJO_ID";
                GestionRepControl.buscarOrdenCriterio(criterio);
                System.out.println("ORDEN TRABAJO");
            }else if(criterioSeleccionado.equals("Apellido Cliente")) {
                criterio = "APELLIDO";
                GestionRepControl.buscarOrdenCriterio(criterio);
                System.out.println("APELLIDO");
            }else if(criterioSeleccionado.equals("Estado")) {
                criterio = "ESTADO";
                GestionRepControl.buscarOrdenCriterio(criterio);
                System.out.println("ESTADO");
            }else if(criterioSeleccionado.equals("Fecha ingreso")) {
                criterio = "FECHA_INGRESO";
                GestionRepControl.buscarOrdenCriterio(criterio);
                System.out.println("FECHA DE INGRESO");
            }else if(criterioSeleccionado.equals("Empresa")) {
                criterio = "EMPRESA";
                GestionRepControl.buscarOrdenCriterio(criterio);
                System.out.println("EMPRESA");
            }else if(criterioSeleccionado.equals("Telefono")) {
                criterio = "TELEFONO";
                GestionRepControl.buscarOrdenCriterio(criterio);
                System.out.println("TELEFONO");
            }
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

    public void mostrarFormDeBusqueda(String Criterio, String )

    //VER UNA ORDEN DE TRABAJO POR ID
    private void verOrdentrabajo(String ordenId) {

        List<Map<String, Object>> datos = GestionRepControl.obtenerDatosOrdenPorId(ordenId);


        Stage ventanaVerOrden = new Stage();
        ventanaVerOrden.setTitle("Orden de Trabajo: " + ordenId);

        //TITULO
        Label titulo = new Label("Orden de Trabajo - " + ordenId);
        titulo.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));

        //CONTENEDOR
        VBox contenedor = new VBox(10);
        contenedor.setPadding(new Insets(15));
        contenedor.getChildren().add(titulo);
        int contador = 1;
        for (Map<String, Object> fila : datos) {
            Label numMaquina = new Label("Máquina número: " + contador);
            Label lblFecha = new Label("Fecha ingreso: " + (fila.get("FECHA_INGRESO")));
            Label lblDescripcion = new Label("Descripción falla: " + fila.get("DESCRIPCION_FALLA"));
            Label lblEstado = new Label("Estado: " + fila.get("ESTADO"));
            Label lblCliente = new Label("Cliente: " + fila.get("NOMBRE") + " " + fila.get("APELLIDO"));
            Label lblMaquina = new Label("Máquina: " + "-" + fila.get("MAQUINA_ID") + " "
                    + fila.get("TIPO") + " " + fila.get("MARCA") + " " + fila.get("MODELO"));

            Label lblNovedad = new Label("Novedad ID: " + (fila.get("NOVEDAD_ID")
            != null ? fila.get("NOVEDAD_ID") : "Sin novedades."));
            Label lblFechaNovedad = new Label("Fecha novedad: " + (fila.get("FECHA_NOVEDAD")
            != null ? fila.get("FECHA_NOVEDAD") : "Sin novedades."));
            Label lblAdmin = new Label("Admin ID: " + (fila.get("ADMIN_ID") != null ? fila.get("ADMIN_ID") : "Sin administrador."));
            Label lblItem = new Label("Item ID: " + (fila.get("ITEMID") != null ? fila.get("ITEMID") : "Sin item."));
            TextArea comentarioArea = new TextArea((String) fila.get("COMENTARIOITEM"));
            comentarioArea.setWrapText(true);
            comentarioArea.setEditable(false);
            comentarioArea.setPrefRowCount(4);
            comentarioArea.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            Label lblPresupuesto = new Label("Presupuesto: " +
                    (fila.get("PRESUPUESTO_TOTAL") != null ? fila.get("PRESUPUESTO_TOTAL") : "Sin presupuesto"));

            Label lblFechaPresupuesto = new Label("Fecha presupuesto: " +
                    (fila.get("PRESUPUESTO_FECHA") != null ? fila.get("PRESUPUESTO_FECHA") : "Sin fecha"));

            Label lblConFactura = new Label(
                    ((fila.get("CON_FACTURA") != null && (Boolean) fila.get("CON_FACTURA"))
                            ? "Con factura" : "Sin factura")
            );
            lblConFactura.setStyle(
                    (fila.get("CON_FACTURA") != null && (Boolean) fila.get("CON_FACTURA"))
                            ? "-fx-text-fill: green;" : "-fx-text-fill: red;"
            );

            Button btnAgregarPresupuesto = new Button("Agregar Presupuesto");
            btnAgregarPresupuesto.setOnAction(e -> {
                String maquinaId = fila.get("MAQUINA_ID") != null ? fila.get("MAQUINA_ID").toString() : null;
                if (maquinaId != null) {
                    PresupuestosVista.mostrarFormCrearPresupuesto(maquinaId);
                } else {
                    mostrarAdvertencia("El id de máquina es nulo");
                }
            });



            contenedor.getChildren().addAll(
                    numMaquina, lblFecha, lblDescripcion, lblEstado, lblCliente, lblMaquina,
                    new Separator(),
                    lblNovedad, lblFechaNovedad, lblAdmin, lblItem, comentarioArea,
                    btnAgregarPresupuesto, lblPresupuesto, lblFechaPresupuesto, lblConFactura,
                    new Separator(),
                    new Separator()
            );
            contador++;
        }
        ScrollPane scrollPane = new ScrollPane(contenedor);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500);

        Scene scene = new Scene(scrollPane, 600, 650);
        ventanaVerOrden.setScene(scene);
        ventanaVerOrden.show();
    }

    //CAMBIAR ESTADO ORDEN POR ID
    private void cambiarEstadoOrden(String ordenId) {
        System.out.println("CAMBIAR ESTADO ORDEN");
    }
	
	//EDITAR ORDEN POR ID
	private void editarOrdenPorId(String ordenId) {
		System.out.println("EDITAR ORDEN");
	}
	
	//ELIMINAR ORDEN POR ID
	private void eliminarOrdenPorId(String ordenId) {
		System.out.println("ELIMINAR ORDEN");
	}

    //OBTENER ORDENES DISPONIBLES
    public static String obtenerOrdenesDisponibles(Consumer<String> callback) {
        String consultaSQL = "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.DESCRIPCION_FALLA, O.ESTADO, "
                + "C.NOMBRE, C.APELLIDO, OM.MAQUINA_ID, M.TIPO, M.MARCA, M.MODELO "
                + "FROM ORDEN_DE_TRABAJO O "
                + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
                + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
                + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
                + "ORDER BY O.ORDEN_TRABAJO_ID";

        final String[] ordenId = {""};

        // VENTANA DE LISTAR ORDENES DE TRABAJO
        Stage ventana = new Stage();
        ventana.setTitle("SELECCIONE UNA ORDEN DE TRABAJO");

        //BOTONES
        Button btnSeleccionarOrden = new Button("Seleccionar");
        btnSeleccionarOrden.setStyle("-fx-background-color: #15bb15; -fx-text-fill: white; -fx-font-weight: bold;");

        //TABLA DE FILAS DE STRING
        TableView<ObservableList<String>> tabla = new TableView<>();

        //LISTA DE FILAS, CADA FILA ES UNA LISTA DE STRINGS
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();

        String[] nombresCampos = {
                "Orden_trabajo_id", "Fecha_ingreso",
                "Descripcion_falla", "Estado",
                "Cliente", "Maquina_id",
                "Tipo", "Marca", "Modelo"
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
                case "Descripcion_falla" -> 200;
                case "Cliente" -> 150;
                case "Estado" -> 150;
                case "Maquina_id" -> 100;
                case "Tipo" -> 150;
                case "Marca" -> 120;
                case "Modelo" -> 150;
                default -> 120;
            });
            tabla.getColumns().add(columna);
        }

        //HACER CONSULTA Y CARGAR LAS FILAS
        try (PreparedStatement consultaPreparada = GestionRepControl.conexion.prepareStatement(consultaSQL)) {
            ResultSet resultado = consultaPreparada.executeQuery();

            while (resultado.next()) {
                ObservableList<String> fila =
                        FXCollections.observableArrayList();

                String orden_trabajo_id = resultado.getString("orden_trabajo_"
                        + "id");
                String fecha_ingreso = resultado.getString("fecha_ingreso");
                String descripcion_falla = resultado.getString("descripcion_falla");
                String estado = resultado.getString("estado");
                String cliente = resultado.getString("nombre") + " " +
                        resultado.getString("apellido");
                String maquina_id = resultado.getString("maquina_id");
                String tipo = resultado.getString("tipo");
                String marca = resultado.getString("marca");
                String modelo = resultado.getString("modelo");

                if(maquina_id == null) {
                    maquina_id = "X";
                    tipo = "Sin";
                    marca = "Máquina";
                    modelo = "Asociada";
                }

                //INSERTAR VARIABLES EN FILA
                fila.addAll(orden_trabajo_id, fecha_ingreso,
                        descripcion_falla, estado,
                        cliente, maquina_id, tipo,
                        marca, modelo);

                //INSERTAR CADA FILA EN DATOS
                datos.add(fila);
            }

            //INSERTAR TODAS LAS FILAS EN LA TABLA DE FILAS DE STRING
            tabla.setItems(datos);

            //BOTONES
            Button botonEditar = new Button("✏️ Editar");
            Button botonEliminar = new Button("Eliminar");
            Button botonCerrar = new Button("Cerrar");
            Button botonVer = new Button("Ver");
            Button botonCambiarEstado = new Button("Cambiar Estado");
            Button botonBuscarOrden = new Button("Buscar Orden");
            Button botonIngresarDetalleRep = new Button("Ingresar Detalle de Reparación");



            botonCerrar.setOnAction(e ->
                    ventana.close());

            //LAYOUT

            HBox botonesBox = new HBox(10,
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

        btnSeleccionarOrden.setOnAction(event -> {
            ObservableList<String> seleccionado = tabla.getSelectionModel().getSelectedItem();

            if(seleccionado != null){
                String idSeleccionado = seleccionado.get(0);
                callback.accept(idSeleccionado);
                ventana.close();
            }else{
                mostrarAdvertencia("Debe seleccionar una orden de la tabla.");
            }
        });

        VBox layout = new VBox(15, tabla, btnSeleccionarOrden);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        Scene escena = new Scene(layout);
        ventana.setScene(escena);
        ventana.show();

        return ordenId[0];
    }
		
    //SETTERS Y GETTERS
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public TecnicoModelo getTecnicoAsignado() {
		return tecnicoAsignado;
	}
	
	public void setTecnicoAsignado(TecnicoModelo tecnicoAsignado) {
		this.tecnicoAsignado = tecnicoAsignado;
	}
	
	public ClienteModelo getCliente() {
		return cliente;
	}
	
	public void setCliente(ClienteModelo cliente) {
		this.cliente = cliente;
	}
	
	public String getDetalleRep() {
		return detalleRep;
	}
	
	public void setDetalleRep(String detalleRep) {
		this.detalleRep = detalleRep;
	}
	
	public String getMensajeExito() {
		return mensajeExito;
	}
	
	public void setMensajeExito(String mensajeExito) {
		this.mensajeExito = mensajeExito;
	}
	
	public String getMensajeError() {
		return mensajeError;
	}
	
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public void mostrarMenuOrdenesTrabajo() {
		
		System.out.println("Menú de Órdenes de Trabajo");
		
	}
}
