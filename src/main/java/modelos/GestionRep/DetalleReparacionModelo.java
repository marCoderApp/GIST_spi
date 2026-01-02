package modelos.GestionRep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import controladores.GestionRepControl;

public class DetalleReparacionModelo {
	
	private String detalleRepId;
	private String descripcion;
	private String repuestos; 
	private String tecnicoId;
	private LocalDateTime fecha;
	private String ordenId;
	private boolean ordenAsociada;
    private GestionRepControl gestionRepControl = new GestionRepControl();
	private int nivelService = 0;

	public DetalleReparacionModelo(String detalleRepId, String descripcion,
			String repuestos, String tecnicoId,
			LocalDateTime fecha,
			String ordenId,
            int nivelService) {
		this.detalleRepId = generarDetalleRepId();
		this.descripcion = descripcion;
		this.repuestos = repuestos;
		this.tecnicoId = tecnicoId;
		this.fecha = fecha;
		this.ordenId = ordenId;
        this.nivelService = nivelService;
	}
	
	public String generarDetalleRepId() {
        String sql = "SELECT id FROM DETALLEREPARACION ORDER BY id DESC LIMIT 1";
        String ultimoId = null;
        try (PreparedStatement ps = gestionRepControl.conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                ultimoId = rs.getString("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int siguienteNumero = 1;

        if (ultimoId != null && ultimoId.startsWith("DTR")) {
            try {
                int numeroActual = Integer.parseInt(ultimoId.substring(5));
                siguienteNumero = numeroActual + 1;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return String.format("DTR%05d", siguienteNumero);
	}
	
	public static Boolean guardarDetalleBD(DetalleReparacionModelo nuevoDetalleRep, String maquinaId) {

        String sql = "INSERT INTO DETALLEREPARACION VALUES (?,?,?,?,?,?)";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
                ps.setString(1,nuevoDetalleRep.getDetalleRepId());
                ps.setString(2, nuevoDetalleRep.getDescripcion());
                ps.setObject(3, nuevoDetalleRep.getFecha());
                ps.setString(4, nuevoDetalleRep.getTecnicoId());
                ps.setInt(5, nuevoDetalleRep.getNivelService());
                ps.setString(6, maquinaId);

               int filas = ps.executeUpdate();

                if(filas > 0){
                    return true;
                }


        }catch(SQLException e){
            e.printStackTrace();
        }

		return false;
	}
	
	public void eliminarDetalle(String detalleRepId) {}
	
	public void modificarDetalle(String detalleRepId) {}
	

	// Getters
	public String getDetalleRepId() {
	    return detalleRepId;
	}

	public String getRepuestos() {
	    return repuestos;
	}

	public String getTecnicoId() {
	    return tecnicoId;
	}

	public LocalDateTime getFecha() {
	    return fecha;
	}

	public String getOrdenId() {
	    return ordenId;
	}

	public boolean isOrdenAsociada() {
	    return ordenAsociada;
	}

	// Setters
	public void setDetalleRepId(String detalleRepId) {
	    this.detalleRepId = detalleRepId;
	}

	public void setRepuestos(String repuestos) {
	    this.repuestos = repuestos;
	}

	public void setTecnicoId(String tecnicoId) {
	    this.tecnicoId = tecnicoId;
	}

	public void setFecha(LocalDateTime fecha) {
	    this.fecha = fecha;
	}

	public void setOrdenId(String ordenId) {
	    this.ordenId = ordenId;
	}

	public void setOrdenAsociada(boolean ordenAsociada) {
	    this.ordenAsociada = ordenAsociada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

    public void setNivelService(int nivelService){
        this.nivelService = nivelService;
    }

    public int getNivelService(){
        return this.nivelService;
    }

	
	
	

}
