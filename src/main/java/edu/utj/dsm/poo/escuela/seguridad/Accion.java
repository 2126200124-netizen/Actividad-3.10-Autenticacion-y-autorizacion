// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.seguridad;


public enum Accion {
    CREAR("crear", "crear registros"),
    LEER("leer", "consultar registros"),
    ACTUALIZAR("actualizar", "modificar registros"),
    ELIMINAR("eliminar", "eliminar registros");

    private final String columna;   
    private final String descripcion;

    Accion(String columna, String descripcion) {
        this.columna = columna;
        this.descripcion = descripcion;
    }

    public String columna() { return columna; }

    public String descripcion() { return descripcion; }
}
