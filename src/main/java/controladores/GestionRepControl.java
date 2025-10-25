package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import conexion.ConexionDB;
import modelos.GestionRep.ClienteModelo;
import modelos.GestionRep.DetalleReparacionModelo;
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
		Scanner scanner = new Scanner(System.in);
		ClientesVista clientesVista = new ClientesVista();
		
		System.out.println("Seleccione una opción:");
		System.out.println("1. Elegir cliente existente");
		System.out.println("2. Crear nuevo cliente");
		 int opcion = scanner.nextInt();
		    scanner.nextLine(); 
		    
			if (opcion == 1) {
				List<ClienteModelo> clientes = ClienteModelo.listarClientes();
				ClientesVista.mostrarListaClientes(clientes);
				System.out.println("Ingrese el ID del cliente: ");
				String clienteId = scanner.nextLine();
				System.out.println("Cliente seleccionado: " + clienteId);
				clienteIdGestionRep = clienteId;
				return clienteId;
			} else if (opcion == 2) {
				System.out.println("Creando nuevo cliente...");
				
				ClienteModelo nuevoCliente = clientesVista.crearNuevoCliente();
				clienteIdGestionRep = nuevoCliente.getClienteId();
				return nuevoCliente.getClienteId(); // Retornar el ID del nuevo cliente
				
			} else {
				System.out.println("Opción inválida. Por favor, intente de nuevo.");
			}
		
		return "";
    }
	
	public List<MaquinaModelo> seleccionarMaquinas(OrdenTrabajoModelo orden) {
		String ordenId = orden.getOrdenId();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Seleccione una opción:");
		System.out.println("1. Seleccionar máquina/s existente/s");
		System.out.println("2. Añadir máquina/s nueva/s");
		
		 int opcion = scanner.nextInt();
		    scanner.nextLine(); 
		    
			if (opcion == 1) {
				List<MaquinaModelo> maquinas = MaquinaModelo.listarMaquinas();
				MaquinaModelo.mostrarListaMaquinas(maquinas);
				List<MaquinaModelo> maquinasSeleccionadas = agregarMaquinasExistentes();
		        insertarEnOrdenMaquinas(maquinasSeleccionadas);
				System.out.println("Desea añadir una nueva máquina?:");
				System.out.println("1. Si");
				System.out.println("2. No");
				
				
				if (scanner.nextInt() == 1) {
					List<MaquinaModelo> nuevasMaquinas = agregarMaquinasNuevas(ordenId);
					maquinasSeleccionadas.addAll(nuevasMaquinas);
				}
				
				return maquinasSeleccionadas;
			} else if (opcion == 2) {
				List<MaquinaModelo> nuevasMaquinas = agregarMaquinasNuevas(ordenId);
				return nuevasMaquinas;
				
			} else {
				System.out.println("Opción inválida. Por favor, intente de nuevo.");
			}
		
		
		return null;
	}
	
	public List<MaquinaModelo> agregarMaquinasExistentes() {
		List<MaquinaModelo> maquinasSeleccionadas = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		System.out.println("=== Seleccionar máquinas existentes ===");
		System.out.println("Ingrese los IDs de las máquinas separadas por coma (ej: MAQ0001,MAQ0003,MAQ0007): ");
		String entrada = scanner.nextLine();

		// separar los IDs
		String[] ids = entrada.split(",");

		for (String id : ids) {
		    id = id.trim(); // quitar espacios
		    MaquinaModelo maquina1 = buscarMaquinaPorId(id);

		    if (maquina1 != null) {
		        maquinasSeleccionadas.add(maquina1);
		        
		        
		    } else {
		        System.out.println("⚠️  No se encontró la máquina con ID: " + id);
		    }
		}

		System.out.println("Máquinas seleccionadas correctamente ✅");
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
	
	public void obtenerListaOrdenes() {
		// Lógica para obtener la lista de órdenes de trabajo
	}	
	
	public void registrarEntrega(LocalDateTime fechaEntrega, String ordenId) {
		// Lógica para registrar la entrega de una orden de trabajo
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
