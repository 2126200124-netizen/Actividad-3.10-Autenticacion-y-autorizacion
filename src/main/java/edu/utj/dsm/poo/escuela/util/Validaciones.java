package edu.utj.dsm.poo.escuela.util;

import java.time.LocalDate;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


public final class Validaciones {

    public static final String OBLIGATORIO = "Este campo es obligatorio";

    
    public static final String R_LETRAS = "[\\p{L} .'-]*";
    public static final String R_TEXTO = "[\\p{L}\\d .,'-]*";
    public static final String R_DIGITOS = "\\d*";
    public static final String R_DECIMAL = "\\d*(\\.\\d*)?";
    public static final String R_ALFANUM = "[A-Za-z0-9-]*";
    public static final String R_RFC = "[A-Z0-9&Ñ]*";
    public static final String R_HORA = "[0-9:]*";

    private Validaciones() { }

   
    public static void filtrar(TextField tf, Label error, String regex, int max,
                               boolean mayusculas, String mensaje) {
        tf.setTextFormatter(new TextFormatter<String>(c -> {
            if (!c.isContentChange()) return c;
            if (mayusculas) c.setText(c.getText().toUpperCase());
            String nuevo = c.getControlNewText();
            if (nuevo.length() > max) {
                error.setText("Máximo " + max + " caracteres");
                return null;
            }
            if (!nuevo.matches(regex)) {
                error.setText(mensaje);
                return null;
            }
            return c;
        }));
    }

  
    public static String obligatorio(String t) {
        return (t == null || t.trim().isEmpty()) ? OBLIGATORIO : null;
    }

    public static String letras(String t) {
        String e = obligatorio(t);
        if (e != null) return e;
        return t.trim().matches("[\\p{L} .'-]+") ? null : "Solo se permiten letras y espacios";
    }

    public static String texto(String t) {
        String e = obligatorio(t);
        if (e != null) return e;
        return t.trim().matches("[\\p{L}\\d .,'-]+") ? null : "Solo letras, números y signos básicos";
    }

    public static String alfanumerico(String t, String ejemplo) {
        String e = obligatorio(t);
        if (e != null) return e;
        return t.trim().matches("[A-Za-z0-9-]+") ? null : "Formato inválido (ej. " + ejemplo + ")";
    }

    public static String entero(String t, long min, long max) {
        String e = obligatorio(t);
        if (e != null) return e;
        if (!t.trim().matches("\\d+")) return "Ingresa un número entero (ej. 20)";
        try {
            long v = Long.parseLong(t.trim());
            if (v < min || v > max) return "Debe estar entre " + min + " y " + max;
        } catch (NumberFormatException ex) {
            return "Número demasiado grande";
        }
        return null;
    }

    public static String decimal(String t, double min, double max) {
        String e = obligatorio(t);
        if (e != null) return e;
        if (!t.trim().matches("\\d+(\\.\\d+)?")) return "Ingresa un decimal válido (ej. 8.5)";
        double v = Double.parseDouble(t.trim());
        if (v < min || v > max) return "Debe estar entre " + fmt(min) + " y " + fmt(max);
        return null;
    }

    public static String telefono(String t) {
        String e = obligatorio(t);
        if (e != null) return e;
        return t.trim().matches("\\d{10}") ? null : "Debe tener 10 dígitos";
    }

    public static String rfc(String t) {
        String e = obligatorio(t);
        if (e != null) return e;
        return t.trim().toUpperCase().matches("[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}")
                ? null : "RFC inválido (ej. ABCD123456XYZ)";
    }

    public static String correo(String t) {
        String e = obligatorio(t);
        if (e != null) return e;
        return t.trim().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")
                ? null : "Formato válido: usuario@dominio.com";
    }

    public static String hora(String t) {
        String e = obligatorio(t);
        if (e != null) return e;
        return t.trim().matches("([01]\\d|2[0-3]):[0-5]\\d") ? null : "Formato HH:mm (ej. 08:00)";
    }

    public static String seleccion(Object valor) {
        return valor == null ? "Selecciona una opción" : null;
    }

    public static String fecha(LocalDate d) {
        return d == null ? "Selecciona una fecha" : null;
    }

  
    public static int minutos(String hhmm) {
        String[] p = hhmm.trim().split(":");
        return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
    }

    private static String fmt(double v) {
        return v == Math.rint(v) ? String.valueOf((long) v) : String.valueOf(v);
    }
}