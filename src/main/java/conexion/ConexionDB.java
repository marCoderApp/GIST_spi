package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionDB {

	private static final String URL = "jdbc:mysql://localhost:3306/bd_gist";
	private static final String USER = "root";
	private static final String PASSWORD = "pass123";
	
	public static Connection conectar() {
		Connection conexion = null;
		
		try {
			conexion = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Conexi��n exitosa a la base de datos");
		} catch (SQLException e) {
			System.out.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		return conexion;
	}
	
}
