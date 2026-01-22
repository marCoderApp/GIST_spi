package daos.Notificiacion;

import controladores.GestionRepControl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificacionesDao {


public static List<Map<String, Object>> obtenerDatosNovedadBD(String nov_id){
    List<Map<String, Object>> resultados = new ArrayList<>();

    String sql = "SELECT N.ID, N.FECHA, N.ADMIN_ID, " +
            "NI.ITEMID, NI.ORDENID, NI.COMENTARIO FROM NOVEDADES N" +
            " JOIN NOVEDAD_ITEM NI ON N.ID = NI.NOVEDADID" +
            " WHERE N.ID = ? " +
            "ORDER BY N.ID";

    try(PreparedStatement ps = GestionRepControl.conexion
            .prepareStatement(sql)){

        ps.setString(1, nov_id);

        try (ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Map<String, Object> fila = new HashMap<>();
                fila.put("ID", rs.getString("ID"));
                fila.put("FECHA", rs.getString("FECHA"));
                fila.put("ADMIN_ID", rs.getString("ADMIN_ID"));
                fila.put("ITEMID", rs.getString("ITEMID"));
                fila.put("COMENTARIO", rs.getString("COMENTARIO"));
                fila.put("ORDENID", rs.getString("ORDENID"));
                resultados.add(fila);
            }
        }
    }catch(SQLException e){
        e.printStackTrace();
    }
return resultados;
}

}
