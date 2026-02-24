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

        String sql = "SELECT * FROM presupuesto";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
               PresupuestosDTO presupuesto = new PresupuestosDTO(
                       rs.getString("presupuesto_id"),
                       rs.getFloat("total"),
                       rs.getBoolean("con_factura"),
                       rs.getString("maquina_id"),
                       rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                       rs.getString("admin_id")
               );
               presupuestosDTOS.add(presupuesto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return presupuestosDTOS;
    }

}
