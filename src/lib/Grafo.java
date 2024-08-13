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

    public void calcularCaminhoMinimo(T origem, T destino) {
        Vertice<T> origemVert = obterVertice(origem);
        Vertice<T> destinoVert = obterVertice(destino);

        if (origemVert == null || destinoVert == null) {
            System.out.println("Um ou ambos os vértices não existem no grafo.");
            return;
        }

        // Passo 1: Inicializar estruturas para Dijkstra
        Map<Vertice<T>, Float> distancias = new HashMap<>();
        Map<Vertice<T>, Vertice<T>> predecessores = new HashMap<>();
        PriorityQueue<Vertice<T>> filaPrioridade = new PriorityQueue<>(Comparator.comparing(distancias::get));

        for (Vertice<T> vertice : vertices) {
            distancias.put(vertice, Float.MAX_VALUE);
            predecessores.put(vertice, null);
        }

        distancias.put(origemVert, 0f);
        filaPrioridade.add(origemVert);

        // Passo 2: Executar o algoritmo de Dijkstra
        while (!filaPrioridade.isEmpty()) {
            Vertice<T> atual = filaPrioridade.poll();

            for (Aresta aresta : obterDestinos(atual)) {
                Vertice<T> vizinho = aresta.getDestino();
                float novaDistancia = distancias.get(atual) + aresta.getPeso();

                if (novaDistancia < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDistancia);
                    predecessores.put(vizinho, atual);
                    filaPrioridade.add(vizinho);
                }
            }
        }

        // Passo 3: Recuperar e imprimir o caminho mínimo
        if (distancias.get(destinoVert) == Float.MAX_VALUE) {
            System.out.println("Não existe caminho entre " + origem + " e " + destino);
            return;
        }

        List<Vertice<T>> caminho = new ArrayList<>();
        for (Vertice<T> vertice = destinoVert; vertice != null; vertice = predecessores.get(vertice)) {
            caminho.add(vertice);
        }
        Collections.reverse(caminho);

        System.out.println("Caminho mínimo de " + origem + " até " + destino + ":");
        for (Vertice<T> vertice : caminho) {
            System.out.print(vertice.getValor() + " ");
        }
        System.out.println("\nDistância total: " + distancias.get(destinoVert));
    }
}