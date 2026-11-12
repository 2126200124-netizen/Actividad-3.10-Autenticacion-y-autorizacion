// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import java.util.Objects;

/** Clase base de alumnos, docentes y administrativos. */
public abstract class Persona implements ConImagen {
    protected String nombre;
    protected String apellido;
    protected int edad;
    protected String telefono, RFC;
    protected String rutaImagen;

    public Persona(String nombre, String apellido, int edad, String telefono) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
    }

    public Persona(String nombre, String apellido, int edad, String telefono, String RFC) {
        this(nombre, apellido, edad, telefono);
        this.RFC = RFC;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getRFC() { return RFC; }
    public void setRFC(String RFC) { this.RFC = RFC; }
    @Override public String getRutaImagen() { return rutaImagen; }
    @Override public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    @Override
    public int hashCode() { return 17 * 7 + Objects.hashCode(this.RFC); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Objects.equals(this.RFC, ((Persona) obj).RFC);
    }

    @Override
    public String toString() {
        return "Persona{nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + ", telefono=" + telefono + ", RFC=" + RFC + '}';
    }
}
