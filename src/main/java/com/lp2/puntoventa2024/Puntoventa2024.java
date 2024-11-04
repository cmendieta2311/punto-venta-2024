/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.lp2.puntoventa2024;

import com.lp2.puntoventa2024.vista.VentanaPrincipal;
import javax.swing.JFrame;

/**
 *
 * @author cmendieta
 */
public class Puntoventa2024 {
    
    public static void main(String[] args) {
        VentanaPrincipal gui = new VentanaPrincipal();
        gui.setTitle("Sistema de punto de venta v.1.0");
        gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.setVisible(true);
    }
}
