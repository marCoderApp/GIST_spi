package modelos.Personal;

import java.util.List;
import modelos.Personal.PersonalBase;
import modelos.Notificacion.TareasModelo;

public class TecnicoModelo extends PersonalBase {

	private String especialidad;
	private List<TareasModelo> tareasAsignadas;
	private int tareasCompletadas;
	private int tareasPendientes;
	
	public TecnicoModelo(String nombre, String apellido, String especialidad,
			List<TareasModelo> tareasAsignadas, int tareasCompletadas, int tareasPendientes) {
		super(nombre, apellido);
        this.id = generarId();
		this.especialidad = especialidad;
		this.tareasAsignadas = tareasAsignadas;
		this.tareasCompletadas = tareasCompletadas;
		this.tareasPendientes = tareasPendientes;
	}
	
	public String generarId() {
		return "TEC" + System.currentTimeMillis();
	}
	
     //Getters and Setters
	
	public String getId() {
		        return id;
	}

    @Override
    public String getRol(){
        return "TECNICO";
    }
	
	public void setTecnicoId(String tecnicoId) {
		this.id = id;
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
