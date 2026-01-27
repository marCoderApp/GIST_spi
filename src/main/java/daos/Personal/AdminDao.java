package daos.Personal;

import controladores.GestionRepControl;
import modelos.Personal.AdminModelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDao {

    //GUARDAR NUEVO ADMIN DB
    public static Boolean guardarNuevoAdminDB(AdminModelo nuevoAdmin){
      String sql = "INSERT INTO ADMINISTRADOR (ADMINISTRADOR_ID, " +
              "NOMBRE, APELLIDO, TURNO) VALUES (?, ?, ?, ?)";

      try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
          ps.setString(1, nuevoAdmin.getId());
          ps.setString(2, nuevoAdmin.getNombre());
          ps.setString(3, nuevoAdmin.getApellido());
          ps.setString(4, nuevoAdmin.getTurno());
          int filas = ps.executeUpdate();

          if(filas>0){
              return true;
          }else{
              throw new SQLException();
          }

      }catch(SQLException e){
          e.printStackTrace();
      }
      return false;
    }
}
