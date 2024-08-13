package lib;

import java.util.*;

public class Grafo <T> {
    private ArrayList<Aresta> arestas;
    private ArrayList<Vertice<T>> vertices;

    public Grafo () {
        this.arestas = new ArrayList<Aresta>();
        this.vertices = new ArrayList<Vertice<T>>();
    }

    public ArrayList<Vertice<T>> getVertices () {
        return this.vertices;
    }

    public Vertice<T> adicionarVertice(T valor) {
        Vertice<T> vertice = new Vertice<T>(valor);

        if (this.obterVertice(valor) == null) {
            this.vertices.add(vertice);
            return vertice;
        }

        return null;
    }

    private Vertice obterVertice(T valor) {
        Vertice v;

        for (int i = 0; i < this.vertices.size(); i++) {
            v = this.vertices.get(i);
            if (v.getValor().equals(valor))
                return v;
        }

        return null;
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

    private ArrayList<Aresta> obterDestinos (Vertice origem) {
        ArrayList<Aresta> destinos = new ArrayList<Aresta>();
        Aresta atualAres;

        for (int i = 0; i < this.arestas.size(); i++) {
            atualAres = this.arestas.get(i);
            if (atualAres.getOrigem().equals(origem)) {
                destinos.add(atualAres);
            }
        }

        return destinos;
    }
}