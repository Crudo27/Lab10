package lab10.mvc;

import java.sql.SQLException;
import javax.swing.*;

public class InventarioApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                InventarioModelo modelo = new InventarioModelo("jdbc:hsqldb:file:datos/inventario");
                InventarioVista vista = new InventarioVista();
                new InventarioControlador(modelo,vista);
                vista.setVisible(true);
            } catch(SQLException ex) {
                JOptionPane.showMessageDialog(null,"No se pudo iniciar la base de datos: " + ex.getMessage());
            }
        });
    }
}
