package vistas.Personal;

import controladores.PersonalControl;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelos.GestionRep.Credenciales;
import modelos.GestionRep.RolCredencial;
import modelos.Personal.TecnicoModelo;
import controladores.PersonalControl;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

import javafx.scene.control.Button;

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
        botonCrearAdmin.setOnAction(e -> opcionCrearAdmin());
        botonListarAdmins.setOnAction(e -> mostrarLista());
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

        botonCrearTecnico.setOnAction(e -> opcionCrearTecnico());
        botonListarTecnicos.setOnAction(e -> mostrarLista());
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
    }
	
	public void abrirFicha() {
                // Lógica para abrir la ficha del técnico
    }
	
	public void mostrarFichaDatos(TecnicoModelo tecnico) {
		// Lógica para mostrar los datos del técnico
	}
	
	public void mostrarLista() {

        List<TecnicoModelo> listaTecnicos = personalControl.obtenerListaTecnicos();
        Stage ventanaListarTecnicos = new Stage();
        ventanaListarTecnicos.setTitle("Lista de Órdenes de Trabajo");

        //VISTA DE TABLA Y FILAS DE TIPO TECNICO MODELO
        TableView<TecnicoModelo> tablaTecnicos = new TableView<>();
        ObservableList<TecnicoModelo> datos = FXCollections.observableArrayList();

        //COLUMNAS DE LA TABLA
        TableColumn<TecnicoModelo, String> columnaId = new TableColumn<>("ID");
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnaId.setPrefWidth(80);

        TableColumn<TecnicoModelo, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaNombre.setPrefWidth(80);

        TableColumn<TecnicoModelo, String> columnaApellido = new TableColumn<>("Apellido");
        columnaApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        columnaApellido.setPrefWidth(50);

        TableColumn<TecnicoModelo, String> columnaEspecialidad = new TableColumn<>("Especialidad");
        columnaEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        columnaEspecialidad.setPrefWidth(80);

        TableColumn<TecnicoModelo, String> columnaTareasCompletadas = new TableColumn<>("Tareas completadas");
        columnaTareasCompletadas.setCellValueFactory(new PropertyValueFactory<>("cantidadTareasCompletadas"));
        columnaTareasCompletadas.setPrefWidth(50);

        TableColumn<TecnicoModelo, String> columnaTareasAsignadas = new TableColumn<>("Tareas asignadas");
        columnaTareasAsignadas.setCellValueFactory(new PropertyValueFactory<>("cantidadTareasAsignadas"));
        columnaTareasAsignadas.setPrefWidth(50);

        TableColumn<TecnicoModelo, String> columnaTareasPendientes = new TableColumn<>("Tareas pendientes");
        columnaTareasPendientes.setCellValueFactory(new PropertyValueFactory<>("cantidadTareasPendientes"));
        columnaTareasPendientes.setPrefWidth(50);

        tablaTecnicos.getColumns().addAll(columnaId,
                columnaNombre,
                columnaApellido,
                columnaEspecialidad,
                columnaTareasAsignadas,
                columnaTareasCompletadas,
                columnaTareasPendientes);


        //ITERAR LA LISTA DE TECNICOS Y AGREGARLAS A LA TABLA
        for (TecnicoModelo tecnico : listaTecnicos) {
            datos.add(tecnico);
        }

        tablaTecnicos.setItems(datos);

        //BOTONES
        Button btnVer = new Button("Ver");
        btnVer.setPrefWidth(100);
        btnVer.setOnAction(e -> {});

        Button btnEditar = new Button("Editar");
        btnEditar.setPrefWidth(100);
        btnEditar.setOnAction(e -> {});

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setPrefWidth(100);
        btnEliminar.setOnAction(e -> {});

        Button btnVolver = new Button("Volver");
        btnVolver.setPrefWidth(100);
        btnVolver.setOnAction(e -> ventanaListarTecnicos.close());

        //LAYOUT
        HBox hbox = new HBox(10, btnVer, btnEditar, btnEliminar, btnVolver);

        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));

        VBox layout = new VBox(10, tablaTecnicos, hbox);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        Scene escena = new Scene(layout);
        ventanaListarTecnicos.setScene(escena);
        ventanaListarTecnicos.showAndWait();
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
                        rs.getInt("turno")
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

        javafx.scene.control.TableView<modelos.Personal.TecnicoModelo> tablaResultados =
                new javafx.scene.control.TableView<>();

        javafx.collections.ObservableList<modelos.Personal.TecnicoModelo> datos =
                javafx.collections.FXCollections.observableArrayList(tecnicos);

        // Columnas
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
        javafx.stage.Stage ventanaResultados = new javafx.stage.Stage();
        ventanaResultados.setTitle("Resultados de Búsqueda - Administradores");

        javafx.scene.control.TableView<modelos.Personal.AdminModelo> tablaResultados =
                new javafx.scene.control.TableView<>();

        javafx.collections.ObservableList<modelos.Personal.AdminModelo> datos =
                javafx.collections.FXCollections.observableArrayList(admins);

        // Columnas
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

        tablaResultados.getColumns().addAll(columnaId, columnaNombre, columnaApellido, columnaTurno);
        tablaResultados.setItems(datos);

        javafx.scene.control.Button btnCerrar = new javafx.scene.control.Button("Cerrar");
        btnCerrar.setOnAction(e -> ventanaResultados.close());

        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));
        layout.getChildren().addAll(tablaResultados, btnCerrar);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        javafx.scene.Scene escena = new javafx.scene.Scene(layout, 450, 400);
        ventanaResultados.setScene(escena);
        ventanaResultados.show();
    }
	
	public void actualizarCantidadTareas(int nuevaCantidad) {
		this.cantidadTareas = nuevaCantidad;
	}



	//Getters y Setters
	
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
