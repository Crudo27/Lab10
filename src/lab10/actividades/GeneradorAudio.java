package lab10.actividades;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.*;

public class GeneradorAudio {
    private static final int TASA = 22050;

    public static byte[] crearWav(String sonido) throws IOException {
        double duracion = sonido.equals("Melodia") ? 5.0 : 1.4;
        int cantidad = (int) (TASA * duracion);
        byte[] pcm = new byte[cantidad * 2];
        Random azar = new Random(2026);
        double[] notas = {261.63, 329.63, 392.0, 523.25, 392.0, 329.63, 293.66, 261.63};
        for(int i = 0; i < cantidad; i++) {
            double tiempo = (double)i/TASA;
            double muestra;
            switch(sonido) {
                case "Aplausos" -> muestra = (azar.nextDouble()*2-1) * Math.exp(-2.5*tiempo) * Math.pow(Math.sin(2*Math.PI*8*tiempo), 2);
                case "Campana" -> muestra = Math.sin(2*Math.PI*880*tiempo) * Math.exp(-2.8*tiempo) + .38*Math.sin(2*Math.PI*1320*tiempo) * Math.exp(-3.4*tiempo);
                case "Explosion" -> muestra = (azar.nextDouble()*2-1)*Math.exp(-3.5*tiempo) + .2*Math.sin(2*Math.PI*65*tiempo)*Math.exp(-4.0*tiempo);
                default -> {
                    int indice = Math.min((int)(tiempo/0.625), notas.length-1);
                    double fase = tiempo % 0.625;
                    muestra = Math.sin(2*Math.PI*notas[indice]*tiempo) * Math.min(1.0, fase*25) * Math.min(1.0, (0.625-fase)*8);
                }
            }
            int valor = (int)(Math.max(-1, Math.min(1, muestra*.65))*32767);
            pcm[2*i] = (byte)(valor & 255);
            pcm[2*i+1] = (byte)((valor >> 8)&255);
        }
        AudioFormat formato = new AudioFormat(TASA,16,1,true,false);
        try(ByteArrayInputStream datos = new ByteArrayInputStream(pcm);
            AudioInputStream flujo = new AudioInputStream(datos,formato,cantidad);
            ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            AudioSystem.write(flujo,AudioFileFormat.Type.WAVE,salida);
            return salida.toByteArray();
        }
    }

    public static Clip abrir(byte[] wav) throws Exception {
        try(AudioInputStream entrada = AudioSystem.getAudioInputStream(new ByteArrayInputStream(wav))) {
            Clip clip = AudioSystem.getClip();
            clip.open(entrada);
            return clip;
        }
    }
}
