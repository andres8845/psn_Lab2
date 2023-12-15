/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zlaboratorioexamenfinal;

/**
 *
 * @author andre
 */
public class entry {
    String usuario;
    long posicion;
    entry siguiente;
    
    public entry(String username,long position){
        usuario=username;
        posicion=position;
        siguiente=null;
    }
}
