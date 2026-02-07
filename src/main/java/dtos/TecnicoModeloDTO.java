package dtos;

public class TecnicoModeloDTO {

    public String nombre;
    public String id;
    public String usuario;
    public String apellido;
    public String especialidad;
    private int cantidadTareasAsignadas;
    private int cantidadTareasCompletadas;
    private int cantidadTareasPendientes;

    public TecnicoModeloDTO(String id,
                            String usuario,
                            String nombre,
                            String apellido,
                            String especialidad,
                            int cantidadTareasAsignadas,
                            int cantidadTareasCompletadas,
                            int cantidadTareasPendientes){
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.cantidadTareasAsignadas = cantidadTareasAsignadas;
        this.cantidadTareasCompletadas = cantidadTareasCompletadas;
        this.cantidadTareasPendientes = cantidadTareasPendientes;
    }

    public String getId() {
        return this.id;
    }

    public int getCantidadTareasAsignadas() {
        return cantidadTareasAsignadas;
    }

    public int getCantidadTareasCompletadas() {
        return cantidadTareasCompletadas;
    }

    public int getCantidadTareasPendientes() {
        return cantidadTareasPendientes;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getApellido() {
        return apellido;
    }

    public String getUsuario() {
        return usuario;
    }
}
