package modelos.GestionRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionDB;

public class ClienteModelo {
	
	private String clienteId;
	private String nombre;
	private String apellido;
	private String empresa;
	private String telefono;
	private String dni;
	private String cuit;
	private static List<ClienteModelo> clientes = new ArrayList<>();
	private static Connection conexion = ConexionDB.conectar();

	public ClienteModelo(String nombre, String apellido,
			String empresa, String telefono,
			String dni, String cuit) {
		this.clienteId = generarClienteId();
		this.nombre = nombre;
		this.apellido = apellido;
		this.empresa = empresa;
		this.telefono = telefono;
		this.dni = dni;
		this.setCuit(cuit);
	}
	
	public String generarClienteId() {
		String sql = "SELECT cliente_id FROM CLIENTE ORDER BY cliente_id DESC LIMIT 1";
	    String ultimoId = null;
	    try (PreparedStatement ps = conexion.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            ultimoId = rs.getString("cliente_id");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    int siguienteNumero = 1;

	    if (ultimoId != null && ultimoId.startsWith("CL")) {
	        try {
	            int numeroActual = Integer.parseInt(ultimoId.substring(2));
	            siguienteNumero = numeroActual + 1;
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }

	    return String.format("CL%04d", siguienteNumero);
	}
	
	public Boolean guardarCliente(PreparedStatement ps) {
		int rowsAfectadas = 0;
		
		try {
			rowsAfectadas = ps.executeUpdate();
			
			if (rowsAfectadas > 0) {
				System.out.println("Cliente guardado exitosamente.");
				return true;
			} else {
				System.out.println("No se pudo guardar el cliente.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
		
	}
	
	public void eliminarCliente(String clienteId) {

	}
	
	public void actualizarDatos(ClienteModelo datos) {
		
	}
	
	public ClienteModelo obtenerCliente(String clienteId) {
		return new ClienteModelo(clienteId, clienteId, clienteId, clienteId, clienteId, clienteId);
	}
	
	public static List<ClienteModelo> listarClientes() {
		
		String sqlConsulta = "SELECT * FROM cliente";
		
		try (PreparedStatement ps = conexion.prepareStatement(sqlConsulta)) {
			ResultSet result = ps.executeQuery();

			while (result.next()) {
				ClienteModelo cliente = new ClienteModelo(
						result.getString("nombre"),
						result.getString("apellido"),
						result.getString("empresa"),
						result.getString("telefono"),
						result.getString("dni"),
						result.getString("cuit"));

				cliente.setClienteId(result.getString("cliente_id"));
				clientes.add(cliente);
			}

			return clientes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	// Getters
	public String getClienteId() {
		return clienteId;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getEmpresa() {
		return empresa;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public String getDni() {
		return dni;
	}
	
	
	// Setters
	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	

}
