// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import java.util.Objects;

public class aula implements ConImagen {
    private int idAula;
    private String numeroAula;
    private int capacidad;
    private String estatus = "Disponible";
    private String rutaImagen;

    public aula() { this(0, "", 0); }

    public aula(int idAula, String numeroAula, int capacidad) {
        this.idAula = idAula;
        this.numeroAula = numeroAula;
        this.capacidad = capacidad;
    }

    public void ocuparAula() { this.estatus = "Ocupada"; }
    public void liberarAula() { this.estatus = "Disponible"; }

    public int getIdAula() { return idAula; }
    public void setIdAula(int idAula) { this.idAula = idAula; }
    public String getNumeroAula() { return numeroAula; }
    public void setNumeroAula(String numeroAula) { this.numeroAula = numeroAula; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    public boolean isEstaDisponible() { return "Disponible".equals(estatus); }
    public void setEstaDisponible(boolean disponible) { this.estatus = disponible ? "Disponible" : "Ocupada"; }
    @Override public String getRutaImagen() { return rutaImagen; }
    @Override public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return idAula == ((aula) o).idAula;
    }
    @Override public int hashCode() { return Objects.hash(idAula); }
    @Override public String toString() {
        return "ID Aula: " + idAula + ", Número: " + numeroAula + ", Capacidad: " + capacidad + ", Estatus: " + estatus;
    }
}
