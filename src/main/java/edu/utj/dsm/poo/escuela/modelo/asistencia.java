// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import java.time.LocalDate;
import java.util.Objects;

public class asistencia implements ConImagen {
    private int idAsistencia;
    private String matriculaAlumno;
    private int idMateria;
    private String nombreMateria;   // solo para mostrar en la tabla
    private String periodo;
    private LocalDate fecha;
    private String estatus = "Presente";
    private String rutaImagen;

    public asistencia(int idAsistencia, String matriculaAlumno, int idMateria, String periodo, LocalDate fecha, String estatus) {
        this.idAsistencia = idAsistencia;
        this.matriculaAlumno = matriculaAlumno;
        this.idMateria = idMateria;
        this.periodo = periodo;
        this.fecha = fecha;
        this.estatus = estatus;
    }

    public int getIdAsistencia() { return idAsistencia; }
    public void setIdAsistencia(int idAsistencia) { this.idAsistencia = idAsistencia; }
    public String getMatriculaAlumno() { return matriculaAlumno; }
    public void setMatriculaAlumno(String matriculaAlumno) { this.matriculaAlumno = matriculaAlumno; }
    public int getIdMateria() { return idMateria; }
    public void setIdMateria(int idMateria) { this.idMateria = idMateria; }
    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    public boolean isPresente() { return "Presente".equals(estatus) || "Retardo".equals(estatus); }
    @Override public String getRutaImagen() { return rutaImagen; }
    @Override public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return idAsistencia == ((asistencia) o).idAsistencia;
    }
    @Override public int hashCode() { return Objects.hash(idAsistencia); }
    @Override public String toString() {
        return "ID Asistencia: " + idAsistencia + ", Alumno: " + matriculaAlumno + ", Fecha: " + fecha + ", Estatus: " + estatus;
    }
}
