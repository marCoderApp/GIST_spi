package modelos.GestionRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import conexion.ConexionDB;

public class MaquinaModelo {

	private String maquinaId;
	private String tipo;
	private String marca;
	private String modelo;
	private String color;
	private static Connection conexion = ConexionDB.conectar();
	// Constructors
	
	public MaquinaModelo(String tipo, String marca, String modelo, String color) {
		this.maquinaId = generarMaquinaId();
		this.tipo = tipo;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
	}
	
	public String generarMaquinaId() {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "MAQ-" + uuid;

    }
	
	public static List<MaquinaModelo> listarMaquinas(){
		List<MaquinaModelo> listaMaquinas = new ArrayList<>();
		
		String sqlConsulta = "SELECT * FROM MAQUINAS";
		
		try (PreparedStatement ps = conexion.prepareStatement(sqlConsulta); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				MaquinaModelo maquina = new MaquinaModelo(rs.getString("tipo"), 
						rs.getString("marca"),
						rs.getString("modelo"),
						rs.getString("color"));
				maquina.setMaquinaId(rs.getString("id"));
				listaMaquinas.add(maquina);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaMaquinas;
	}
	
	public static void mostrarListaMaquinas(List<MaquinaModelo> maquinas) {
		for (MaquinaModelo maquina : maquinas) {
			System.out.println("ID: " + maquina.getMaquinaId() +
					", Tipo: " + maquina.getTipo() +
					", Marca: "
					+ maquina.getMarca() +
					", Modelo: " + maquina.getModelo() +
					", Color: " + maquina.getColor()
					+ ", Orden ID: ");
		}
	}
	
	public static boolean guardarNuevaMaquina(List<MaquinaModelo> maquinas, String ordenId) {
		// Lógica para crear una nueva máquina
		String sqlInsertarMaquina = "INSERT INTO MAQUINAS (id, tipo, marca, modelo, color) VALUES (?, ?, ?, ?, ?)";
		String sqlOrdenMaquina = "INSERT INTO ORDEN_MAQUINAS (orden_id, maquina_id) VALUES (?, ?)";
	    
	    try (Connection conexion = ConexionDB.conectar();
	         PreparedStatement ps = conexion.prepareStatement(sqlInsertarMaquina);
	    	PreparedStatement psOrdenMaquina = conexion.prepareStatement(sqlOrdenMaquina)) {

	        for (MaquinaModelo m : maquinas) {
	            ps.setString(1, m.getMaquinaId());
	            ps.setString(2, m.getTipo());
	            ps.setString(3, m.getMarca());
	            ps.setString(4, m.getModelo());
	            ps.setString(5, m.getColor());
	            ps.addBatch();
	            
	            psOrdenMaquina.setString(1, ordenId);
	            psOrdenMaquina.setString(2, m.getMaquinaId());
	            psOrdenMaquina.addBatch();
	        }
	        
	        ps.executeBatch();
	        psOrdenMaquina.executeBatch();
	        return true;

	    } catch (SQLException e) {
	        System.err.println("Error al registrar máquinas: " + e.getMessage());
	    }
	    return false;
	}
	
	// Getters
	public String getMaquinaId() {
		return maquinaId;
	}

	public String getTipo() {
		return tipo;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public String getColor() {
		return color;
	}

	
	// Setters
	
	public void setMaquinaId(String maquinaId) {
		this.maquinaId = maquinaId;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	
	// Methods
	
	public Boolean crearMaquina(MaquinaModelo datos) {
		return true;
	}
	
	
	
}
