package modelos.Notificacion;

public enum EstadoTarea {
	PENDIENTE("Pendiente"),
    EN_PROCESO("En_Proceso"),
    COMPLETADO("Realizado");

    private final String valor;

    EstadoTarea(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static EstadoTarea fromString(String valor) {
        for (EstadoTarea e : EstadoTarea.values()) {
            if (e.getValor().equalsIgnoreCase(valor)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + valor);
    }
}
