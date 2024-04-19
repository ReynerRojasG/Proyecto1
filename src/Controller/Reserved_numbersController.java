package Controller;

import java.net.URL;
import java.util.Collections;
import java.util.List;
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
public class Reserved_numbersController implements Initializable {

    @FXML
    private GridPane reserved_pane;
    @FXML
    private Button return_btn; 
    private List<Integer> reservedNumbersList; // Lista de numeros reservados


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Se obtiene la instancia del controlador de numeros
         Numbers_windowController numbersController = new Numbers_windowController();
         // Se obtiene la lista de numeros reservados
         reservedNumbersList = numbersController.reservedNumbersList;
          
        // Ordenar la lista de numeros comprados de manera ascendente
        Collections.sort(reservedNumbersList);
        int row = 0;
        int column = 0;
       
         // Limpiar el GridPane 
        reserved_pane.getChildren().clear();
        reserved_pane.getColumnConstraints().clear();
        reserved_pane.getRowConstraints().clear();
       
        // Ajustar el espaciado entre los nodos del GridPane
        reserved_pane.setHgap(10); // Espacio horizontal entre nodos
        reserved_pane.setVgap(10); // Espacio vertical entre nodos
        reserved_pane.setPadding(new Insets(10)); // Relleno exterior del GridPane
        
        for (Integer number : reservedNumbersList) {
            Button button = new Button(String.valueOf(number));
            button.setPrefSize(50, 50);
             if (reservedNumbersList.contains(number)) {
                button.setStyle("-fx-background-color: yellow"); // Modifica la interfaz de los botones a amarillo (reservados)
            }
            reserved_pane.add(button, column, row);
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
