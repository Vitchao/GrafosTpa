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

public void adicionarAresta(T origem, T destino, float peso) {
    Vertice origemVert, destinoVert;
    Aresta novaAresta;

    origemVert = this.obterVertice(origem);
    if (origemVert == null) {
        origemVert = this.adicionarVertice(origem);
    }

    destinoVert = obterVertice(destino);
    if (destinoVert == null) {
        destinoVert = this.adicionarVertice(destino);
    }

    novaAresta = new Aresta(origemVert, destinoVert, peso);
    this.arestas.add(novaAresta);
}

public Aresta obterAresta(Vertice<T> origem, Vertice<T> destino) {
    for (Aresta aresta : arestas) {
        if (aresta.getOrigem().equals(origem) && aresta.getDestino().equals(destino)) {
            return aresta;
        }
    }
    return null; // Retorna null se a aresta não for encontrada
}