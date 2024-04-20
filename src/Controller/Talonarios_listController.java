package Controller;


import Classes.DatabaseConnect;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import oracle.jdbc.OracleTypes;
import proyecto1.Proyecto1;

/**
 * FXML Controller class
 *
 * @author Reyner
 */
public class Talonarios_listController implements Initializable {

    @FXML
    private Button create_btn; 
    @FXML
    private ListView<String> raffle_lv = new ListView<>();  // ListView para mostrar los nombres de las rifas 
    @FXML
    private Label lblShow;
    @FXML
    private Label lblRaffleShow;
    public static int numbersQuantity;   // Cantidad de numeros en la rifa seleccionada
    public static String selectedRaffle; // Nombre de la rifa seleccionada
    @FXML
    private Button return_btn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
           // Llama al metodo para obtener y mostrar los nombres de las rifas
           getRaffleName();     
    }    
    
    // Cambia a la ventana de numeros al dar enter
    @FXML
    private void EnterAction(ActionEvent event) {
       String nameSelected = raffle_lv.getSelectionModel().getSelectedItem(); // Obtiene el nombre
       // Verifica si hay una rifa seleccionada para cambiar la escena
        if(nameSelected != null){        
            try {  
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/numbers_window.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Numeros");                       
            } catch (Exception e) {
                System.out.println("No se pudo cargar");
            }
        }
    }           
    
    // Metodo para obtener y mostrar los nombres de las rifas desde la base de datos
    public void getRaffleName(){     
        try (Connection conn = DatabaseConnect.getConnection()) {
            try (CallableStatement showRaffleName = conn.prepareCall("{call Get_Raffle_Name(?)}")) {
                showRaffleName.registerOutParameter(1, OracleTypes.CURSOR); // Registro del parametro de salida
                showRaffleName.execute();
                ResultSet rs = (ResultSet) showRaffleName.getObject(1);

                while(rs.next()){
                String raffleName = rs.getString("R_NAME");
                raffle_lv.getItems().add(raffleName); // Agrega el nombre de la rifa al ListView
                raffle_lv.refresh(); // Actualiza la visualizacion del ListView
                }           
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
  }     
    
     // Metodo para manejar el evento de clic en un elemento de la lista de rifas
    @FXML
    private void showRaffle(MouseEvent event) {
     selectedRaffle = raffle_lv.getSelectionModel().getSelectedItem(); // Obtiene el nombre de la rifa seleccionada
        if (selectedRaffle != null) {
           showRaffleQuantity(selectedRaffle); // Llama al metodo para mostrar la cantidad de la rifa seleccionada
         }
    }

    // Metodo para obtener y mostrar la cantidad de numeros de la rifa seleccionada
    private void showRaffleQuantity(String selectedRaffle) {
        if (selectedRaffle != null) {
            try (Connection conn = DatabaseConnect.getConnection()) {
                try (CallableStatement numTotal = conn.prepareCall("{call Get_Raffle_Quantity(?, ?)}")) {
                    numTotal.setString(1, selectedRaffle);
                    numTotal.registerOutParameter(2, Types.INTEGER); // Registro del parámetro de salida
                    numTotal.execute();      
                 
                  numbersQuantity = numTotal.getInt(2); // Obtiene la cantidad de numeros de la rifa
               }
             } catch (SQLException e) {
                e.printStackTrace();
             }  
         } 
    }     
    
    // Se cambia la ventana a la "Pagina pirncipal"
    @FXML
    private void returnNumbers(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/principal_page.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Menu Principal");
            } catch (Exception e) {
                System.out.println("No se pudo cargar");
            }
    }
}

                                                                