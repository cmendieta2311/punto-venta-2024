/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lp2.puntoventa2024.modelo.dao;

import com.lp2.puntoventa2024.modelo.Cliente;
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
public class ClienteCrudImpl implements crud<Cliente> {

    Connection conec;
    PreparedStatement sentencia;

    //lo primero que se ejecuta cuando se crea un objeto
    public ClienteCrudImpl() {
        Conexion conectar = new Conexion();
        conec = conectar.conectarBD();
    }

    @Override
    public void insertar(Cliente m) {
        try {
            //Preparar sentencia
            String sql = "insert into cliente (ruc, razon_social, telefono, direccion, correo) values (?,?,?,?,?)";
            sentencia = conec.prepareStatement(sql);
            //Asginar valor a los parametros
            sentencia.setString(1, m.getRut());
            sentencia.setString(2, m.getRazonSocial());
            sentencia.setString(3, m.getTelefono());
            sentencia.setString(4, m.getDireccion());
            sentencia.setString(5, m.getCorreo());
            //Ejecutar sentencia
            sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteCrudImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(Cliente obj) {
        try {
            String sql = "update cliente set ruc=?,razon_social=?,telefono=?,direccion=?,correo=? where id=?";
            sentencia = conec.prepareStatement(sql);
            sentencia.setString(1, obj.getRut());
            sentencia.setString(2, obj.getRazonSocial());
            sentencia.setString(3, obj.getTelefono());
            sentencia.setString(4, obj.getDireccion());
            sentencia.setString(5, obj.getCorreo());

            sentencia.setInt(6, obj.getId());
            sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteCrudImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar(Cliente obj) {
        try {
            //Prepara sentencia sql
            String sql = "delete from cliente where id=?";
            sentencia = conec.prepareStatement(sql);
            // enviar valores de los parametros
            sentencia.setInt(1, obj.getId());
            // ejecutar sentencia
            sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteCrudImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Cliente> listar(String textoBuscado) {
        System.out.println("texto buscado " + textoBuscado);
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            String sql = "select * from cliente where ruc ||' '|| razon_social ilike ? order by razon_social asc";
            sentencia = conec.prepareStatement(sql);
            sentencia.setString(1, "%" + textoBuscado + "%");
            ResultSet rs = sentencia.executeQuery();

            //recorrer una lista
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setRut(rs.getString("ruc"));
                cliente.setRazonSocial(rs.getString("razon_social"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreo(rs.getString("correo"));
                lista.add(cliente);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteCrudImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

}
