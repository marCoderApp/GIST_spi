package daos.GestioRep;

public class OrdenTrabajoDao {

    public OrdenTrabajoDao() {
    }

    public static boolean traerResultadoBusquedaSqlPorID(String criterio) {

        String consultaSQL = "SELECT O.ORDEN_TRABAJO_ID, O.FECHA_INGRESO, O.DESCRIPCION_FALLA, O.ESTADO, "
                + "C.NOMBRE, C.APELLIDO, OM.MAQUINA_ID, M.TIPO, M.MARCA, M.MODELO "
                + "FROM ORDEN_DE_TRABAJO O "
                + "JOIN CLIENTE C ON C.CLIENTE_ID = O.CLIENTE_ID "
                + "LEFT JOIN ORDEN_MAQUINAS OM ON OM.ORDEN_ID = O.ORDEN_TRABAJO_ID "
                + "LEFT JOIN MAQUINAS M ON M.ID = OM.MAQUINA_ID "
                + "WHERE"
                + "ORDER BY O.ORDEN_TRABAJO_ID";



        return false;
    }

}

