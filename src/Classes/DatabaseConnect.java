/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Reyner
 */
// Clase que proporciona métodos estáticos para conectarse a la base de datos.
public class DatabaseConnect {
    // URL de conexión a la base de datos Oracle
    private static final String url = "jdbc:oracle:thin:@//localhost:1521/XE";
    private static final String user = "talonario"; // Nombre de usuario de la base de datos
    private static final String password = "1234"; // Contraseña de la base de datos

    public static Connection getConnection() throws SQLException {
        // Establecer y devolver la conexión utilizando la URL, usuario y contraseña proporcionados
        return DriverManager.getConnection(url, user, password); 
    }
}