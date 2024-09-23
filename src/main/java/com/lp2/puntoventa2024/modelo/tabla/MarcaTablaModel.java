/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp2.puntoventa2024.modelo.tabla;

import com.lp2.puntoventa2024.modelo.Marca;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author cmendieta
 */
public class MarcaTablaModel extends AbstractTableModel{
    List<Marca> lista;
    private String[] columnas ={"NOMBRE"};
    
    public void setLista(List<Marca> lista){
        // Inicializamos las lista de marcas
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
        Marca marca = lista.get(fila);
        switch (columna) {
            case 0:
                return marca.getNombre();
//            case 1:
//                return marca.getNombre();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column]; 
    }
    
    
}
