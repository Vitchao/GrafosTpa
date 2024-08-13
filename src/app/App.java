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

    // Método para carregar o grafo a partir de um arquivo
    private static void carregarGrafoDeArquivo(Grafo<String> grafo, String nomeArquivo) {
        try {
            File arquivo = new File(nomeArquivo);
            if (!arquivo.exists()) {
                System.out.println("Arquivo '" + nomeArquivo + "' não encontrado. Um novo grafo será criado.");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            int n = Integer.parseInt(br.readLine().trim());

            List<String> cidades = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                String cidade = br.readLine().trim();
                cidades.add(cidade);
                grafo.adicionarVertice(cidade);
            }

            for (int i = 0; i < n; i++) {
                String[] distancias = br.readLine().split(",");
                for (int j = 0; j < n; j++) {
                    float distancia = Float.parseFloat(distancias[j].trim());
                    if (distancia > 0) {
                        grafo.adicionarAresta(cidades.get(i), cidades.get(j), distancia);
                    }
                }
            }

            br.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar o arquivo: " + e.getMessage());
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

    // Método para calcular o caminho mínimo entre duas cidades
    private static void calcularCaminhoMinimo(Grafo<String> grafo, Scanner scanner) {
        System.out.println("Digite o nome da cidade de origem:");
        String origem = scanner.nextLine().trim();
        System.out.println("Digite o nome da cidade de destino:");
        String destino = scanner.nextLine().trim();

        grafo.calcularCaminhoMinimo(origem, destino);
    }

    // Método para gravar o grafo em um arquivo
    private static void gravarGrafoEmArquivo(Grafo<String> grafo, String nomeArquivo) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo));

            List<Vertice<String>> vertices = grafo.getVertices();
            int n = vertices.size();
            bw.write(n + "\n");

            for (Vertice<String> vertice : vertices) {
                bw.write(vertice.getValor() + "\n");
            }

            for (Vertice<String> vertice1 : vertices) {
                StringBuilder linha = new StringBuilder();
                for (Vertice<String> vertice2 : vertices) {
                    Aresta aresta = grafo.obterAresta(vertice1, vertice2);
                    if (aresta != null) {
                        linha.append(aresta.getPeso());
                    } else {
                        linha.append("0");
                    }
                    linha.append(",");
                }
                bw.write(linha.deleteCharAt(linha.length() - 1).toString() + "\n");
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("Erro ao gravar o arquivo: " + e.getMessage());
        }
    }
}