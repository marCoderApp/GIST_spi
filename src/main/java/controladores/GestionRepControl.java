package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import conexion.ConexionDB;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import modelos.GestionRep.ClienteModelo;
import modelos.GestionRep.DetalleReparacionModelo;
import modelos.GestionRep.EstadoOrden;
import modelos.GestionRep.MaquinaModelo;
import modelos.GestionRep.OrdenTrabajoModelo;
import modelos.GestionRep.PedidoModelo;
import modelos.GestionRep.PresupuestoModelo;
import vistas.GestionRep.ClientesVista;

public class GestionRepControl {

	private boolean ordenCreada;
	private List<OrdenTrabajoModelo> ordenesTrabajo = new ArrayList<>();
	public static Connection conexion = ConexionDB.conectar();
	public static String clienteIdGestionRep;
	private String ordenId;
	
	public GestionRepControl() {
		
		this.setOrdenCreada(false);
	}

	
	public boolean registrarOrden(OrdenTrabajoModelo orden) {
        this.ordenesTrabajo.add(orden);
        this.setOrdenCreada(true);
        return true;
    }
	
	public OrdenTrabajoModelo buscarOrdenCriterio(String criterio) {
		for (OrdenTrabajoModelo orden : ordenesTrabajo) {
			if (orden.getOrdenId().equalsIgnoreCase(criterio) || orden.getCliente_id().equalsIgnoreCase(criterio)
					|| orden.getAdminId().equalsIgnoreCase(criterio)) {
				return orden; // Retorna la orden si se encuentra una coincidencia
			}
		}
		return null; // Retorna null si no se encuentra la orden
	}
	
	public ClienteModelo registrarCliente(ClienteModelo cliente) {
		
String sqlSentencia = "INSERT INTO cliente (cliente_id, nombre, apellido, empresa, telefono, dni, cuit) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement ps = conexion.prepareStatement(sqlSentencia)) {
			ps.setString(1, cliente.getClienteId());
			ps.setString(2, cliente.getNombre());
			ps.setString(3, cliente.getApellido());
			
			 if (cliente.getEmpresa() == null || cliente.getEmpresa().trim().isEmpty()) {
		            ps.setNull(4, java.sql.Types.VARCHAR);
		        } else {
		            ps.setString(4, cliente.getEmpresa());
		        }
			 
			 if (cliente.getTelefono() == null || cliente.getTelefono().trim().isEmpty()) {
		            ps.setNull(5, java.sql.Types.VARCHAR);
		        } else {
		            ps.setString(5, cliente.getTelefono());
		        }
			 
			 if (cliente.getDni() == null || cliente.getDni().trim().isEmpty()) {
		            ps.setNull(6, java.sql.Types.VARCHAR);
		        } else {
		            ps.setString(6, cliente.getDni());
		        }
			 
				if (cliente.getCuit() == null || cliente.getCuit().trim().isEmpty()) {
					ps.setNull(7, java.sql.Types.VARCHAR);
				} else {
					ps.setString(7, cliente.getCuit());
				}
				
				Boolean guardado = cliente.guardarCliente(ps);
				if (guardado) {
					return cliente;
				}
			
		} catch (Exception e) {
			System.out.println("Error al registrar el cliente: " + e.getMessage());
			return null;
		}
		return null;
	}
	
	public String elegirCliente() {
		ClientesVista clientesVista = new ClientesVista();

	    // 1️⃣ Diálogo para elegir entre cliente existente o crear uno nuevo
	    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
	    alerta.setTitle("Selección de Cliente");
	    alerta.setHeaderText("Seleccione una opción:");
	    alerta.setContentText("¿Qué desea hacer?");

	    ButtonType botonExistente = new ButtonType("Elegir cliente existente");
	    ButtonType botonNuevo = new ButtonType("Crear nuevo cliente");
	    ButtonType botonCancelar = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

	    alerta.getButtonTypes().setAll(botonExistente, botonNuevo, botonCancelar);

	    Optional<ButtonType> resultado = alerta.showAndWait();

	    // ✅ Elegir existente
	    if (resultado.isPresent() && resultado.get() == botonExistente) {

	        List<ClienteModelo> clientes = ClienteModelo.listarClientes();

	        if (clientes.isEmpty()) {
	            Alert alertaVacio = new Alert(Alert.AlertType.WARNING);
	            alertaVacio.setHeaderText("No hay clientes cargados");
	            alertaVacio.setContentText("Debe crear un cliente nuevo.");
	            alertaVacio.show();
	            return "";
	        }

	        List<String> opcionesClientes = clientes.stream()
	                .map(cliente -> cliente.getClienteId() + " - " + cliente.getNombre() + " " + cliente.getApellido())
	                .toList();

	        ChoiceDialog<String> dialogoClientes =
	                new ChoiceDialog<>(opcionesClientes.get(0), opcionesClientes);
	        dialogoClientes.setTitle("Clientes");
	        dialogoClientes.setHeaderText("Seleccione un cliente existente:");
	        dialogoClientes.setContentText("Clientes:");

	        Optional<String> seleccion = dialogoClientes.showAndWait();

	        if (seleccion.isPresent()) {
	            String clienteId = seleccion.get().split(" - ")[0]; // obtener solo el ID
	            System.out.println("Cliente seleccionado: " + clienteId);
	            clienteIdGestionRep = clienteId;
	            return clienteId;
	        } else {
	            return "";
	        }
	    }

	 
	    if (resultado.isPresent() && resultado.get() == botonNuevo) {
	        ClienteModelo nuevoCliente = clientesVista.crearNuevoCliente();
	        clienteIdGestionRep = nuevoCliente.getClienteId();
	        return nuevoCliente.getClienteId();
	    }

	   
	    Alert alertaInvalido = new Alert(Alert.AlertType.ERROR);
	    alertaInvalido.setHeaderText("Opción inválida");
	    alertaInvalido.setContentText("Debe seleccionar una opción válida.");
	    alertaInvalido.show();

	    return "";
    }
	
	public List<MaquinaModelo> seleccionarMaquinas(OrdenTrabajoModelo orden) {
		 String ordenId = orden.getOrdenId();
		    List<MaquinaModelo> maquinasSeleccionadas = null;

		    List<String> opcionesSeleccion = List.of(
		            "Seleccionar máquina/s existente/s",
		            "Añadir máquina/s nueva/s"
		    );

		    ChoiceDialog<String> dialogoOpciones = new ChoiceDialog<>(
		            opcionesSeleccion.get(0), opcionesSeleccion
		    );
		    dialogoOpciones.setTitle("Selección de Máquinas");
		    dialogoOpciones.setHeaderText("Seleccione una opción");
		    dialogoOpciones.setContentText("Elija una opción:");

		    Optional<String> resultado = dialogoOpciones.showAndWait();

		    if (resultado.isPresent()) {
		        String seleccion = resultado.get();

		        if (seleccion.equals(opcionesSeleccion.get(0))) {
		            // Llamamos al método separado
		            maquinasSeleccionadas = agregarMaquinasExistentes();
		            insertarEnOrdenMaquinas(maquinasSeleccionadas);

		            // Preguntar si quiere agregar nuevas máquinas
		            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		            alerta.setTitle("Agregar Máquina");
		            alerta.setHeaderText("¿Desea añadir una nueva máquina?");
		            alerta.getButtonTypes().setAll(
		                    new ButtonType("Sí", ButtonData.YES),
		                    new ButtonType("No", ButtonData.NO)
		            );

		            Optional<ButtonType> respuesta = alerta.showAndWait();

		            if (respuesta.isPresent() &&
		                respuesta.get().getButtonData() == ButtonData.YES) {

		                List<MaquinaModelo> nuevasMaquinas = agregarMaquinasNuevas(ordenId);
		                maquinasSeleccionadas.addAll(nuevasMaquinas);
		            }

		            return maquinasSeleccionadas;

		        } else if (seleccion.equals(opcionesSeleccion.get(1))) {
		            // Solo agregar máquinas nuevas
		            return agregarMaquinasNuevas(ordenId);
		        }
		    }

		    // Si se cierra el diálogo o selección inválida
		    Alert error = new Alert(Alert.AlertType.ERROR);
		    error.setTitle("Error");
		    error.setHeaderText("Opción inválida");
		    error.setContentText("Debe seleccionar una opción válida.");
		    error.showAndWait();

		    return null;
	}
	
	public List<MaquinaModelo> agregarMaquinasExistentes() {
		List<MaquinaModelo> listaMaquinas = MaquinaModelo.listarMaquinas();
	    List<MaquinaModelo> maquinasSeleccionadas = new ArrayList<>();

	    if (listaMaquinas.isEmpty()) {
	        Alert alertaVacio = new Alert(Alert.AlertType.WARNING);
	        alertaVacio.setHeaderText("No hay máquinas cargadas");
	        alertaVacio.setContentText("Debe agregar nuevas máquinas primero.");
	        alertaVacio.show();
	        return maquinasSeleccionadas;
	    }

	    boolean continuar = true;

	    while (continuar) {

	        // Preparar lista de opciones
	        List<String> opciones = listaMaquinas.stream()
	                .map(m -> m.getMaquinaId() + " | " + m.getTipo() + " | " + m.getMarca() + " | " + m.getModelo())
	                .toList();

	        ChoiceDialog<String> dialogo = new ChoiceDialog<>(opciones.get(0), opciones);
	        dialogo.setTitle("Seleccionar Máquina");
	        dialogo.setHeaderText("Seleccione una máquina existente:");
	        dialogo.setContentText("Máquina:");

	        Optional<String> resultado = dialogo.showAndWait();

	        if (resultado.isPresent()) {
	            String seleccionado = resultado.get();
	            String idSeleccionado = seleccionado.split(" \\| ")[0];

	            // Agregar máquina seleccionada a la lista
	            listaMaquinas.stream()
	                    .filter(m -> m.getMaquinaId().equals(idSeleccionado))
	                    .findFirst()
	                    .ifPresent(maquinasSeleccionadas::add);

	            // Preguntar si quiere seleccionar otra máquina
	            Alert pregunta = new Alert(Alert.AlertType.CONFIRMATION);
	            pregunta.setTitle("Seleccionar más máquinas");
	            pregunta.setHeaderText("¿Desea agregar otra máquina?");
	            pregunta.getButtonTypes().setAll(
	                    new ButtonType("Sí", ButtonData.YES),
	                    new ButtonType("No", ButtonData.NO)
	            );

	            Optional<ButtonType> respuesta = pregunta.showAndWait();
	            continuar = respuesta.isPresent() &&
	                        respuesta.get().getButtonData() == ButtonData.YES;

	        } else {
	            continuar = false;
	        }
	    }

	    return maquinasSeleccionadas;
		
	}
	
	private void insertarEnOrdenMaquinas(List <MaquinaModelo> maquinasSeleccionadas) {
		
		String sqlInsertarEnOrdenMaquinas = "INSERT INTO orden_maquinas"
				+ "(orden_id, maquina_id) VALUES (?, ?)";
		
		try(PreparedStatement ps = conexion.prepareStatement(sqlInsertarEnOrdenMaquinas)){
			
			for(MaquinaModelo m : maquinasSeleccionadas) {
				ps.setString(1, ordenId);
				ps.setString(2, m.getMaquinaId());
				ps.addBatch();
			}
			
			ps.executeBatch();
			
		}catch(Exception e) {
			System.out.println("Error al buscar la máquina: " + e.getMessage());
		}
		
	}
	
	private MaquinaModelo buscarMaquinaPorId(String id) {
		
		String sqlConsulta = "SELECT * FROM MAQUINAS WHERE id = ?";
		
		try(PreparedStatement ps = conexion.prepareStatement(sqlConsulta)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                MaquinaModelo maquina = new MaquinaModelo(
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("color"));
                maquina.setMaquinaId(rs.getString("id"));
                return maquina;
            }
            
        } catch (Exception e) {
            System.out.println("Error al buscar la máquina: " + e.getMessage());
        }
		
		return null;
	}

	private List<MaquinaModelo> agregarMaquinasNuevas(String ordenId) {
		Scanner scanner	 = new Scanner(System.in);
		List<MaquinaModelo> nuevasMaquinas = new ArrayList<>();
		MaquinaModelo nuevaMaquina = new MaquinaModelo(null, null, null, null);
		
		boolean agregarOtra = true;

        System.out.println("=== Registrar nuevas máquinas ===");

        while (agregarOtra) {
            System.out.print("Tipo (eléctrica o combustión): ");
            String tipo = scanner.nextLine();

            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();

            System.out.print("Color: ");
            String color = scanner.nextLine();

            
            //Asignar valores a la nueva máquina
            
            nuevaMaquina.setTipo(tipo);
            nuevaMaquina.setMarca(marca);
            nuevaMaquina.setModelo(modelo);
            nuevaMaquina.setColor(color);
            
            nuevasMaquinas.add(nuevaMaquina);
            
            try {
				boolean guardado = nuevaMaquina.guardarNuevaMaquina(nuevasMaquinas, ordenId);
					if (guardado) {
						System.out.println("Máquinas registradas correctamente ✅");
					}
		            System.out.print("¿Desea agregar otra máquina? (s/n): ");
		            String respuesta = scanner.nextLine();
		            if (!respuesta.equalsIgnoreCase("s")) {
		                agregarOtra = false;
		            }
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		
		return nuevasMaquinas;
	}
	
	public void insertarOrdenBase(OrdenTrabajoModelo orden) {
		
		ordenId = orden.getOrdenId();
		
		String sqlInsertarOrdenBase = "INSERT INTO "
				+ "ORDEN_DE_TRABAJO (orden_trabajo_id, cliente_id, descripcion_falla, fecha_ingreso, estado, admin_id, tecnico_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
			try(PreparedStatement psInsertarOrdenBase = conexion.prepareStatement(sqlInsertarOrdenBase)){
					psInsertarOrdenBase.setString(1, orden.getOrdenId());
					psInsertarOrdenBase.setString(2, clienteIdGestionRep);
					psInsertarOrdenBase.setString(3, orden.getDescripcion_falla());
					if (orden.getFechaIngreso() == null) {
					    orden.setFechaIngreso(LocalDate.now()); // o LocalDateTime.now() si usás fecha+hora
					}
					psInsertarOrdenBase.setDate(4, java.sql.Date.valueOf(orden.getFechaIngreso()));
					psInsertarOrdenBase.setString(5, orden.getEstado().getValor());
					psInsertarOrdenBase.setString(6, PersonalControl.adminIdPersonalControl);
					psInsertarOrdenBase.setString(7, PersonalControl.tecnicoIdPersonalControl);
				
					psInsertarOrdenBase.executeUpdate();
				
			}catch(Exception e) {
				 System.out.println("Error al insertar Orden: " + e.getMessage());
				 e.printStackTrace(); // <-- esto muestra el tipo de error y la línea exacta
			}
	}

	public static boolean chequearIdOrden(String ordenId) {
		String sqlCheckOrdenId = "SELECT * FROM orden_de_trabajo WHERE orden_trabajo_id = ?";
		boolean esGuardado = false;
		
		try(PreparedStatement ps = conexion.prepareStatement(sqlCheckOrdenId)){
			ps.setString(1, ordenId);
			
			  try (ResultSet rs = ps.executeQuery()) {
		            esGuardado = rs.next(); 
		        }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return esGuardado;
		
	}
	
	public boolean registrarEntrega(String ordenIdParam, String adminId) {
		
		String sqlRetirarEntrega = "UPDATE ORDEN_DE_TRABAJO SET despacho = ?, "
				+ "fecha_retiro = ? WHERE ORDEN_TRABAJO_ID = ?";
		
		try(PreparedStatement ps = conexion.prepareStatement(sqlRetirarEntrega)){
			ps.setString(1, adminId);
			ps.setTimestamp(2, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
			ps.setString(3, ordenIdParam);
			int filasAfectadas = ps.executeUpdate();

		        if (filasAfectadas > 0) {
		        	OrdenTrabajoModelo.actualizarEstado(ordenIdParam, EstadoOrden.RETIRADA);
		            System.out.println("\n✅ La máquina fue entregada al cliente correctamente.\n");
		            return true;
		        } else {
		            System.out.println("\n⚠️ No se encontró una orden LISTA con ese ID.\n");
		        }
		}catch(Exception e) {
			 e.printStackTrace(); // <-- esto muestra el tipo de error y la línea exacta
		}
		return false;
		
	}

	public DetalleReparacionModelo registrarDetalle(DetalleReparacionModelo detalle) {
		
		return detalle; 
	}
	
	public void crearPresupuesto(PresupuestoModelo presupuesto) {
		// Lógica para crear un presupuesto
	}
	
	public void registrarPedido(PedidoModelo pedido) {
		// Lógica para registrar un pedido
	}
	
	public void obtenerListaOrdenes(PedidoModelo pedido) {
		// Lógica para obtener la lista de órdenes de trabajo
	}
	
	public static void obtenerListaOrdenes() {
	

	}	
	
	//Getters and Setters
	public boolean isOrdenCreada() {
		return ordenCreada;
	}

	public void setOrdenCreada(boolean ordenCreada) {
		this.ordenCreada = ordenCreada;
	}

	public List<OrdenTrabajoModelo> getOrdenesTrabajo() {
		return ordenesTrabajo;
	}

	public void setOrdenesTrabajo(List<OrdenTrabajoModelo> ordenesTrabajo) {
		this.ordenesTrabajo = ordenesTrabajo;
	}
	
	
}
