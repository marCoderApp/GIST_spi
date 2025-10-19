package modelos.Personal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.ConexionDB;
import modelos.GestionRep.Credenciales;

public class AdminModelo {
	
	private String id;
	private String nombre;
	private String apellido;
	private int turno;
	private Connection conexion = ConexionDB.conectar();
	
	public AdminModelo(String nombre, String apellido, int turno) {
		this.id = generarId();
		this.nombre = nombre;
		this.apellido = apellido;
		this.turno = turno;
	}
	
	public String generarId() {
		String sql = "SELECT cliente_id FROM ADMINISTRADOR ORDER BY  DESC LIMIT 1";
	    String ultimoId = null;
	    try (PreparedStatement ps = conexion.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            ultimoId = rs.getString("administrador_id");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    int siguienteNumero = 1;

	    if (ultimoId != null && ultimoId.startsWith("ADM")) {
	        try {
	            int numeroActual = Integer.parseInt(ultimoId.substring(3));
	            siguienteNumero = numeroActual + 1;
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }

	    return String.format("ADM%04d", siguienteNumero);
	}
	
	
	public Credenciales buscarUsuario(String nombreUsuario) {
		// Lógica para buscar un administrador por su nombre de usuario
		
		String sqlConsulta = "SELECT * FROM credenciales WHERE usuario = ?";
		
		  try (PreparedStatement ps = conexion.prepareStatement(sqlConsulta)) {
	            ps.setString(1, nombreUsuario);
	            ResultSet result = ps.executeQuery();
	            if (result.next()) {
	                Credenciales cred = new Credenciales(null, null, null, null, null);
	                cred.setUsuario(result.getString("usuario"));
	                cred.setContrasena(result.getString("contraseña"));
	                cred.setRol(modelos.GestionRep.RolCredencial.valueOf(result.getString("rol")));
	                cred.setFechaCreacion(result.getTimestamp("fechaCreacion").toLocalDateTime());
	                cred.setUsuarioId(result.getString("usuario_id"));
	                
	                return cred;             
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
		return null;
	}
	
	public Boolean verificarContraseña(String contraseña) {
		// Lógica para verificar la contraseña del administrador
		// Retorna true si la contraseña es correcta, de lo contrario retorna false
		return null;
	}
	
	public String getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getApellido() {
		return apellido;
	}

	public int getTurno() {
		return turno;
	}
	
	public void setTurno(int turno) {
		this.turno = turno;
	}
	
	@Override
	public String toString() {
		return "AdminModelo [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", turno=" + turno + "]";
	}

}
