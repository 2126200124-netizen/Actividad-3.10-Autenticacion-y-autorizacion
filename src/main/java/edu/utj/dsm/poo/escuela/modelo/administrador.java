// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

/** Personal administrativo. */
public class administrador extends Persona {
    private String turno;
    private String area;
    private int idAdministrador;
    private String estatus = "Activo";

    public administrador(String nombre, String apellido, int edad, String telefono, String turno, String area) {
        super(nombre, apellido, edad, telefono);
        this.area = area;
        this.turno = turno;
        this.idAdministrador = 0;
    }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public int getidAdministrador() { return idAdministrador; }
    public void setidAdministrador(int idAdministrador) { this.idAdministrador = idAdministrador; }
    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
}
