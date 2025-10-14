package vistas.Personal;

import java.util.Scanner;

import controladores.PersonalControl;
import modelos.GestionRep.Credenciales;

public class LoginVista {
	Scanner scanner = new Scanner(System.in);

	
	
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
	
	
	public boolean ingresarCredenciales(Credenciales credenciales) {
		PersonalControl personalControl = new PersonalControl(null, false, false, null);
		
		System.out.println("Por favor, ingresa tus credenciales para iniciar sesión.");
		System.out.print("Usuario: ");
		String usuario = scanner.nextLine();
		System.out.print("Contraseña: ");
		String contraseña = scanner.nextLine();
		
		
		//VALIDACION DE CREDENCIALES
		credenciales.setUsuario(usuario);
		credenciales.setContrasena(contraseña);
		
		boolean acceso = personalControl.validarCredenciales(credenciales);
	
		if (acceso) {
			System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + usuario + "!");
		} else {
			System.out.println("Credenciales inválidas. Por favor, inténtalo de nuevo.");
		}
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
