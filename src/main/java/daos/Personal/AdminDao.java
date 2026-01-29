package daos.Personal;

import controladores.GestionRepControl;
import modelos.GestionRep.Credenciales;
import modelos.Personal.AdminModelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    //GUARDAR NUEVO ADMIN DB
    public static Boolean guardarNuevoAdminDB(AdminModelo nuevoAdmin,
                                              Credenciales credencialIngresada){
      String sql = "INSERT INTO ADMINISTRADOR (ADMINISTRADOR_ID, " +
              "NOMBRE, APELLIDO, TURNO) VALUES (?, ?, ?, ?)";

      String sqlCredencial = "INSERT INTO CREDENCIALES (USUARIO, CONTRASEñA, " +
              "FECHACREACION, ADMIN_ID, TECNICO_ID, ROL) VALUES (?, ?, ?, " +
              "?, ?, ?)";


      try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
          ps.setString(1, nuevoAdmin.getId());
          ps.setString(2, nuevoAdmin.getNombre());
          ps.setString(3, nuevoAdmin.getApellido());
          ps.setString(4, nuevoAdmin.getTurno());

          int filas = ps.executeUpdate();

          if(filas>0 ){
              Boolean credGuardada = Credenciales.crearCredenciales(credencialIngresada);

              if(credGuardada){
                  return true;
              }
          }else{
              throw new SQLException();
          }

      }catch(SQLException e){
          e.printStackTrace();
      }
      return false;
    }

    //COMPRAR EXISTENCIA DE ADMIN BY USER
    public static Boolean comprobarExistenciaAdmin(String usuario){
        String sql = "SELECT * FROM CREDENCIALES WHERE USUARIO = ?";
        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.setString(1, usuario);
            ps.executeQuery();

            if(ps.getResultSet().next()){
                return true;
            }else{
                return false;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    //OBTENER LISTA DE ADMINS
    public static List<AdminModelo> obtenerListaAdminsDB(){
        String sql = "SELECT * FROM ADMINISTRADOR";
        List<AdminModelo> listaAdmins = new ArrayList<>();

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            java.sql.ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String administradorId = rs.getString("administrador_id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String turno = rs.getString("turno");

                AdminModelo adminModelo = new AdminModelo(
                        nombre,
                        apellido,
                        turno
                );

                adminModelo.setId(administradorId);
                listaAdmins.add(adminModelo);
            }
        return listaAdmins;
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
