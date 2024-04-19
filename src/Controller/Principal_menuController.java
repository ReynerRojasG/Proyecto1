package Controller;

import Controller.Talonarios_listController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import proyecto1.Proyecto1;

/**
 * FXML Controller class
 *
 * @author Reyner
 */
public class Principal_menuController implements Initializable {
    // Botones principales para crear y ver talonarios
    @FXML
    private Button createRaffle_btn;
    @FXML
    private Button viewRaffle_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         
    }    


    @FXML
    private void createRafflePane(ActionEvent event) {
           try {
               // Carga la interfaz de usuario de creacion de rifa
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/menu_raffle.fxml"));
                // Crea una nueva escena con la interfaz de usuario cargada
                Scene scene = new Scene(root);
                // Establece la escena y un nombre para la ventana
                Proyecto1.SetNext(scene, "Crear Rifa");
                                
            } catch (Exception e) {
                // Maneja cualquier error
                System.out.println("No se pudo cargar");
            }
    }
    
    // Cambia a la ventana donde esta la lista de talonarios
    @FXML
    private void viewRafflePane(ActionEvent event) {
          try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/talonarios_list.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Lista de talonarios");
          
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
}
