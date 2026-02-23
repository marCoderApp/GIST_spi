package modelos.GestionRep;

public class RepuestoModelo {
	
	private String repuestoId;
	private String nombre;
	private int cantidad;
	private double precioUnitario;
	private String destinatario;
	private Boolean recibido;
	
	//Constructor
	public RepuestoModelo(String repuestoId, String nombre, int cantidad, double precioUnitario) {
		this.repuestoId = repuestoId;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
	}

	public RepuestoModelo(String repuestoId,
						  String nombre,
						  int cantidad,
						  double precioUnitario,
						  String destinatario,
						  Boolean recibido) {
		this.repuestoId = repuestoId;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.destinatario = destinatario;
		this.recibido = recibido;
	}




	//Getters y Setters

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public Boolean getRecibido() {
		return recibido;
	}
	
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

	public void setRecibido(Boolean recibido) {
		this.recibido = recibido;
	}

}
