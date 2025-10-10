package modelos.Personal;

public class AdminModelo {
	
	private String id;
	private String nombre;
	private String apellido;
	private int turno;
	
	public AdminModelo(String id, String nombre, String apellido, int turno) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.turno = turno;
	}
	
	public AdminModelo buscarAdmin(String id) {
		// Lógica para buscar un administrador en la base de datos
		// Retorna un objeto AdminModelo si se encuentra, de lo contrario retorna null
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
