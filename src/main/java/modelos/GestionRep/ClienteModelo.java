package modelos.GestionRep;

import java.util.ArrayList;
import java.util.List;

public class ClienteModelo {
	
	private String clienteId;
	private String nombre;
	private String empresa;
	private String telefono;
	private String dni;
	private String numeroTel;

	public ClienteModelo(String nombre, String empresa, String telefono, String dni, String numeroTel) {
		this.clienteId = generarClienteId();
		this.nombre = nombre;
		this.empresa = empresa;
		this.telefono = telefono;
		this.dni = dni;
		this.numeroTel = numeroTel;
	}
	
	public String generarClienteId() {
		return "CL" + System.currentTimeMillis();
	}
	
	public Boolean guardarCliente(ClienteModelo datos) {
		return true;
	}
	
	public void eliminarCliente(String clienteId) {

	}
	
	public void registrarCliente(String clienteId) {

	}
	
	public void actualizarDatos(ClienteModelo datos) {
		
	}
	
	public ClienteModelo obtenerCliente(String clienteId) {
		return new ClienteModelo(clienteId, clienteId, clienteId, clienteId, clienteId);
	}
	
	public List<ClienteModelo> listarClientes() {
		return new ArrayList<>();
	}
	
	
	// Getters
	public String getClienteId() {
		return clienteId;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getEmpresa() {
		return empresa;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public String getDni() {
		return dni;
	}
	
	public String getNumeroTel() {
		return numeroTel;
	}
	
	// Setters
	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public void setNumeroTel(String numeroTel) {
		this.numeroTel = numeroTel;
	}
	

}
