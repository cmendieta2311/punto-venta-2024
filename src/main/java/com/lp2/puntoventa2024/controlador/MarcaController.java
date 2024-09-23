/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp2.puntoventa2024.controlador;

import com.lp2.puntoventa2024.modelo.Marca;
import com.lp2.puntoventa2024.modelo.dao.MarcaCrudImpl;
import com.lp2.puntoventa2024.modelo.tabla.MarcaTablaModel;
import com.lp2.puntoventa2024.vista.GUIMarca;
import java.util.List;

/**
 *
 * @author cmendieta
 */
public class MarcaController {
    
    private GUIMarca gui;
    private MarcaCrudImpl crud;
    
    MarcaTablaModel modelo = new MarcaTablaModel();

    public MarcaController(GUIMarca gui, MarcaCrudImpl crud) {
        this.gui = gui;
        this.crud = crud;
        listar();
    }
    
    public void mostrarVentana(){
        gui.setVisible(true);
    }
    
    public void listar(){
        List<Marca> lista = crud.listar();
        modelo.setLista(lista);
        gui.tabla.setModel(modelo);
    }
    
    
    
}
