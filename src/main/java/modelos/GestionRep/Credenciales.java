package modelos.GestionRep;

import java.time.LocalDateTime;

public class Credenciales {

	   private String usuario;
	    private String contraseña;
	    private RolCredencial rol;
	    private LocalDateTime fechaCreacion; 
	    private String admin_id;
	    private String tecnico_id;
	    

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
