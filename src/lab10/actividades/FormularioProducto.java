package lab10.actividades;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import javax.swing.*;
import lab10.modelo.Producto;

public class FormularioProducto extends JFrame {
    private final Producto producto = new Producto("Cuaderno", new BigDecimal("8.50"), 12, "Utiles");
    private final JTextField nombre = new JTextField(producto.getNombre());
    private final JTextField precio = new JTextField(producto.getPrecio().toPlainString());
    private final JTextField stock = new JTextField(String.valueOf(producto.getCantidadStock()));
    private final JTextField categoria = new JTextField(producto.getCategoria());
    private final JLabel resultado = new JLabel(producto.resumen());

    public FormularioProducto() {
        super("Actividad 1 - Binding de datos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel campos = new JPanel(new GridLayout(4, 2, 10, 10));
        campos.setBorder(BorderFactory.createEmptyBorder(18, 18, 12, 18));
        campos.add(new JLabel("Producto")); campos.add(nombre);
        campos.add(new JLabel("Precio (S/)")); campos.add(precio);
        campos.add(new JLabel("Cantidad en stock")); campos.add(stock);
        campos.add(new JLabel("Categoria")); campos.add(categoria);
        JButton guardar = new JButton("Actualizar Producto");
        guardar.addActionListener(e -> actualizar());
        producto.agregarObservador(e -> resultado.setText(String.valueOf(e.getNewValue())));
        JPanel inferior = new JPanel(new BorderLayout(5, 8));
        inferior.setBorder(BorderFactory.createEmptyBorder(5, 18, 18, 18));
        inferior.add(guardar, BorderLayout.NORTH);
        inferior.add(resultado, BorderLayout.SOUTH);
        add(campos, BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);
        setSize(560, 300);
        setLocationRelativeTo(null);
    }

    public void actualizar() {
        try {
            BigDecimal monto = new BigDecimal(precio.getText().trim());
            int cantidad = Integer.parseInt(stock.getText().trim());
            producto.actualizar(nombre.getText(), monto, cantidad, categoria.getText());
            System.out.println("Producto actualizado: " + producto.resumen());
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Precio o cantidad con formato incorrecto.");
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormularioProducto().setVisible(true));
    }
}
