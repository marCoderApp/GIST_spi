package modelos.GestionRep;

public enum EstadoOrden {
	PENDIENTE("Pendiente"),
    EN_REVISION("En revision"),
    REVISADA("Revisada"),
    CONFIRMADA("Confirmada"),
    RECHAZADA("Rechazada"),
    LISTA("Lista"),
    RETIRADA("Retirada");

    private final String valor;

    EstadoOrden(String valor) {
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
