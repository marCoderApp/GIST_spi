package modelos.GestionRep;

public class MaquinaModelo {

	private String maquinaId;
	private String tipo;
	private String marca;
	private String modelo;
	private String color;
	private String ordenId;
	
	// Constructors
	
	public MaquinaModelo(String tipo, String marca, String modelo, String color, String ordenId) {
		this.maquinaId = generarMaquinaId();
		this.tipo = tipo;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
		this.ordenId = ordenId;
	}
	
	public String generarMaquinaId() {
		return "MAQ" + System.currentTimeMillis();
	}
	
	// Getters
	public String getMaquinaId() {
		return maquinaId;
	}

	public String getTipo() {
		return tipo;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public String getColor() {
		return color;
	}

	public String getOrdenId() {
		return ordenId;
	}
	
	// Setters
	
	public void setMaquinaId(String maquinaId) {
		this.maquinaId = maquinaId;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public void setOrdenId(String ordenId) {
		this.ordenId = ordenId;
	}
	
	// Methods
	
	public Boolean crearMaquina(MaquinaModelo datos) {
		return true;
	}
	
	
	
}
