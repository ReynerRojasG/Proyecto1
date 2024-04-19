package Controller;

import Classes.DatabaseConnect;
import static Controller.Numbers_windowController.select_name;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import oracle.jdbc.OracleTypes;
import javafx.scene.Parent;
import javafx.scene.Scene;
import proyecto1.Proyecto1;



/**
 * FXML Controller class
 *
 * @author Reyner
 */
public class Winner_dataController implements Initializable {

    @FXML
    private Button return_btn;
    @FXML
    private Label winRaffleName_lbl;
    @FXML
    private Label winName_lbl;
    @FXML
    private Label winNumber_lbl;
    @FXML
    private Label winPayment_lbl;
   
    @FXML
    private Button winnerGenerate_btn;
     private List<Integer> purchasedNumbersList; // Lista de numeros comprados
    private String established_raffleName; // Nombre establecido de la rifa
    private String established_buyer; // Comprador establecido
    private int winningNumber; // Numero ganador
    private int established_state; // Estado establecido
    private String established_payment; // Método de pago establecido
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Se obtiene la instancia del controlador de numeros
         Numbers_windowController numbersController = new Numbers_windowController();
        // Se obtiene la lista de numeros comprados
         purchasedNumbersList = numbersController.purchasedNumbersList;
    }    
     public int selectWinner(List<Integer> purchasedNumbersList) {
        // Mezclar aleatoriamente la lista de numeros comprados
        Collections.shuffle(purchasedNumbersList);
        
        // Obtener un numero aleatorio de la lista mezclada
        Random random = new Random();
        int index = random.nextInt(purchasedNumbersList.size());
        int winnerNumber = purchasedNumbersList.get(index);
        
        return winnerNumber;
    }
      // Metodo para obtener los datos del ganador de la rifa.
    private void getWinnerData() {
        int winnerNumber = selectWinner(purchasedNumbersList);
        // Llamar al procedimiento almacenado para obtener los datos del numero ganador
        try (Connection conn = DatabaseConnect.getConnection()) {
            try (CallableStatement cs = conn.prepareCall("{ call Get_Winning_Number_Info(?, ?, ?) }")) {
                cs.setString(1, select_name); // Nombre de la rifa
                cs.setInt(2, winnerNumber);   // Numero ganador
                cs.registerOutParameter(3, OracleTypes.CURSOR);
                cs.execute();
            
                 // Obtener el resultado del procedimiento
                try (ResultSet rs = (ResultSet) cs.getObject(3)) {
                    while (rs.next()) {
                         // Obtener los datos del resultado
                        established_raffleName = rs.getString("RAFFLE_NAME");
                        established_buyer = rs.getString("BUYER");
                        winningNumber = rs.getInt("RAFFLE_NUMBER");
                        established_state = rs.getInt("RAFFLE_STATE");
                        established_payment = rs.getString("PAYMENT");
                        // Establecer la información del ganador en la vista
                        setWinnerInfo(established_raffleName, established_buyer, winningNumber, established_payment);        
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    // Imprime los datos en pantalla 
    public void setWinnerInfo(String raffleName, String buyer, int winningNumber, String payment) {
      winRaffleName_lbl.setText(raffleName); // Establecer el nombre de la rifa de la base de datos
      winName_lbl.setText(buyer); // Establecer el nombre del comprador
      winNumber_lbl.setText(String.valueOf(winningNumber)); // Establecer el número ganador
      winPayment_lbl.setText(payment); // Establecer el método de pago
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
    // Maneja el evento de clic en el botón para generar un nuevo ganador.
    @FXML
    private void generateWinner(ActionEvent event) {
        getWinnerData();
    }
    
}
