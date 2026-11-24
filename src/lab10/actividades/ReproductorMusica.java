package lab10.actividades;

import java.awt.*;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class ReproductorMusica extends JFrame {
    private Clip clip;
    private int posicion;
    private final JLabel estado = new JLabel("Pista de prueba: melodia original WAV", SwingConstants.CENTER);

    public ReproductorMusica() {
        super("Actividad 4 - Reproductor de musica");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel opciones = new JPanel(new GridLayout(1,3,12,12));
        opciones.setBorder(BorderFactory.createEmptyBorder(20,18,10,18));
        JButton reproducir = new JButton("Reproducir");
        JButton pausar = new JButton("Pausar");
        JButton reanudar = new JButton("Reanudar");
        reproducir.addActionListener(e -> reproducir());
        pausar.addActionListener(e -> pausar());
        reanudar.addActionListener(e -> reanudar());
        opciones.add(reproducir);opciones.add(pausar);opciones.add(reanudar);
        add(opciones, BorderLayout.CENTER);
        add(estado, BorderLayout.SOUTH);
        setSize(530,160);
        setLocationRelativeTo(null);
    }

    private void reproducir() {
        try {
            if(clip != null) { clip.stop(); clip.close(); }
            clip = GeneradorAudio.abrir(GeneradorAudio.crearWav("Melodia"));
            posicion = 0;
            clip.start();
            estado.setText("Reproduciendo desde el inicio");
            System.out.println("Pista iniciada");
        } catch(Exception ex) { estado.setText("Audio no disponible: " + ex.getClass().getSimpleName()); }
    }

    private void pausar() {
        if(clip != null && clip.isRunning()) {
            posicion = clip.getFramePosition();
            clip.stop();
            estado.setText("Pausado en fotograma " + posicion);
            System.out.println("Pista pausada en " + posicion);
        }
    }

    private void reanudar() {
        if(clip != null && !clip.isRunning() && posicion < clip.getFrameLength()) {
            clip.setFramePosition(posicion);
            clip.start();
            estado.setText("Reanudado desde fotograma " + posicion);
            System.out.println("Pista reanudada");
        }
    }

    @Override public void dispose() {
        if(clip != null) { clip.stop(); clip.close(); }
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReproductorMusica().setVisible(true));
    }
}
