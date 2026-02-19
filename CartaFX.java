package com.example.demoxxx;

import com.example.demoxxx.DeckOfCards.CartaInglesa;
import com.example.demoxxx.DeckOfCards.Palo;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CartaFX extends StackPane {

    public static final double ANCHO = 100;
    public static final double ALTO  = 150;

    private CartaInglesa carta;
    private boolean seleccionada = false;
    private static Image imagenReverso;

    public CartaFX(CartaInglesa carta) {
        this.carta = carta;
        setPrefSize(ANCHO, ALTO);
        setMaxSize(ANCHO, ALTO);
        renderizar();
    }

    public void renderizar() {
        getChildren().clear();

        if (carta.isFaceup()) {
            String nombreImagen = construirNombreImagen(carta);
            Image img = cargarImagen("/cartas/" + nombreImagen + ".png");

            if (img != null) {
                ImageView iv = new ImageView(img);
                iv.setFitWidth(ANCHO);
                iv.setFitHeight(ALTO);
                iv.setPreserveRatio(false);
                getChildren().add(iv);
            } else {
                getChildren().add(construirFrenteTexto());
            }
        } else {
            Image img = obtenerImagenReverso();
            if (img != null) {
                ImageView iv = new ImageView(img);
                iv.setFitWidth(ANCHO);
                iv.setFitHeight(ALTO);
                iv.setPreserveRatio(false);
                getChildren().add(iv);
            } else {
                getChildren().add(construirReversoTexto());
            }
        }

        if (seleccionada) {
            DropShadow brillo = new DropShadow();
            brillo.setColor(Color.GOLD);
            brillo.setRadius(18);
            brillo.setSpread(0.6);
            setEffect(brillo);
        } else {
            setEffect(null);
        }
    }

    private String construirNombreImagen(CartaInglesa carta) {
        String valorStr;
        int valor = carta.getValor();

        if (valor == 14 || valor == 1) {
            valorStr = "A";
        } else if (valor == 11) {
            valorStr = "J";
        } else if (valor == 12) {
            valorStr = "Q";
        } else if (valor == 13) {
            valorStr = "K";
        } else {
            valorStr = String.valueOf(valor);
        }

        return valorStr + obtenerLetraPalo(carta.getPalo());
    }

    private String obtenerLetraPalo(Palo palo) {
        return switch (palo) {
            case TREBOL   -> "C";
            case DIAMANTE -> "D";
            case CORAZON  -> "H";
            case PICA     -> "S";
        };
    }

    private Image cargarImagen(String ruta) {
        try {
            var stream = getClass().getResourceAsStream(ruta);
            if (stream != null) {
                return new Image(stream);
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar imagen: " + ruta);
        }
        return null;
    }

    private Image obtenerImagenReverso() {
        if (imagenReverso == null) {
            imagenReverso = cargarImagen("/cartas/reverso.png");
        }
        return imagenReverso;
    }

    private StackPane construirFrenteTexto() {
        StackPane sp = new StackPane();
        Rectangle bg = new Rectangle(ANCHO, ALTO);
        bg.setArcWidth(8); bg.setArcHeight(8);
        bg.setFill(Color.WHITE);
        bg.setStroke(Color.LIGHTGRAY);
        javafx.scene.control.Label lbl = new javafx.scene.control.Label(
                construirNombreImagen(carta) + "\n" + carta.getPalo().getFigura());
        lbl.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-alignment: center;");
        lbl.setTextFill(carta.getColor().equals("rojo") ? Color.RED : Color.BLACK);
        sp.getChildren().addAll(bg, lbl);
        return sp;
    }

    private StackPane construirReversoTexto() {
        StackPane sp = new StackPane();
        Rectangle bg = new Rectangle(ANCHO, ALTO);
        bg.setArcWidth(8); bg.setArcHeight(8);
        bg.setFill(Color.DARKBLUE);
        bg.setStroke(Color.BLACK);
        sp.getChildren().add(bg);
        return sp;
    }

    public CartaInglesa getCarta() {
        return carta;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
        renderizar();
    }

}
