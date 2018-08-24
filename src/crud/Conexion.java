package crud;

import com.mysql.jdbc.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.*;

/**
 *
 * @author chelo
 */
public class Conexion {

    Connection cx;
    String bd = "crudjava";
    String url = "jdbc:mysql://localhost:3306/" + bd;
    String user = "root";
    String pass = "root";

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cx = (Connection) DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión con exito");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error en la conexión " + ex);
        }
        return cx;
    }

    public void desconectar() {
        try {
            cx.close();
            System.out.println("Conexión Cerrada");
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexión " + ex);
        }
    }

}
