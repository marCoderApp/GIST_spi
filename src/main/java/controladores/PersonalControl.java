package controladores;

import modelos.GestionRep.Credenciales;
import modelos.Personal.AdminModelo;
import modelos.Personal.TecnicoModelo;

import java.sql.PreparedStatement;

public class PersonalControl {

	private AdminModelo datosAdministrador = null;
	private boolean tecnicoGuardado;
	private boolean contraseñaValida;
	private TecnicoModelo datosTecnico;
	public static String adminIdPersonalControl;
	public static String tecnicoIdPersonalControl;
	
	public PersonalControl(AdminModelo datosAdministrador,
			boolean tecnicoGuardado,
			boolean contraseñaValida,
			TecnicoModelo datosTecnico) {
		this.setDatosAdministrador(new AdminModelo(null, null, 0));
		this.tecnicoGuardado = false;
		this.setContraseñaValida(false);
		this.setDatosTecnico(new TecnicoModelo(null, null, null, 0, 0, 0));
	}
	
	public boolean validarCredenciales(Credenciales credencialIngresada) {
		AdminModelo user = new AdminModelo(null, null, 0);
		
		Credenciales cred_bdgist = user.buscarUsuario(credencialIngresada.getUsuario());
		
		 if (cred_bdgist == null) {
		        System.out.println("⚠️ Usuario no encontrado en la base de datos: " + credencialIngresada.getUsuario());
		        return false;
		    }
		
		filtrarIdPorRol(cred_bdgist, cred_bdgist.getRol().getValor());
		
		if(cred_bdgist != null && cred_bdgist.getContrasena().equals(credencialIngresada.getContrasena())) {
			return true;
		}else {
			return false;
		}
			
    }
	
	
	public void filtrarIdPorRol(Credenciales cred_bdgist, String rolValor) {

		if (rolValor == "ADMIN") {
			PersonalControl.adminIdPersonalControl = cred_bdgist.getAdmin_id();
		}else {
			PersonalControl.tecnicoIdPersonalControl = cred_bdgist.getTecnico_id();
		}
	}
	
	public String obtenerTecnicoId(String usuarioId) {

		return usuarioId;

	}
	
	public String obtenerAdminId(String usuarioId) {
		
		return usuarioId;
		
	}
	
	public TecnicoModelo registrarTecnico(TecnicoModelo tecnico) {
		String sqlSentencia = "INSERT INTO tecnico (tecnico_id, nombre, especialidad, cantidadTareas," +
                "cantidad_tareas_asignadas, cantidad_tareas_pendientes) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try(PreparedStatement ps = conexion.ConexionDB.conectar().prepareStatement(sqlSentencia)){
            ps.setString(1, tecnico.getId());
            ps.setString(2, tecnico.getNombre());
            ps.setString(3, tecnico.getEspecialidad());
            ps.setInt(4,tecnico.getCantidadTareasCompletadas());
            ps.setInt(5,tecnico.getCantidadTareasAsignadas());
            ps.setInt(6,tecnico.getCantidadTareasPendientes());

            Boolean guardado = tecnico.guardarTecnico(ps);
            if (guardado) {
                return tecnico;
            }

        }catch(Exception e){
            System.out.println("Error al registrar el tecnico: " + e.getMessage());

        }

        return tecnico;
    }
	
	public void eliminarTecnico() {
        this.setDatosAdministrador(null);
        this.tecnicoGuardado = false;
    }
	
	public void modificarTecnico(String nombre, String especialidad, int experiencia) {
		if (this.tecnicoGuardado) {
			this.setDatosTecnico(new TecnicoModelo(nombre, especialidad, especialidad, 0,0,0));
		}
	}
	
	public void registrarAdministrador(String nombre, String especialidad, int turno) {
        this.setDatosAdministrador(new AdminModelo(nombre, especialidad, turno));
    }
	
	public void eliminarAdministrador(String adminId) {
		
	}
	
	public void modificarAdministrador(String nombre, String especialidad, int turno) {
		if (this.datosAdministrador != null) {
			this.setDatosAdministrador(new AdminModelo(nombre, especialidad, turno));
		}
	}

	public AdminModelo getDatosAdministrador() {
		return datosAdministrador;
	}

	public void setDatosAdministrador(AdminModelo datosAdministrador) {
		this.datosAdministrador = datosAdministrador;
	}

	public boolean isContraseñaValida() {
		return contraseñaValida;
	}

	public void setContraseñaValida(boolean contraseñaValida) {
		this.contraseñaValida = contraseñaValida;
	}

	public TecnicoModelo getDatosTecnico() {
		return datosTecnico;
	}

	public void setDatosTecnico(TecnicoModelo datosTecnico) {
		this.datosTecnico = datosTecnico;
	}
	
	
}
