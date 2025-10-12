package vistas.Personal;

import modelos.Personal.TecnicoModelo;
import modelos.Notificacion.TareasModelo;
import java.util.List;

public class FichaTecnicoVista {

	
	private String tecnicoId;
	private String nombre;
	private String apellido;
	private String especialidad;
	private int cantidadTareas;
	
	public FichaTecnicoVista(String tecnicoId, String nombre, String apellido, String especialidad,
			int cantidadTareas) {
		super();
		this.tecnicoId = tecnicoId;
		this.nombre = nombre;
		this.apellido = apellido;
		this.especialidad = especialidad;
		this.cantidadTareas = cantidadTareas;
	}
	
	public void abrirFicha() {
                // Lógica para abrir la ficha del técnico
    }
	
	public void mostrarFichaDatos(TecnicoModelo tecnico) {
		// Lógica para mostrar los datos del técnico
	}
	
	public void mostrarLista(List<TareasModelo> listaTareasOrdenada) {
		// Lógica para mostrar la lista de técnicos
	}
	
	public void actualizarCantidadTareas(int nuevaCantidad) {
		this.cantidadTareas = nuevaCantidad;
	}
	
	//Getters y Setters
	
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
	
	public int getCantidadTareas() {
		return cantidadTareas;
	}
	
	public void setCantidadTareas(int cantidadTareas) {
		this.cantidadTareas = cantidadTareas;
	}
}
