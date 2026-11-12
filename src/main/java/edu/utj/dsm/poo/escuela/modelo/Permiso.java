// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import edu.utj.dsm.poo.escuela.seguridad.Accion;

/** Permisos de un rol sobre una tabla/módulo. */
public class Permiso {
    private final String modulo;
    private final boolean crear;
    private final boolean leer;
    private final boolean actualizar;
    private final boolean eliminar;

    public Permiso(String modulo, boolean crear, boolean leer, boolean actualizar, boolean eliminar) {
        this.modulo = modulo;
        this.crear = crear;
        this.leer = leer;
        this.actualizar = actualizar;
        this.eliminar = eliminar;
    }

    public String getModulo() { return modulo; }

    public boolean permite(Accion accion) {
        switch (accion) {
            case CREAR: return crear;
            case LEER: return leer;
            case ACTUALIZAR: return actualizar;
            case ELIMINAR: return eliminar;
            default: return false;
        }
    }
}
