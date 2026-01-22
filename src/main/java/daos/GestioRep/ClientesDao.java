package daos.GestioRep;

import controladores.GestionRepControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientesDao {

    public static Boolean traerOrdenesRelacionadasAClienteDB(String clienteId){

        return false;
    }

    public static ObservableList<ObservableList<String>> traerResultadoBusquedaClienteDB(String criterio, String dato){
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();
        String consultaSQL = "SELECT * "
                + "FROM CLIENTE "
                + "WHERE " + criterio + " LIKE ? "
                + "ORDER BY CLIENTE_ID";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(consultaSQL)){
            ps.setString(1, "%" + dato + "%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ObservableList<String> fila = FXCollections.observableArrayList();

                fila.add(rs.getString("cliente_id"));
                fila.add(rs.getString("nombre"));
                fila.add(rs.getString("apellido"));
                fila.add(rs.getString("empresa"));
                fila.add(rs.getString("telefono"));
                fila.add(rs.getString("cuit"));
                fila.add(rs.getString("dni"));

                datos.add(fila);
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return datos;
    }

}
