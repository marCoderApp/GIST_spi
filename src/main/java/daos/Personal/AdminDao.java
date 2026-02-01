package daos.Personal;

import controladores.GestionRepControl;
import modelos.GestionRep.Credenciales;
import modelos.GestionRep.RolCredencial;
import modelos.Personal.AdminModelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private static final int  MAX_ADMINS_AMOUNT = 10;
    public static String adminActualId;
    public static String tecnicoActualId;
    public static String rolActual;

    //GUARDAR NUEVO ADMIN DB
    public static Boolean guardarNuevoAdminDB(AdminModelo nuevoAdmin,
                                              Credenciales credencialIngresada){
      String sql = "INSERT INTO ADMINISTRADOR (ADMINISTRADOR_ID, " +
              "NOMBRE, APELLIDO, TURNO) VALUES (?, ?, ?, ?)";

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

    //CONTAR NUMERO DE ADMINS
    public static int contarAdmins(){
        String sql = "SELECT COUNT(*) AS c FROM ADMINISTRADOR";
        try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("c");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
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

    //COMPROBAR SI EXISTE EL SUPER ADMIN
    public boolean existeSuperAdmin() throws SQLException {
        String sql = "SELECT 1 FROM CREDENCIALES WHERE rol = ? LIMIT 1";
        try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)) {
            ps.setString(1, "SUPER_ADMIN");
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }


    //CREAR SUPER ADMIN
    public void crearSuperAdmin(String id, String nombre, String apellido, String email, String hashPwd) throws SQLException {
        String sql = "INSERT INTO ADMINISTRADOR (ADMINISTRADOR_ID, " +
                "NOMBRE, APELLIDO, TURNO) VALUES (?, ?, ?, ?)";

        Credenciales credencialIngresada = new Credenciales(
                "admin_base",
                hashPwd,
                RolCredencial.SUPER_ADMIN,
                LocalDateTime.now(),
                id,
                null
        );

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, "Completo");

            int filas = ps.executeUpdate();

            if(filas>0 ){
                Boolean credGuardada = Credenciales.crearCredenciales(credencialIngresada);
            }else{
                throw new SQLException();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
