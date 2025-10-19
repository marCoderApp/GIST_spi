package modelos.GestionRep;

public class RepuestoModelo {
	
	private String repuestoId;
	private String nombre;
	private int cantidad;
	private double precioUnitario;
	
	//Constructor
	public RepuestoModelo(String repuestoId, String nombre, int cantidad, double precioUnitario) {
		this.repuestoId = repuestoId;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}
	
	//Getters y Setters
	
	public String getRepuestoId() {
		return repuestoId;
	}
	
	public void setRepuestoId(String repuestoId) {
		this.repuestoId = repuestoId;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public double getPrecioUnitario() {
		return precioUnitario;
	}
	
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
}
