package modelos.GestionRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionDB;

public class MaquinaModelo {

	private String maquinaId;
	private String tipo;
	private String marca;
	private String modelo;
	private String color;
	private String ordenId;
	private static Connection conexion = ConexionDB.conectar();
	// Constructors
	
	public MaquinaModelo(String tipo, String marca, String modelo, String color, String ordenId) {
		this.maquinaId = generarMaquinaId();
		this.tipo = tipo;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
		this.ordenId = ordenId;
	}
	
	public String generarMaquinaId() {
		String sql = "SELECT id FROM MAQUINAS ORDER BY id DESC LIMIT 1";
	    String ultimoId = null;
	    try (PreparedStatement ps = conexion.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            ultimoId = rs.getString("id");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    int siguienteNumero = 1;

	    if (ultimoId != null && ultimoId.startsWith("MAQ")) {
	        try {
	            int numeroActual = Integer.parseInt(ultimoId.substring(3));
	            siguienteNumero = numeroActual + 1;
	        } catch (NumberFormatException e) {
	            e.printStackTrace();
	        }
	    }

	    return String.format("MAQ%04d", siguienteNumero);
	}
	
	public static List<MaquinaModelo> listarMaquinas(){
		List<MaquinaModelo> listaMaquinas = new ArrayList<>();
		
		String sqlConsulta = "SELECT * FROM MAQUINAS";
		
		try (PreparedStatement ps = conexion.prepareStatement(sqlConsulta); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				MaquinaModelo maquina = new MaquinaModelo(rs.getString("tipo"), 
						rs.getString("marca"),
						rs.getString("modelo"),
						rs.getString("color"),
						rs.getString("orden_id"));
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
					+ ", Orden ID: " 
					+ maquina.getOrdenId());
		}
	}
	
	private void crearNuevaMaquina(List<MaquinaModelo> maquinas) {
		// Lógica para crear una nueva máquina
		String sql = "INSERT INTO MAQUINAS (id, tipo, marca, modelo, color) VALUES (?, ?, ?, ?, ?)";
	    
	    try (Connection conexion = ConexionDB.conectar();
	         PreparedStatement ps = conexion.prepareStatement(sql)) {

	        for (MaquinaModelo m : maquinas) {
	            ps.setString(1, m.getMaquinaId());
	            ps.setString(2, m.getTipo());
	            ps.setString(3, m.getMarca());
	            ps.setString(4, m.getModelo());
	            ps.setString(5, m.getColor());
	            ps.executeUpdate();
	        }

	        System.out.println("Máquinas registradas correctamente ✅");

	    } catch (SQLException e) {
	        System.err.println("Error al registrar máquinas: " + e.getMessage());
	    }
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

	public String getOrdenId() {
		return ordenId;
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
	
	public void setOrdenId(String ordenId) {
		this.ordenId = ordenId;
	}
	
	// Methods
	
	public Boolean crearMaquina(MaquinaModelo datos) {
		return true;
	}
	
	
	
}
