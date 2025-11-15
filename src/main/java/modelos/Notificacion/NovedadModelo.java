package modelos.Notificacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import controladores.GestionRepControl;
import controladores.PersonalControl;

import static controladores.PersonalControl.adminIdPersonalControl;
import static controladores.PersonalControl.tecnicoIdPersonalControl;

public class NovedadModelo {
	
	private String novedadId;
	private LocalDateTime fechaCreacion;
	private String ordenId;
    private List<NovedadItem> items;
    private GestionRepControl gestionRepControl = new GestionRepControl();
	
	public NovedadModelo(LocalDateTime fechaCreacion, List<NovedadItem> items) {
		this.novedadId = generarNovedadId();
		this.fechaCreacion = fechaCreacion;
        this.items = items;
	}
	
	public String generarNovedadId() {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String idNovedad = "NVD-" + uuid; // Ej: MAQ-3F9A1B2C
        return idNovedad;
    }

    //GUARDAR NOVEDAD EN LA BD
    public boolean guardarNovedadBD(NovedadModelo nuevaNovedad){
        String sqlConsulta = "INSERT INTO NOVEDADES (id, fecha, admin_id)" +
                " VALUES (?, ?, ?)";

        try(PreparedStatement ps = gestionRepControl.conexion.prepareStatement(sqlConsulta)){
            gestionRepControl.conexion.setAutoCommit(false);
            ps.setString(1, nuevaNovedad.getNovedadId());
            ps.setObject(2, nuevaNovedad.getFechaCreacion());
            ps.setString(3, filtrarPorRol());
            ps.executeUpdate();

            String sql= "INSERT INTO NOVEDAD_ITEM (comentarioItem, itemId, novedadId, ordenId)" +
                    " VALUES (?, ?, ?, ?)";


            try(PreparedStatement ps2 = gestionRepControl.conexion.prepareStatement(sql)) {
                for (NovedadItem item : nuevaNovedad.getItems()) {
                    ps2.setString(1, item.getComentario());
                    ps2.setString(2, item.getId());
                    ps2.setString(3, nuevaNovedad.getNovedadId());
                    ps2.setString(4, item.getOrdenId());
                    ps2.addBatch();
                }

                ps2.executeBatch();
                gestionRepControl.conexion.commit();
                return true;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public String filtrarPorRol(){
        if(adminIdPersonalControl != null){
            return adminIdPersonalControl;
        }else{
            return tecnicoIdPersonalControl;
        }
    }
	
	public void crearTareaAPartirDeNovedad() {
		// Lógica para crear una tarea a partir de la novedad
	}
	
	//Getters y Setters
	
	public String getNovedadId() {
		return novedadId;
	}
	
	public void setNovedadId(String novedadId) {
		this.novedadId = novedadId;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	
	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getOrdenId() {
		return ordenId;
	}
	
	public void setOrdenId(String ordenId) {
		this.ordenId = ordenId;
	}

    public void setItems(List<NovedadItem> items) {
        this.items = items;
    }

    public List<NovedadItem> getItems() {
        return items;
    }
	
}
