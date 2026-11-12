// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.seguridad;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Hash de contraseñas con PBKDF2-HMAC-SHA256 y sal aleatoria.
 * Formato guardado en la BD: PBKDF2$iteraciones$sal(Base64)$hash(Base64)
 */
public final class Contrasenas {

    private static final int ITERACIONES = 120000;
    private static final int BITS_HASH = 256;

    private Contrasenas() { }

    public static String hash(String clave) {
        byte[] sal = new byte[16];
        new SecureRandom().nextBytes(sal);
        byte[] h = derivar(clave.toCharArray(), sal, ITERACIONES);
        return "PBKDF2$" + ITERACIONES + "$" + Base64.getEncoder().encodeToString(sal)
                + "$" + Base64.getEncoder().encodeToString(h);
    }

    public static boolean verificar(String clave, String almacenado) {
        if (clave == null || almacenado == null) return false;
        String[] p = almacenado.split("\\$");
        if (p.length != 4 || !"PBKDF2".equals(p[0])) return false;
        try {
            int iteraciones = Integer.parseInt(p[1]);
            byte[] sal = Base64.getDecoder().decode(p[2]);
            byte[] esperado = Base64.getDecoder().decode(p[3]);
            byte[] calculado = derivar(clave.toCharArray(), sal, iteraciones);
            return MessageDigest.isEqual(esperado, calculado);   // comparación en tiempo constante
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private static byte[] derivar(char[] clave, byte[] sal, int iteraciones) {
        try {
            PBEKeySpec spec = new PBEKeySpec(clave, sal, iteraciones, BITS_HASH);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new IllegalStateException("No se pudo calcular el hash de la contraseña", ex);
        }
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: Contrasenas <contraseña>");
            return;
        }
        System.out.println(hash(args[0]));
    }
}
