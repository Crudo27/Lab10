package lab10.mvc;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventarioVista extends JFrame {
    final JTextField nombre = new JTextField();
    final JTextField precio = new JTextField();
    final JTextField stock = new JTextField();
    final JTextField categoria = new JTextField();
    final JButton guardar = new JButton("Guardar");
    final JButton nuevo = new JButton("Nuevo");
    final JButton eliminar = new JButton("Eliminar");
    final JButton actualizar = new JButton("Actualizar tabla");
    final JLabel estado = new JLabel("Inventario listo");
    final DefaultTableModel datos = new DefaultTableModel(new String[]{"ID","Producto","Precio (S/)","Stock","Categoria"},0) {
        @Override public boolean isCellEditable(int fila,int columna) { return false; }
    };
    final JTable tabla = new JTable(datos);

    public InventarioVista() {
        super("Inventario de tienda - MVC y JDBC");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel formulario = new JPanel(new GridLayout(4,2,8,8));
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del producto"));
        formulario.add(new JLabel("Nombre"));formulario.add(nombre);
        formulario.add(new JLabel("Precio"));formulario.add(precio);
        formulario.add(new JLabel("Cantidad"));formulario.add(stock);
        formulario.add(new JLabel("Categoria"));formulario.add(categoria);
        JPanel acciones=new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.add(guardar);acciones.add(nuevo);acciones.add(eliminar);acciones.add(actualizar);
        JPanel superior=new JPanel(new BorderLayout());
        superior.setBorder(BorderFactory.createEmptyBorder(8,12,0,12));
        superior.add(formulario,BorderLayout.CENTER);
        superior.add(acciones,BorderLayout.SOUTH);
        tabla.setRowHeight(27);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(superior,BorderLayout.NORTH);
        add(new JScrollPane(tabla),BorderLayout.CENTER);
        add(estado,BorderLayout.SOUTH);
        setSize(850,560);
        setLocationRelativeTo(null);
    }

    void aviso(String mensaje) { estado.setText("  " + mensaje); }
}
