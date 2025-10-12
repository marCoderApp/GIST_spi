package vistas.Personal;

import modelos.GestionRep.Credenciales;

public class LoginVista {
	
	private String usuario;
	private String contrasena;
	private String rol;
	private String error;
	private int intentosFallidos;
	private boolean recordarUsuario;
	
	public LoginVista(String usuario, String contrasena, String rol, String error, int intentosFallidos, boolean recordarUsuario) {
		this.usuario = "";
		this.contrasena = "";
		this.rol = "";
		this.error = "";
		this.intentosFallidos = 0;
		this.recordarUsuario = false;
	}
	
	public Credenciales ingresarCredenciales(Credenciales credenciales) {
		return credenciales;
	}
	
	public boolean confirmarAcceso(boolean acceso) {
		return acceso;
	}
	
	//Getters and Setters
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public String getRol() {
		return rol;
	}
	
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public int getIntentosFallidos() {
		return intentosFallidos;
	}
	
	public void setIntentosFallidos(int intentosFallidos) {
		this.intentosFallidos = intentosFallidos;
	}
	
	public boolean isRecordarUsuario() {
		return recordarUsuario;
	}
	
	public void setRecordarUsuario(boolean recordarUsuario) {
		this.recordarUsuario = recordarUsuario;
	}
	
	
	
}
