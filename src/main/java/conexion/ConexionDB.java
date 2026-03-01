package conexion;

import javafx.scene.control.Alert;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConexionDB {

	public static Connection conectar() {

		try (InputStream input = ConexionDB.class
				.getClassLoader()
				.getResourceAsStream("db.properties")) {

			if (input == null) {
				throw new RuntimeException("No se encontró db.properties");
			}

			Properties props = new Properties();
			props.load(input);

			Connection conexion = DriverManager.getConnection(
					props.getProperty("db.url"),
					props.getProperty("db.user"),
					props.getProperty("db.password")
			);

			System.out.println("Conexión exitosa");
			return conexion;

		} catch (Exception e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText("Error de conexión");
			alert.setContentText("Verifique que MySQL esté instalado y la base creada.");
			alert.showAndWait();

			throw new RuntimeException("Error al conectar a la base de datos", e);
		}
	}
}