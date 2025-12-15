package daos.GestioRep;

import controladores.GestionRepControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vistas.GestionRep.OrdenTrabajoVista;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrdenTrabajoDao {

    public OrdenTrabajoDao() {
    }

    public static ObservableList<ObservableList<String>> traerResultadoBusquedaSQL(String criterio, String dato) {
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();

        String consultaSQL = "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.DESCRIPCION_FALLA, O.ESTADO, "
                + "C.NOMBRE, C.APELLIDO, C.EMPRESA, C.TELEFONO, OM.MAQUINA_ID, M.TIPO, M.MARCA, M.MODELO "
                + "FROM ORDEN_DE_TRABAJO O "
                + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
                + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
                + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
                + "WHERE " + criterio + " = ? "
                + "ORDER BY O.ORDEN_TRABAJO_ID";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(consultaSQL)){
                ps.setString(1, dato);

                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    ObservableList<String> fila = FXCollections.observableArrayList();

                    fila.add(rs.getString("orden_trabajo_id"));
                    fila.add(rs.getString("fecha_ingreso"));
                    fila.add(rs.getString("descripcion_falla"));
                    fila.add(rs.getString("estado"));
                    fila.add(rs.getString("nombre") + " " + rs.getString("apellido"));
                    fila.add(rs.getString("empresa"));
                    fila.add(rs.getString("telefono"));
                    fila.add(rs.getString("maquina_id"));
                    fila.add(rs.getString("tipo"));
                    fila.add(rs.getString("marca"));
                    fila.add(rs.getString("modelo"));

                    datos.add(fila);
                }
        }catch(SQLException e){
            System.out.println("Error SQL: " + e.getMessage());
            System.out.println("Código: " + e.getErrorCode());
            System.out.println("Estado SQL: " + e.getSQLState());
        }
        return datos;
    }

}

