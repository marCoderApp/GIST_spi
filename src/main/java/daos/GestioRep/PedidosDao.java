package daos.GestioRep;

import controladores.GestionRepControl;
import dtos.PedidosDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelos.GestionRep.PedidoModelo;
import modelos.GestionRep.RepuestoModelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PedidosDao {

    //CREAR PEDIDO
    public static Boolean crearPedido(PedidosDto pedido){

        String sql = "INSERT INTO pedidos VALUES (?,?,?,?)";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.setString(1, pedido.getId());
            ps.setObject(2, pedido.getFecha());
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

    //LISTAR TODOS LOS PEDIDOS
    public static List<PedidosDto> listarTodosPedidos() {
        List<PedidosDto> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos ORDER BY fecha DESC";

        try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PedidosDto pedido = new PedidosDto(
                        rs.getString("id"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("admin_id"),
                        rs.getString("estado")
                );
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    //OBTENER REPUESTOS DE UN PEDIDO
    public static List<RepuestoModelo> obtenerRepuestosDePedido(String pedidoId) {
        List<RepuestoModelo> repuestos = new ArrayList<>();
        String sql = "SELECT * FROM repuestos WHERE pedido_id = ?";

        try (PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)) {
            ps.setString(1, pedidoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                RepuestoModelo repuesto = new RepuestoModelo(
                        rs.getString("repuesto_id"),
                        rs.getString("nombre_repuesto"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getString("destinatario"),
                        rs.getBoolean("recibido")
                );
                repuestos.add(repuesto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repuestos;
    }

    //MARCAR REPUESTO COMO RECIBIDO
    public static Boolean marcarRepuestoComoRecibido(RepuestoModelo repuesto) {
        String sql = "UPDATE repuestos SET recibido = true WHERE repuesto_id = ?";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(sql)){
            ps.setString(1, repuesto.getRepuestoId());
            int filas = ps.executeUpdate();
            if(filas>0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }

    //TRAER RESULTADO BUSQUEDA DE PEDIDOS
    public static ObservableList<ObservableList<String>> buscarPedidoPorCriterioDB(String criterio, String dato){
        ObservableList<ObservableList<String>> resultado = FXCollections.observableArrayList();

        String consultaSQL = " SELECT P.ID, P.FECHA, P.ADMIN_ID, P.ESTADO, R.REPUESTO_ID, " +
                "R.NOMBRE_REPUESTO, R.CANTIDAD, R.PRECIO AS PRECIO_UNITARIO, R.DESTINATARIO FROM PEDIDOS P " +
                "JOIN REPUESTOS R ON P.ID = R.PEDIDO_ID " +
                " WHERE " + criterio + " LIKE ? ";

        try(PreparedStatement ps = GestionRepControl.conexion.prepareStatement(consultaSQL)){
            ps.setString(1, "%" + dato + "%");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ObservableList<String> fila = FXCollections.observableArrayList();
                fila.add(rs.getString("ID"));
                fila.add(rs.getString("FECHA").substring(0, 10));
                fila.add(rs.getString("ADMIN_ID"));
                fila.add(rs.getString("ESTADO"));
                fila.add(rs.getString("REPUESTO_ID"));
                fila.add(rs.getString("NOMBRE_REPUESTO"));
                fila.add(rs.getString("CANTIDAD"));
                fila.add(rs.getString("PRECIO_UNITARIO"));
                fila.add(rs.getString("DESTINATARIO"));

                resultado.add(fila);
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return resultado;
    }
}
