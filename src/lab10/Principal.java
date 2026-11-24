package lab10;

import java.awt.*;
import javax.swing.*;
import lab10.actividades.*;
import lab10.mvc.InventarioApp;

public class Principal extends JFrame {
    public Principal() {
        super("Laboratorio 10 - Java Swing Avanzado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contenido = new JPanel(new GridLayout(5,1,8,12));
        contenido.setBorder(BorderFactory.createEmptyBorder(25,35,25,35));
        agregar(contenido,"1. Binding de producto",() -> new FormularioProducto().setVisible(true));
        agregar(contenido,"2. Grafico de temperaturas",() -> new GraficoTemperaturas().setVisible(true));
        agregar(contenido,"3. Efectos WAV",() -> new EfectosSonido().setVisible(true));
        agregar(contenido,"4. Reproductor WAV",() -> new ReproductorMusica().setVisible(true));
        agregar(contenido,"5. Inventario MVC y base de datos",() -> InventarioApp.main(new String[0]));
        add(contenido,BorderLayout.CENTER);
        setSize(485,410);
        setLocationRelativeTo(null);
    }

    private void agregar(JPanel panel,String texto,Runnable accion) {
        JButton boton = new JButton(texto);
        boton.addActionListener(e -> accion.run());
        panel.add(boton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); }
            catch(Exception ex) { System.out.println("Tema visual predeterminado"); }
            new Principal().setVisible(true);
        });
    }
}
