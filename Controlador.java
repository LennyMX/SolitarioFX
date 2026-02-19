package com.example.demoxxx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.example.demoxxx.solitaire.SolitaireGame;

public class Controlador {

    private SolitaireGame juego;
    private TableroFX tablero;

    private CartaFX cartaSeleccionada;
    private String  origenSeleccion;
    private int     indiceSeleccion;

    public Controlador(SolitaireGame juego) {
        this.juego = juego;
    }

    public void setTablero(TableroFX tablero) {
        this.tablero = tablero;
    }

    public void alClickearMazo() {
        limpiarSeleccion();
        if (juego.getDrawPile().hayCartas()) {
            juego.drawCards();
        } else {
            juego.reloadDrawPile();
        }
        tablero.actualizar();
    }

    public void alClickearWaste(CartaFX cv) {
        if (cartaSeleccionada == cv) {
            limpiarSeleccion();
            return;
        }
        limpiarSeleccion();
        cartaSeleccionada = cv;
        origenSeleccion   = "waste";
        indiceSeleccion   = -1;
        cv.setSeleccionada(true);
    }

    public void alClickearCartaTableau(CartaFX cv, int indiceTableau) {
        if (cartaSeleccionada == null) {
            if (cv.getCarta().isFaceup()) {
                cartaSeleccionada = cv;
                origenSeleccion   = "tableau";
                indiceSeleccion   = indiceTableau;
                cv.setSeleccionada(true);
            }
        } else {
            if (cartaSeleccionada == cv) {
                limpiarSeleccion();
                return;
            }
            boolean movido = false;
            if ("waste".equals(origenSeleccion)) {
                movido = juego.moveWasteToTableau(indiceTableau);
            } else if ("tableau".equals(origenSeleccion)) {
                if (indiceSeleccion != indiceTableau) {
                    movido = juego.moveTableauToTableau(indiceSeleccion, indiceTableau);
                }
            }
            limpiarSeleccion();
            if (movido) {
                tablero.actualizar();
                verificarFinJuego();
            }
        }
    }

    public void alClickearTableauVacio(int indiceTableau) {
        if (cartaSeleccionada == null) return;
        boolean movido = false;
        if ("waste".equals(origenSeleccion)) {
            movido = juego.moveWasteToTableau(indiceTableau);
        } else if ("tableau".equals(origenSeleccion)) {
            movido = juego.moveTableauToTableau(indiceSeleccion, indiceTableau);
        }
        limpiarSeleccion();
        if (movido) {
            tablero.actualizar();
            verificarFinJuego();
        }
    }

    public void alClickearFoundation() {
        if (cartaSeleccionada == null) return;

        boolean movido = false;
        if ("waste".equals(origenSeleccion)) {
            movido = juego.moveWasteToFoundation();
        } else if ("tableau".equals(origenSeleccion)) {
            movido = juego.moveTableauToFoundation(indiceSeleccion);
        }

        limpiarSeleccion();
        if (movido) {
            tablero.actualizar();
            verificarFinJuego();
        }
    }

    public void nuevaPartida() {
        limpiarSeleccion();
        juego = new SolitaireGame();
        tablero.setJuego(juego);
        tablero.actualizar();
    }

    public void limpiarSeleccion() {
        if (cartaSeleccionada != null) {
            cartaSeleccionada.setSeleccionada(false);
        }
        cartaSeleccionada = null;
        origenSeleccion   = null;
        indiceSeleccion   = -1;
    }

    private void verificarFinJuego() {
        if (juego.isGameOver()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alerta.setTitle("¡Ganaste!");
            alerta.setHeaderText("🎉 ¡Felicidades!");
            alerta.setContentText("Completaste el solitario. ¿Quieres jugar otra partida?");
            alerta.showAndWait();
            nuevaPartida();
        }
    }

    public SolitaireGame getJuego() {
        return juego;
    }
}
