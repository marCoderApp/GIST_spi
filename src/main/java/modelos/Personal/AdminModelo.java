package modelos.Personal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import conexion.ConexionDB;
import modelos.GestionRep.Credenciales;
import modelos.Personal.PersonalBase;

public class AdminModelo extends PersonalBase {

	private int turno;
	private Connection conexion = ConexionDB.conectar();
	
	public AdminModelo(String nombre, String apellido, int turno) {
		super(nombre, apellido);
        this.id = generarId();
        this.turno = turno;
	}
	
	public String generarId() {
		String sql = "SELECT administrador_id FROM ADMINISTRADOR ORDER BY administrador_id DESC LIMIT 1";
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

	    return String.format("ADM%03d", siguienteNumero);
	}
	
	
	public Credenciales buscarUsuario(String nombreUsuario) {
		// Lógica para buscar un administrador por su nombre de usuario
		
		String sqlConsulta = "SELECT * FROM credenciales WHERE usuario = ?";
		
		  try (PreparedStatement ps = conexion.prepareStatement(sqlConsulta)) {
	            ps.setString(1, nombreUsuario);
	            ResultSet result = ps.executeQuery();
	            if (result.next()) {
	                Credenciales cred = new Credenciales(null, null, null, null, null, null);
	                cred.setUsuario(result.getString("usuario"));
	                cred.setContrasena(result.getString("contraseña"));
	                cred.setRol(modelos.GestionRep.RolCredencial.valueOf(result.getString("rol")));
	                cred.setFechaCreacion(result.getTimestamp("fechaCreacion").toLocalDateTime());
	                cred.setAdmin_id(result.getString("admin_id"));
	                cred.setTecnico_id(result.getString("tecnico_id"));
	                
	                return cred;             
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
		return null;
	}
	
	public Boolean verificarContraseña(String contraseña) {
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
    public String getRol(){
        return "ADMIN";
    }
	
	@Override
	public String toString() {
		return "AdminModelo [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", turno=" + turno + "]";
	}

}
