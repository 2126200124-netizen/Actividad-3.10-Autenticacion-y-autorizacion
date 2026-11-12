// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import java.util.Objects;

public class calificacion implements ConImagen {
    private int idCalificaciones;
    private String matriculaAlumno;
    private int idMateria;
    private String nombreMateria;   // solo para mostrar en la tabla
    private String periodo;
    private double valorCalificacion;
    private String rutaImagen;

    public calificacion(int idCalificaciones, String matriculaAlumno, int idMateria, String periodo, double valorCalificacion) {
        this.idCalificaciones = idCalificaciones;
        this.matriculaAlumno = matriculaAlumno;
        this.idMateria = idMateria;
        this.periodo = periodo;
        this.valorCalificacion = valorCalificacion;
    }

    public boolean estaAprobada() { return this.valorCalificacion >= 70.0; }

    public int getidCalificaciones() { return idCalificaciones; }
    public void setidCalificaciones(int idCalificaciones) { this.idCalificaciones = idCalificaciones; }
    public String getMatriculaAlumno() { return matriculaAlumno; }
    public void setMatriculaAlumno(String matriculaAlumno) { this.matriculaAlumno = matriculaAlumno; }
    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }
    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public double getValorCalificacion() { return valorCalificacion; }
    public void setValorCalificacion(double valorCalificacion) { this.valorCalificacion = valorCalificacion; }
    @Override public String getRutaImagen() { return rutaImagen; }
    @Override public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return idCalificaciones == ((calificacion) o).idCalificaciones;
    }
    @Override public int hashCode() { return Objects.hash(idCalificaciones); }
    @Override public String toString() {
        return "ID Calificación: " + idCalificaciones + ", Materia: " + idMateria + ", Calificación: " + valorCalificacion
                + ", Periodo: " + periodo + ", Alumno: " + matriculaAlumno;
    }
}
