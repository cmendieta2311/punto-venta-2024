/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.lp2.puntoventa2024;

import com.lp2.puntoventa2024.controlador.MarcaController;
import com.lp2.puntoventa2024.modelo.dao.MarcaCrudImpl;
import com.lp2.puntoventa2024.vista.GUIMarca;


/**
 *
 * @author cmendieta
 */
public class Puntoventa2024 {

    public static void main(String[] args) {
        GUIMarca marca = new GUIMarca(null, true);
        MarcaCrudImpl crud = new MarcaCrudImpl();
        MarcaController ctrl = new MarcaController(marca,crud);
        ctrl.mostrarVentana();
    }
}
