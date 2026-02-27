package conexion;

import javafx.scene.control.Alert;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConexionDB {

	private static final String URL = "jdbc:mysql://localhost:3306/bd_gist";
	private static final String USER = "root";
	private static final String PASSWORD = "pass123";
	
	public static Connection conectar() {
		Connection conexion = null;
		try {
			Properties props = new Properties();
			InputStream input = ConexionDB.class
					.getClassLoader()
					.getResourceAsStream("db.properties");

			props.load(input);

			conexion = DriverManager.getConnection(
					props.getProperty("db.url"),
					props.getProperty("db.user"),
					props.getProperty("db.password")
			);

			System.out.println("Conexión exitosa");
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Error de conexión");
			alert.setContentText("Verifique que MySQL esté instalado y la base creada.");
			alert.showAndWait();
		}

		return conexion;
	}
	
}
