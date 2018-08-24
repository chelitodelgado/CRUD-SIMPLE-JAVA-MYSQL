package crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author chelo
 */
public class daoProducto {

    Conexion c;

    public daoProducto() {
        c = new Conexion();

    }

    public boolean create(Producto p) {
        String sql = "INSERT INTO producto (nombre,precio,cantidad,proveedor) VALUES(?,?,?,?);";
        try {
            PreparedStatement ps = c.conectar().prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getPrecio());
            ps.setInt(3, p.getCantidad());
            ps.setString(4, p.getProveedor());
            ps.execute();
            ps.close();
            ps = null;
            c.desconectar();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error en el registro de datos");
            return false;
        }

    }

    public ArrayList<Producto> read(){
        ArrayList<Producto> lista = new ArrayList<Producto>();
        String sql="SELECT * FROM producto";
        try {
            PreparedStatement ps =c.conectar().prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Producto p = new Producto();
                p.setId_Producto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getInt("precio"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setProveedor(rs.getString("proveedor"));
                lista.add(p);
            }
            ps.close();
            ps=null;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Fallo se consulta ");
        }
        return lista;
    }
    
    public boolean delete(int id){
        try {
            String sql="DELETE FROM producto WHERE id_producto=?";
            PreparedStatement ps = c.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();;
            ps=null;
            c.desconectar(); 
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    public boolean update(Producto p){
        String sql = "UPDATE producto SET nombre=?,precio=?,cantidad=?,proveedor=? WHERE id_producto=?";
        try {
            PreparedStatement ps = c.conectar().prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setInt(2, p.getPrecio());
            ps.setInt(3, p.getCantidad());
            ps.setString(4, p.getProveedor());
            ps.setInt(5, p.getId_Producto());
            ps.execute();
            ps.close();
            ps = null;
            c.desconectar();
            return true;
        } catch (SQLException ex) {
            System.out.println("Error en el registro de datos");
            return false;
        }
    }
    
    public Producto read(int id){
        Producto p = new Producto();
        
        try {
            String sql="SELECT * FROM producto WHERE id_producto=?";
            PreparedStatement ps =c.conectar().prepareCall(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                p.setId_Producto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getInt("precio"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setProveedor(rs.getString("proveedor"));
            }
            ps.close();
            ps=null;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Fallo se consulta ");
        }
        return p;
    }
    
}
