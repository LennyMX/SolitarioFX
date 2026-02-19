package com.example.demoxxx;

import com.example.demoxxx.DeckOfCards.CartaInglesa;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.demoxxx.solitaire.FoundationDeck;
import com.example.demoxxx.solitaire.SolitaireGame;
import com.example.demoxxx.solitaire.TableauDeck;

import java.util.ArrayList;

public class TableroFX extends BorderPane {

    private Controlador controlador;
    private SolitaireGame juego;

    private StackPane panelMazo;
    private StackPane panelWaste;
    private StackPane[] panelesFundacion = new StackPane[4];
    private Pane[] panelesTableau = new Pane[7];

    private static final double OFFSET_BOCA_ABAJO  = 28;
    private static final double OFFSET_BOCA_ARRIBA = 40;

    public TableroFX(Controlador controlador) {
        this.controlador = controlador;
        this.juego = controlador.getJuego();
        setBackground(new Background(new BackgroundFill(
                Color.web("#1a6b35"), CornerRadii.EMPTY, Insets.EMPTY)));
        construirBarraSuperior();
        construirAreaTableaux();
        actualizar();
    }

    private void construirBarraSuperior() {
        HBox barraSuperior = new HBox(12);
        barraSuperior.setPadding(new Insets(18, 20, 12, 20));
        barraSuperior.setAlignment(Pos.CENTER_LEFT);

        Button btnNueva = new Button("🔄  Nueva Partida");
        btnNueva.setStyle(
                "-fx-background-color: #c8a84b; -fx-text-fill: #1a1a1a; " +
                        "-fx-font-size: 13px; -fx-font-weight: bold; " +
                        "-fx-background-radius: 6; -fx-padding: 7 14 7 14; -fx-cursor: hand;");
        btnNueva.setOnAction(e -> controlador.nuevaPartida());

        Button btnFundacion = new Button("⬆ Enviar a Fundación");
        btnFundacion.setStyle(
                "-fx-background-color: #4a90d9; -fx-text-fill: white; " +
                        "-fx-font-size: 13px; -fx-font-weight: bold; " +
                        "-fx-background-radius: 6; -fx-padding: 7 14 7 14; -fx-cursor: hand;");
        btnFundacion.setOnAction(e -> controlador.alClickearFoundation());

        panelMazo  = new StackPane();
        panelMazo.setPrefSize(CartaFX.ANCHO + 4, CartaFX.ALTO + 4);

        panelWaste = new StackPane();
        panelWaste.setPrefSize(CartaFX.ANCHO + 4, CartaFX.ALTO + 4);

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        HBox cajaFundaciones = new HBox(8);
        cajaFundaciones.setAlignment(Pos.CENTER);
        for (int i = 0; i < 4; i++) {
            panelesFundacion[i] = new StackPane();
            panelesFundacion[i].setPrefSize(CartaFX.ANCHO + 4, CartaFX.ALTO + 4);
            cajaFundaciones.getChildren().add(panelesFundacion[i]);
        }

        barraSuperior.getChildren().addAll(btnNueva, btnFundacion, panelMazo, panelWaste, espaciador, cajaFundaciones);
        setTop(barraSuperior);
    }

    private void construirAreaTableaux() {
        HBox areaTableaux = new HBox(14);
        areaTableaux.setPadding(new Insets(8, 20, 20, 20));
        areaTableaux.setAlignment(Pos.TOP_CENTER);

        for (int i = 0; i < 7; i++) {
            Pane panel = new Pane();
            panel.setPrefWidth(CartaFX.ANCHO + 4);
            panel.setMinHeight(CartaFX.ALTO + 4);
            panelesTableau[i] = panel;

            StackPane contenedor = new StackPane(panel);
            contenedor.setAlignment(Pos.TOP_CENTER);
            contenedor.setPrefWidth(CartaFX.ANCHO + 4);
            HBox.setHgrow(contenedor, Priority.ALWAYS);
            areaTableaux.getChildren().add(contenedor);
        }

        setCenter(areaTableaux);
    }

    public void actualizar() {
        renderizarMazo();
        renderizarWaste();
        renderizarFundaciones();
        renderizarTableaux();
    }

    public void setJuego(SolitaireGame juego) {
        this.juego = juego;
    }

    private void renderizarMazo() {
        panelMazo.getChildren().clear();
        panelMazo.setOnMouseClicked(null);

        if (juego.getDrawPile().hayCartas()) {
            StackPane reverso = construirReverso();
            reverso.setOnMouseClicked(e -> controlador.alClickearMazo());
            reverso.setStyle("-fx-cursor: hand;");
            panelMazo.getChildren().add(reverso);
        } else {
            Label lbl = new Label("↺");
            lbl.setStyle("-fx-text-fill: rgba(255,255,255,0.6); -fx-font-size: 28px;");
            lbl.setMouseTransparent(true);
            panelMazo.getChildren().add(lbl);
            panelMazo.setOnMouseClicked(e -> controlador.alClickearMazo());
        }
    }

    private void renderizarWaste() {
        panelWaste.getChildren().clear();
        panelWaste.setOnMouseClicked(null);

        CartaInglesa top = juego.getWastePile().verCarta();
        if (top != null) {
            top.makeFaceUp();
            CartaFX cv = new CartaFX(top);
            cv.setStyle("-fx-cursor: hand;");
            cv.setOnMouseClicked(e -> {
                e.consume();
                controlador.alClickearWaste(cv);
            });
            panelWaste.getChildren().add(cv);
        }
    }

    private void renderizarFundaciones() {
        ArrayList<FoundationDeck> fundaciones = juego.getFoundation();

        for (int i = 0; i < 4; i++) {
            panelesFundacion[i].getChildren().clear();
            panelesFundacion[i].setOnMouseClicked(null);

            FoundationDeck fd = fundaciones.get(i);
            CartaInglesa top = fd.getUltimaCarta();

            if (top != null) {
                top.makeFaceUp();
                CartaFX cv = new CartaFX(top);
                cv.setMouseTransparent(true);
                panelesFundacion[i].getChildren().add(cv);
            } else {
                String[] palos = {"♣", "♦", "❤", "♠"};
                Label lbl = new Label(palos[i]);
                lbl.setStyle("-fx-text-fill: rgba(255,255,255,0.4); -fx-font-size: 22px;");
                lbl.setMouseTransparent(true);
                panelesFundacion[i].getChildren().add(lbl);
            }
        }
    }

    private void renderizarTableaux() {
        ArrayList<TableauDeck> tableaux = juego.getTableau();

        for (int i = 0; i < 7; i++) {
            Pane panel = panelesTableau[i];
            panel.getChildren().clear();

            TableauDeck td = tableaux.get(i);
            ArrayList<CartaInglesa> cartas = td.getCards();
            final int indiceTableau = i + 1;

            if (cartas.isEmpty()) {
                Rectangle invisible = new Rectangle(CartaFX.ANCHO, CartaFX.ALTO);
                invisible.setFill(Color.TRANSPARENT);
                invisible.setOnMouseClicked(e -> controlador.alClickearTableauVacio(indiceTableau));
                invisible.setStyle("-fx-cursor: hand;");
                panel.getChildren().add(invisible);
                panel.setMinHeight(CartaFX.ALTO);
            } else {
                panel.setMinHeight(calcularAlturaTableau(cartas));

                double offsetY = 0;
                for (int j = 0; j < cartas.size(); j++) {
                    CartaInglesa carta = cartas.get(j);
                    CartaFX cv = new CartaFX(carta);
                    cv.setLayoutX(0);
                    cv.setLayoutY(offsetY);
                    cv.setStyle("-fx-cursor: hand;");

                    final CartaFX finalCv = cv;
                    cv.setOnMouseClicked(e -> {
                        e.consume();
                        controlador.alClickearCartaTableau(finalCv, indiceTableau);
                    });

                    panel.getChildren().add(cv);

                    if (j < cartas.size() - 1) {
                        offsetY += carta.isFaceup() ? OFFSET_BOCA_ARRIBA : OFFSET_BOCA_ABAJO;
                    }
                }
            }
        }
    }

    private double calcularAlturaTableau(ArrayList<CartaInglesa> cartas) {
        double altura = CartaFX.ALTO;
        for (int i = 0; i < cartas.size() - 1; i++) {
            altura += cartas.get(i).isFaceup() ? OFFSET_BOCA_ARRIBA : OFFSET_BOCA_ABAJO;
        }
        return altura + 10;
    }

    private StackPane construirReverso() {
        StackPane sp = new StackPane();
        sp.setPrefSize(CartaFX.ANCHO, CartaFX.ALTO);
        try {
            var stream = getClass().getResourceAsStream("/cartas/reverso.png");
            if (stream != null) {
                javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(
                        new javafx.scene.image.Image(stream));
                iv.setFitWidth(CartaFX.ANCHO);
                iv.setFitHeight(CartaFX.ALTO);
                iv.setPreserveRatio(false);
                sp.getChildren().add(iv);
            }
        } catch (Exception e) {
            System.err.println("No se encontró reverso.png");
        }
        return sp;
    }
}
