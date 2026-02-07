package dtos;

public class AdminModeloDTO {

    public String id;
    public String nombre;
    public String apellido;
    public String turno;
    public String usuario;

    public AdminModeloDTO(
            String id, String nombre, String apellido, String turno, String usuario
    ){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.turno = turno;
        this.usuario = usuario;
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

    public String getTurno() {
        return turno;
    }

    public String getUsuario() {
        return usuario;
    }
}
