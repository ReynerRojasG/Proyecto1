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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import oracle.jdbc.OracleTypes;
import proyecto1.Proyecto1;

/**
 * FXML Controller class
 *
 * @author Reyner
 */
public class Menu_raffleController implements Initializable {
    // Espacios para llenar los datos correspondientes de los talonarios
    @FXML
    private TextField name_txt;
    @FXML
    private TextField numbers_txt;
    @FXML
    private Label name_lb;
    @FXML
    private Label date_lb;
    @FXML
    private Label numbers_lb;
    @FXML
    private Label price_lb;
    @FXML
    private DatePicker date_pck;
    @FXML
    private TextField price_txt;
    @FXML
    private Button create_btn;
    @FXML
    private Label prize_lb;
    @FXML
    private TextField prize_txt;
    @FXML
    private Button return_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
    }    

    // Metodo para insertar los datos de la rifa en la base de datos
    private void insertData(){
    try (Connection conn = DatabaseConnect.getConnection()) {
            try (CallableStatement addRuffle = conn.prepareCall("{call Insert_Raffle(?, ?, ?, ?, ?)}")) {
                // Establece los parametros del procedure 
                addRuffle.setString(1, name_txt.getText());
                addRuffle.setString(2, date_pck.getValue().toString());
                addRuffle.setInt(3, Integer.parseInt(numbers_txt.getText()));
                addRuffle.setInt(4, Integer.parseInt(price_txt.getText()));
                addRuffle.setString(5, prize_txt.getText());
                // Ejecuta el procedimiento almacenado
                addRuffle.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Metodo para manejar el evento de clic en el boton "Crear Rifa"
    @FXML
    private void createOption(ActionEvent event) {
         try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/principal_page.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Menu Principal");
                // Se insertan los datos en la base cuando se crea
                insertData();
                
            } catch (Exception e) {
                System.out.println("No se pudo cargar");
            }
    }   
    
    // Metodo para manejar el evento de clic en el boton "Regresar al menu principal"
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
