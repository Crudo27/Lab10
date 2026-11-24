package lab10.actividades;

import java.awt.*;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class EfectosSonido extends JFrame {
    private Clip anterior;
    private final JLabel estado = new JLabel("Selecciona un efecto sonoro.", SwingConstants.CENTER);

    public EfectosSonido() {
        super("Actividad 3 - Efectos WAV");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel botones = new JPanel(new GridLayout(1,3,12,12));
        botones.setBorder(BorderFactory.createEmptyBorder(25,20,15,20));
        for(String efecto : new String[]{"Aplausos","Campana","Explosion"}) {
            JButton boton = new JButton(efecto);
            boton.addActionListener(e -> reproducir(efecto));
            botones.add(boton);
        }
        add(botones, BorderLayout.CENTER);
        add(estado, BorderLayout.SOUTH);
        setSize(570,160);
        setLocationRelativeTo(null);
    }

    private void reproducir(String efecto) {
        try {
            if(anterior != null) { anterior.stop(); anterior.close(); }
            anterior = GeneradorAudio.abrir(GeneradorAudio.crearWav(efecto));
            anterior.start();
            estado.setText("Reproduciendo: " + efecto);
            System.out.println("Sonido WAV reproducido: " + efecto);
        } catch(Exception ex) {
            estado.setText("No fue posible abrir el audio: " + ex.getClass().getSimpleName());
        }
    }

    @Override public void dispose() {
        if(anterior != null) { anterior.stop(); anterior.close(); }
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EfectosSonido().setVisible(true));
    }
}
