package com.example.demoxxx;

import com.example.demoxxx.DeckOfCards.CartaInglesa;

import java.util.ArrayList;

public class Movimiento {

    private String tipo;
    private Object origen;
    private Object destino;
    private ArrayList<CartaInglesa> cartas;

    public Movimiento(String tipo, Object origen, Object destino, ArrayList<CartaInglesa> cartas) {
        this.tipo = tipo;
        this.origen = origen;
        this.destino = destino;
        this.cartas = cartas;
    }

    public String getTipo() {
        return tipo;
    }

    public Object getOrigen() {
        return origen;
    }

    public Object getDestino() {
        return destino;
    }

    public ArrayList<CartaInglesa> getCartas() {
        return cartas;
    }
}
