package daos.GestioRep;

import controladores.GestionRepControl;
import dtos.PedidosDto;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PedidosDao {

    //CREAR PEDIDO
    public static Boolean crearPedido(PedidosDto pedido){

        String sql = "INSERT INTO pedidos VALUES (?,?,?,?,?)";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.setString(1, pedido.getPedidoId());
            ps.setString(2, pedido.getFecha());
            ps.setString(3, pedido.getRepuestos());
            ps.setString(4, pedido.getAdminId());
            ps.setString(5, pedido.getEstado());

            int rows = ps.executeUpdate();

            if(rows > 0){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    //AGREGAR ITEMS
    public void agregarItemAPedido(String pedidoId, String repuestoId, int cantidad, double precioUnitario) {
        String sql = """
        INSERT INTO pedido_repuestos (pedido_id, repuesto_id, cantidad, precio_unitario)
        VALUES (?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            cantidad = cantidad + VALUES(cantidad),
            precio_unitario = VALUES(precio_unitario)
        """;

        try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)) {

            ps.setString(1, pedidoId);
            ps.setString(2, repuestoId);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precioUnitario);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error agregando item al pedido", e);
        }
    }

}
