package com.example.demoxxx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.demoxxx.solitaire.SolitaireGame;

public class SolitarioApp extends Application {

    @Override
    public void start(Stage escenario) {
        SolitaireGame juego = new SolitaireGame();
        Controlador controlador = new Controlador(juego);
        TableroFX tablero = new TableroFX(controlador);
        controlador.setTablero(tablero);

        Scene escena = new Scene(tablero, 1280, 1000);
        escenario.setTitle("Solitario");
        escenario.setScene(escena);
        escenario.setResizable(false);
        escenario.show();
    }
}
