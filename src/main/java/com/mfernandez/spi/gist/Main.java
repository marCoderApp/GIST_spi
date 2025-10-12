package com.mfernandez.spi.gist;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
                    break;
                case 2:
                    System.out.println("=== Lista de Órdenes de trabajo ===");
                    // Lógica para la Opción 2
                    break;
                case 3:
                    System.out.println("=== Marcar Orden como Retirada ===");
                    System.out.print("Ingresa el código de orden: ");
                    // Lógica para la Opción 3
                    break;
                case 4:
                    System.out.println("=== Menú Gestionar Clientes ===");
                    // Lógica para la Opción 3
                    break;
                case 5:
                    System.out.println("===Menú Gestionar Técnicos ===");
                    // Lógica para la Opción 3
                    break;
                case 6:
                    System.out.println("Novedades");
                    salir = true;
                    break;
                case 7:
                    System.out.println("Presupuestos");
                    // Lógica para la Opción 3
                    break;
                case 8:
                    System.out.println("Pedidos");
                    // Lógica para la Opción 3
                    break;
                case 9:
                    System.out.println("Ordenes de trabajo");
                    // Lógica para la Opción 3
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


