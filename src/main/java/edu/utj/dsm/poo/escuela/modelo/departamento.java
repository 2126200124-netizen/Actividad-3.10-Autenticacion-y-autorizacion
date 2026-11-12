// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

import java.util.Objects;

public class departamento implements ConImagen {
    private int idDepartamento;
    private String nombreDepartamento;
    private String edificio;
    private String estatus = "Activo";
    private String rutaImagen;

    public departamento() { this(0, "", ""); }

    public departamento(int idDepartamento, String nombreDepartamento, String edificio) {
        this.idDepartamento = idDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.edificio = edificio;
    }

    public int getIdDepartamento() { return idDepartamento; }
    public void setIdDepartamento(int idDepartamento) { this.idDepartamento = idDepartamento; }
    public String getNombreDepartamento() { return nombreDepartamento; }
    public void setNombreDepartamento(String nombreDepartamento) { this.nombreDepartamento = nombreDepartamento; }
    public String getEdificio() { return edificio; }
    public void setEdificio(String edificio) { this.edificio = edificio; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
    @Override public String getRutaImagen() { return rutaImagen; }
    @Override public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return idDepartamento == ((departamento) o).idDepartamento;
    }
    @Override public int hashCode() { return Objects.hash(idDepartamento); }
    @Override public String toString() {
        return "ID Departamento: " + idDepartamento + ", Nombre: " + nombreDepartamento + ", Edificio: " + edificio;
    }
}
