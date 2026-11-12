// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import java.util.Objects;

/** Alumno. 'calificacion' es el promedio calculado desde la tabla calificacion (solo lectura). */
public final class Alumno extends Persona {
    private String matricula;
    private String grado;
    private double calificacion;
    private String estatus = "Activo";

    public Alumno(String nombre, String apellido, int edad, String telefono, String matricula, String grado, double calificacion) {
        super(nombre, apellido, edad, telefono);
        this.grado = grado;
        this.matricula = matricula;
        this.calificacion = calificacion;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }
    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    @Override
    public int hashCode() { return 83 * 5 + Objects.hashCode(this.matricula); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Objects.equals(this.matricula, ((Alumno) obj).matricula);
    }

    @Override
    public String toString() {
        return super.toString() + "Alumno{matricula=" + matricula + ", grado=" + grado + ", calificacion=" + calificacion + ", estatus=" + estatus + '}';
    }
}
