package lab10.modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;

public class Producto {
    private String nombre;
    private BigDecimal precio;
    private int cantidadStock;
    private String categoria;
    private final PropertyChangeSupport cambios = new PropertyChangeSupport(this);

    public Producto(String nombre, BigDecimal precio, int cantidadStock, String categoria) {
        actualizar(nombre, precio, cantidadStock, categoria);
    }

    public void actualizar(String nombre, BigDecimal precio, int cantidadStock, String categoria) {
        if(nombre == null || nombre.isBlank() || categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Nombre y categoria son obligatorios.");
        }
        if(precio == null || precio.signum() < 0 || cantidadStock < 0) {
            throw new IllegalArgumentException("Precio y cantidad no pueden ser negativos.");
        }
        this.nombre = nombre.trim();
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria.trim();
        cambios.firePropertyChange("producto", null, resumen());
    }

    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
    public int getCantidadStock() { return cantidadStock; }
    public String getCategoria() { return categoria; }

    public String resumen() {
        return nombre + " | S/ " + precio.toPlainString() + " | Stock: " + cantidadStock + " | " + categoria;
    }

    public void agregarObservador(PropertyChangeListener escucha) {
        cambios.addPropertyChangeListener(escucha);
    }
}
