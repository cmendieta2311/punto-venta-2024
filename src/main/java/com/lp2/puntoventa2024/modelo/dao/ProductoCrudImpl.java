/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp2.puntoventa2024.modelo.dao;

import com.lp2.puntoventa2024.modelo.Iva;
import com.lp2.puntoventa2024.modelo.Marca;
import com.lp2.puntoventa2024.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmendieta
 */
public class ProductoCrudImpl implements crud<Producto> {

    Connection conec;
    PreparedStatement sentencia;

    //lo primero que se ejecuta cuando se crea un objeto
    public ProductoCrudImpl() {
        Conexion conectar = new Conexion();
        conec = conectar.conectarBD();
    }

    @Override
    public void insertar(Producto m) {
        try {
            //Preparar sentencia
            String sql = "insert into producto (nombre,precio,marca_id,iva_id) values(?,?,?,?)";
            sentencia = conec.prepareStatement(sql);
            //Asginar valor a los parametros
            sentencia.setString(1, m.getNombre());
            sentencia.setInt(2, m.getPrecio());
            sentencia.setInt(3, m.getMarca().getId());
            sentencia.setInt(4, m.getIva().getId());
            //Ejecutar sentencia
            sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoCrudImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Producto obj) {
        try {
            String sql = "update producto set nombre=?, precio=?,marca_id=?,iva_id=? where id=?";
            sentencia = conec.prepareStatement(sql);
            sentencia.setString(1, obj.getNombre());
            sentencia.setInt(2, obj.getPrecio());
            sentencia.setInt(3, obj.getMarca().getId());
            sentencia.setInt(4, obj.getIva().getId());
            sentencia.setInt(5, obj.getId());
            sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoCrudImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar(Producto obj) {
        try {
            //Prepara sentencia sql
            String sql = "delete from producto where id=?";
            sentencia = conec.prepareStatement(sql);
            // enviar valores de los parametros
            sentencia.setInt(1, obj.getId());
            // ejecutar sentencia
            sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoCrudImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Producto> listar(String textoBuscado) {
        System.out.println("texto buscado " + textoBuscado);
        ArrayList<Producto> lista = new ArrayList<>();
        try {
//            String sql = "select * from producto where nombre ilike ? order by nombre asc";
String sql ="select p.*,i.nombre as iva,m.nombre as marca\n" +
"from producto p\n" +
" inner join iva i on p.iva_id = i.id\n" +
" inner join marca m on p.marca_id = m.id\n" +
"where p.nombre ilike ? order by p.nombre asc"  ;      
sentencia = conec.prepareStatement(sql);
            sentencia.setString(1, "%" + textoBuscado + "%");
            ResultSet rs = sentencia.executeQuery();

            //recorrer una lista
            while (rs.next()) {
                Producto producto = new Producto();
                Iva iva = new Iva();
                Marca marca  = new Marca();
                
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getInt("precio"));
                
                //Asignar valor a iva
                iva.setId(rs.getInt("iva_id"));
                iva.setNombre(rs.getString("iva"));
                producto.setIva(iva);
                
                //asignar valor a marca
                marca.setId(rs.getInt("marca_id"));
                marca.setNombre(rs.getString("marca"));
                producto.setMarca(marca);
                
                lista.add(producto);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductoCrudImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

}
