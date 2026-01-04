package modelos.GestionRep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Enums.MaquinaEstado;
import conexion.ConexionDB;

public class MaquinaModelo {

	private String maquinaId;
	private String tipo;
	private String marca;
	private String modelo;
	private String color;
	private String estado;
	private boolean esReingreso;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private String descripcion_falla;
	private String observaciones;
	private Boolean activo = true;
	private static Connection conexion = ConexionDB.conectar();
	// Constructors
	
	public MaquinaModelo(String tipo, String marca,
						 String modelo, String color,
						 String estado, boolean esReingreso,
						 LocalDateTime created_at,
						 LocalDateTime updated_at,
						 String descripcion_falla,
						 String observaciones,
						 Boolean activo) {
		this.maquinaId = generarMaquinaId();
		this.tipo = tipo;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
		this.estado = (estado != null) ? estado : MaquinaEstado.EN_LISTA.getStatus();
		this.esReingreso = esReingreso;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.descripcion_falla = descripcion_falla;
		this.observaciones = observaciones;
		this.activo = activo;
	}

	//GENERAR ID DE MAQUINA USANDO UUID
	public String generarMaquinaId() {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "MAQ-" + uuid;

    }

    //LISTAR MAQUINAS DESDE LA BASE DE DATOS
	public static List<MaquinaModelo> listarMaquinas(){
		List<MaquinaModelo> listaMaquinas = new ArrayList<>();
		
		String sqlConsulta = "SELECT * FROM MAQUINAS";
		
		try (PreparedStatement ps = conexion.prepareStatement(sqlConsulta); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				// Mapeo correcto de DATETIME a LocalDateTime
				LocalDateTime createdAt = null;
				if (rs.getTimestamp("CREATED_AT") != null) {
					createdAt = rs.getTimestamp("CREATED_AT").toLocalDateTime();
				}

				LocalDateTime updatedAt = null;
				if (rs.getTimestamp("UPDATED_AT") != null) {
					updatedAt = rs.getTimestamp("UPDATED_AT").toLocalDateTime();
				}


				MaquinaModelo maquina = new MaquinaModelo(rs.getString("tipo"),
						rs.getString("marca"),
						rs.getString("modelo"),
						rs.getString("color"),
						rs.getString("estado"),
						rs.getBoolean("REINGRESO"),
						createdAt,
					    updatedAt,
						rs.getString("DESCRIPCION_FALLA"),
						rs.getString("OBSERVACIONES"),
						rs.getBoolean("ACTIVO"));
				maquina.setMaquinaId(rs.getString("id"));
				listaMaquinas.add(maquina);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaMaquinas;
	}

    //MOSTRAR LISTA DE MAQUINAS
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

    //GUARDAR NUEVA MAQUINA EN LA BASE DE DATOS
	public static boolean guardarNuevaMaquina(List<MaquinaModelo> maquinas, String ordenId) {
		String sqlInsertarMaquina = "INSERT INTO MAQUINAS (id," +
				" tipo," +
				" marca," +
				" modelo," +
				" color," +
				" estado_maquina," +
				" reingreso," +
				"created_at," +
				"updated_at," +
				"descripcion_falla," +
				"observaciones," +
				"activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
				ps.setString(6, m.getEstado());
				ps.setBoolean(7, m.esReingreso);
				ps.setTimestamp(8, java.sql.Timestamp.valueOf(m.created_at));
				ps.setTimestamp(9, java.sql.Timestamp.valueOf(m.updated_at));
				ps.setString(10, m.descripcion_falla);
				ps.setString(11,m.observaciones );
				ps.setBoolean(12, m.activo);
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

	public String getEstado() {
		return estado;
	}

	public boolean isEsReingreso() {return esReingreso;}

	public boolean isActivo() {return activo;}

	public String getDescripcionFalla() {return descripcion_falla;}

	public String getObservaciones() {return observaciones;}


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

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setEsReingreso(boolean esReingreso) {this.esReingreso = esReingreso;}

	public void setActivo(boolean activo) {this.activo = activo;}

	public void setDescripcionFalla(String descripcion_falla) {this.descripcion_falla = descripcion_falla;}

	public void setObservaciones(String observaciones) {this.observaciones = observaciones;}

}
