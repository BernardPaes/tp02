import java.io.*;
import java.util.*;
// Código densenvolvido com auxílio do ChatGpt 3.5
public class ProblemaKCentros {

    static int[][] grafo;
    static int V;
    static int E;
    static int k;

    public static void main(String[] args) throws IOException {
        // Registrar o tempo de início
        long inicio = System.currentTimeMillis();

        // Ler dados do arquivo
        String nomeArquivo = "arquivoBase/arquivoBase/pmed38.txt";
        Scanner sc = new Scanner(new File(nomeArquivo));

        V = sc.nextInt();
        E = sc.nextInt();
        k = sc.nextInt();

        grafo = new int[V][V];
        for (int[] linha : grafo) {
            Arrays.fill(linha, Integer.MAX_VALUE / 2); // Preencher com um valor alto para evitar overflow
        }

        for (int i = 0; i < V; i++) {
            grafo[i][i] = 0;
        }

        for (int i = 0; i < E; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int custo = sc.nextInt();
            grafo[u][v] = custo;
            grafo[v][u] = custo;
        }

        sc.close();

        // Aplicar o algoritmo de Floyd-Warshall para obter a matriz de distâncias
        floydWarshall();

        // Encontrar a solução exata para o problema do k-centros
        int[] centros = new int[k];
        int resultado = resolverKCentros(centros);

        // Registrar o tempo de término
        long fim = System.currentTimeMillis();
        long tempoExecucao = fim - inicio;

        // Imprimir o resultado
        System.out.println("O raio mínimo para " + k + " centros é: " + resultado);
        System.out.println("Tempo de execução: " + tempoExecucao + " ms");
    }

    // Algoritmo de Floyd-Warshall
    private static void floydWarshall() {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (grafo[i][j] > grafo[i][k] + grafo[k][j]) {
                        grafo[i][j] = grafo[i][k] + grafo[k][j];
                    }
                }
            }
        }
    }

    // Função para resolver o problema dos k-centros
    private static int resolverKCentros(int[] centros) {
        return resolverKCentrosRecursivo(centros, 0, 0);
    }

    // Função recursiva para encontrar a solução exata
    private static int resolverKCentrosRecursivo(int[] centros, int indice, int inicio) {
        if (indice == k) {
            return calcularRaio(centros);
        }

        int raioMinimo = Integer.MAX_VALUE;
        for (int i = inicio; i < V; i++) {
            centros[indice] = i;
            raioMinimo = Math.min(raioMinimo, resolverKCentrosRecursivo(centros, indice + 1, i + 1));
        }
        return raioMinimo;
    }

    // Função para calcular o raio de uma solução
    private static int calcularRaio(int[] centros) {
        int distanciaMaxima = 0;
        for (int i = 0; i < V; i++) {
            int distanciaMinima = Integer.MAX_VALUE;
            for (int centro : centros) {
                distanciaMinima = Math.min(distanciaMinima, grafo[i][centro]);
            }
            distanciaMaxima = Math.max(distanciaMaxima, distanciaMinima);
        }
        return distanciaMaxima;
    }
}
