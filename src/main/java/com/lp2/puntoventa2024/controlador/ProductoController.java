/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp2.puntoventa2024.controlador;

import com.lp2.puntoventa2024.modelo.Iva;
import com.lp2.puntoventa2024.modelo.Marca;
import com.lp2.puntoventa2024.modelo.Producto;
import com.lp2.puntoventa2024.modelo.dao.IvaCrudImpl;
import com.lp2.puntoventa2024.modelo.dao.MarcaCrudImpl;
import com.lp2.puntoventa2024.modelo.dao.ProductoCrudImpl;
import com.lp2.puntoventa2024.modelo.tabla.ProductoTablaModel;
import com.lp2.puntoventa2024.vista.GUIProducto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author cmendieta
 */
public class ProductoController implements ActionListener , KeyListener {
    
    private GUIProducto gui;
    private ProductoCrudImpl crud;
    
    private char operacion;
    Producto producto = new Producto();
    
    MarcaCrudImpl crudMarca = new MarcaCrudImpl();
    IvaCrudImpl crudIva = new IvaCrudImpl();
    
    ProductoTablaModel modelo = new ProductoTablaModel();
    
    public ProductoController(GUIProducto gui, ProductoCrudImpl crud) {
        this.gui = gui;
        this.crud = crud;
        this.gui.btn_guardar.addActionListener(this);
        this.gui.btn_cancelar.addActionListener(this);
        this.gui.btn_nuevo.addActionListener(this);
        this.gui.btn_editar.addActionListener(this);
        this.gui.btn_eliminar.addActionListener(this);
        this.gui.txt_buscar.addKeyListener(this);
        
        gui.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable tabla = (JTable) e.getSource();
                int row = tabla.rowAtPoint(e.getPoint());
                ProductoTablaModel model = (ProductoTablaModel) tabla.getModel();
                //Devolver el objeto seleccionado en la fila

                setProductoForm(model.getProductoByRow(row));
            }
        });
        
        llenarComboMarca(gui.cbo_Marca);
        llenarComboIva(gui.cbo_Iva);
        
        habilitarCampos(false);
        habilitarBoton(false);
        
        listar("");
    }
    
    public void mostrarVentana() {
        gui.setLocationRelativeTo(gui);
        gui.setVisible(true);
    }
    
    public void listar(String valorBuscado) {
        List<Producto> lista = crud.listar(valorBuscado);
        modelo.setLista(lista);
        gui.tabla.setModel(modelo);
        gui.tabla.updateUI();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Evento click");
        
        if(e.getSource()== gui.txt_buscar){
            String valor = gui.txt_buscar.getText();
            listar(valor);
        }
        if (e.getSource() == gui.btn_nuevo) {
            operacion = 'N';
            limpiar();
            habilitarCampos(true);
             habilitarBoton(true);
            gui.txt_nombre.requestFocus();
        }
        if (e.getSource() == gui.btn_editar) {
            operacion = 'E';
            habilitarCampos(true);
             habilitarBoton(true);
            gui.txt_nombre.requestFocus();
        }
        
        if (e.getSource() == gui.btn_eliminar) {
            int fila = gui.tabla.getSelectedRow();
            if (fila >= 0) {
                int ok = JOptionPane.showConfirmDialog(gui,
                        "Realmente desea elimnar el registro?", 
                        "Confirmar operacion", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE);
                if (ok == 0) {
                    crud.eliminar(modelo.getProductoByRow(fila));
                    listar("");
                }
            } else {
                JOptionPane.showMessageDialog(gui, "Debe seleccionar una fila");
            }
        }
        if (e.getSource() == gui.btn_cancelar) {
            habilitarCampos(false);
             habilitarBoton(false);
            limpiar();
        }
        
        if (e.getSource() == gui.btn_guardar) {
            boolean v_control = validarDatos();
            if(v_control == true){
                JOptionPane.showMessageDialog(gui, "favor completar los datos");
                return;
            }
            System.out.println("Evento click de guardar");
            if (operacion == 'N') {
                crud.insertar(getProductoForm());
                
                gui.txt_nombre.requestFocus();
            }
            
            if (operacion == 'E') {
                crud.actualizar(getProductoForm());
                habilitarCampos(false);
            }
            
            listar("");
            limpiar();
            
        }
        System.out.println(operacion);
    }

    // Metodo encargado de habilitar o deshabilitar los campos
    private void habilitarCampos(Boolean estado) {
        gui.txt_nombre.setEnabled(estado);
        gui.txt_precio.setEnabled(estado);
        gui.cbo_Marca.setEnabled(estado);
        gui.cbo_Iva.setEnabled(estado);
    }
    
      private void habilitarBoton(Boolean estado) {
        gui.btn_guardar.setEnabled(estado);
        gui.btn_cancelar.setEnabled(estado);
    }
    
    private void limpiar() {
        gui.txt_nombre.setText("");
    }

    // funcion o metodo encargado de recuperrar los valores de los JTextField en un objeto
    private Producto getProductoForm() {
        producto.setNombre(gui.txt_nombre.getText());
        producto.setPrecio(Integer.valueOf(gui.txt_precio.getText()));
        producto.setIva((Iva) gui.cbo_Iva.getSelectedItem());
        producto.setMarca((Marca) gui.cbo_Marca.getSelectedItem());
        return producto;
    }

    //Funcion o metodo encargado asignar valor los JTextField
    private void setProductoForm(Producto item) {
        System.out.println(item);
        producto.setId(item.getId());
        gui.txt_nombre.setText(item.getNombre());
        gui.txt_precio.setText(item.getPrecio().toString());
        gui.cbo_Iva.setSelectedItem(item.getIva());
        gui.cbo_Marca.setSelectedItem(item.getMarca());
    }
    
    private boolean validarDatos(){
        boolean vacio = false;
        if(gui.txt_nombre.getText().isEmpty()){
            vacio = true;
        }
        return vacio;
    }
    
    private void llenarComboMarca(JComboBox cbo){
        DefaultComboBoxModel<Marca> model = new DefaultComboBoxModel();
        List<Marca> lista = crudMarca.listar("");
        for (int i = 0; i < lista.size(); i++) {
            Marca marca = lista.get(i);
            model.addElement(marca);
        }
        cbo.setModel(model);
    }
    
      private void llenarComboIva(JComboBox cbo){
        DefaultComboBoxModel<Iva> model = new DefaultComboBoxModel();
        List<Iva> lista = crudIva.listar("");
        for (int i = 0; i < lista.size(); i++) {
            Iva iva = lista.get(i);
            model.addElement(iva);
        }
        cbo.setModel(model);
    }

    @Override
    public void keyTyped(KeyEvent e) {
      
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.listar(gui.txt_buscar.getText());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
