package controladores;

import conexion.PasswordHasher;
import daos.Personal.AdminDao;
import dtos.AdminModeloDTO;
import dtos.TecnicoModeloDTO;
import modelos.GestionRep.Credenciales;
import modelos.Personal.AdminModelo;
import modelos.Personal.TecnicoModelo;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

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
		this.setDatosAdministrador(new AdminModelo(null, null, ""));
		this.tecnicoGuardado = false;
		this.setContraseñaValida(false);
		this.setDatosTecnico(new TecnicoModelo(null, null, null, 0, 0, 0));
	}

    //VALIDAR CREDENCIALES
	public boolean validarCredenciales(Credenciales credencialIngresada) {
		AdminModelo user = new AdminModelo(null, null, "");
		
		Credenciales cred_bdgist = user.buscarUsuario(credencialIngresada.getUsuario());
		
		 if (cred_bdgist == null) {
		        System.out.println("⚠️ Usuario no encontrado en la base de datos: " + credencialIngresada.getUsuario());
		        return false;
		    }
		
		filtrarIdPorRol(cred_bdgist, cred_bdgist.getRol().getValor());

         String contraGuardada = cred_bdgist.getContrasena();
         String contraIngresada = credencialIngresada.getContrasena();
		
	    boolean ok;

        if(contraGuardada.startsWith("pbkdf2$")) {
            ok = PasswordHasher.verify(contraIngresada.toCharArray(), contraGuardada);
           AdminDao.rolActual = cred_bdgist.getRol().getValor();
		   AdminDao.adminActualId = cred_bdgist.getAdmin_id();
        }else{
            ok = contraGuardada.equals(contraIngresada);

            if(ok){
                String nuevoHash = PasswordHasher.hash(contraIngresada.toCharArray());
                Credenciales.actualizarContraPorUsuario(credencialIngresada.getUsuario(), nuevoHash);
                AdminDao.rolActual = cred_bdgist.getRol().getValor();
            }

        }

     return ok;
    }

    //CREAR NUEVA CREDENCIAL
    public Credenciales crearNuevaCredencial(Credenciales credencialIngresada){

        try{

           boolean guardado = credencialIngresada.crearCredenciales(credencialIngresada);
            if (guardado) {
                return credencialIngresada;
            }

        }catch(Exception e){
            System.out.println("Error al crear la credencial: " + e.getMessage());
        }

        return null;
    }

    // FILTRAR ID POR ROL
	public void filtrarIdPorRol(Credenciales cred_bdgist, String rolValor) {

		if (rolValor == "ADMIN") {
			PersonalControl.adminIdPersonalControl = cred_bdgist.getAdmin_id();
		}else {
			PersonalControl.tecnicoIdPersonalControl = cred_bdgist.getTecnico_id();
		}
	}

    //MOSTRAR LISTA DE TECNICOS, LLAMA METODO QUE REALIZA CONSULTA EN BD
    public List<TecnicoModeloDTO> obtenerListaTecnicos(){

    List<TecnicoModeloDTO> listaTecnicos = TecnicoModelo.obtenerListaTecnicosBD();

    if(listaTecnicos != null){
        return listaTecnicos;
    }
        return null;
    }
	
	public String obtenerTecnicoId(String usuarioId) {

		return usuarioId;

	}
	
	public String obtenerAdminId(String usuarioId) {
		
		return usuarioId;
		
	}

	//GUARDAR ADMIN
	public static Boolean guardarNuevoAdmin(AdminModelo nuevoAdmin,
											Credenciales credencialIngresada){
		if(nuevoAdmin == null){
			return false;
		}

		try{
			Boolean guardado = AdminDao.guardarNuevoAdminDB(nuevoAdmin, credencialIngresada);
			return guardado;
		}catch(RuntimeException e){
			e.printStackTrace();
			return false;
		}
	}

	//DAR DE BAJA ADMIN
	public static Boolean darDeBajaAdminDB(String adminId){
		try{

			Boolean guardado = AdminDao.darDeBajaAdminDB(adminId);
			return guardado;
		}catch (Exception e){
			e.printStackTrace();
		}

		return false;
	}

	//EDITAR ADMIN
	public static Boolean editarAdmin(String adminId, AdminModelo newAdmin){
		try{
			Boolean editado = AdminDao.editarAdminDB(adminId, newAdmin);
			if(editado){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

    //REGISTRAR TECNICO
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
	
	public static Boolean modificarTecnico(TecnicoModelo tecnico, String tecnicoId) {

		try{
			Boolean editado = TecnicoModelo.editarTecnicoDB(tecnico, tecnicoId);

			if (editado) {
				System.out.println("Tecnico modificado exitosamente: " + editado);
				return true;
			}


		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void registrarAdministrador(String nombre, String especialidad, String turno) {
        this.setDatosAdministrador(new AdminModelo(nombre, especialidad, turno));
    }

    //MOSTRAR DATOS DE ADMINS
    public static List<AdminModeloDTO> obtenerListaAdmins(){

		List<AdminModeloDTO> listaAdmins = AdminDao.obtenerListaAdminsDB();

		if(listaAdmins != null){
			return listaAdmins;
		}
		return null;
	}

	public void eliminarAdministrador(String adminId) {
		
	}
	
	public void modificarAdministrador(String nombre, String especialidad, String turno) {
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
