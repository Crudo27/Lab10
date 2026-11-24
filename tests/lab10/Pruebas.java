package lab10;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import lab10.actividades.GeneradorAudio;
import lab10.modelo.Producto;
import lab10.mvc.InventarioModelo;

public class Pruebas {
    private static int correctas;

    private static void verificar(boolean condicion, String etiqueta) {
        if(!condicion) throw new AssertionError(etiqueta);
        correctas++;
        System.out.println("OK " + correctas + " - " + etiqueta);
    }

    public static void main(String[] args) throws Exception {
        Producto p = new Producto("Teclado",new BigDecimal("45.50"),6,"Tecnologia");
        verificar(p.getPrecio().compareTo(new BigDecimal("45.50")) == 0,"Modelo conserva precio decimal");
        final boolean[] aviso = {false};
        p.agregarObservador(e -> aviso[0] = true);
        p.actualizar("Teclado",new BigDecimal("40.00"),5,"Tecnologia");
        verificar(aviso[0] && p.getCantidadStock()==5,"Binding notifica cambios");
        for(String efecto:new String[]{"Aplausos","Campana","Explosion","Melodia"}) {
            byte[] wav=GeneradorAudio.crearWav(efecto);
            verificar(wav.length>100 && new String(wav,0,4).equals("RIFF"),"Audio WAV " + efecto);
        }
        Path temporal=Files.createTempDirectory("lab10-jdbc-");
        String url="jdbc:hsqldb:file:" + temporal.resolve("inventario").toAbsolutePath();
        InventarioModelo modelo=new InventarioModelo(url);
        verificar(modelo.listar().isEmpty(),"Inventario inicia sin registros");
        modelo.guardar(null,"Mouse",new BigDecimal("55.70"),8,"Perifericos");
        verificar(modelo.listar().size()==1,"INSERT de producto");
        int id=modelo.listar().get(0).id();
        modelo.guardar(id,"Mouse gamer",new BigDecimal("60.00"),3,"Perifericos");
        verificar(modelo.listar().get(0).stock()==3,"UPDATE de producto");
        modelo.eliminar(id);
        verificar(modelo.listar().isEmpty(),"DELETE de producto");
        boolean error=false;
        try { modelo.guardar(null,"",new BigDecimal("1"),1,"Categoria"); }
        catch(IllegalArgumentException e) {error=true;}
        verificar(error,"Validacion de nombre obligatorio");
        modelo.cerrar();
        System.out.println("PRUEBAS SUPERADAS: " + correctas + "/" + correctas);
    }
}
