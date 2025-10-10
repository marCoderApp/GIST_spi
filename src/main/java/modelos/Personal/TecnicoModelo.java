package modelos.Personal;

import java.util.List;

import modelos.Notificacion.TareasModelo;

public class TecnicoModelo {

	private String tecnicoId;
	private String nombre;
	private String apellido;
	private String especialidad;
	private List<TareasModelo> tareasAsignadas;
	private int tareasCompletadas;
	private int tareasPendientes;
	
	public TecnicoModelo(String nombre, String apellido, String especialidad,
			List<TareasModelo> tareasAsignadas, int tareasCompletadas, int tareasPendientes) {
		this.tecnicoId = generarTecnicoId();
		this.nombre = nombre;
		this.apellido = apellido;
		this.especialidad = especialidad;
		this.tareasAsignadas = tareasAsignadas;
		this.tareasCompletadas = tareasCompletadas;
		this.tareasPendientes = tareasPendientes;
	}
	
	public String generarTecnicoId() {
		return "TEC" + System.currentTimeMillis();
	}
	
     //Getters and Setters
	
	public String getTecnicoId() {
		        return tecnicoId;
	}
	
	public void setTecnicoId(String tecnicoId) {
		this.tecnicoId = tecnicoId;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getEspecialidad() {
		return especialidad;
	}
	
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	public List<TareasModelo> getTareasAsignadas() {
		return tareasAsignadas;
	}
	
	public void setTareasAsignadas(List<TareasModelo> tareasAsignadas) {
		this.tareasAsignadas = tareasAsignadas;
	}
	
	public int getTareasCompletadas() {
		return tareasCompletadas;
	}
	
	public void setTareasCompletadas(int tareasCompletadas) {
		this.tareasCompletadas = tareasCompletadas;
	}
	
	public int getTareasPendientes() {
		return tareasPendientes;
	}
	
	public void setTareasPendientes(int tareasPendientes) {
		this.tareasPendientes = tareasPendientes;
	}
	
	
}
