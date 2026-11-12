// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import java.util.Objects;

/** Docente. */
public class Docente extends Persona {
    private String numDocente;
    private String especialidad;
    private Integer idDepartamento;
    private String estatus = "Activo";

    public Docente(String nombre, String apellido, int edad, String telefono, String numDocente, String especialidad) {
        super(nombre, apellido, edad, telefono);
        this.numDocente = numDocente;
        this.especialidad = especialidad;
    }

    public String getNumDocente() { return numDocente; }
    public void setNumDocente(String numDocente) { this.numDocente = numDocente; }
    public String getespecialidad() { return especialidad; }
    public void setespecialidad(String especialidad) { this.especialidad = especialidad; }
    public Integer getIdDepartamento() { return idDepartamento; }
    public void setIdDepartamento(Integer idDepartamento) { this.idDepartamento = idDepartamento; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    @Override
    public int hashCode() { return 89 * 7 + Objects.hashCode(this.numDocente); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return Objects.equals(this.numDocente, ((Docente) obj).numDocente);
    }

    @Override
    public String toString() {
        return super.toString() + "Docente{numDocente=" + numDocente + ", especialidad=" + especialidad + ", estatus=" + estatus + '}';
    }
}
