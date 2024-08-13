package lib;

public class Vertice <T> {
    private T valor;

    public Vertice(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return this.valor;
    }
}
