/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp2.puntoventa2024.modelo.tabla;

import com.lp2.puntoventa2024.modelo.Producto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author cmendieta
 */
public class ProductoTablaModel extends AbstractTableModel {

    List<Producto> lista;
    private String[] columnas = {"ID", "NOMBRE", "MARCA", "IVA", "PRECIO", "CANTIDAD"};

    public void setLista(List<Producto> lista) {
        // Inicializamos las lista de productos
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        // El tamaño de la lista
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        // El numero de columnass
        return columnas.length;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Producto producto = lista.get(fila);
        switch (columna) {
            case 0:
                return producto.getId();
            case 1:
                return producto.getNombre();
            case 2:
                return producto.getMarca().getNombre();
            case 3:
                return producto.getIva().getNombre();
            case 4:
                return producto.getPrecio();
            case 5:
                return producto.getCantidad();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    public Producto getProductoByRow(int index) {
        return lista.get(index);
    }

}
