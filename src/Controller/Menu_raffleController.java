package Controller;

import Classes.DatabaseConnect;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
private void insertData() {
    try (Connection conn = DatabaseConnect.getConnection()) {
        // Verificar si la cantidad de numeros es menor a 100
        int numbers = Integer.parseInt(numbers_txt.getText());
        if (numbers <= 100) {
            // Verificar si ya existe un nombre de rifa igual en la base de datos
            if (isRaffleNameUnique(name_txt.getText(), conn)) {
                try (CallableStatement addRuffle = conn.prepareCall("{call Insert_Raffle(?, ?, ?, ?, ?)}")) {
                    // Establece los parámetros del procedimiento
                    addRuffle.setString(1, name_txt.getText());
                    addRuffle.setString(2, date_pck.getValue().toString());
                    addRuffle.setInt(3, numbers);
                    addRuffle.setInt(4, Integer.parseInt(price_txt.getText()));
                    addRuffle.setString(5, prize_txt.getText());
                    // Ejecuta el procedimiento almacenado
                    addRuffle.execute();
                }
            } else {
                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText("Error");
                    alert.setContentText("El nombre de la rifa ya está en uso, crea otro distinto");
                    alert.showAndWait();
                });
            }
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("La cantidad de numeros debe ser menor a 100.");
                alert.showAndWait();
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Ingrese un numero valido para la cantidad de numeros.");
            alert.showAndWait();
        });
    }
}

    
    private boolean isRaffleNameUnique(String raffleName, Connection conn) throws SQLException {
    // Realizar una consulta para verificar si el nombre de la rifa ya existe en la base de datos
    String query = "SELECT COUNT(*) FROM Raffle WHERE R_NAME = ?";
    try (CallableStatement statement = conn.prepareCall(query)) {
        statement.setString(1, raffleName);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                // Si el recuento es 0, significa que el nombre de la rifa es único
                return resultSet.getInt(1) == 0;
            }
        }
    }
    return false;
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
