package Controller;

import Classes.DatabaseConnect;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import proyecto1.Proyecto1;
import Controller.Talonarios_listController;
import java.awt.BorderLayout;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import oracle.jdbc.OracleTypes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.scene.Node;

/**
 * FXML Controller class
 *
 * @author Reyner
 */
public class Numbers_windowController implements Initializable {

    @FXML
    private BorderPane background;
    @FXML
    private AnchorPane principal_pane;
    @FXML
    private Label comunicate_lb;
    @FXML
    private GridPane gridpane;
    @FXML
    private TextField buyer_txt;
    @FXML
    private ChoiceBox<String> paymentChoice_bx;
    @FXML
    private Button buy_btn;
    @FXML
    private Button reserved_btn;
    @FXML
    private Button generateWinner_btn;
    // Opciones de metodo de pago
    private String[] payment = {"Efectivo", "Sinpe", "Transferencia"};
    // Numero de boton clickeado
    private String buttonCLicked;
    // Opciones de metodo de pago
    private int state;
    // Nombre de la rifa seleccionada
    public static String select_name;
    // Listas para almacenar los numeros comprados, reservados y disponibles
    public static List<Integer> purchasedNumbersList = new ArrayList<>();
    public static List<Integer> reservedNumbersList = new ArrayList<>();
    public static List<Integer> availableNumbersList = new ArrayList<>();
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        // Obtener la cantidad de numeros para la rifa seleccionada
        int quantity = Talonarios_listController.numbersQuantity;
        // Obtener el nombre de la rifa seleccionada
        select_name = Talonarios_listController.selectedRaffle;
        // Agregar opciones de pago al ChoiceBox
        paymentChoice_bx.getItems().addAll(payment);
        // Obtener los numeros comprados y reservados
        getPurchasedNumbers(select_name);
        getReservedNumbers(select_name);
        // Generar los numeros en la interfaz grafica
        numbersGenerate(quantity);
        // Actualizar la lista de numeros disponibles
        updateAvailableNumbersList(quantity);
    }    

    @FXML
    private void show_numbers(MouseEvent event) {
        // Mostrar la seccion de numeros
        background.setCenter(principal_pane);
    }
    // Mostrar la pagina de numeros disponibles
    @FXML
    private void show_available(MouseEvent event) {
           try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/availableNumbers_page.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Disponibles");
                
            } catch (Exception e) {
                System.out.println("No se pudo cargar");
            }
    }
    // Mostrar la pagina de numeros reservados
    @FXML
    private void show_reserved(MouseEvent event) {
           try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/reservedNumbers_page.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Reservados");
                
            } catch (Exception e) {
                System.out.println("No se pudo cargar");
            }
    }
    // Mostrar la pagina de numeros pagados o comprados
    @FXML
    private void show_paid(MouseEvent event) {
           try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/purchasedNumbers_page.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Comprados");
                
            } catch (Exception e) {
                System.out.println("No se pudo cargar");
            }
    }
    // Cambia la ventana a la "Pagina principal"
    @FXML
    private void return_btn(MouseEvent event) {
           try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/principal_page.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Crear talonario");
                
            } catch (Exception e) {
                System.out.println("No se pudo cargar");
            }
    }
    // Metodo para obtener los numeros comprados de una rifa
      public void getPurchasedNumbers(String raffleName) {
        purchasedNumbersList.clear();
          try (Connection conn = DatabaseConnect.getConnection()) {
            try (CallableStatement cs = conn.prepareCall("{ call Get_Purchased_Numbers(?, ?) }")) {
                cs.setString(1, select_name);
                // Registrar el parametro de salida para el cursor
                cs.registerOutParameter(2, OracleTypes.CURSOR);
                // Ejecutar la llamada al procedimiento almacenado
                cs.execute();
                // Obtener el cursor de resultados
                try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                    // Procesar los resultados
                    while (rs.next()) {
                        int purchasedNumber = rs.getInt("RAFFLE_NUMBER");  // Obtiene el numero                     
                        purchasedNumbersList.add(purchasedNumber); // Los carga en la lista de comprados
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Metodo para obtener los numeros reservados de una rifa
      public void getReservedNumbers(String raffleName) {
       reservedNumbersList.clear();
        try (Connection conn = DatabaseConnect.getConnection()) {
            try (CallableStatement cs = conn.prepareCall("{ call Get_Reserved_Numbers(?, ?) }")) {
                cs.setString(1, select_name);
                cs.registerOutParameter(2, OracleTypes.CURSOR);
                cs.execute();
                // Obtener el cursor de resultados
                try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                    // Procesar los resultados
                    while (rs.next()) {
                        int reservedNumber = rs.getInt("RAFFLE_NUMBER"); // Obtiene el numero 
                        reservedNumbersList.add(reservedNumber); // Los carga en la lista de reservados
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      
    public static void updateAvailableNumbersList(int numbersQuantity) {
        availableNumbersList.clear(); // Limpia la lista de numeros disponibles
    
    // Agregr todos los numeros del 1 al total de numeros disponibles inicialmente
        for (int i = 1; i <= numbersQuantity; i++) {
            availableNumbersList.add(i);
         }
    
    // Quita de la lista los numeros que estan comprados o reservados
    availableNumbersList.removeAll(purchasedNumbersList);
    availableNumbersList.removeAll(reservedNumbersList);
}

    private void numbersGenerate(int numbersQuantity){
         // Limpiar el GridPane 
        gridpane.getChildren().clear();
        gridpane.getColumnConstraints().clear();
        gridpane.getRowConstraints().clear();
       
        // Ajustar el espaciado entre los nodos del GridPane
        gridpane.setHgap(10); // Espacio horizontal entre nodos
        gridpane.setVgap(10); // Espacio vertical entre nodos
        gridpane.setPadding(new Insets(10)); // Relleno exterior del GridPane
        // Ajustar las columnas del GridPane
        for(int i = 0; i < 10; i++){
         ColumnConstraints colConstraints = new ColumnConstraints();
         colConstraints.setPercentWidth(100/10);
         gridpane.getColumnConstraints().add(colConstraints);
        }
        // Calcular el numero de filas necesario
        int rowsNumber = (numbersQuantity + 9)/10;
        // Ajustar las filas del GridPane
        for(int i = 0; i < rowsNumber; i++){
           RowConstraints rowConstraints = new RowConstraints();
           rowConstraints.setPercentHeight(100/ rowsNumber);
           gridpane.getRowConstraints().add(rowConstraints);
        }
        
        //Llenar el Gridpane con botones
        int number = 1;
        for(int i = 0; i < numbersQuantity; i++){
        Button button = new Button(String.valueOf(number));
        button.setPrefSize(50, 50);
        button.setOnAction(event -> {
        buttonCLicked = button.getText(); // Obtiene el texto del boton cuando es clickeado
        });
        
        int column = i % 10; // Columna en la que se debe agregar el botón
        int row = i / 10; // Fila en la que se debe agregar el botón   
        gridpane.add(button, column, row); // Agregar el botón al GridPane
        number++;
        }
        gridpane.requestLayout();
        updateButtonAppearance();
    }   

    // Metodo para insertar los numeros comprados o reservados en la base de datos
    private void insertNumbersToDB(){
    try (Connection conn = DatabaseConnect.getConnection()) {
            try (CallableStatement addNumber = conn.prepareCall("{call Insert_Numbers(?, ?, ?, ?, ?)}")) {
                addNumber.setString(1, select_name);
                addNumber.setString(2, buyer_txt.getText());
                addNumber.setInt(3, Integer.parseInt(buttonCLicked));
                addNumber.setInt(4, state);
                addNumber.setString(5, paymentChoice_bx.getValue().toString());
                addNumber.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Se ejecuta cuando se le da click al boton comprar
    @FXML
    private void buyNumber(ActionEvent event) {
        state = 1; // Estado de compra (este caso 1 equivale a comprado)
        insertNumbersToDB(); // Insertar numero comprado en la base de datos
        updateButtonAppearance(); // Actualizar apariencia de botones   
        updatePage(); // Actualizar la pagina
    }

    @FXML
    private void reserveNumber(ActionEvent event) {
        state = 2; // Estado de compra (este caso 2 equivale a reservado)
        insertNumbersToDB(); // Insertar numero comprado en la base de datos
        updateButtonAppearance(); // Actualizar apariencia de botones   
        updatePage(); // Actualizar la pagina
    }
    // Metodo para imprimir los numeros disponibles
    private void printAvailableNumbersList() {
    for (Integer number : availableNumbersList) {
        System.out.println(number);
    }
}
     // Metodo para actualizar la apariencia de los botones segun el estado
    private void updateButtonAppearance() {
        for (Node node : gridpane.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                int buttonNumber = Integer.parseInt(button.getText());
                if (purchasedNumbersList.contains(buttonNumber)) {
                    button.setStyle("-fx-background-color: green"); // Comprado
                }else if (reservedNumbersList.contains(buttonNumber)) {
                button.setStyle("-fx-background-color: yellow"); // Reservado
                }
            }
        }
    } 
 
    // Metodo para actualizar los datos y la apariencia de la pagina
    private void updatePage() {
        getPurchasedNumbers(select_name); // Actualiza numeros comprados
        getReservedNumbers(select_name); // Actualiza numeros reservados
        numbersGenerate(Talonarios_listController.numbersQuantity); // Regenera numeros
        updateAvailableNumbersList(Talonarios_listController.numbersQuantity); // Actualiza numeros disponibles
        printAvailableNumbersList(); // Imprimir numeros disponibles
    }
    // Muestra la pagina para el ganador
    @FXML
    private void printWinner(ActionEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/winner_page.fxml"));
                Scene scene = new Scene(root);
                Proyecto1.SetNext(scene, "Ganador");
            } catch (Exception e) {
                System.out.println("No se pudo cargar");
            }
    }
  
}
