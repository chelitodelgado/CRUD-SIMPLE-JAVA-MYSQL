package crud;

/**
 *
 * @author chelo
 */
public class Producto {

    int id_Producto;
    String nombre;
    int Precio;
    int cantidad;
    String Proveedor;

    public Producto() {
        System.out.println("Se creo procuto");
    }

    public Producto(int id_Producto, String nombre, int Precio, int cantidad, String Proveedor) {
        this.id_Producto = id_Producto;
        this.nombre = nombre;
        this.Precio = Precio;
        this.cantidad = cantidad;
        this.Proveedor = Proveedor;
    }

    public Producto(String nombre, int Precio, int cantidad, String Proveedor) {
        this.nombre = nombre;
        this.Precio = Precio;
        this.cantidad = cantidad;
        this.Proveedor = Proveedor;
    }

    public int getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(int id_Producto) {
        this.id_Producto = id_Producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int Precio) {
        this.Precio = Precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String Proveedor) {
        this.Proveedor = Proveedor;
    }

    @Override
    public String toString() {
        return "Producto{" + "id_Producto=" + id_Producto + ", nombre=" + nombre + ", Precio=" + Precio + ", cantidad=" + cantidad + ", Proveedor=" + Proveedor + '}';
    }

}
