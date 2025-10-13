package vistas.Personal;

import java.util.ArrayList;
import java.util.List;
import modelos.Personal.TecnicoModelo;


public class TecnicoVista {

	private List<TecnicoModelo> listaTecnicos = new ArrayList<>();
	
	//Constructor
	public TecnicoVista(List<TecnicoModelo> listaTecnicos) {
		this.listaTecnicos = new ArrayList<>();
	}
	
	public void opcionCrearTecnico() {
		
	}
	
	public void navegar() {
		
	}
	
	public void mostrarLista(List<TecnicoModelo> tecnicos) {

	}
	
	//Getters and Setters
	public List<TecnicoModelo> getListaTecnicos() {
		return listaTecnicos;
	}
	
	public void setListaTecnicos(List<TecnicoModelo> listaTecnicos) {
		this.listaTecnicos = listaTecnicos;
	}

	public void mostrarMenuTecnicos() {
		
		System.out.println("=== Gestión de Técnicos ===");
		System.out.println("1. Listar Técnicos");
		System.out.println("2. Crear Técnico");
		System.out.println("3. Salir");
		
	}
	
	
	
}
