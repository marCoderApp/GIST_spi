package daos.GestioRep;

import controladores.GestionRepControl;
import dtos.PedidosDto;
import modelos.GestionRep.PedidoModelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PedidosDao {

    //CREAR PEDIDO
    public static Boolean crearPedido(PedidosDto pedido){

        String sql = "INSERT INTO pedidos VALUES (?,?,?,?)";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.setString(1, pedido.getPedidoId());
            ps.setString(2, pedido.getFecha());
            ps.setString(3, pedido.getAdminId());
            ps.setString(4, pedido.getEstado());

            int rows = ps.executeUpdate();

            if(rows > 0){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }


    //AGREGAR ITEM MANUAL AL PEDIDO
    public void agregarItemManualAPedido(String pedidoId,
                                         String repuestoId,
                                         String repuestoNombre,
                                         int cantidad,
                                         double precioUnitario,
                                         String destinatario) {
        String sqlRepuesto = """
                INSERT INTO repuestos (pedido_id,
                                       repuesto_id,
                 nombre_repuesto,
                 precio,
                 cantidad,
                 destinatario) VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement psRepuesto = GestionRepControl.conexion.prepareStatement(sqlRepuesto)) {
            psRepuesto.setString(1, pedidoId);
            psRepuesto.setString(2, repuestoId);
            psRepuesto.setString(3, repuestoNombre);
            psRepuesto.setDouble(4, precioUnitario);
            psRepuesto.setInt(5, cantidad);
            psRepuesto.setString(6, destinatario);

            int rows = psRepuesto.executeUpdate();

            if(rows > 0){
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
