package modelos.Personal;

public abstract class PersonalBase {
    protected String id;
    protected String nombre;
    protected String apellido;

    public PersonalBase(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public abstract String generarId();

    public abstract String getRol();

    public String getNombreCompleto(){
        return this.nombre + " " + this.apellido;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return this.getNombreCompleto();
    }
}
