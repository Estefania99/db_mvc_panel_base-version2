/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author fanny
 */
public class ModelAgenda {

    private Connection conexion;
    private Statement st;
    private ResultSet rs;

    private String nombre;
    private String email;
    private String telefono;
    private String limpiar;

    public String getLimpiar() {
        return limpiar;
    }

    public void setLimpiar(String limpiar) {
        this.limpiar = limpiar;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que realiza las siguietnes acciones: 1- Conecta con la base
     * agenda_mvc, 2- Consulta todo los registros de la tabla contactos, 3-
     * Obtiene el nombre y el email y los guarda en las variables globales
     * nombre y email.
     */
    public void conectarDB() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3307/agenda_mvc", "root", "");
            st = conexion.createStatement();
            rs = st.executeQuery("SELECT * FROM contactos;");
            rs.next();
            nombre = rs.getString("nombre");
            email = rs.getString("email");
            telefono = rs.getString("telefono");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error ModelAgenda 001: " + err.getMessage());
        }
    }

    /**
     * Lee los valores del registro seleccionado y los asigna a las variables
     * miembre nombre y email.
     */
    public void setValues() {
        try {
            nombre = rs.getString("nombre");
            email = rs.getString("email");
            telefono = rs.getString("telefono");
        } catch (SQLException err) {
            JOptionPane.showMessageDialog(null, "Error model 102: " + err.getMessage());

        }
    }

    /**
     * Método que realiza las siguiente acciones: 1.- Moverse al primer registro
     * 2.- obtener el valor del nombre de rs y guardarlo en la variable nombre
     * 3.- obtener el valor del email de rs y guardarlo en la variable email
     */
    public void moverPrimerRegistro() {
        System.out.println("moverPrimerRegistro");
         try{
            if(rs.isLast()==false){
                rs.first();
                nombre = rs.getString("nombre");
                email = rs.getString("email");
                telefono = rs.getString("telefono");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"ERROR"+e.getMessage());
        }
    }
    

    /**
     * Método que realiza las siguiente acciones: 1.- Moverse al siguiente
     * registro 2.- obtener el valor del nombre de rs y guardarlo en la variable
     * nombre 3.- obtener el valor del email de rs y guardarlo en la variable
     * email
     */
    public void moverSiguienteRegistro() {
        System.out.println("moverSiguienteRegistro");
         try{
            if(rs.isLast()==false){
                rs.next();
                nombre = rs.getString("nombre");
                email = rs.getString("email");
                telefono = rs.getString("telefono");
            }
            else{
                rs.previous();
                JOptionPane.showMessageDialog(null,"Estas en el ultimo registro");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"ERROR"+e.getMessage());
        }
    }

    /**
     * Método que realiza las siguiente acciones: 1.- Moverse al anterior
     * registro 2.- obtener el valor del nombre de rs y guardarlo en la variable
     * nombre 3.- obtener el valor del email de rs y guardarlo en la variable
     * email
     */
    public void moverAnteriorRegistro() {
        System.out.println("moverAnteriorRegistro");
         try{
            if(rs.isLast()==false){
                rs.previous();
                nombre = rs.getString("nombre");
                email = rs.getString("email");
                telefono = rs.getString("telefono");
            }
            else{
                rs.next();
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Estas en el primer Registro");
        }
    }

    /**
     * Método que realiza las siguiente acciones: 1.- Moverse al ultimo registro
     * 2.- obtener el valor del nombre de rs y guardarlo en la ariable nombre
     * 3.- obtener el valor del email de rs y guardarlo en la variable email
     */
    public void moverUltimoRegistro() {
        System.out.println("moverUltimoRegistro");
        try{
            if(rs.isLast()==false){
                rs.last();
                nombre = rs.getString("nombre");
                email = rs.getString("email");
                telefono = rs.getString("telefono");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"ERROR"+e.getMessage());
        }

    }
    public void Guardar(){
       try{ 
            nombre = this.getNombre();
            email = this.getEmail();
            st.executeUpdate("Insert into contactos (nombre,email,telefono)"+" values ('"+nombre+"','"+email+"','"+telefono+"');"); 
            JOptionPane.showMessageDialog(null,"Guardado correctamente");
            this.conectarDB();
            this.moverUltimoRegistro();//sirve para mostrar el registro guardado
        } catch(Exception e){ 
            JOptionPane.showMessageDialog(null,"ERROR"+e.getMessage()); 
        }
        }
    public void Modificar(){
        int confirmar = JOptionPane.showConfirmDialog(null, "Esta seguro que desea modifiar el registro?");
        if(JOptionPane.OK_OPTION==confirmar){
            try{ 
               int id_contacto=rs.getInt("id_contacto");
               st.executeUpdate("update contactos set nombre = '"+ nombre +"', email = '"+ email +"',telefono = '"+ telefono+"' where id_contacto = "+ id_contacto +"; ");
               JOptionPane.showMessageDialog(null,"Registro modificado");
               st.executeQuery("select*from contactos");  
               this.conectarDB();
               this.moverUltimoRegistro(); //se llama al ultimo registro agregado
            } catch(Exception err){ 
                JOptionPane.showMessageDialog(null,"Error "+err.getMessage()); 
            }
        } 
    }
    public void Eliminar (){
          int confirmar = JOptionPane.showConfirmDialog (null, "Desea eliminar el registro");
        if (JOptionPane.OK_OPTION == confirmar) { 
        try{
           int id_contacto = rs.getInt ("id_contacto");
           st.executeUpdate ("delete  from contactos where  id_contacto =" + id_contacto);
            JOptionPane.showMessageDialog (null, "El registro se ha borrado");

       } catch(Exception e) { 
            JOptionPane.showMessageDialog (null, "Error" + e.getMessage ()); 
        } 

    }
        } 
}
