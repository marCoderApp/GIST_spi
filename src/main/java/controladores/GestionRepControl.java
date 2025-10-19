package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionDB;
import modelos.GestionRep.ClienteModelo;
import modelos.GestionRep.DetalleReparacionModelo;
import modelos.GestionRep.OrdenTrabajoModelo;
import modelos.GestionRep.PedidoModelo;
import modelos.GestionRep.PresupuestoModelo;

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
	
	public boolean registrarCliente(ClienteModelo cliente) {
		
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
					return true;
				}
			
		} catch (Exception e) {
			System.out.println("Error al registrar el cliente: " + e.getMessage());
			return false;
		}
		return false;
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
