// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.modelo;

/** Cuenta de acceso al sistema (no incluye la contraseña). */
public class Usuario {
    private final int idUsuario;
    private final String nombre;
    private final String correo;
    private final int idRol;
    private final String rol;
    private final String estatus;

    public Usuario(int idUsuario, String nombre, String correo, int idRol, String rol, String estatus) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.idRol = idRol;
        this.rol = rol;
        this.estatus = estatus;
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public int getIdRol() { return idRol; }
    public String getRol() { return rol; }
    public String getEstatus() { return estatus; }
    public boolean estaActivo() { return "Activo".equals(estatus); }
}
