package modelos.GestionRep;

import controladores.GestionRepControl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PresupuestoModelo {

	private String presupuestoId;
	private LocalDateTime fechaCreacion;
	private String adminId;
	private float total;
	private String estado;
    private String maquinaId;
    private boolean conFactura;

	public PresupuestoModelo(
			String adminId,
            String maquinaId,
			float total,
            boolean conFactura
			) {
		
		this.presupuestoId = generarPresupuestoId();
		this.adminId = adminId;
		this.fechaCreacion = LocalDateTime.now();
		this.total = total;
        this.maquinaId = maquinaId;
        this.conFactura = conFactura;
	}

    //INSERTA NUEVO PRESUPUESTO EN LA BASE DE DATOS. UTILIZA LOS DATOS CORRESPONDIENTES
    public static boolean ingresarPresupuestoBD(PresupuestoModelo presupuesto)
    {

        String sqlDelete = "DELETE FROM PRESUPUESTO WHERE MAQUINA_ID = ?";
        String sql = "INSERT INTO presupuesto " +
                "(presupuesto_id, maquina_id, total, con_factura, fecha_creacion, admin_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql);
            PreparedStatement psDelete = GestionRepControl.conexion.prepareStatement(sqlDelete);) {

            //ELIMINAR PRESUPUESTO ANTERIOR
            psDelete.setString(1, presupuesto.getMaquinaId());
            psDelete.executeUpdate();
            //AGREGAR UN PRESUPUESTO
            ps.setString(1, presupuesto.getPresupuestoId());
            ps.setString(2, presupuesto.getMaquinaId());
            ps.setDouble(3, presupuesto.getTotal());
            ps.setBoolean(4, presupuesto.conFactura());
            ps.setTimestamp(5, Timestamp.valueOf(presupuesto.getFechaCreacion())); // LocalDateTime → Timestamp
            ps.setString(6, presupuesto.getAdminId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    //GENERAR ID DE PRESUPUESTO
    public String generarPresupuestoId() {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String idPresupuesto = "PRE-" + uuid;
        return idPresupuesto;
	}

    //SETTERS Y GETTERS
	public String getPresupuestoId() {
        return presupuestoId;
    }

    public void setPresupuestoId(String presupuestoId) {
        this.presupuestoId = presupuestoId;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMaquinaId() {
         return maquinaId;
    }

    public void setMaquinaId(String maquinaId) {
        this.maquinaId = maquinaId;
    }

    public Boolean conFactura() {
    	return conFactura;
    }

    public void setConFactura(Boolean conFactura) {
        this.conFactura = conFactura;
    }
}
