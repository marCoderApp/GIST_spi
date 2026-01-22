package controladores;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import daos.Notificiacion.NotificacionesDao;
import modelos.Notificacion.NovedadModelo;
import modelos.Notificacion.TareasModelo;
import vistas.GestionRep.OrdenTrabajoVista;

public class NotificacionesControlador {
	
	private LocalDateTime fecha;
	private List<TareasModelo> tareas = new ArrayList<>();
	private String adminActivo;
	
	public NotificacionesControlador(List<TareasModelo> tareas ,String adminActivo) {
		this.tareas = tareas;
		this.adminActivo = adminActivo;
		this.fecha = LocalDateTime.now();
	}
	
	public void crearNotificacion(TareasModelo tarea) {
		
	}
	
	public void verificarFechas() {
			
	}
	
	public void marcarComoLeida(String notificacionId) {

	}

	public void crearTarea(String descripcion, LocalDateTime fechaLimite) {
		TareasModelo nuevaTarea = new TareasModelo(descripcion, descripcion, fechaLimite, descripcion);
		tareas.add(nuevaTarea);
	}
	
	public static boolean crearNovedad(NovedadModelo nuevaNovedad) {

        try{

            boolean guardado = nuevaNovedad.guardarNovedadBD(nuevaNovedad);

            if(guardado){
                return true;
            }

        }catch(Exception e){
            System.out.println("Ocurrio un error: " + e.getMessage());
        }

		return false;
	}
	
	public void mostrarNotificacion(String notificacionId) {

	}

	//ELIMINAR UNA NOVEDAD
	public static Boolean eliminarNovedad(String nov_id){
		return false;
	}

	//OBTENER DATOS DE NOVEDAD POR ID
	public static List<Map<String, Object>> obtenerNovedadPorId(String nov_id){

		if(nov_id==null){
			return null;
		}

		List<Map<String, Object>> resultados;

		try{
			resultados = NotificacionesDao.obtenerDatosNovedadBD(nov_id);

		} catch (RuntimeException e) {
			OrdenTrabajoVista.mostrarAdvertencia("Ocurrió un error al obtener" +
					" datos de NOVEDAD.");
			return null;
		}


		return resultados;
	}

	
	
	//Getters and Setters
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	public List<TareasModelo> getTareas() {
		return tareas;
	}
	
	public void setTareas(List<TareasModelo> tareas) {
		this.tareas = tareas;
	}
	
	public String getAdminActivo() {
		return adminActivo;
	}
	
	public void setAdminActivo(String adminActivo) {
		this.adminActivo = adminActivo;
	}
	
	
	

}
