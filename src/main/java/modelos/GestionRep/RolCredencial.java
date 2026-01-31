package modelos.GestionRep;

public enum RolCredencial {
	ADMIN("ADMIN"),
    TECNICO("TECNICO"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String valor;

    RolCredencial(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static RolCredencial fromString(String valor) {
        for (RolCredencial e : RolCredencial.values()) {
            if (e.getValor().equalsIgnoreCase(valor)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Estado no válido: " + valor);
    }
}
