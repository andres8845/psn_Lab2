/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zlaboratorioexamenfinal;

import javax.swing.JOptionPane;

/**
 *
 * @author andre
 */
public class hashtable {
    entry inicio;
    long size=0;
    
    
    public void add(String username,long pos){
        entry temporal = new entry(username,pos);
        if(username==null){
            JOptionPane.showMessageDialog(null, "El usuario no puede ser nulo!");
            return;
        }
        if (inicio == null) {
            System.out.println("Set on start");
            inicio = temporal;
            size++;
            return;
        }
        
        if(search(username)==-1){
            while(temporal.siguiente!=null){
                temporal=temporal.siguiente;
            }
            temporal.siguiente=temporal;
            size++;
            JOptionPane.showMessageDialog(null, "Usuario agregado correctamente!");
            return;
        }
            JOptionPane.showMessageDialog(null, "Este usuario ya existe!");        
    }
    
    public void remove(String username){
        if(search(username)==-1){
            JOptionPane.showMessageDialog(null, "No se puede remover un usuario inexistente!");
            return;
        }
        
            if(inicio.usuario.equals(username)){
                inicio=inicio.siguiente;
                size--;
                JOptionPane.showMessageDialog(null, "Se ha removido usuario correctamente!");
                return;
            }
            
            entry temporal=inicio;
            
            while(temporal.siguiente!=null){
                if(temporal.siguiente.usuario.equals(username)){
                    temporal.siguiente=temporal.siguiente.siguiente;
                    size--;
                    JOptionPane.showMessageDialog(null, "Se ha removido usuario correctamente!");
                    return;
                }
                temporal=temporal.siguiente;
            }
        JOptionPane.showMessageDialog(null, "Usuario no encontrado!");
    }
    
    public long search(String username){
        entry temporal=inicio;
        long pos=0;
        while(temporal!=null){
            if(temporal.usuario.equals(username)){
                return pos;
            }
            temporal=temporal.siguiente;
            pos++;
        }
        return -1;
    }
    
    public enum trofeos{
        platino(5),oro(3),plata(2),bronce(1);
        int points;
        
        trofeos(int puntos){
            points=puntos;
        }
    }
}
