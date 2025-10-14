package modelos.GestionRep;

public enum EstadoOrden {
	PENDIENTE("Pendiente"),
    EN_PROCESO("En_proceso"),
    COMPLETADO("Completado");

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
