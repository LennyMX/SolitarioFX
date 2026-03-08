package com.example.demoxxx.solitaire;

import com.example.demoxxx.DeckOfCards.CartaInglesa;
import com.example.demoxxx.Pila;

import java.util.ArrayList;

/**
 * Modela el montículo donde se colocan las cartas
 * que se extraen de Draw pile.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */
public class WastePile {
    private Pila<CartaInglesa> cartas;

    public WastePile() {
        cartas = new Pila<>(52);;
    }

    public void addCartas(ArrayList<CartaInglesa> nuevas) {
        for (CartaInglesa carta : nuevas) {
            cartas.push(carta);
        }
    }


    public ArrayList<CartaInglesa> emptyPile() {
        ArrayList<CartaInglesa> pile = new ArrayList<>();
        while (!cartas.pilaVacia()) {
            pile.add(cartas.pop());
        }
        return pile;
    }

    /**
     * Obtener la última carta sin removerla.
     * @return Carta que está encima. Si está vacía, es null.
     */
    public CartaInglesa verCarta() {
        if (!cartas.pilaVacia()) {
            return cartas.peek();
        }
        return null;
    }
    public CartaInglesa getCarta() {
        if (!cartas.pilaVacia()) {
            return cartas.pop();
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        if (cartas.pilaVacia()) {
            stb.append("---");
        } else {
            CartaInglesa regresar = cartas.peek();
            regresar.makeFaceUp();
            stb.append(regresar.toString());
        }
        return stb.toString();
    }

    public boolean hayCartas() {
        return !cartas.pilaVacia();
    }
}
