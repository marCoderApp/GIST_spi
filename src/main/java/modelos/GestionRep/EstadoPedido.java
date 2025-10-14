package modelos.GestionRep;

public enum EstadoPedido {
	PENDIENTE("Pendiente"),
    EN_CAMINO("En camino"),
    RECIBIDO("Recibido");

    private final String valor;

    EstadoPedido(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static EstadoOrden fromString(String valor) {
        for (EstadoOrden e : EstadoOrden.values()) {
            if (e.getValor().equalsIgnoreCase(valor)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + valor);
    }
}
