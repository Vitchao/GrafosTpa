package lib;

public class Aresta {
    private Vertice origem, destino;
    private float peso;

    public Aresta(Vertice origem, Vertice destino, float peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice getOrigem() {
        return this.origem;
    }

    public Vertice getDestino() {
        return this.destino;
    }

    public float getPeso() {
        return this.peso;
    }
}