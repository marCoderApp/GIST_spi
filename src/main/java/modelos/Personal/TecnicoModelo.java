package modelos.Personal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Personal.PersonalBase;
import modelos.Notificacion.TareasModelo;
import controladores.GestionRepControl;

public class TecnicoModelo extends PersonalBase {

	private String especialidad;
	private int cantidadTareasAsignadas;
	private int cantidadTareasCompletadas;
	private int cantidadTareasPendientes;
    private static GestionRepControl gestionRepControl = new GestionRepControl();
	
	public TecnicoModelo(String nombre, String apellido, String especialidad,
			int tareasAsignadas, int tareasCompletadas, int tareasPendientes) {
		super(nombre,apellido);
        this.id = generarId();
		this.especialidad = especialidad;
		this.cantidadTareasAsignadas = tareasAsignadas;
		this.cantidadTareasCompletadas = tareasCompletadas;
		this.cantidadTareasPendientes = tareasPendientes;
	}
	
	public String generarId() {

        String sql = "SELECT tecnico_id FROM TECNICO ORDER BY tecnico_id DESC LIMIT 1";
        String ultimoId = null;
        try (PreparedStatement ps = gestionRepControl.conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                ultimoId = rs.getString("tecnico_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int siguienteNumero = 1;

        if (ultimoId != null && ultimoId.startsWith("TEC")) {
            try {
                int numeroActual = Integer.parseInt(ultimoId.substring(3));
                siguienteNumero = numeroActual + 1;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return String.format("TEC%03d", siguienteNumero);

	}
	
    public Boolean guardarTecnico(PreparedStatement ps){
        int rowsAfectadas = 0;

        try {
            rowsAfectadas = ps.executeUpdate();

            if (rowsAfectadas > 0) {
                System.out.println("Cliente guardado exitosamente.");
                return true;
            } else {
                System.out.println("No se pudo guardar el cliente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static List<TecnicoModelo> obtenerListaTecnicosBD(){
        String sql = "SELECT * FROM tecnico";
        List<TecnicoModelo> listaTecnicos = new ArrayList<TecnicoModelo>();

        try(PreparedStatement ps = gestionRepControl.conexion.prepareStatement(sql);){
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String tecnicoId = rs.getString("tecnico_id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String especialidad = rs.getString("especialidad");
                int tareasAsignadas = rs.getInt("cantidadTareas");
                int tareasCompletadas = rs.getInt("cantidad_tareas_asignadas");
                int tareasPendientes = rs.getInt("cantidad_tareas_pendientes");

                TecnicoModelo tecnico = new TecnicoModelo(nombre, apellido, especialidad, tareasAsignadas, tareasCompletadas, tareasPendientes);
                tecnico.setTecnicoId(tecnicoId);

                listaTecnicos.add(tecnico);
            }

            return listaTecnicos;
        }catch (SQLException e){
            System.out.println("Error al registrar el tecnico: " + e.getMessage());
        }
        return null;
    }

     //Getters and Setters
	
	public String getId() {
		        return id;
	}

    @Override
    public String getRol(){
        return "TECNICO";
    }
	
	public void setTecnicoId(String tecnicoId) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public String getEspecialidad() {
		return especialidad;
	}
	
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
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

}
