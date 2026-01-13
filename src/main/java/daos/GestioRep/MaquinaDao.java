package daos.GestioRep;

import conexion.ConexionDB;
import controladores.GestionRepControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MaquinaDao {

    private static Connection conexion = ConexionDB.conectar();

    //DAR DE BAJA MAQUINA DE DATABASE
    public static Boolean darDeBajaMaquinaBD(String idMaquina) {

        String sqlSentencia = "UPDATE MAQUINAS SET activo = FALSE WHERE id = ?";

        try(PreparedStatement ps = conexion.prepareStatement(sqlSentencia)){
            ps.setString(1, idMaquina);

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //MODIFICAR MAQUINA EN DATABASE
    public static Boolean modificarMaquinaDB(String idMaquina){
        return false;
    }

}
