package lib;

import java.util.*;

public class Grafo <T> {
    private ArrayList<Aresta> arestas;
    private ArrayList<Vertice<T>> vertices;

    public Grafo () {
        this.arestas = new ArrayList<Aresta>();
        this.vertices = new ArrayList<Vertice<T>>();
    }
}
