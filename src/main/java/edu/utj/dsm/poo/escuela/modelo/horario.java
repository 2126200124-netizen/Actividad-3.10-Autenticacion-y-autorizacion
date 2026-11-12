// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import java.util.Objects;

/** Horas en formato HH:mm. */
public class horario implements ConImagen {
    private int idHorario;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String rutaImagen;

    public horario() { this(0, "", "", ""); }

    public horario(int idHorario, String diaSemana, String horaInicio, String horaFin) {
        this.idHorario = idHorario;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
    @Override public String getRutaImagen() { return rutaImagen; }
    @Override public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return idHorario == ((horario) o).idHorario;
    }
    @Override public int hashCode() { return Objects.hash(idHorario); }
    @Override public String toString() {
        return "ID Horario: " + idHorario + ", Días: " + diaSemana + " de " + horaInicio + " a " + horaFin;
    }
}
