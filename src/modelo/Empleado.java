/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class Empleado extends Persona{
    private int id;
    private String cod_empleado/*, puesto*/;
    
    Conexion cn;
    
    public Empleado(){}
    public Empleado(int id, String cod_empleado, /*String puesto,*/ String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.cod_empleado = cod_empleado;
        this.id = id;
        /*this.puesto = puesto;*/
    }
    
    
    public String getCod_empleado() {
        return cod_empleado;
    }

    public void setCod_empleado(String cod_empleado) {
        this.cod_empleado = cod_empleado;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    /*public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }*/
    public DefaultTableModel leer(){
    DefaultTableModel tabla = new DefaultTableModel();
    try{
        cn = new Conexion();
        cn.abrir_conexion();
        String query;
        query = "Select id_empleado as id,codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento from db_empresa.empleados;";
        ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
        
        String encabezado [] = {"Id", "Codigo","Nombres", "Apellidos", "Direccion", "Telefono","Nacimiento"};
        tabla.setColumnIdentifiers(encabezado);
        
        String datos []=new String [7];
        
        while(consulta.next()){
        datos [0] = consulta.getString("id");
        datos [1] = consulta.getString("codigo");
        datos [2] = consulta.getString("nombres");
        datos [3] = consulta.getString("apellidos");
        datos [4] = consulta.getString("direccion");
        datos [5] = consulta.getString("telefono");
        datos [6] = consulta.getString("fecha_nacimiento");
        tabla.addRow(datos);
        }
        
        cn.cerrar_conexion();
                
    }catch(SQLException ex){
        cn.cerrar_conexion();
        System.out.println("Error:..."+ ex.getMessage());
    }
    return tabla;
    }
    @Override
    public void agregar(){
        try{
            PreparedStatement parametro;
            String query ="INSERT INTO db_empresa.empleados(codigo,nombres,apellidos,direccion,telefono,fecha_nacimiento) VALUES (?,?,?,?,?,?);";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, this.getCod_empleado());
            parametro.setString(2, this.getNombres());
            parametro.setString(3, this.getApellidos());
            parametro.setString(4, this.getDireccion());
            parametro.setString(5, this.getTelefono());
            parametro.setString(6, this.getFecha_nacimiento());
            /*parametro.setString(7, this.getPuesto());*/
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro Exitoso","Mensaje",JOptionPane.INFORMATION_MESSAGE);
            
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error..."+ ex.getMessage() );
        }
    
    }
    @Override
    public void actualizar (){
            try{
            PreparedStatement parametro;
            String query ="update empleados set codigo = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?"+
                    "where id_empleado = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, this.getCod_empleado());
            parametro.setString(2, this.getNombres());
            parametro.setString(3, this.getApellidos());
            parametro.setString(4, this.getDireccion());
            parametro.setString(5, this.getTelefono());
            parametro.setString(6, this.getFecha_nacimiento());
            parametro.setInt(7, this.getId());
            /*parametro.setString(7, this.getPuesto());*/
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Actualizacion Realizado Con Exito","Mensaje",JOptionPane.INFORMATION_MESSAGE);
            
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error..."+ ex.getMessage() );
        }

      }
    @Override
    public void eliminar (){
    try{
            PreparedStatement parametro;
            String query ="delete from empleados where id_empleado = ?";
            cn = new Conexion();
            cn.abrir_conexion();
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, this.getId());
            
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro Eliminado Con Exito","Mensaje",JOptionPane.INFORMATION_MESSAGE);
            
            
        }catch(HeadlessException | SQLException ex){
            System.out.println("Error..."+ ex.getMessage() );
        }
    }

    }
