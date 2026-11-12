// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import java.util.Objects;

public class materia implements ConImagen {
    private int idMateria;
    private String codigo;
    private String nombre;
    private int creditos;
    private String estatus = "Activa";
    private String rutaImagen;

    public materia() { this(0, "", "", 0); }

    public materia(int idMateria, String codigo, String nombre, int creditos) {
        this.idMateria = idMateria;
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public void mostrarDetallesMateria() {
        System.out.println("Materia: " + nombre + " (" + codigo + ") - Créditos: " + creditos);
    }

    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    @Override public String getRutaImagen() { return rutaImagen; }
    @Override public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return idMateria == ((materia) o).idMateria;
    }
    @Override public int hashCode() { return Objects.hash(idMateria); }
    @Override public String toString() {
        return "ID Materia: " + idMateria + ", Código: " + codigo + ", Nombre: " + nombre + ", Créditos: " + creditos;
    }
}
