package daos.GestioRep;

import controladores.GestionRepControl;
import dtos.PedidosDto;
import dtos.PresupuestosDTO;
import modelos.GestionRep.PresupuestoModelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PresupuestosDao {

    //LISTAR PRESUPUESTOS
    public static List<PresupuestosDTO> listarPresupuestosDB(){
        List<PresupuestosDTO> presupuestosDTOS = new ArrayList<>();

        String sql = "SELECT P.PRESUPUESTO_ID, P.TOTAL, P.CON_FACTURA, " +
                "M.TIPO AS NOMBREMAQUINA, M.ID AS MAQUINA_ID, C.NOMBRE AS NOMBRECLIENTE," +
                " C.APELLIDO AS APELLIDOCLIENTE, P.FECHA_CREACION, P.ADMIN_ID, P.ORDEN_ID" +
                " FROM presupuesto P " +
                "LEFT JOIN orden_de_trabajo O ON O.ORDEN_TRABAJO_ID = P.ORDEN_ID" +
                " LEFT JOIN cliente C ON C.CLIENTE_ID = O.CLIENTE_ID" +
                " LEFT JOIN maquinas M ON M.ID = P.MAQUINA_ID" +
                " ORDER BY P.FECHA_CREACION DESC";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
               PresupuestosDTO presupuesto = new PresupuestosDTO(
                       rs.getString("presupuesto_id"),
                       rs.getFloat("total"),
                       rs.getBoolean("con_factura"),
                       rs.getString("nombreMaquina"),
                       rs.getString("maquina_id"),
                       rs.getString("nombreCliente"),
                       rs.getString("apellidoCliente"),
                       rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                       rs.getString("admin_id"),
                       rs.getString("orden_id")
               );
               presupuestosDTOS.add(presupuesto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return presupuestosDTOS;
    }

    //BUSCAR PRESUPUESTOS POR CRITERIO
    public static List<PresupuestosDTO> buscarPresupuestoPorCriterioDB(String criterio, String dato){
        List<PresupuestosDTO> presupuestosDTOS = new ArrayList<>();

        String sql = "SELECT P.PRESUPUESTO_ID, P.TOTAL, P.CON_FACTURA, " +
                "M.TIPO AS NOMBREMAQUINA, M.ID AS MAQUINA_ID, C.NOMBRE AS NOMBRECLIENTE," +
                " C.APELLIDO AS APELLIDOCLIENTE, P.FECHA_CREACION, P.ADMIN_ID, P.ORDEN_ID" +
                " FROM presupuesto P " +
                "LEFT JOIN orden_de_trabajo O ON O.ORDEN_TRABAJO_ID = P.ORDEN_ID" +
                " LEFT JOIN cliente C ON C.CLIENTE_ID = O.CLIENTE_ID" +
                " LEFT JOIN maquinas M ON M.ID = P.MAQUINA_ID " +
                " WHERE " + criterio + " LIKE ?" +
                " ORDER BY P.FECHA_CREACION DESC";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.setString(1, "%" + dato + "%");
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                PresupuestosDTO presupuesto = new PresupuestosDTO(
                        rs.getString("presupuesto_id"),
                        rs.getFloat("total"),
                        rs.getBoolean("con_factura"),
                        rs.getString("nombreMaquina"),
                        rs.getString("maquina_id"),
                        rs.getString("nombreCliente"),
                        rs.getString("apellidoCliente"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getString("admin_id"),
                        rs.getString("orden_id")
                );
                presupuestosDTOS.add(presupuesto);
            }

            return presupuestosDTOS;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


}
