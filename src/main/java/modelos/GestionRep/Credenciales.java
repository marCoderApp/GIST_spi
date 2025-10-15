package modelos.GestionRep;

public class Credenciales {

	   private String usuario;
	    private String contraseña;
	    private RolCredencial rol;

	    public Credenciales(String usuario, String contraseña, RolCredencial rol) {
	        this.usuario = usuario;
	        this.contraseña = contraseña;
	        this.setRol(rol);
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
}
