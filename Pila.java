package com.example.demoxxx;

public class Pila<T> {

    private T[] pila;
    private int tope = -1;
    public Pila(int cantidad) {
        pila = (T[]) new Object[cantidad];
    }


    public void push(T dato) {
        if (pilaLlena()) {
            System.out.println("Desbordamiento");
        } else {
            tope++;
            pila[tope] = dato;
        }
    }


    public T pop() {
        if (pilaVacia()) {
            System.out.println("Subdesbordamiento");
            return null;
        } else {
            T dato = pila[tope];
            pila[tope] = null;
            tope--;
            return dato;
        }
    }

    public boolean pilaLlena() {
        return tope == pila.length - 1;
    }


    public boolean pilaVacia() {
        return tope == -1;
    }
    public T peek() {
        if (pilaVacia()) {
            return null;
        }
        return pila[tope];
    }
}
