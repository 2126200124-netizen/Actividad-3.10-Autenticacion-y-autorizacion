// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.persistencia;

import java.util.List;


public interface listable<T> {
    boolean agregar(T elemento);
    boolean actualizar(T elemento);
    boolean eliminar(T elemento);      
    List<T> listar();
    T traer(String clave);
}
