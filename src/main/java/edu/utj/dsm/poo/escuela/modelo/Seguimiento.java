// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

/** Fila de la vista v_seguimiento (calificación + asistencia). Solo lectura. */
public class Seguimiento {
    private final String matricula;
    private final String alumno;
    private final String materia;
    private final String periodo;
    private final double calificacion;
    private final String resultado;
    private final int clases;
    private final Double pctAsistencia;   // null si no hay asistencias registradas

    public Seguimiento(String matricula, String alumno, String materia, String periodo,
                       double calificacion, String resultado, int clases, Double pctAsistencia) {
        this.matricula = matricula;
        this.alumno = alumno;
        this.materia = materia;
        this.periodo = periodo;
        this.calificacion = calificacion;
        this.resultado = resultado;
        this.clases = clases;
        this.pctAsistencia = pctAsistencia;
    }

    public String getMatricula() { return matricula; }
    public String getAlumno() { return alumno; }
    public String getMateria() { return materia; }
    public String getPeriodo() { return periodo; }
    public double getCalificacion() { return calificacion; }
    public String getResultado() { return resultado; }
    public int getClases() { return clases; }
    public Double getPctAsistencia() { return pctAsistencia; }
}
