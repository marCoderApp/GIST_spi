package modelos.GestionRep;

import java.time.LocalDateTime;

public class Credenciales {

	   private String usuario;
	    private String contraseña;
	    private RolCredencial rol;
	    private LocalDateTime fechaCreacion; 
	    private String usuarioId;
	    

	    public Credenciales(String usuario, String contraseña, RolCredencial rol, LocalDateTime fechaCreacion, String usuarioId) {
	        this.usuario = usuario;
	        this.contraseña = contraseña;
	        this.setRol(rol);
	        this.setFechaCreacion(fechaCreacion);
	        this.setUsuarioId(usuarioId);
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

		public String getUsuarioId() {
			return usuarioId;
		}

		public void setUsuarioId(String usuarioId) {
			this.usuarioId = usuarioId;
		}
}
