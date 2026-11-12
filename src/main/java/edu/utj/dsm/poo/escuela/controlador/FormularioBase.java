//Morones Torres Jorge Arnulfo
package edu.utj.dsm.poo.escuela.controlador;

import edu.utj.dsm.poo.escuela.modelo.ConImagen;
import edu.utj.dsm.poo.escuela.persistencia.listable;
import edu.utj.dsm.poo.escuela.seguridad.Accion;
import edu.utj.dsm.poo.escuela.seguridad.Sesion;
import edu.utj.dsm.poo.escuela.util.Imagenes;
import edu.utj.dsm.poo.escuela.util.Validaciones;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;


public abstract class FormularioBase<T extends ConImagen> implements Initializable {

    @FXML protected TableView<T> tabla;
    @FXML protected Button btnNuevo, btnValidar, btnGuardar, btnEditar, btnEliminar, btnLimpiar;
    @FXML protected ImageView imgFoto;

    private final List<Campo> campos = new ArrayList<>();
    private boolean soloLectura;   
    private File imagenElegida;   

    private static final class Campo {
        final Label error;
        final Supplier<String> regla;
        boolean tocado;

        Campo(Label error, Supplier<String> regla) {
            this.error = error;
            this.regla = regla;
        }

        boolean mostrar() {
            String mensaje = regla.get();
            error.setText(mensaje == null ? "" : mensaje);
            return mensaje == null;
        }
    }



    protected abstract listable<T> dao();
    protected abstract T leer();                 
    protected abstract String clave(T item);     
    protected abstract void configurar();       
    protected abstract void configurarTabla();   
    protected abstract void mostrar(T item);     
    protected abstract void limpiarCampos();

    protected void cargarCatalogos() { }                 
    protected void modoEdicion(boolean editando) { }     


    protected String carpeta() {
        return getClass().getSimpleName().replace("Controller", "").toLowerCase();
    }


    protected String modulo() {
        return getClass().getSimpleName().replace("Controller", "").toLowerCase();
    }

    

    @Override
    public final void initialize(URL url, ResourceBundle rb) {
        configurar();
        configurarTabla();
        aplicarPermisosVista();
        tabla.getSelectionModel().selectedItemProperty().addListener((o, v, n) -> {
            if (n != null) {
                mostrar(n);
                imagenElegida = null;
                ponerImagen(Imagenes.cargar(n.getRutaImagen()));
            }
            modoEdicionSeguro(n != null);
            actualizarBotones();
        });
        try {
            cargarCatalogos();
            refrescar();
        } catch (RuntimeException ex) {
            avisar(ex);
        }
        actualizarBotones();
    }

    protected void refrescar() {
        tabla.getItems().setAll(dao().listar());
    }



    protected void registrar(ObservableValue<?> origen, Label error, Supplier<String> regla,
                             ObservableValue<?>... dependencias) {
        Campo campo = new Campo(error, regla);
        campos.add(campo);
        origen.addListener((o, v, n) -> {
            campo.tocado = true;
            campo.mostrar();
            actualizarBotones();
        });
        for (ObservableValue<?> dep : dependencias) {
            dep.addListener((o, v, n) -> {
                if (campo.tocado) campo.mostrar();
                actualizarBotones();
            });
        }
    }

    protected void campoLetras(TextField tf, Label err, int max) {
        Validaciones.filtrar(tf, err, Validaciones.R_LETRAS, max, false, "Solo se permiten letras");
        registrar(tf.textProperty(), err, () -> Validaciones.letras(tf.getText()));
    }

    protected void campoTexto(TextField tf, Label err, int max) {
        Validaciones.filtrar(tf, err, Validaciones.R_TEXTO, max, false, "Solo letras, números y signos básicos");
        registrar(tf.textProperty(), err, () -> Validaciones.texto(tf.getText()));
    }

    protected void campoAlfanumerico(TextField tf, Label err, int max, String ejemplo) {
        Validaciones.filtrar(tf, err, Validaciones.R_ALFANUM, max, false, "Solo letras, números y guion");
        registrar(tf.textProperty(), err, () -> Validaciones.alfanumerico(tf.getText(), ejemplo));
    }

    protected void campoEntero(TextField tf, Label err, long min, long max) {
        Validaciones.filtrar(tf, err, Validaciones.R_DIGITOS, String.valueOf(max).length(),
                false, "Solo se permiten números enteros");
        registrar(tf.textProperty(), err, () -> Validaciones.entero(tf.getText(), min, max));
    }

    protected void campoDecimal(TextField tf, Label err, double min, double max) {
        Validaciones.filtrar(tf, err, Validaciones.R_DECIMAL, 6, false, "Solo números (ej. 8.5)");
        registrar(tf.textProperty(), err, () -> Validaciones.decimal(tf.getText(), min, max));
    }

    protected void campoTelefono(TextField tf, Label err) {
        Validaciones.filtrar(tf, err, Validaciones.R_DIGITOS, 10, false, "Solo se permiten dígitos");
        registrar(tf.textProperty(), err, () -> Validaciones.telefono(tf.getText()));
    }

    protected void campoRfc(TextField tf, Label err) {
        Validaciones.filtrar(tf, err, Validaciones.R_RFC, 13, true, "Solo letras y números");
        registrar(tf.textProperty(), err, () -> Validaciones.rfc(tf.getText()));
    }

    protected void campoCorreo(TextField tf, Label err) {
        registrar(tf.textProperty(), err, () -> Validaciones.correo(tf.getText()));
    }

    protected void campoSeleccion(ComboBox<?> cmb, Label err) {
        registrar(cmb.valueProperty(), err, () -> Validaciones.seleccion(cmb.getValue()));
    }

    protected void campoFecha(DatePicker dp, Label err) {
        registrar(dp.valueProperty(), err, () -> Validaciones.fecha(dp.getValue()));
    }

    protected boolean validarTodo() {
        boolean ok = true;
        for (Campo c : campos) {
            c.tocado = true;
            ok &= c.mostrar();
        }
        actualizarBotones();
        return ok;
    }

    protected boolean esValido() {
        return campos.stream().allMatch(c -> c.regla.get() == null);
    }




    @SuppressWarnings("unchecked")
    protected void col(int indice, Function<T, ?> valor) {
        TableColumn<T, String> c = (TableColumn<T, String>) tabla.getColumns().get(indice);
        c.setCellValueFactory(d -> new SimpleStringProperty(Objects.toString(valor.apply(d.getValue()), "")));
    }


    protected <E> void convertidor(ComboBox<E> cmb, Function<E, String> texto) {
        cmb.setConverter(new StringConverter<E>() {
            @Override public String toString(E e) { return e == null ? "" : texto.apply(e); }
            @Override public E fromString(String s) { return null; }
        });
    }

    protected static <E> E buscar(ComboBox<E> cmb, Predicate<E> criterio) {
        return cmb.getItems().stream().filter(criterio).findFirst().orElse(null);
    }



    protected void actualizarBotones() {
        boolean seleccionado = tabla.getSelectionModel().getSelectedItem() != null;
        boolean valido = esValido();

        btnGuardar.setDisable(seleccionado || !valido || !Sesion.puede(modulo(), Accion.CREAR));
        btnEditar.setDisable(!seleccionado || !valido || !Sesion.puede(modulo(), Accion.ACTUALIZAR));
        btnEliminar.setDisable(!seleccionado || !Sesion.puede(modulo(), Accion.ELIMINAR));
    }

    protected void limpiar() {
        limpiarCampos();
        for (Campo c : campos) {
            c.error.setText("");
            c.tocado = false;
        }
        imagenElegida = null;
        ponerImagen(null);
        tabla.getSelectionModel().clearSelection();
        modoEdicionSeguro(false);
        actualizarBotones();
    }

    @FXML protected void onNuevo() { limpiar(); }

    @FXML protected void onLimpiar() { limpiar(); }

    @FXML
    protected void onValidar() {
        if (validarTodo()) {
            alerta(AlertType.INFORMATION, "Validación", "Todos los datos cumplen las validaciones.");
        }
    }

    @FXML
    protected void onSeleccionarImagen() {
        File f = Imagenes.elegir(tabla.getScene().getWindow());
        if (f == null) return;
        imagenElegida = f;
        ponerImagen(Imagenes.cargar(f));
    }

    @FXML
    protected void onGuardar() {
        if (!autorizar(Accion.CREAR) || !validarTodo()) return;
        String ruta = null;
        try {
            T nuevo = leer();
            if (imagenElegida != null) ruta = Imagenes.copiar(imagenElegida, carpeta(), clave(nuevo));
            nuevo.setRutaImagen(ruta);
            if (dao().agregar(nuevo)) {
                terminar("Registro guardado correctamente.");
            } else {
                Imagenes.borrar(ruta);
                alerta(AlertType.WARNING, "Guardar", "No se guardó ningún registro.");
            }
        } catch (IOException ex) {
            alerta(AlertType.ERROR, "Imagen", "No se pudo copiar la imagen: " + ex.getMessage());
        } catch (RuntimeException ex) {
            Imagenes.borrar(ruta);   
            alerta(AlertType.ERROR, "Error", ex.getMessage());
        }
    }

    
    @FXML
    protected void onEditar() {
        if (!autorizar(Accion.ACTUALIZAR)) return;
        T seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null || !validarTodo()) return;
        String nueva = null;
        try {
            T editado = leer();
            if (imagenElegida != null) nueva = Imagenes.copiar(imagenElegida, carpeta(), clave(editado));
            editado.setRutaImagen(nueva != null ? nueva : seleccionado.getRutaImagen());
            if (dao().actualizar(editado)) {
                if (nueva != null) Imagenes.borrar(seleccionado.getRutaImagen());
                terminar("Registro actualizado correctamente.");
            } else {
                Imagenes.borrar(nueva);
                alerta(AlertType.WARNING, "Editar", "No se actualizó ningún registro.");
            }
        } catch (IOException ex) {
            alerta(AlertType.ERROR, "Imagen", "No se pudo copiar la imagen: " + ex.getMessage());
        } catch (RuntimeException ex) {
            Imagenes.borrar(nueva);
            alerta(AlertType.ERROR, "Error", ex.getMessage());
        }
    }


    @FXML
    protected void onEliminar() {
        if (!autorizar(Accion.ELIMINAR)) return;
        T seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        Alert a = new Alert(AlertType.CONFIRMATION,
                "¿Eliminar definitivamente el registro seleccionado? Esta acción no se puede deshacer.",
                ButtonType.OK, ButtonType.CANCEL);
        a.setTitle("Eliminar");
        a.setHeaderText(null);
        conDueno(a);
        if (a.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) return;
        try {
            if (dao().eliminar(seleccionado)) {
                Imagenes.borrar(seleccionado.getRutaImagen());
                terminar("Registro eliminado correctamente.");
            } else {
                alerta(AlertType.WARNING, "Eliminar", "No se eliminó ningún registro.");
            }
        } catch (RuntimeException ex) {
            alerta(AlertType.ERROR, "Error", ex.getMessage());
        }
    }




    private void aplicarPermisosVista() {
        soloLectura = !Sesion.puede(modulo(), Accion.CREAR) && !Sesion.puede(modulo(), Accion.ACTUALIZAR);
        if (soloLectura && tabla.getParent() != null) {
            for (Node n : tabla.getParent().getChildrenUnmodifiable()) {
                if (n != tabla && n instanceof Control && !(n instanceof Label)) n.setDisable(true);
            }
        }
    }


    private void modoEdicionSeguro(boolean editando) {
        if (!soloLectura) modoEdicion(editando);
    }


    private boolean autorizar(Accion accion) {
        if (Sesion.puede(modulo(), accion)) return true;
        alerta(AlertType.WARNING, "Sin permiso", "Tu perfil (" + Sesion.nombreRol()
                + ") no tiene permiso para " + accion.descripcion() + " en este módulo.");
        return false;
    }



    private void terminar(String mensaje) {
        limpiar();
        try {
            refrescar();
        } catch (RuntimeException ex) {
            alerta(AlertType.ERROR, "Base de datos", ex.getMessage());
            return;
        }
        alerta(AlertType.INFORMATION, "Listo", mensaje);
    }

    private void ponerImagen(Image imagen) {
        if (imgFoto != null) imgFoto.setImage(imagen);
    }


    private void avisar(RuntimeException ex) {
        Platform.runLater(() -> alerta(AlertType.ERROR, "Base de datos", ex.getMessage()));
    }

    protected void alerta(AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        conDueno(a);
        a.showAndWait();
    }


    private void conDueno(Alert a) {
        if (tabla != null && tabla.getScene() != null && tabla.getScene().getWindow() != null) {
            a.initOwner(tabla.getScene().getWindow());
        }
    }
}
