package vistas.Personal;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import modelos.Personal.AdminModelo;
import modelos.Personal.TecnicoModelo;
import vistas.Personal.FichaUsuarioVista;


public class UsuarioVista {

    private List<TecnicoModelo> listaTecnicos = new ArrayList<>();
    private List<AdminModelo> listaAdmin = new ArrayList<>();
    public FichaUsuarioVista fichaUsuarioVista = new FichaUsuarioVista(null, null, null, null, 0);

    //Constructor
    public UsuarioVista(List<TecnicoModelo> listaTecnicos) {
        this.listaTecnicos = new ArrayList<>();
    }

    public void navegar() {

    }

    public void mostrarLista(List<TecnicoModelo> tecnicos) {

    }

    public void mostrarMenuUsuarios() {

        //VENTANA
        Stage gestionUsuariosVentana = new Stage();
        gestionUsuariosVentana.setTitle("Gestion de Usuarios");

        //TITULO
        Label titulo = new Label("Gestion de Usuarios - GIST");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        //BOTONES
        Button botonGestionAdministradores = new Button("Administradores");
        Button botonGestionTecnicos = new Button("Técnicos");
        Button botonVolver = new Button("Volver");

        botonGestionAdministradores.setPrefWidth(250);
        botonGestionTecnicos.setPrefWidth(250);
        botonVolver.setPrefWidth(250);

        botonGestionAdministradores.setOnAction(e -> {
            fichaUsuarioVista.mostrarMenuAdministradores();
        });
        botonGestionTecnicos.setOnAction(e -> {
            fichaUsuarioVista.mostrarMenuTecnicos();
        });
        botonVolver.setOnAction(e -> {
            gestionUsuariosVentana.close();
        });

        //ESTILOS

        String estiloBoton =
                "-fx-background-color: white;" +
                        "-fx-text-fill: black;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-font-size: 14px;";

        botonGestionAdministradores.setStyle(estiloBoton);
        botonGestionTecnicos.setStyle(estiloBoton);
        botonVolver.setStyle(estiloBoton);

        //HOVER EFFECTS
        Button[] botones = {botonGestionAdministradores, botonGestionTecnicos, botonVolver};
        for (Button btn : botones) {
            btn.setOnMouseEntered(e -> btn.setStyle(estiloBoton + "-fx-background-color: #f5851c;"));
            btn.setOnMouseExited(e -> btn.setStyle(estiloBoton));
        }

        //LAYOUT
        javafx.scene.layout.VBox layout = new javafx.scene.layout.VBox(15);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(30));
        layout.getChildren().addAll(titulo,
                botonGestionAdministradores,
                botonGestionTecnicos,
                botonVolver);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #F7F9FB, #E4E9F0);");

        Scene escena = new Scene(layout, 400, 450);
        gestionUsuariosVentana.setScene(escena);
        gestionUsuariosVentana.show();

    }

    //GETTERS Y SETTERS
    public List<TecnicoModelo> getListaTecnicos() {
        return listaTecnicos;
    }

    public void setListaTecnicos(List<TecnicoModelo> listaTecnicos) {
        this.listaTecnicos = listaTecnicos;
    }


}
