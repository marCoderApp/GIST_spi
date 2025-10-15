package com.mfernandez.spi.gist;

import java.util.Scanner;
import vistas.GestionRep.*;
import vistas.Personal.*;
import vistas.Notificacion.*;
import modelos.GestionRep.Credenciales;
import conexion.ConexionDB;
import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		
		Credenciales credenciales = new Credenciales(null, null, null);
		LoginVista loginVista = new LoginVista(null, null, null, null, 0, false);
		Connection conn = ConexionDB.conectar();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("╔═══════════════════════════════════════╗");
		System.out.println("║           BIENVENIDO A GIST           ║");
		System.out.println("╚═══════════════════════════════════════╝");
		
	      if (conn != null) {
	            System.out.println("La conexión está funcionando correctamente ✅");
	        } else {
	            System.out.println("La conexión falló ❌");
	        }

		// INICIO DE SESIÓN
	
		if (loginVista.ingresarCredenciales(credenciales)) {
			mostrarMenu();
		} else {
			System.out.println("Credenciales inválidas. Por favor, inténtalo de nuevo.");
		}
		
		scanner.close();
      
    }
	
	private static void mostrarMenu() {
		
        OrdenTrabajoVista ordenVista = new OrdenTrabajoVista(null, null, null, null, null, null, null, null, null);

		
Scanner scanner = new Scanner(System.in);
		
        boolean salir = false;

        while (!salir) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║     🛠️ MENÚ PRINCIPAL - GIST            ║");
            System.out.println("╠════════════════════════════════════════=╣");
            System.out.println("║ 1. Registrar nueva orden de trabajo     ║");
            System.out.println("║ 2. Listar órdenes de trabajo            ║");
            System.out.println("║ 3. Marcar orden como retirada           ║");
            System.out.println("║ 4. Gestionar clientes                   ║");
            System.out.println("║ 5. Gestionar técnicos                   ║");
            System.out.println("║ 6. Gestionar Novedades                  ║");
            System.out.println("║ 7. Gestionar Presupuestos               ║");
            System.out.println("║ 8. Gestionar Pedidos                    ║");
            System.out.println("║ 9. Gestionar Órdenes de Trabajo         ║");
            System.out.println("║ 10. Salir                               ║");
            System.out.println("╚═════════════════════════════════════════╝");
            System.out.print("Seleccioná una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("=== Nueva Orden de trabajo ===");
                       // Lógica para la Opción 1
                    ordenVista.mostrarFormularioRegistro();
                    break;
                case 2:
                    System.out.println("=== Lista de Órdenes de trabajo ===");
                    // Lógica para la Opción 2
					ordenVista.mostrarLista(null);
                    break;
                case 3:
                    System.out.println("=== Marcar Orden como Retirada ===");
                    System.out.print("Ingresa el código de orden: ");
                    // Lógica para la Opción 3
                    ordenVista.navegar();
                    break;
                case 4:
                    System.out.println("=== Menú Gestionar Clientes ===");
                    // Lógica para la Opción 3
                    ClientesVista clientesVista = new ClientesVista();
                    clientesVista.mostrarMenuClientes();
                    break;
                case 5:
                    System.out.println("===Menú Gestionar Técnicos ===");
                    // Lógica para la Opción 3
                    TecnicoVista tecnicoVista = new TecnicoVista(null);
                    tecnicoVista.mostrarMenuTecnicos();
                    break;
                case 6:
                    System.out.println("Novedades");
                    // Lógica para la Opción 3
                    NovedadesVista novedadesVista = new NovedadesVista(null, null, null, null, null);
                    novedadesVista.mostrarMenuNovedades();
                    
                    break;
                case 7:
                    System.out.println("Presupuestos");
                    // Lógica para la Opción 3
                    PresupuestosVista presupuestoVista = new PresupuestosVista(null, null, null);
                    presupuestoVista.mostrarMenuPresupuestos();
                    break;
                case 8:
                    System.out.println("Pedidos");
                    // Lógica para la Opción 3
                    PedidosVista pedidosVista = new PedidosVista(null, null, null, null, opcion);
                    pedidosVista.mostrarMenuPedidos();
                    break;
                case 9:
                    System.out.println("Ordenes de trabajo");
                    // Lógica para la Opción 3
                    ordenVista.mostrarMenuOrdenesTrabajo();
                    break;
                case 10:
                    System.out.println("SALIR");
                    salir = true;
                    // Lógica para la Opción 3
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        scanner.close();
	}
}


