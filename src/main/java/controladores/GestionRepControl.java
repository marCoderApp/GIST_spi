package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
				return clienteId;
			} else if (opcion == 2) {
				System.out.println("Creando nuevo cliente...");
				
				ClienteModelo nuevoCliente = clientesVista.crearNuevoCliente();
				return nuevoCliente.getClienteId(); // Retornar el ID del nuevo cliente
			} else {
				System.out.println("Opción inválida. Por favor, intente de nuevo.");
			}
		
		return "";
    }
	
	public List<MaquinaModelo> seleccionarMaquinas() {
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
				
				System.out.println("Desea añadir una nueva máquina?:");
				System.out.println("1. Si");
				System.out.println("2. No");
				
				
				if (scanner.nextInt() == 1) {
					List<MaquinaModelo> nuevasMaquinas = agregarMaquinasNuevas();
					maquinasSeleccionadas.addAll(nuevasMaquinas);
				}
				
				return maquinasSeleccionadas;
			} else if (opcion == 2) {
				
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
		        return maquinasSeleccionadas;
		        
		    } else {
		        System.out.println("⚠️  No se encontró la máquina con ID: " + id);
		    }
		}

		System.out.println("Máquinas seleccionadas correctamente ✅");
		return maquinasSeleccionadas;
		
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
                        rs.getString("color"),
                        rs.getString("orden_id"));
                maquina.setMaquinaId(rs.getString("id"));
                return maquina;
            }
            
        } catch (Exception e) {
            System.out.println("Error al buscar la máquina: " + e.getMessage());
        }
		
		return null;
	}

	private List<MaquinaModelo> agregarMaquinasNuevas() {
		
		
		
		return null;
	}

	public DetalleReparacionModelo registrarDetalle(DetalleReparacionModelo detalle) {
		// Lógica para registrar el detalle de reparación
		return detalle; // Retorna el detalle registrado (simulado)
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
