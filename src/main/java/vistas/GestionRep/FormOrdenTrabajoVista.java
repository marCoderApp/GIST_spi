package vistas.GestionRep;

import java.util.List;
import java.util.Scanner;

import controladores.GestionRepControl;
import modelos.GestionRep.ClienteModelo;

public class FormOrdenTrabajoVista {
	
	private GestionRepControl gestionRepControl = new GestionRepControl();
	private String ordenId;
	
	public FormOrdenTrabajoVista(String ordenId) {
		this.setOrdenId(ordenId);
	}
	
	public void abrirForm(String ordenIdParam, String adminId) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Confirmar retiro de la Orden ID = " + ordenIdParam);
		System.out.println("1. Si");
		System.out.println("2. No");
		
		int opcion = scanner.nextInt();
	    scanner.nextLine();
		
		if (opcion == 1) {
			
			gestionRepControl.registrarEntrega(ordenIdParam, adminId);
			
		} else if (opcion == 2) {
			
			
			
		} else {
			System.out.println("Opción inválida. Por favor, intente de nuevo.");
		}
	
		
	}
	
	public void ingresarEntrega() {
		
	}
	
	public String getOrdenId() {
		return ordenId;
	}

	public void setOrdenId(String ordenId) {
		this.ordenId = ordenId;
	}

}
