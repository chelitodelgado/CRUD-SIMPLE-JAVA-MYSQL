package crud;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author chelo
 */
public class Controlador implements ActionListener, MouseListener {

    Vista v;
    Producto p, p1;
    daoProducto dao;
    int id = 0;

    public static void main(String[] arsg) {
        Controlador c = new Controlador();

    }

    public Controlador() {
        v = new Vista();

        dao = new daoProducto();
        p1 = new Producto();
        v.btnAgregar.addActionListener(this);
        v.btnEliminar.addActionListener(this);
        v.btnGuardar.addActionListener(this);
        v.btnLimpiar.addActionListener(this);
        v.btnPDF.addActionListener(this);
        v.tblDato.addMouseListener(this);
        refrescarTabla();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == v.btnAgregar) { // BOTON QUE INSERTA EL REGISTRO A LA TABLA
            if (v.txtNombre.getText().equals("") || v.spnPrecio.getValue().equals(0) || v.spnCantidad.getValue().equals(0) || v.cboProveedor.getSelectedItem().equals("Seleccione")) {//codigo para agregar el registro
                JOptionPane.showMessageDialog(this.v, "Rellene todos los campos");

            } else {
                p = new Producto();
                p.setNombre(v.txtNombre.getText());
                p.setPrecio(Integer.parseInt(v.spnPrecio.getValue().toString()));
                p.setCantidad(Integer.parseInt(v.spnCantidad.getValue().toString()));
                p.setProveedor(v.cboProveedor.getSelectedItem().toString());
                if (!dao.create(p)) {
                    JOptionPane.showMessageDialog(this.v, "Registro Completado sin exito!!!");
                } else {
                    JOptionPane.showMessageDialog(this.v, "Registro Insertado");
                }
                refrescarTabla();
                limpiarCampos();
            }
        }
        if (e.getSource() == v.btnEliminar) { //BOTON QUE ELIMINA UNREGITRO DE LA TABLA
            int x = JOptionPane.showConfirmDialog(this.v, "Estas de seguro de eliminar este campo");
            if (x == 0 && id > 0) {
                if (!dao.delete(id)) {
                    JOptionPane.showMessageDialog(this.v, "Elemento cancelado ");
                }
            }
            refrescarTabla();
        }
        if (e.getSource() == v.btnGuardar) { //BOTON QUE ALMACENA O ACTUALIZA UN REGISTRO
            if (v.txtNombre.getText().equals("") || v.spnPrecio.getValue().equals(0) || v.spnCantidad.getValue().equals(0) || v.cboProveedor.getSelectedItem().equals("Seleccione")) {//codigo para agregar el registro
                JOptionPane.showMessageDialog(this.v, "Rellene todos los campos");

            } else {
                p1.setNombre(v.txtNombre.getText());
                p1.setPrecio(Integer.parseInt(v.spnPrecio.getValue().toString()));
                p1.setCantidad(Integer.parseInt(v.spnCantidad.getValue().toString()));
                p1.setProveedor(v.cboProveedor.getSelectedItem().toString());
            }
            if (!dao.update(p1)) {
                JOptionPane.showMessageDialog(this.v, "No se pudo editar");
            }
            refrescarTabla();
        }
        if (e.getSource() == v.btnLimpiar) { //BOTON ELIMINA LO ESCRITO EN LOS COMPONENTES
            limpiarCampos();
        }
        if (e.getSource() == v.btnPDF) { // BOTON GENERADOR DE PDF DE LOS REGISTRO DE LA TABLA

            while (v.model.getRowCount() > 0) {
                v.model.removeRow(0);
            }
            ArrayList<Producto> lista = dao.read();
            for (Producto p : lista) {
                Object item[] = new Object[5];
                item[0] = p.getId_Producto();
                item[1] = p.getNombre();
                item[2] = p.getPrecio();
                item[3] = p.getCantidad();
                item[4] = p.getProveedor();
                v.model.addRow(item);
            }
            v.tblDato.setModel(v.model);

            SimpleDateFormat formato = new SimpleDateFormat("\t \t \t \t \t dd/MMMMMMMM/yyyy h:mm a");
            String cadenaFecha = formato.format(new java.util.Date());

            Document documento = new Document(PageSize.LETTER, 50, 50, 50, 50);
            Font tnr5 = new Font(Font.TIMES_ROMAN, 18, Font.BOLD, Color.BLUE);
            Font tnr = new Font(Font.TIMES_ROMAN, 11, Font.BOLD, Color.BLUE);
            Font tnr2 = new Font(Font.TIMES_ROMAN, 11, Font.UNDERLINE, Color.GREEN);
            Font tnr3 = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.RED);
            Font tnr4 = new Font(Font.TIMES_ROMAN, 10, Font.ITALIC, Color.RED);

            try {

                FileOutputStream archivo;
                File file = new File("/home/nimeria/NetBeansProjects/CRUD_JAVA/src/pdf/reporte.pdf");
                archivo = new FileOutputStream(file);

                PdfWriter.getInstance(documento, archivo);
                documento.open();
                Image img = Image.getInstance("/home/nimeria/NetBeansProjects/CRUD_JAVA/src/images/general.png");
                img.setAlignment(Element.ALIGN_CENTER);
                img.scaleToFit(100, 100);
                img.scalePercent(20);
                documento.add(img);

                documento.add(new Paragraph("   \t\t\t\t\t\t                     " + cadenaFecha + "\n", tnr4));
                documento.add(new Paragraph("\n             INFORMACIÓN DEL VENDEDOR \n\n", tnr5));
                PdfPTable tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                PdfPCell c1 = new PdfPCell(new Phrase("ID", tnr));
                PdfPCell c2 = new PdfPCell(new Phrase("NOMBRE", tnr));
                PdfPCell c3 = new PdfPCell(new Phrase("PRECIO", tnr));
                PdfPCell c4 = new PdfPCell(new Phrase("CANTIDAD", tnr));
                PdfPCell c5 = new PdfPCell(new Phrase("PROVEEDOR", tnr));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(Color.CYAN);
                c2.setBackgroundColor(Color.CYAN);
                c3.setBackgroundColor(Color.CYAN);
                c4.setBackgroundColor(Color.CYAN);
                c5.setBackgroundColor(Color.CYAN);
                tabla.addCell(c1);
                tabla.addCell(c2);
                tabla.addCell(c3);
                tabla.addCell(c4);
                tabla.addCell(c5);
                //AGREGAR LOS REGISTROS
                for (Producto p : lista) {
                    tabla.addCell("" + p.getId_Producto());
                    tabla.addCell("" + p.getNombre());
                    tabla.addCell("" + p.getPrecio());
                    tabla.addCell("" + p.getPrecio());
                    tabla.addCell("" + p.getProveedor());
                }
                documento.add(tabla);

                documento.close();
                archivo.close();
                Desktop.getDesktop().open(file);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == v.tblDato) {
            int fila = v.tblDato.getSelectedRow();
            id = Integer.parseInt(v.tblDato.getValueAt(fila, 0).toString());
            p1 = dao.read(id);

            v.lblIdproducto.setText("" + p1.getId_Producto());
            v.txtNombre.setText(p1.getNombre());
            v.spnPrecio.setValue(p1.getPrecio());
            v.spnCantidad.setValue(p1.getCantidad());
            v.cboProveedor.setSelectedItem(p1.getProveedor());
        }
    }

    public void refrescarTabla() {
        while (v.model.getRowCount() > 0) {
            v.model.removeRow(0);
        }
        ArrayList<Producto> lista = dao.read();
        for (Producto p : lista) {
            Object item[] = new Object[5];
            item[0] = p.getId_Producto();
            item[1] = p.getNombre();
            item[2] = p.getPrecio();
            item[3] = p.getCantidad();
            item[4] = p.getProveedor();
            v.model.addRow(item);
        }
        v.tblDato.setModel(v.model);
    }

    public void limpiarCampos() {
        v.txtNombre.setText("");
        v.spnCantidad.setValue(0);
        v.spnPrecio.setValue(0);
        v.cboProveedor.setSelectedItem("Seleccione");
        v.lblIdproducto.setText("");
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
