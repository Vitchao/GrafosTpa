package lib;
import java.util.*;

public class Grafo <T> {
    
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
}