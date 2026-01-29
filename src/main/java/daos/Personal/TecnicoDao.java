package daos.Personal;

import controladores.GestionRepControl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TecnicoDao {

    private static final int MAX_TECNICOS_AMOUNT = 25;

    public static int contarTecnicos(){
        String sql = "SELECT COUNT(*) AS c FROM TECNICO";
        try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("c");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
