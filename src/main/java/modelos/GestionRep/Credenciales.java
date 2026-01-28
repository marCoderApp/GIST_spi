package modelos.GestionRep;

import com.mysql.cj.xdevapi.PreparableStatement;
import conexion.ConexionDB;
import controladores.GestionRepControl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Credenciales {

	   private String usuario;
	    private String contraseña;
	    private RolCredencial rol;
	    private LocalDateTime fechaCreacion; 
	    private String admin_id;
	    private String tecnico_id;
	    private GestionRepControl gestionRepControl;

	    public Credenciales(String usuario, String contraseña, RolCredencial rol, LocalDateTime fechaCreacion,
	    		String admin_id,
	    		String tecnico_id) {
	        this.usuario = usuario;
	        this.contraseña = contraseña;
	        this.setRol(rol);
	        this.setFechaCreacion(fechaCreacion);
	        this.setAdmin_id(admin_id);
	        this.setTecnico_id(tecnico_id);
	    }

        public static boolean crearCredenciales(Credenciales credenciales) {

            String sqlSentencia = "INSERT INTO CREDENCIALES" +
                    " (usuario, contraseña, fechaCreacion, rol, admin_id, tecnico_id)" +
                    " VALUES (?, ?, ?, ?, ?, ?);";

            try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sqlSentencia)) {

                ps.setString(1, credenciales.getUsuario());
                ps.setString(2, credenciales.getContrasena());
                ps.setTimestamp(3, java.sql.Timestamp.valueOf(credenciales.getFechaCreacion()));
                ps.setString(4, credenciales.getRol().toString());
                ps.setString(5, credenciales.getAdmin_id());
                ps.setString(6, credenciales.getTecnico_id());

                if(ps.executeUpdate() == 1) {
                    return true;
                }


            }catch (SQLException e){
                e.printStackTrace();
            }

            return false;
        }

	    public String getUsuario() {
	        return usuario;
	    }

	    public void setUsuario(String usuario) {
	        this.usuario = usuario;
	    }

	    public String getContrasena() {
	        return contraseña;
	    }

	    public void setContrasena(String contraseña) {
	        this.contraseña = contraseña;
	    }

		public RolCredencial getRol() {
			return rol;
		}

		public void setRol(RolCredencial rol) {
			this.rol = rol;
		}

		public LocalDateTime getFechaCreacion() {
			return fechaCreacion;
		}

		public void setFechaCreacion(LocalDateTime fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}


		public String getAdmin_id() {
			return admin_id;
		}

		public void setAdmin_id(String admin_id) {
			this.admin_id = admin_id;
		}

		public String getTecnico_id() {
			return tecnico_id;
		}

		public void setTecnico_id(String tecnico_id) {
			this.tecnico_id = tecnico_id;
		}
}
