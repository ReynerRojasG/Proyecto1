/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package proyecto1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Reyner
 */
public class Proyecto1 extends Application {
    // Crear un nuevo escenario estático para la aplicación
     static Stage stage1 = new Stage(); 
    @Override
    public void start(Stage stage) throws Exception {
        // Cargar el archivo FXML de la pagina principal
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/principal_page.fxml"));
        // Crear una nueva escena con el contenido cargado
        Scene scene = new Scene(root);
        // Establecer la escena en el escenario principal
        Proyecto1.SetNext(scene, "Crear talonario");
        // Establecer la escena en el escenario principal y mostrarlo   
        stage1.setScene(scene);
        stage1.show();
    }
    
    public static void SetNext(Scene scene, String title){
        stage1.setScene(scene);
        // Establecer el titulo del escenario
        stage1.setTitle(title);
        // Mostrar el escenario
        stage1.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}
