package daos.GestioRep;

import controladores.GestionRepControl;
import modelos.GestionRep.RepuestoModelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepuestosDAO {

    //BUSCAR REPUESTO POR ID
    public static RepuestoModelo buscarPorId(String repuestoId){
        String sql = "SELECT * FROM repuestos WHERE repuesto_id = ?";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){

            ps.setString(1, repuestoId);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new RepuestoModelo(
                            rs.getString("repuesto_id"),
                            rs.getString("nombre_repuesto"),
                            rs.getInt("CANTIDAD"),
                            rs.getDouble("precio")
                    );
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
