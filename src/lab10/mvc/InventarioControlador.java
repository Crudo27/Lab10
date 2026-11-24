package lab10.mvc;

import java.math.BigDecimal;
import java.sql.SQLException;
import javax.swing.*;

public class InventarioControlador {
    private final InventarioModelo modelo;
    private final InventarioVista vista;
    private Integer seleccionado;

    public InventarioControlador(InventarioModelo modelo,InventarioVista vista) {
        this.modelo = modelo;this.vista = vista;
        vista.guardar.addActionListener(e -> guardar());
        vista.eliminar.addActionListener(e -> eliminar());
        vista.nuevo.addActionListener(e -> limpiar());
        vista.actualizar.addActionListener(e -> refrescar());
        vista.tabla.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) seleccionar();
        });
        refrescar();
    }

    private void guardar() {
        try {
            modelo.guardar(seleccionado,vista.nombre.getText(),new BigDecimal(vista.precio.getText().trim()),Integer.parseInt(vista.stock.getText().trim()),vista.categoria.getText());
            vista.aviso(seleccionado == null ? "Producto agregado" : "Producto actualizado");
            limpiarCampos();
            refrescar();
        } catch(NumberFormatException ex) { vista.aviso("Precio o stock invalidos"); }
        catch(IllegalArgumentException | SQLException ex) { vista.aviso(ex.getMessage()); }
    }

    private void eliminar() {
        if(seleccionado == null) { vista.aviso("Selecciona un producto"); return; }
        try {
            modelo.eliminar(seleccionado);
            limpiarCampos();
            refrescar();
            vista.aviso("Producto eliminado");
        } catch(SQLException ex) { vista.aviso(ex.getMessage()); }
    }

    private void refrescar() {
        try {
            vista.datos.setRowCount(0);
            for(var p:modelo.listar()) {
                vista.datos.addRow(new Object[]{p.id(),p.nombre(),p.precio().toPlainString(),p.stock(),p.categoria()});
            }
        } catch(SQLException ex) { vista.aviso(ex.getMessage()); }
    }

    private void seleccionar() {
        int fila = vista.tabla.getSelectedRow();
        if(fila < 0) return;
        int f = vista.tabla.convertRowIndexToModel(fila);
        seleccionado = (Integer) vista.datos.getValueAt(f,0);
        vista.nombre.setText(String.valueOf(vista.datos.getValueAt(f,1)));
        vista.precio.setText(String.valueOf(vista.datos.getValueAt(f,2)));
        vista.stock.setText(String.valueOf(vista.datos.getValueAt(f,3)));
        vista.categoria.setText(String.valueOf(vista.datos.getValueAt(f,4)));
    }

    private void limpiarCampos() {
        seleccionado = null;
        vista.tabla.clearSelection();
        vista.nombre.setText("");vista.precio.setText("");vista.stock.setText("");vista.categoria.setText("");
    }

    private void limpiar() { limpiarCampos();vista.aviso("Nuevo registro"); }
}
