package daos.GestioRep;

import controladores.GestionRepControl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelos.GestionRep.EstadoOrden;
import modelos.GestionRep.OrdenTrabajoModelo;
import vistas.GestionRep.OrdenTrabajoVista;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class OrdenTrabajoDao {

    public OrdenTrabajoDao() {
    }

    //BUSCAR ORDEN SEGUN CRITERIO
    public static ObservableList<ObservableList<String>> traerResultadoBusquedaSQL(String criterio, String dato) {
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();

        String consultaSQL = "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.ESTADO, "
                + "C.NOMBRE, C.APELLIDO, C.EMPRESA, C.TELEFONO, OM.MAQUINA_ID, M.TIPO, M.MARCA, M.MODELO," +
                " M.DESCRIPCION_FALLA "
                + "FROM ORDEN_DE_TRABAJO O "
                + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
                + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
                + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
                + "WHERE " + criterio + " LIKE ? "
                + "ORDER BY O.ORDEN_TRABAJO_ID";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(consultaSQL)){
                ps.setString(1, "%" + dato + "%");

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

    //LISTAR ORDENES INACTIVAS
    public static  ObservableList<ObservableList<String>> listarOrdenesInactivasDB(){
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();

        String consultaSQL = "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.ESTADO, "
                + "C.NOMBRE, C.APELLIDO, C.EMPRESA, C.TELEFONO, OM.MAQUINA_ID, M.TIPO, M.MARCA, M.MODELO," +
                " M.DESCRIPCION_FALLA, O.ACTIVO "
                + "FROM ORDEN_DE_TRABAJO O "
                + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
                + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
                + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
                + "WHERE O.ACTIVO = FALSE "
                + "ORDER BY O.ORDEN_TRABAJO_ID";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(consultaSQL)){
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

    //RESTAURAR ORDEN A ACTIVA
    public static Boolean restaurarOrdenInactivaDB(String ordenId){
        String sql = "UPDATE ORDEN_DE_TRABAJO SET ACTIVO = TRUE " +
                "WHERE ORDEN_TRABAJO_ID = '" + ordenId + "'";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            int filas = ps.executeUpdate();
            if(filas>0){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //MODIFICAR ORDEN DE TRABAJO
    public static Boolean modificarOrdenTrabajoDB(String ordenId){
        return true;
    }

    //DAR DE BAJA ORDEN
    public static Boolean darDeBajaOrdenDB(String ordenId){

        String sql = "UPDATE ORDEN_DE_TRABAJO" +
                " SET ACTIVO = FALSE WHERE ORDEN_TRABAJO_ID = ?";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.setString(1, ordenId);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                return true;
            }else{
                return false;
            }

        }catch(SQLException e){
            OrdenTrabajoVista.mostrarAdvertencia(e.getMessage().toString());
            return false;
        }
    }

    //ENCONTRAR ORDEN POR ID
    public static Boolean encontrarOrdenPorId(String ordenId){
        String sql = "SELECT * FROM ORDEN_DE_TRABAJO" +
                " WHERE orden_trabajo_id = ?";
        try(
                PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql);
                ){
            ps.setString(1, ordenId);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                  return true;
                }else{
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //CAMBIAR ESTADO DE ORDEN
    public static Boolean cambiarEstadoOrdenDB(String ordenId, String estado){
        String sql = "UPDATE ORDEN_DE_TRABAJO " +
                "SET ESTADO = ? " +
                "WHERE ORDEN_TRABAJO_ID = ?";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.setString(1, estado);
            ps.setString(2, ordenId);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
}

