package lab10.actividades;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;
import javax.swing.*;

public class GraficoTemperaturas extends JFrame {
    private final String[] dias = {"Lun", "Mar", "Mie", "Jue", "Vie", "Sab", "Dom"};
    private final JTextField[] entradas = new JTextField[7];
    private final PanelGrafico grafico = new PanelGrafico();
    private final JLabel mensaje = new JLabel("Ingresa las temperaturas y presiona Mostrar Grafico.");

    public GraficoTemperaturas() {
        super("Actividad 2 - Temperaturas de una semana");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel arriba = new JPanel(new GridLayout(2, 7, 8, 4));
        arriba.setBorder(BorderFactory.createEmptyBorder(12, 12, 10, 12));
        for(String dia : dias) arriba.add(new JLabel(dia, SwingConstants.CENTER));
        for(int i = 0; i < 7; i++) {
            entradas[i] = new JTextField(String.valueOf(18 + (i % 4) * 2));
            arriba.add(entradas[i]);
        }
        JButton boton = new JButton("Mostrar Grafico");
        boton.addActionListener(e -> actualizar());
        JPanel abajo = new JPanel(new BorderLayout());
        abajo.setBorder(BorderFactory.createEmptyBorder(4, 12, 12, 12));
        abajo.add(boton, BorderLayout.WEST);
        abajo.add(mensaje, BorderLayout.CENTER);
        add(arriba, BorderLayout.NORTH);
        add(grafico, BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);
        setSize(850, 530);
        setLocationRelativeTo(null);
        actualizar();
    }

    private void actualizar() {
        try {
            double[] valores = new double[7];
            for(int i = 0; i < valores.length; i++) {
                valores[i] = Double.parseDouble(entradas[i].getText().trim());
                if(!Double.isFinite(valores[i]) || valores[i] < -80 || valores[i] > 70) {
                    throw new NumberFormatException();
                }
            }
            grafico.establecer(valores);
            mensaje.setText(String.format("   Minimo: %.1f C  |  Maximo: %.1f C", Arrays.stream(valores).min().orElse(0), Arrays.stream(valores).max().orElse(0)));
            System.out.println("Temperaturas actualizadas: " + Arrays.toString(valores));
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa siete temperaturas validas entre -80 y 70.");
        }
    }

    private class PanelGrafico extends JPanel {
        private double[] valores = new double[7];
        private int resaltado = -1;
        private int[] puntosX = new int[7];
        private int[] puntosY = new int[7];

        PanelGrafico() {
            setBackground(Color.WHITE);
            setToolTipText("Acerca el cursor a un punto para ver su temperatura.");
            addMouseMotionListener(new MouseAdapter() {
                @Override public void mouseMoved(MouseEvent e) {
                    int nuevo = -1;
                    for(int i = 0; i < 7; i++) if(Math.hypot(e.getX() - puntosX[i], e.getY() - puntosY[i]) < 15) nuevo = i;
                    if(resaltado != nuevo) { resaltado = nuevo; repaint(); }
                    setToolTipText(nuevo < 0 ? "Selecciona un punto" : dias[nuevo] + ": " + valores[nuevo] + " C");
                }
            });
        }

        void establecer(double[] otros) { valores = otros.clone(); repaint(); }

        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int x0 = 70, y0 = 45, ancho = getWidth() - 120, alto = getHeight() - 110;
            double minimo = Math.floor(Arrays.stream(valores).min().orElse(0) / 5) * 5 - 5;
            double maximo = Math.ceil(Arrays.stream(valores).max().orElse(10) / 5) * 5 + 5;
            if(maximo == minimo) maximo += 10;
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            for(int i = 0; i <= 5; i++) {
                int y = y0 + alto - (i * alto / 5);
                double temperatura = minimo + i * (maximo - minimo) / 5;
                g2.setColor(new Color(225, 231, 239));g2.drawLine(x0, y, x0 + ancho, y);
                g2.setColor(Color.DARK_GRAY);g2.drawString(String.format("%.1f", temperatura), 20, y + 5);
            }
            g2.setStroke(new BasicStroke(2.5f));g2.setColor(new Color(34, 103, 167));
            for(int i = 0; i < 7; i++) {
                puntosX[i] = x0 + i * ancho / 6;
                puntosY[i] = y0 + alto - (int) Math.round((valores[i] - minimo) / (maximo - minimo) * alto);
                if(i > 0)g2.drawLine(puntosX[i-1], puntosY[i-1], puntosX[i], puntosY[i]);
            }
            for(int i = 0; i < 7; i++) {
                g2.setColor(i == resaltado ? new Color(222, 121, 48) : new Color(34, 103, 167));
                int radio = i == resaltado ? 13 : 9;
                g2.fill(new Ellipse2D.Double(puntosX[i] - radio/2.0, puntosY[i] - radio/2.0, radio, radio));
                g2.setColor(Color.DARK_GRAY);g2.drawString(dias[i], puntosX[i] - 10, y0 + alto + 22);
                g2.drawString(String.format("%.1f", valores[i]), puntosX[i] - 10, puntosY[i] - 12);
            }
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GraficoTemperaturas().setVisible(true));
    }
}
