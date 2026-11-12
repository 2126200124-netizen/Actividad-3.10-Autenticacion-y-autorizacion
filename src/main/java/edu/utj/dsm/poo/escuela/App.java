package edu.utj.dsm.poo.escuela;

import edu.utj.dsm.poo.escuela.seguridad.Sesion;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage loginStage;

    @Override
    public void start(Stage stage) throws IOException {
        Platform.setImplicitExit(false);
        loginStage = stage;
        stage.setTitle("Sistema Escolar");
        stage.setScene(new Scene(cargarVista("autenticacion")));   
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> Platform.exit());             
        stage.show();
    }

    public static Parent cargarVista(String fxml) throws IOException {
        java.net.URL url = App.class.getResource(fxml + "/" + fxml + ".fxml");
        if (url == null) {
            throw new IOException("No se encontró " + fxml + "/" + fxml
                    + ".fxml dentro de resources/edu/utj/dsm/poo/escuela/");
        }
        return new FXMLLoader(url).load();
    }

    public static void abrirPrincipal() throws IOException {
        Stage principal = new Stage();
        principal.setTitle("Sistema Escolar - Principal (" + Sesion.nombreRol() + ")");
        principal.setScene(new Scene(cargarVista("principal"), 900, 700));
        principal.setOnHidden(e -> volverAlLogin());             
        loginStage.hide();
        principal.show();
    }

    
    private static void volverAlLogin() {
        Sesion.cerrar();
        try {
            loginStage.setScene(new Scene(cargarVista("autenticacion")));
        } catch (IOException ex) {
        
        }
        loginStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
