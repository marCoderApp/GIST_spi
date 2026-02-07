package vistas.Personal;

import controladores.GestionRepControl;
import controladores.PersonalControl;
import daos.Personal.AdminDao;
import daos.Personal.TecnicoDao;
import dtos.AdminModeloDTO;
import dtos.TecnicoModeloDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelos.GestionRep.Credenciales;
import modelos.GestionRep.RolCredencial;
import modelos.Personal.AdminModelo;
import modelos.Personal.TecnicoModelo;
import vistas.GestionRep.OrdenTrabajoVista;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FichaUsuarioVista {

	private String usuarioId;
	private String nombre;
	private String apellido;
	private String especialidad;
	private int cantidadTareas;
    private PersonalControl personalControl = new PersonalControl(null, false, false, null);

	
	public FichaUsuarioVista(String usuarioId, String nombre, String apellido, String especialidad,
                             int cantidadTareas) {
		super();
		this.usuarioId = usuarioId;
		this.nombre = nombre;
		this.apellido = apellido;
		this.especialidad = especialidad;
		this.cantidadTareas = cantidadTareas;
	}

    //MENÚ ADMINISTRADORES
    public void mostrarMenuAdministradores(){
        System.out.println("MOSTRAR MENU ADMINISTRADORES");

        Stage gestionAdministradorVentana = new Stage();
        gestionAdministradorVentana.setTitle("Gestión de Administradores");

        //TÍTULO
        javafx.scene.control.Label titulo = new javafx.scene.control.Label("Gestión de Administradores - GIST");
        titulo.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 24));

        //BOTONES
        Button botonCrearAdmin = new Button("➕ Crear nuevo admin");
        Button botonListarAdmins = new Button("📋 Listar admin");
        Button botonBuscarAdmin = new Button("🔍 Buscar admin");
        Button botonVolver = new Button("🔙 Volver");

        //ESTILOS
        String estiloBoton =
                "-fx-background-color: white;" +
                        "-fx-text-fill: black;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-font-size: 14px;";

        botonCrearAdmin.setStyle(estiloBoton);
        botonBuscarAdmin.setStyle(estiloBoton);
        botonListarAdmins.setStyle(estiloBoton);
        botonVolver.setStyle(estiloBoton);

        botonCrearAdmin.setPrefWidth(250);
        botonListarAdmins.setPrefWidth(250);
        botonBuscarAdmin.setPrefWidth(250);
        botonVolver.setPrefWidth(250);

        //ACCIONES DE BOTONES
        botonCrearAdmin.setOnAction(e -> {

                if(AdminDao.rolActual != "SUPER_ADMIN"){
                    OrdenTrabajoVista.mostrarAdvertencia("No tiene permisos para crear un nuevo administrador.");
                    return;
                }

                    int numAdmins = AdminDao.contarAdmins();
                    if(numAdmins >= 10){
                        OrdenTrabajoVista.mostrarAdvertencia("" +
                                "Ya ha alcanzado el número máximo de Administradores" +
                                " que puede agregar.");
                        return;
                    }
                    opcionCrearAdmin();
                });
        botonListarAdmins.setOnAction(e -> mostrarListaAdmins());
        botonBuscarAdmin.setOnAction(e->{buscarUser("ADMINISTRADOR");});
        botonVolver.setOnAction(e -> {gestionAdministradorVentana.close();});

        // Hover effects
        javafx.scene.control.Button[] botones = {botonCrearAdmin,
                botonListarAdmins,
                botonBuscarAdmin, botonVolver};
        for (javafx.scene.control.Button btn : botones) {
            btn.setOnMouseEntered(e -> btn.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
            btn.setOnMouseExited(e -> btn.setStyle(estiloBoton));
        }

        // Layout
        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(15);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(30));
        layout.getChildren().addAll(titulo,
                botonCrearAdmin,
                botonListarAdmins,
                botonBuscarAdmin,
                botonVolver);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");

        javafx.scene.Scene escena = new javafx.scene.Scene(layout, 400, 450);
        gestionAdministradorVentana.setScene(escena);
        gestionAdministradorVentana.show();

    }

    //MENÚ TECNICOS
    public void mostrarMenuTecnicos(){

        // Ventana
        Stage gestionTecnicosVentana = new Stage();
        gestionTecnicosVentana.setTitle("Gestión de Técnicos");

        // Título
        javafx.scene.control.Label titulo = new javafx.scene.control.Label("Gestión de Técnicos - GIST");
        titulo.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 24));

        // Botones
        javafx.scene.control.Button botonCrearTecnico = new javafx.scene.control.Button("➕ Crear nuevo técnico");
        javafx.scene.control.Button botonListarTecnicos = new javafx.scene.control.Button("📋 Listar técnicos");
        javafx.scene.control.Button botonBuscarTecnico = new javafx.scene.control.Button("🔍 Buscar técnico");
        javafx.scene.control.Button botonVolver = new javafx.scene.control.Button("🔙 Volver");

        botonCrearTecnico.setPrefWidth(250);
        botonListarTecnicos.setPrefWidth(250);
        botonBuscarTecnico.setPrefWidth(250);
        botonVolver.setPrefWidth(250);

        botonCrearTecnico.setOnAction(e -> {

            if(AdminDao.rolActual != "SUPER_ADMIN"){
                OrdenTrabajoVista.mostrarAdvertencia("No tiene los permisos necesarios para crear técnico!");
                return;
            }

            int numTecnicos = TecnicoDao.contarTecnicos();
            if(numTecnicos >= 25){
                OrdenTrabajoVista.mostrarAdvertencia("" +
                        "Ha alcanzado el numero máximos de técnicos" +
                        " admitidos.");
            }
            opcionCrearTecnico();});
        botonListarTecnicos.setOnAction(e -> mostrarListaTecnicos());
        botonBuscarTecnico.setOnAction(e -> buscarUser("TECNICO"));
        botonVolver.setOnAction(e -> gestionTecnicosVentana.close());

        // Estilos
        String estiloBoton =
                "-fx-background-color: white;" +
                        "-fx-text-fill: black;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-font-size: 14px;";

        botonCrearTecnico.setStyle(estiloBoton);
        botonListarTecnicos.setStyle(estiloBoton);
        botonBuscarTecnico.setStyle(estiloBoton);
        botonVolver.setStyle(estiloBoton);

        // Hover effects
        javafx.scene.control.Button[] botones = {botonCrearTecnico, botonListarTecnicos, botonBuscarTecnico, botonVolver};
        for (javafx.scene.control.Button btn : botones) {
            btn.setOnMouseEntered(e -> btn.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
            btn.setOnMouseExited(e -> btn.setStyle(estiloBoton));
        }

        // Layout
        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(15);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(30));
        layout.getChildren().addAll(titulo, botonCrearTecnico, botonListarTecnicos, botonBuscarTecnico, botonVolver);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");

        javafx.scene.Scene escena = new javafx.scene.Scene(layout, 400, 450);
        gestionTecnicosVentana.setScene(escena);
        gestionTecnicosVentana.show();

    }


    //OPCIÓN CREAR TÉCNICO
    public void opcionCrearTecnico() {
        System.out.println("CREANDO TECNICO...");
        ingresarDatosTecnico();
    }

    private TecnicoModelo ingresarDatosTecnico(){
        PersonalControl personalControl = new PersonalControl(null, false, false, null);

        Stage ventanaCrearTecnico = new Stage();
        ventanaCrearTecnico.setTitle("Crear nuevo técnico");
        ventanaCrearTecnico.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        //TÍTULO
        javafx.scene.control.Label titulo = new javafx.scene.control.Label("Crear nuevo técnico");
        titulo.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 20));

        //INPUTS
        javafx.scene.control.TextField txtNombre = new javafx.scene.control.TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.setPrefWidth(250);

        javafx.scene.control.TextField txtApellido = new javafx.scene.control.TextField();
        txtApellido.setPromptText("Apellido");
        txtApellido.setPrefWidth(250);

        javafx.scene.control.TextField txtEspecialidad = new javafx.scene.control.TextField();
        txtEspecialidad.setPromptText("Especialidad");
        txtEspecialidad.setPrefWidth(250);

        javafx.scene.control.TextField txtContrasenia= new javafx.scene.control.TextField();
        txtContrasenia.setPromptText("Contraseña");
        txtContrasenia.setPrefWidth(250);

        javafx.scene.control.TextField txtConfirmarContrasenia= new javafx.scene.control.TextField();
        txtConfirmarContrasenia.setPromptText("Confirmar contraseña");
        txtConfirmarContrasenia.setPrefWidth(250);

        //BOTONES
        javafx.scene.control.Button btnCrear = new javafx.scene.control.Button("Crear");
        btnCrear.setPrefWidth(250);
        javafx.scene.control.Button btnCancelar = new javafx.scene.control.Button("Cancelar");
        btnCancelar.setPrefWidth(250);

        btnCrear.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancelar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");

        final TecnicoModelo[] resultado = new TecnicoModelo[1];

        //ACCIONES DE BOTONES
        btnCrear.setOnAction(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim() != "" ? txtApellido.getText().trim() : "";
            String especialidad = txtEspecialidad.getText().trim();
            String contrasenia = txtContrasenia.getText().trim();
            String confirmarContrasenia = txtConfirmarContrasenia.getText().trim();

            if(!contrasenia.equals(confirmarContrasenia)){
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.WARNING
                );
                alerta.setTitle("Contraseñas no coinciden");
                alerta.setHeaderText("Las contraseñas ingresadas no coinciden. Por favor, vuelva a intentarlo.");
                alerta.setContentText("!!!");
                alerta.showAndWait();
                return;
            }


            if(nombre.isEmpty() || especialidad.isEmpty() || contrasenia.isEmpty() || confirmarContrasenia.isEmpty()){
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.WARNING
                );
                alerta.setTitle("Campos Requeridos");
                alerta.setHeaderText("Todos los campos son obligatorios");
                alerta.setContentText("Por favor, complete los campos marcados con *");
                alerta.showAndWait();
                return;
            }



            //CREAR NUEVO TECNICO
            TecnicoModelo nuevoTecnico = new TecnicoModelo(nombre,
                    apellido,
                    especialidad,
                    0,
                    0,
                    0);

            //CREAR NUEVA CREDENCIAL
            Credenciales nuevaCredencial = new Credenciales(
                    nombre,
                    contrasenia,
                    RolCredencial.TECNICO,
                    LocalDateTime.now(),
                    null,
                    nuevoTecnico.getId()
                    );

            //GUARDAR TÉCNICO MODELO
            TecnicoModelo guardado = personalControl.registrarTecnico(nuevoTecnico);
            Credenciales credGuardada = personalControl.crearNuevaCredencial(nuevaCredencial);


            if (credGuardada != null) {
                javafx.scene.control.Alert exitoAlert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION
                );
                exitoAlert.setTitle("Éxito");
                exitoAlert.setHeaderText("Técnico creado exitosamente ✅");
                exitoAlert.setContentText("ID: " + guardado.getId());
                exitoAlert.show();
            }

            if (guardado != null) {
                javafx.scene.control.Alert exitoAlert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION
                );
                exitoAlert.setTitle("Éxito");
                exitoAlert.setHeaderText("Técnico creado exitosamente ✅");
                exitoAlert.setContentText("ID: " + guardado.getId());
                exitoAlert.showAndWait();

                resultado[0] = guardado;
                ventanaCrearTecnico.close();
            } else {
                javafx.scene.control.Alert errorAlert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.ERROR
                );
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Error al crear el tecnico");
                errorAlert.setContentText("No se pudo registrar el tecnico en la base de datos.");
                errorAlert.showAndWait();
            }

        });
        btnCancelar.setOnAction(e -> ventanaCrearTecnico.close());

        //LAYOUT y GRID
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 20, 20, 20));
        grid.add(titulo, 0, 0, 2, 1);
        grid.add(new javafx.scene.control.Label("Nombre: "), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new javafx.scene.control.Label("Apellido: "), 0, 2);
        grid.add(txtApellido, 1, 2);
        grid.add(new javafx.scene.control.Label("Especialidad: "), 0, 3);
        grid.add(txtEspecialidad, 1, 3);
        grid.add(new javafx.scene.control.Label("Contraseña: "), 0, 4);
        grid.add(txtContrasenia, 1, 4);
        grid.add(new javafx.scene.control.Label("Confirmar contraseña: "), 0, 5);
        grid.add(txtConfirmarContrasenia, 1, 5);

        javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(10);
        hbox.setAlignment(javafx.geometry.Pos.CENTER);
        hbox.getChildren().addAll(btnCrear, btnCancelar);
        grid.add(hbox, 0, 6, 2, 1);
        grid.setStyle("-fx-background-color: white;");

        //MOSTRAR ESCENA
        javafx.scene.Scene escena = new javafx.scene.Scene(grid, 400, 275);
        ventanaCrearTecnico.setScene(escena);
        ventanaCrearTecnico.showAndWait();

        return resultado[0];

    }

    private void opcionCrearAdmin() {
        Stage ventana = new Stage();
        ventana.setTitle("Creación de admin");
        ventana.initModality(Modality.APPLICATION_MODAL);

        //TITULO
        Label titulo = new Label("Crear Admin");
        titulo.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));

        //INPUTS

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");
        txtUsuario.setPrefWidth(100);

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.setPrefWidth(100);

        PasswordField txtContrasenia = new PasswordField();
        txtContrasenia.setPromptText("Contraseña");
        txtContrasenia.setPrefWidth(100);

        PasswordField txtConfirmarContrasenia = new PasswordField();
        txtConfirmarContrasenia.setPromptText("Confirmar contraseña");
        txtConfirmarContrasenia.setPrefWidth(100);

        TextField txtApellido = new TextField();
        txtApellido.setPromptText("Ingrese apellido...");
        txtApellido.setPrefWidth(100);

        ComboBox<String> turnoCombo = new  ComboBox<>();
        turnoCombo.setPromptText("Turno");
        turnoCombo.getItems().addAll("Mañana", "Tarde", "Completo");

        //NUEVO ADMINISTRADOR
        AdminModelo nuevoAdmin = new AdminModelo(
                null,
                null,
                null
        );

        // NUEVAS CREDENCIALES
        Credenciales nuevaCredencial = new Credenciales(
                null,
                null,
                null,
                null,
                null,
                null
        );

        //BOTONES
        //BOTONES
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setStyle("-fx-background-color: #15bb15;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold;");
        btnGuardar.setOnAction(e -> {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String turno = turnoCombo.getValue() != null ?  turnoCombo.getValue() : "";
            String usuario = txtUsuario.getText();
            String contra = txtContrasenia.getText();
            String rol = "ADMIN";
            String confirmarContra = txtConfirmarContrasenia.getText();

            //====================
            // AGREGAR METODO QUE COMPRUEBE SI YA EXISTE UN ADMIN CON ESE USUARIO.
            //====================

            Boolean siExiste = AdminDao.comprobarExistenciaAdmin(usuario);

            if (siExiste){
                OrdenTrabajoVista.mostrarAdvertencia("El usuario ya existe");
                return;
            }

            if(usuario == null || usuario.isEmpty()){
                OrdenTrabajoVista.mostrarAdvertencia("Todos los campos son obligatorios");
                return;
            }

            if(nombre == null || nombre.isEmpty()){
                OrdenTrabajoVista.mostrarAdvertencia("Todos los campos son obligatorios");
                return;
            }

            if(turno.isEmpty()){
                OrdenTrabajoVista.mostrarAdvertencia("Todos los campos son obligatorios");
                return;
            }

            if(apellido == null || apellido.isEmpty()){
                OrdenTrabajoVista.mostrarAdvertencia("Todos los campos son obligatorios");
                return;
            }

            if(txtContrasenia.getText().isEmpty() || txtConfirmarContrasenia.getText().isEmpty()){
                OrdenTrabajoVista.mostrarAdvertencia("Todos los campos son obligatorios");
                return;
            }

            if(!txtContrasenia.getText().equals(txtConfirmarContrasenia.getText())){
                OrdenTrabajoVista.mostrarAdvertencia("Contraseñas no coinciden");
                return;
            }


                //CARGAR LOS DATOS EN EL BOTON
                nuevoAdmin.setNombre(nombre);
                nuevoAdmin.setApellido(apellido);
                nuevoAdmin.setTurno(turno);
                nuevaCredencial.setUsuario(usuario);
                nuevaCredencial.setContrasena(contra);
                nuevaCredencial.setRol(RolCredencial.ADMIN);
                nuevaCredencial.setFechaCreacion(LocalDateTime.now());
                nuevaCredencial.setAdmin_id(nuevoAdmin.getId());
                nuevaCredencial.setTecnico_id(null);
                btnGuardar.setDisable(true);

                boolean exito = PersonalControl.guardarNuevoAdmin(nuevoAdmin, nuevaCredencial);

                if(exito){
                    OrdenTrabajoVista.mostrarAlertaExito("Admin guardado correctamente!",
                            "El admin ha sido guardado correctamente!");
                    ventana.close();
                }else{
                    OrdenTrabajoVista.mostrarAdvertencia(  "Verificá los datos e intentá nuevamente.");
                }

        });
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #da1d38;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold;");
        btnCancelar.setOnAction(e -> ventana.close());

        HBox barraBotones = new HBox(10, btnGuardar, btnCancelar);
        barraBotones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, titulo,
                txtUsuario,
                txtNombre, txtApellido,
                txtContrasenia,
                txtConfirmarContrasenia,
                turnoCombo, barraBotones );
        layout.setAlignment(Pos.CENTER);
        layout.setPrefHeight(600);
        layout.setPadding(new Insets(20));

        Scene escena = new Scene(layout, 300, 350);
        ventana.setScene(escena);
        ventana.show();
    }
	
	public void abrirFicha() {
                // Lógica para abrir la ficha del técnico
    }
	
	public void mostrarFichaDatos(TecnicoModelo tecnico) {
		// Lógica para mostrar los datos del técnico
	}

    //MOSTRAR LISTA DE TECNICOS
	public void mostrarListaTecnicos() {

        List<TecnicoModeloDTO> listaTecnicos = personalControl.obtenerListaTecnicos();
        Stage ventanaListarTecnicos = new Stage();
        ventanaListarTecnicos.setTitle("Lista de Órdenes de Trabajo");

        //VISTA DE TABLA Y FILAS DE TIPO TECNICO MODELO
        TableView<TecnicoModeloDTO> tablaTecnicos = new TableView<>();
        ObservableList<TecnicoModeloDTO> datos = FXCollections.observableArrayList();

        //COLUMNAS DE LA TABLA
        TableColumn<TecnicoModeloDTO, String> columnaId = new TableColumn<>("ID");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaId.setPrefWidth(80);

        TableColumn<TecnicoModeloDTO, String> columnaUsuario = new TableColumn<>("Usuario");
        columnaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        columnaUsuario.setPrefWidth(80);

        TableColumn<TecnicoModeloDTO, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaNombre.setPrefWidth(80);

        TableColumn<TecnicoModeloDTO, String> columnaApellido = new TableColumn<>("Apellido");
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnaApellido.setPrefWidth(50);

        TableColumn<TecnicoModeloDTO, String> columnaEspecialidad = new TableColumn<>("Especialidad");
        columnaEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        columnaEspecialidad.setPrefWidth(80);

        TableColumn<TecnicoModeloDTO, String> columnaTareasCompletadas = new TableColumn<>("Tareas completadas");
        columnaTareasCompletadas.setCellValueFactory(new PropertyValueFactory<>("cantidadTareasCompletadas"));
        columnaTareasCompletadas.setPrefWidth(50);

        TableColumn<TecnicoModeloDTO, String> columnaTareasAsignadas = new TableColumn<>("Tareas asignadas");
        columnaTareasAsignadas.setCellValueFactory(new PropertyValueFactory<>("cantidadTareasAsignadas"));
        columnaTareasAsignadas.setPrefWidth(50);

        TableColumn<TecnicoModeloDTO, String> columnaTareasPendientes = new TableColumn<>("Tareas pendientes");
        columnaTareasPendientes.setCellValueFactory(new PropertyValueFactory<>("cantidadTareasPendientes"));
        columnaTareasPendientes.setPrefWidth(50);

        tablaTecnicos.getColumns().addAll(columnaId,
                columnaUsuario,
                columnaNombre,
                columnaApellido,
                columnaEspecialidad,
                columnaTareasAsignadas,
                columnaTareasCompletadas,
                columnaTareasPendientes);


        //ITERAR LA LISTA DE TECNICOS Y AGREGARLAS A LA TABLA
        for (TecnicoModeloDTO tecnico : listaTecnicos) {
            datos.add(tecnico);
        }

        tablaTecnicos.setItems(datos);

        Button btnEditar = new Button("Editar");
        btnEditar.setPrefWidth(100);
        btnEditar.setOnAction(e -> {

            TecnicoModeloDTO tec = tablaTecnicos.getSelectionModel().getSelectedItem();
            if (tec == null){
                OrdenTrabajoVista.mostrarAdvertencia("Seleccione un administrador para editar");
            }else{

                if(!AdminDao.adminActualId.equals("ADM000")){
                    OrdenTrabajoVista.mostrarAdvertencia("No cuenta con los permisos necesarios para editar");
                }else{
                    mostrarFormEditarTecnico(ventanaListarTecnicos, tec.getId());
                }
            }
        });

        Button btnEliminar = new Button("Dar de Baja");
        btnEliminar.setPrefWidth(100);
        btnEliminar.setOnAction(e -> {

        });

        Button btnVolver = new Button("Volver");
        btnVolver.setPrefWidth(100);
        btnVolver.setOnAction(e -> ventanaListarTecnicos.close());

        //LAYOUT
        HBox hbox = new HBox(10, btnEditar, btnEliminar, btnVolver);

        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));

        VBox layout = new VBox(10, tablaTecnicos, hbox);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        Scene escena = new Scene(layout);
        ventanaListarTecnicos.setScene(escena);
        ventanaListarTecnicos.showAndWait();
	}

    //MOSTRAR FORM EDITAR TECNICO
    public void mostrarFormEditarTecnico(Stage ventanaListarTecnicos, String idTecnico){

        Stage ventanaEditarTecnico = new Stage();
        ventanaEditarTecnico.setTitle("Editar tecnico");
        ventanaEditarTecnico.initModality(Modality.APPLICATION_MODAL);

        Label titulo = new Label("Editar tecnico");
        titulo.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.setPrefWidth(100);

        TextField txtApellido = new TextField();
        txtApellido.setPromptText("Apellido");
        txtApellido.setPrefWidth(100);

        TextField txtEspecialidad = new TextField();
        txtEspecialidad.setPromptText("Especialidad");
        txtApellido.setPrefWidth(100);

        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");
        btnGuardar.setStyle("-fx-background-color: #15bb15;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold;");
        btnGuardar.setOnAction(e -> {

            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String especialidad = txtEspecialidad.getText();

            if(nombre == null || nombre.isEmpty()){
                OrdenTrabajoVista.mostrarAdvertencia("Todos los campos son obligatorios");
                return;
            }

            if(apellido == null || apellido.isEmpty()){
                OrdenTrabajoVista.mostrarAdvertencia("Todos los campos son obligatorios");
            }

            if(especialidad == null || especialidad.isEmpty()){
                OrdenTrabajoVista.mostrarAdvertencia("Todos los campos son obligatorios");
            }

            TecnicoModelo tecnicoInfo = TecnicoModelo.obtenerTecnicoPorId(idTecnico);

            tecnicoInfo.setNombre(nombre);
            tecnicoInfo.setApellido(apellido);
            tecnicoInfo.setEspecialidad(especialidad);
            Boolean editado = PersonalControl.modificarTecnico(tecnicoInfo, idTecnico);

            if(editado){
                OrdenTrabajoVista.mostrarAlertaExito("Tecnico editado correctamente!",
                        "El tecnico ha sido editado correctamente!");
                ventanaEditarTecnico.close();
                ventanaListarTecnicos.close();
                mostrarListaTecnicos();
            };
        });


        btnCancelar.setStyle("-fx-background-color: #da1d38;" +
                " -fx-text-fill: white;" +
                " -fx-font-weight: bold;");
        btnCancelar.setOnAction(e -> ventanaEditarTecnico.close());

        HBox barraBotones = new HBox(10, btnGuardar, btnCancelar);
        barraBotones.setAlignment(Pos.CENTER);


        VBox layout = new VBox(15, titulo, txtNombre, txtApellido, txtEspecialidad, barraBotones);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefHeight(200);
        layout.setPadding(new Insets(20));

        Scene escena = new Scene(layout, 300, 350);
        ventanaEditarTecnico.setScene(escena);
        ventanaEditarTecnico.show();

    }



    //MOSTRAR LISTA DE ADMINS
    public void mostrarListaAdmins() {

        List<AdminModeloDTO> listaAdmins = personalControl.obtenerListaAdmins();
        Stage ventanaListarAdmins = new Stage();
        ventanaListarAdmins.setTitle("Lista de Órdenes de Trabajo");

        //VISTA DE TABLA Y FILAS DE TIPO TECNICO MODELO
        TableView<AdminModeloDTO> tablaAdmins = new TableView<>();
        ObservableList<AdminModeloDTO> datos = FXCollections.observableArrayList();

        //COLUMNAS DE LA TABLA
        TableColumn<AdminModeloDTO, String> columnaId = new TableColumn<>("Administrador ID");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaId.setPrefWidth(80);

        TableColumn<AdminModeloDTO, String> columnaUsuario = new TableColumn<>("Usuario");
        columnaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        columnaUsuario.setPrefWidth(80);

        TableColumn<AdminModeloDTO, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaNombre.setPrefWidth(80);

        TableColumn<AdminModeloDTO, String> columnaApellido = new TableColumn<>("Apellido");
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnaApellido.setPrefWidth(50);

        TableColumn<AdminModeloDTO, String> columnaTurno = new TableColumn<>("Turno");
        columnaTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        columnaTurno.setPrefWidth(80);

        tablaAdmins.getColumns().addAll(columnaId,
                columnaUsuario,
                columnaNombre,
                columnaApellido,
                columnaTurno);

        //ITERAR LA LISTA DE ADMIN Y AGREGARLAS A LA TABLA
        for (AdminModeloDTO admin : listaAdmins) {
            datos.add(admin);
        }

        tablaAdmins.setItems(datos);

        //BOTONES
        Button btnEditar = new Button("Editar");
        btnEditar.setPrefWidth(100);
        btnEditar.setOnAction(e -> {
                AdminModeloDTO admin = tablaAdmins.getSelectionModel().getSelectedItem();
                if (admin == null){
                    OrdenTrabajoVista.mostrarAdvertencia("Seleccione un administrador para editar");
                }else{

                    if(Objects.equals(admin.getUsuario(), "admin_base")){
                        OrdenTrabajoVista.mostrarAdvertencia("No se puede editar el administrador base");
                        return;
                    }
                   mostrarFormEditarAdmin(ventanaListarAdmins, admin.getId());
                }
        });

        Button btnDarDeBaja = new Button("Dar De Baja");
        btnDarDeBaja.setPrefWidth(100);
        btnDarDeBaja.setOnAction(e -> {
                if (AdminDao.rolActual != "SUPER_ADMIN"){
                    OrdenTrabajoVista.mostrarAdvertencia("No tiene permisos para dar de baja a un administrador");
                    return;
                }

                AdminModeloDTO admin = tablaAdmins.getSelectionModel().getSelectedItem();
                if (admin == null){
                    OrdenTrabajoVista.mostrarAdvertencia("Seleccione un administrador para dar de baja");
                }else{

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmación");
                    alert.setHeaderText("¿Desea continuar?");
                    alert.setContentText("Seleccione una opción.");

                    ButtonType btnSi = new ButtonType("Sí");
                    ButtonType btnNo = new ButtonType("No");

                    alert.getButtonTypes().setAll(btnSi, btnNo);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == btnSi) {
                        if(PersonalControl.darDeBajaAdminDB(admin.getId())){
                            OrdenTrabajoVista.mostrarAlertaExito("Administrador dado de baja correctamente", "El administrador ha sido dado de baja correctamente");
                            ventanaListarAdmins.close();
                        }else{
                            OrdenTrabajoVista.mostrarAdvertencia("No se pudo dar de baja al administrador");
                        }
                    } else {
                        return;
                    }
                }
        });

        Button btnVolver = new Button("Volver");
        btnVolver.setPrefWidth(100);
        btnVolver.setOnAction(e -> ventanaListarAdmins.close());

        //LAYOUT
        HBox hbox = new HBox(10, btnEditar, btnDarDeBaja, btnVolver);

        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));

        VBox layout = new VBox(10, tablaAdmins, hbox);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        Scene escena = new Scene(layout);
        ventanaListarAdmins.setScene(escena);
        ventanaListarAdmins.showAndWait();
    }

    //METODO PARA BUSCAR ADMINISTRADOR POR NOMBRE
    private void buscarUser(String rol) {
        javafx.scene.control.TextInputDialog dialogo = new javafx.scene.control.TextInputDialog();

        // Configurar el diálogo según el rol
        if (rol.equals("TECNICO")) {
            dialogo.setTitle("Buscar técnico");
            dialogo.setHeaderText("Ingrese nombre o apellido del técnico");
        } else if (rol.equals("ADMINISTRADOR")) {
            dialogo.setTitle("Buscar administrador");
            dialogo.setHeaderText("Ingrese nombre o apellido del administrador");
        }

        dialogo.setContentText("Búsqueda:");

        java.util.Optional<String> resultado = dialogo.showAndWait();

        if (resultado.isPresent() && !resultado.get().trim().isEmpty()) {
            String criterio = resultado.get().trim();

            // Buscar según el rol
            if (rol.equals("TECNICO")) {
                buscarTecnicoPorNombre(criterio);
            } else if (rol.equals("ADMINISTRADOR")) {
                buscarAdminPorNombre(criterio);
            }
        }
    }

    //METODO PARA EDITAR ADMIN POR ID
    public void mostrarFormEditarAdmin(Stage ventanaListarAdmins, String adminId){
        Stage ventanaEditarAdmin = new Stage();
        ventanaEditarAdmin.setTitle("Editar administrador");
        ventanaEditarAdmin.initModality(Modality.APPLICATION_MODAL);

        Label titulo = new Label("Editar administrador");
        titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.setPrefWidth(100);

        TextField txtApellido = new TextField();
        txtApellido.setPromptText("Apellido");
        txtApellido.setPrefWidth(100);

        ComboBox comboTurno = new ComboBox();
        comboTurno.setPromptText("Elija turno");
        comboTurno.getItems().addAll("Completo", "Mañana", "Tarde");

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setPrefWidth(150);
        btnGuardar.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefWidth(150);
        btnCancelar.setStyle("-fx-font-size: 14px; -fx-background-color: #da1d38; -fx-text-fill: white;");

        btnGuardar.setOnAction(e -> {
            if(txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()
            || comboTurno.getValue() == null){
                OrdenTrabajoVista.mostrarAdvertencia("Todos los campos son obligatorios");
                return;
            }

            AdminModelo newAdmin = new AdminModelo( txtNombre.getText(),
                    txtApellido.getText(),
                    comboTurno.getValue().toString());

           Boolean editado = PersonalControl.editarAdmin(adminId, newAdmin);

            if(editado){
                OrdenTrabajoVista.mostrarAlertaExito("Admin editado correctamente!",
                        "El administrador ha sido editado correctamente!");
               ventanaEditarAdmin.close();
               ventanaListarAdmins.close();
               mostrarListaAdmins();
            }
        });

        btnCancelar.setOnAction(e -> ventanaEditarAdmin.close());

        HBox barraBotones = new HBox(10, btnGuardar, btnCancelar);
        barraBotones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, titulo, txtNombre, txtApellido, comboTurno, barraBotones);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefHeight(200);
        layout.setPadding(new Insets(20));

        Scene escena = new Scene(layout, 300, 250);
        ventanaEditarAdmin.setScene(escena);
        ventanaEditarAdmin.show();
    }

    //METODO PARA BUSCAR TECNICO POR NOMBRE
    private void buscarTecnicoPorNombre(String criterio) {
        java.util.List<modelos.Personal.TecnicoModelo> resultados = new java.util.ArrayList<>();

        String sqlBusqueda = "SELECT * FROM TECNICO WHERE " +
                "LOWER(nombre) LIKE ? OR LOWER(apellido) LIKE ?";

        try (java.sql.PreparedStatement ps = conexion.ConexionDB.conectar().prepareStatement(sqlBusqueda)) {
            ps.setString(1, "%" + criterio.toLowerCase() + "%");
            ps.setString(2, "%" + criterio.toLowerCase() + "%");

            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelos.Personal.TecnicoModelo tecnico = new modelos.Personal.TecnicoModelo(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("especialidad"),
                        0, // tareasAsignadas
                        rs.getInt("tareas_completadas"),
                        rs.getInt("tareas_pendientes")
                );
                tecnico.setTecnicoId(rs.getString("tecnico_id"));
                resultados.add(tecnico);
            }

            if (resultados.isEmpty()) {
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION
                );
                alerta.setTitle("Sin resultados");
                alerta.setHeaderText("No se encontraron técnicos");
                alerta.setContentText("No hay técnicos que coincidan con: " + criterio);
                alerta.showAndWait();
            } else {
                mostrarResultadosTecnicos(resultados);
            }

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR
            );
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al buscar técnicos");
            alerta.setContentText("Error: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    //METODO PARA ADMINISTRADORES POR NOMBRE
    private void buscarAdminPorNombre(String criterio) {
        java.util.List<modelos.Personal.AdminModelo> resultados = new java.util.ArrayList<>();

        String sqlBusqueda = "SELECT * FROM ADMINISTRADOR WHERE " +
                "LOWER(nombre) LIKE ? OR LOWER(apellido) LIKE ?";

        try (java.sql.PreparedStatement ps = conexion.ConexionDB.conectar().prepareStatement(sqlBusqueda)) {
            ps.setString(1, "%" + criterio.toLowerCase() + "%");
            ps.setString(2, "%" + criterio.toLowerCase() + "%");

            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelos.Personal.AdminModelo admin = new modelos.Personal.AdminModelo(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("turno")
                );
                admin.id = rs.getString("administrador_id");
                resultados.add(admin);
            }

            if (resultados.isEmpty()) {
                javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION
                );
                alerta.setTitle("Sin resultados");
                alerta.setHeaderText("No se encontraron administradores");
                alerta.setContentText("No hay administradores que coincidan con: " + criterio);
                alerta.showAndWait();
            } else {
                mostrarResultadosAdmins(resultados);
            }

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR
            );
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al buscar administradores");
            alerta.setContentText("Error: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    //METODO PARA MOSTRAR RESULTADOS DE BUSQUEDA TECNICOS
    private void mostrarResultadosTecnicos(java.util.List<modelos.Personal.TecnicoModelo> tecnicos) {
        javafx.stage.Stage ventanaResultados = new javafx.stage.Stage();
        ventanaResultados.setTitle("Resultados de Búsqueda - Técnicos");

        //CREAR TABLA
        javafx.scene.control.TableView<modelos.Personal.TecnicoModelo> tablaResultados =
                new javafx.scene.control.TableView<>();

        javafx.collections.ObservableList<modelos.Personal.TecnicoModelo> datos =
                javafx.collections.FXCollections.observableArrayList(tecnicos);

        //CREAR COLUMNAS
        javafx.scene.control.TableColumn<modelos.Personal.TecnicoModelo, String> columnaId =
                new javafx.scene.control.TableColumn<>("ID");
        columnaId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        columnaId.setPrefWidth(80);

        javafx.scene.control.TableColumn<modelos.Personal.TecnicoModelo, String> columnaNombre =
                new javafx.scene.control.TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
        columnaNombre.setPrefWidth(120);

        javafx.scene.control.TableColumn<modelos.Personal.TecnicoModelo, String> columnaApellido =
                new javafx.scene.control.TableColumn<>("Apellido");
        columnaApellido.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("apellido"));
        columnaApellido.setPrefWidth(120);

        javafx.scene.control.TableColumn<modelos.Personal.TecnicoModelo, String> columnaEspecialidad =
                new javafx.scene.control.TableColumn<>("Especialidad");
        columnaEspecialidad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("especialidad"));
        columnaEspecialidad.setPrefWidth(150);

        //AÑADIR COLUMNAS A LA TABLA
        tablaResultados.getColumns().addAll(columnaId, columnaNombre, columnaApellido, columnaEspecialidad);
        tablaResultados.setItems(datos);

        javafx.scene.control.Button btnCerrar = new javafx.scene.control.Button("Cerrar");
        btnCerrar.setOnAction(e -> ventanaResultados.close());

        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));
        layout.getChildren().addAll(tablaResultados, btnCerrar);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        javafx.scene.Scene escena = new javafx.scene.Scene(layout, 500, 400);
        ventanaResultados.setScene(escena);
        ventanaResultados.show();
    }

    //METODO PARA MOSTRAR RESULTADOS DE BUSQUEDA ADMINISTRADORES
    private void mostrarResultadosAdmins(java.util.List<modelos.Personal.AdminModelo> admins) {

        //VENTANA PRINCIPAL
        javafx.stage.Stage ventanaResultados = new javafx.stage.Stage();
        ventanaResultados.setTitle("Resultados de Búsqueda - Administradores");

        //CREAR TABLA
        javafx.scene.control.TableView<modelos.Personal.AdminModelo> tablaResultados =
                new javafx.scene.control.TableView<>();

        javafx.collections.ObservableList<modelos.Personal.AdminModelo> datos =
                javafx.collections.FXCollections.observableArrayList(admins);

        //CREAR COLUMNAS
        javafx.scene.control.TableColumn<modelos.Personal.AdminModelo, String> columnaId =
                new javafx.scene.control.TableColumn<>("ID");
        columnaId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        columnaId.setPrefWidth(80);

        javafx.scene.control.TableColumn<modelos.Personal.AdminModelo, String> columnaNombre =
                new javafx.scene.control.TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
        columnaNombre.setPrefWidth(120);

        javafx.scene.control.TableColumn<modelos.Personal.AdminModelo, String> columnaApellido =
                new javafx.scene.control.TableColumn<>("Apellido");
        columnaApellido.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("apellido"));
        columnaApellido.setPrefWidth(120);

        javafx.scene.control.TableColumn<modelos.Personal.AdminModelo, Integer> columnaTurno =
                new javafx.scene.control.TableColumn<>("Turno");
        columnaTurno.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("turno"));
        columnaTurno.setPrefWidth(80);

        //AÑADIR COLUMNAS A LA TABLA
        tablaResultados.getColumns().addAll(columnaId, columnaNombre, columnaApellido, columnaTurno);
        tablaResultados.setItems(datos);

        javafx.scene.control.Button btnCerrar = new javafx.scene.control.Button("Cerrar");
        btnCerrar.setOnAction(e -> ventanaResultados.close());

        //CREAR LA LAYOUT
        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));
        layout.getChildren().addAll(tablaResultados, btnCerrar);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        javafx.scene.Scene escena = new javafx.scene.Scene(layout, 450, 400);
        ventanaResultados.setScene(escena);
        ventanaResultados.show();
    }

    //ACTUALIZA LA CANTIDA DE TAREAS
	public void actualizarCantidadTareas(int nuevaCantidad) {
		this.cantidadTareas = nuevaCantidad;
	}

    //GETTERS Y SETTERS
	public String getusuarioId() {
		return usuarioId;
	}
	
	public void setusuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
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
	
	public int getCantidadTareas() {
		return cantidadTareas;
	}
	
	public void setCantidadTareas(int cantidadTareas) {
		this.cantidadTareas = cantidadTareas;
	}
}
