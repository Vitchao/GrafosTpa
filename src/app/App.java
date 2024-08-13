package app;

import lib.*;
import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<>();
        Grafo<String> agm = null; // Para armazenar a AGM
        Scanner scanner = new Scanner(System.in);

        // Verificar se o arquivo "entrada.txt" existe e carregar o grafo
        carregarGrafoDeArquivo(grafo, "entrada.txt");

        // Menu interativo
        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Acrescentar cidade");
            System.out.println("2. Acrescentar rota");
            System.out.println("3. Calcular árvore geradora mínima (AGM)");
            System.out.println("4. Calcular caminho mínimo entre duas cidades");
            System.out.println("5. Calcular caminho mínimo entre duas cidades considerando apenas a AGM");
            System.out.println("6. Gravar e Sair");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    acrescentarCidade(grafo, scanner);
                    break;
                case 2:
                    acrescentarRota(grafo, scanner);
                    break;
                case 3:
                    agm = grafo.calcularArvoreGeradoraMinima();
                    break;
                case 4:
                    calcularCaminhoMinimo(grafo, scanner);
                    break;
                case 5:
                    if (agm == null) {
                        agm = grafo.calcularArvoreGeradoraMinima();
                    }
                    calcularCaminhoMinimo(agm, scanner);
                    break;
                case 6:
                    gravarGrafoEmArquivo(grafo, "grafoCompleto.txt");
                    if (agm != null) {
                        gravarGrafoEmArquivo(agm, "agm.txt");
                    }
                    System.out.println("Arquivos gravados e saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    // Método para adicionar uma nova cidade
    private static void acrescentarCidade(Grafo<String> grafo, Scanner scanner) {
        System.out.println("Digite o nome da cidade a ser adicionada:");
        String cidade = scanner.nextLine().trim();
        Vertice<String> vertice = grafo.adicionarVertice(cidade);
        if (vertice != null) {
            System.out.println("Cidade '" + cidade + "' adicionada com sucesso.");
        } else {
            System.out.println("Cidade '" + cidade + "' já existe no grafo.");
        }
    }
    
    // Método para adicionar uma nova rota
    private static void acrescentarRota(Grafo<String> grafo, Scanner scanner) {
        System.out.println("Digite o nome da cidade de origem:");
        String origem = scanner.nextLine().trim();
        System.out.println("Digite o nome da cidade de destino:");
        String destino = scanner.nextLine().trim();
        System.out.println("Digite a distância da rota:");
        float distancia = Float.parseFloat(scanner.nextLine().trim());

        grafo.adicionarAresta(origem, destino, distancia);
        System.out.println("Rota de '" + origem + "' para '" + destino + "' com distância " + distancia + " adicionada com sucesso.");
    }
}