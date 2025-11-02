package vistas.GestionRep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import controladores.GestionRepControl;
import controladores.PersonalControl;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
	//MODIFICAR ESTE MÉTODO.
	public void mostrarLista() {
		 String consultaSQL = "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.DESCRIPCION_FALLA, O.ESTADO, "
		            + "C.NOMBRE, C.APELLIDO, OM.MAQUINA_ID, M.TIPO, M.MARCA, M.MODELO "
		            + "FROM ORDEN_DE_TRABAJO O "
		            + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
		            + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
		            + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
		            + "ORDER BY O.ORDEN_TRABAJO_ID";
		 
		  Stage ventana = new Stage();
	      ventana.setTitle("Lista de Órdenes de Trabajo");

	      TableView<ObservableList<String>> tabla = new TableView<>();
	      ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();
	      
	      String[] nombresCampos = {
	    		  "Orden_trabajo_id", "Fecha_ingreso",
	    		  "Descripcion_falla", "Estado",
	    		  "Cliente", "Maquina_id", 
	    		  "Tipo", "Marca", "Modelo"
	      };
	      
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
		          
		          fila.addAll(orden_trabajo_id, fecha_ingreso,
		        		  descripcion_falla, estado,
		        		  cliente, maquina_id, tipo,
		        		  marca, modelo);
		          datos.add(fila); 
		        }
		        
		        tabla.setItems(datos);
		        
		        Button botonEditar = new Button("✏️ Editar");
		        Button botonEliminar = new Button("Eliminar");
		        Button botonCerrar = new Button("Cerrar");
		        
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
		        
		        HBox botonesBox = new HBox(10, botonEditar, botonEliminar, 
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
	private static void mostrarAdvertencia(String mensaje) {
	    Alert alerta = new Alert(Alert.AlertType.WARNING);
	    alerta.setTitle("Advertencia");
	    alerta.setHeaderText(null);
	    alerta.setContentText(mensaje);
	    alerta.showAndWait();
	}
	
	//EDITAR ORDEN POR ID
	private void editarOrdenPorId(String ordenId) {
		System.out.println("EDITAR ORDEN");
	}
	
	//ELIMINAR ORDEN POR ID
	private void eliminarOrdenPorId(String ordenId) {
		System.out.println("ELIMINAR ORDEN");
	}
		
	
	//Setters and getters
	
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
