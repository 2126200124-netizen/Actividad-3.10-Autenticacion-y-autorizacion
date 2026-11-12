// Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * Manejo de imágenes: elegir con FileChooser, copiar a la carpeta "imagenes/" del proyecto,
 * cargar para mostrar en un ImageView y borrar el archivo.
 */
public final class Imagenes {


    private static final Path RAIZ = Paths.get("imagenes");

    private Imagenes() { }

 
    public static File elegir(Window ventana) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar imagen");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));
        return fc.showOpenDialog(ventana);
    }


    public static String copiar(File origen, String carpeta, String clave) throws IOException {
        String nombre = origen.getName();
        int punto = nombre.lastIndexOf('.');
        String extension = punto >= 0 ? nombre.substring(punto).toLowerCase(Locale.ROOT) : ".png";
        String limpio = clave.replaceAll("[^A-Za-z0-9_-]", "_");
        Path destino = RAIZ.resolve(carpeta).resolve(limpio + "_" + System.currentTimeMillis() + extension);
        Files.createDirectories(destino.getParent());
        Files.copy(origen.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
        return destino.toString().replace('\\', '/');
    }


    public static Image cargar(String ruta) {
        if (ruta == null || ruta.isBlank()) return null;
        return cargar(Paths.get(ruta).toFile());
    }

    public static Image cargar(File archivo) {
        if (archivo == null || !archivo.isFile()) return null;
        try (InputStream in = Files.newInputStream(archivo.toPath())) {
            return new Image(in, 400, 400, true, true);   
        } catch (IOException ex) {
            return null;
        }
    }


    public static void borrar(String ruta) {
        if (ruta == null || ruta.isBlank()) return;
        try {
            Path p = Paths.get(ruta).normalize();
            if (p.startsWith(RAIZ)) Files.deleteIfExists(p);
        } catch (IOException ex) {
            
        }
    }
}
