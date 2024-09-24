/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp2.puntoventa2024.controlador;

import com.lp2.puntoventa2024.modelo.Marca;
import com.lp2.puntoventa2024.modelo.dao.MarcaCrudImpl;
import com.lp2.puntoventa2024.modelo.tabla.MarcaTablaModel;
import com.lp2.puntoventa2024.vista.GUIMarca;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author cmendieta
 */
public class MarcaController implements ActionListener {
    
    private GUIMarca gui;
    private MarcaCrudImpl crud;
    
    private char operacion;
    Marca marca = new Marca();
    
    MarcaTablaModel modelo = new MarcaTablaModel();
    
    public MarcaController(GUIMarca gui, MarcaCrudImpl crud) {
        this.gui = gui;
        this.crud = crud;
        this.gui.btn_guardar.addActionListener(this);
        this.gui.btn_cancelar.addActionListener(this);
        this.gui.btn_nuevo.addActionListener(this);
        this.gui.btn_editar.addActionListener(this);
        this.gui.btn_eliminar.addActionListener(this);
        
        gui.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable tabla = (JTable) e.getSource();
                int row = tabla.rowAtPoint(e.getPoint());
                MarcaTablaModel model = (MarcaTablaModel) tabla.getModel();
                //Devolver el objeto seleccionado en la fila

                setMarcaForm(model.getMarcaByRow(row));
            }
        });
        
        habilitarCampos(
                false);
        
        listar();
    }
    
    public void mostrarVentana() {
        gui.setVisible(true);
    }
    
    public void listar() {
        List<Marca> lista = crud.listar();
        modelo.setLista(lista);
        gui.tabla.setModel(modelo);
        gui.tabla.updateUI();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Evento click");
        if (e.getSource() == gui.btn_nuevo) {
            operacion = 'N';
            limpiar();
            habilitarCampos(true);
            gui.txt_nombre.requestFocus();
        }
        if (e.getSource() == gui.btn_editar) {
            operacion = 'E';
            habilitarCampos(true);
            gui.txt_nombre.requestFocus();
        }
        
        if (e.getSource() == gui.btn_eliminar) {
            int fila = gui.tabla.getSelectedRow();
            if (fila >= 0) {
                int ok = JOptionPane.showConfirmDialog(gui, "Realmente desea elimnar el registro?", "Confirmar operacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (ok == 0) {
                    crud.eliminar(modelo.getMarcaByRow(fila));
                    listar();
                }
            } else {
                JOptionPane.showMessageDialog(gui, "Debe seleccionar una fila");
            }
        }
        if (e.getSource() == gui.btn_cancelar) {
            habilitarCampos(false);
            limpiar();
        }
        
        if (e.getSource() == gui.btn_guardar) {
            System.out.println("Evento click de guardar");
            if (operacion == 'N') {
                crud.insertar(getMarcaForm());
                
                gui.txt_nombre.requestFocus();
            }
            
            if (operacion == 'E') {
                crud.actualizar(getMarcaForm());
                habilitarCampos(false);
            }
            
            listar();
            limpiar();
            
        }
        System.out.println(operacion);
    }

    // Metodo encargado de habilitar o deshabilitar los campos
    private void habilitarCampos(Boolean estado) {
        gui.txt_nombre.setEnabled(estado);
    }
    
    private void limpiar() {
        gui.txt_nombre.setText("");
    }

    // funcion o metodo encargado de recuperrar los valores de los JTextField en un objeto
    private Marca getMarcaForm() {
        marca.setNombre(gui.txt_nombre.getText());
        return marca;
    }

    //Funcion o metodo encargado asignar valor los JTextField
    private void setMarcaForm(Marca item) {
        System.out.println(item);
        marca.setId(item.getId());
        gui.txt_nombre.setText(item.getNombre());
    }
}
