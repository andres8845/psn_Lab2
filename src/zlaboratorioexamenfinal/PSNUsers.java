/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zlaboratorioexamenfinal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author andre
 */
public class PSNUsers {
    RandomAccessFile registros;
    RandomAccessFile psn;
    hashtable users;
    
    public PSNUsers() throws FileNotFoundException, IOException{
        registros = new RandomAccessFile("registros","rw");
        psn = new RandomAccessFile("psn","rw");
        users = new hashtable();
        reloadHashTable();
    }
    
    long size;
    
    private void reloadHashTable() throws IOException{
        registros.seek(0);
        size=0;
        
        while(registros.getFilePointer()<registros.length()){
            String username=registros.readUTF();
            registros.readInt();
            registros.readInt();
            boolean activo=registros.readBoolean();
            
            if(activo){
               users.add(username, size);
               size++;
            }
            System.out.println("usuario "+username+" esta inactivo.");
        }
    }
    
    public boolean addUser(String username) throws IOException{
        if(users.search(username)!=-1){
            JOptionPane.showMessageDialog(null, "No se puede crear un usuario que ya existe!");
            return false;
        }
        
        while(registros.getFilePointer()<registros.length()){
            registros.readUTF();
            registros.readInt();
            registros.readInt();
            registros.readBoolean();
        }
        //Nombre
        registros.writeUTF(username);
        //Cantidad de trofeos
        registros.writeInt(0);
        //Puntaje de trofeos
        registros.writeInt(0);
        //Activo
        registros.writeBoolean(true);
        //Agrega al hashtable
        users.add(username, size);
        //Incrementa cantidad de users
        size++;
        return true;
    }
    
    public void deactivateUsers(String username) throws IOException{
        if(users.search(username)==-1){
            JOptionPane.showMessageDialog(null, "No se puede desactivar un usuario inexistente!");
            return;
        }
        
        registros.seek(0);
        long puntero;
        while(registros.getFilePointer()<registros.length()){
            registros.readUTF();
            registros.readInt();
            registros.readInt();
            puntero=registros.getFilePointer();
            boolean activo=registros.readBoolean();
            if(activo){
                registros.seek(puntero);
                registros.writeBoolean(false);
                JOptionPane.showMessageDialog(null, "Se ha desactivado el usuario!");
                return;
            }else{
                JOptionPane.showMessageDialog(null, "La cuenta ya habia sido desactivada!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "No se encontro el usuario!");
    }

    public void addTrophieTo(String username,String trophyGame,String trophyName,hashtable.trofeos type) throws IOException{
        if(users.search(username)==-1){
            JOptionPane.showMessageDialog(null, "No se puede agregar trofeo a un usuario inexistente!");
            return;
        }
        psn.seek(0);
        while(psn.getFilePointer()<psn.length()){
            //usaurio
            psn.readUTF();
            //juego
            psn.readUTF();
            //nombre trofeo
            psn.readUTF();
            //tipo trofeo
            psn.readUTF();
            //fecha formateada
            psn.readUTF();
        }
        
        psn.writeUTF(username);
        psn.writeUTF(trophyGame);
        psn.writeUTF(trophyName);
        psn.writeUTF(type.name());
        Date fechaLogro = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");
        String fechaFormateada="Fecha De Logro: "+formato.format(fechaLogro);
        psn.writeUTF(fechaFormateada);
        
        registros.seek(0);
        while(registros.getFilePointer()<registros.length()){
            String user=registros.readUTF();
            long puntero=registros.getFilePointer();
            int cantTrofeos=registros.readInt();
            int puntosTrofeos=registros.readInt();
            if(!registros.readBoolean()){
                JOptionPane.showMessageDialog(null, "No se puede agregar trofeos a un usuario inactivo!");
                return;
            }
            if(user.equals(username)){
                registros.seek(puntero);
                registros.writeInt(cantTrofeos+1);
                registros.writeInt(puntosTrofeos+type.points);
                registros.readBoolean();
                JOptionPane.showMessageDialog(null, "Se ha agregado el trofeo!");
            }
        }
        JOptionPane.showMessageDialog(null, "Usuario no encotrado o inactivo!");
    }
    
    public String playerInfo(String username) throws IOException{
        if(users.search(username)==-1){
            JOptionPane.showMessageDialog(null, "No se puede desplegar informacion de un usuario inexistente!");
        }
        String infoPlayer = null;
        registros.seek(0);
        while(registros.getFilePointer()<registros.length()){
            String user=registros.readUTF();
            int cantTrofeos=registros.readInt();
            int puntosTrofeos=registros.readInt();
            boolean activo=registros.readBoolean();
            if(user.equals(username)){
                infoPlayer="Usuario: "+user+" - Cantidad de Trofeos: "+cantTrofeos+" - Puntos en Trofeos: "+puntosTrofeos+" - Estado: "+activo+"\n";
            }
        }
        
        psn.seek(0);
        while(psn.getFilePointer()<psn.length()){
            String user=psn.readUTF();
            String juego=psn.readUTF();
            String nombreTrofeo=psn.readUTF();
            String tipoTrofeo=psn.readUTF();
            String fecha=psn.readUTF();
            
            if(user.equals(username)){
                infoPlayer+="*Fecha de Logro: "+fecha+" - Tipo de Trofeo: "+tipoTrofeo+" - Juego: "+juego+" - Descripcion: "+nombreTrofeo+"\n";
            }
        }
        if(infoPlayer==null){
            JOptionPane.showMessageDialog(null, "Usuario no existe");
        }
        return infoPlayer;
    }
}
