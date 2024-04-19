/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import proyecto1.Proyecto1;

/**
 * FXML Controller class
 *
 * @author Reyner
 */
public class Available_numbersController implements Initializable {

    @FXML
    private GridPane available_pane;
    @FXML
    private Button return_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Se obtiene la instancia del controlador de numeros
        Numbers_windowController numbersController = new Numbers_windowController();
        // Se actualiza la lista de numeros disponibles
        numbersController.updateAvailableNumbersList(Talonarios_listController.numbersQuantity);
        int row = 0;
        int column = 0;
       
         // Limpiar el GridPane 
        available_pane.getChildren().clear();
        available_pane.getColumnConstraints().clear();
        available_pane.getRowConstraints().clear();
       
        // Ajustar el espaciado entre los nodos del GridPane
        available_pane.setHgap(10); // Espacio horizontal entre nodos
        available_pane.setVgap(10); // Espacio vertical entre nodos
        available_pane.setPadding(new Insets(10)); // Relleno exterior del GridPane
        
        for (Integer number : numbersController.availableNumbersList) {
            Button button = new Button(String.valueOf(number));
            button.setPrefSize(50, 50);
            available_pane.add(button, column, row);
            column++;
            // Si se llega al final de una fila, se pasa a la siguiente fila
            if (column == 10) {
                column = 0;
                row++;
            }
        }
    }     
    // Regresa la ventana a la "Pagina numeros"
    @FXML
    private void returnNumbers(ActionEvent event) {
         try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/numbers_window.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Numeros");
            } catch (Exception e) {
                System.out.println("No se pudo cargar");
            }
    }
} 

