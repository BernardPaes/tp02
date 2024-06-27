import java.io.File;
import java.io.IOException;
import java.util.*;

class KCenterAproximation {

    static int[][] graph;
    static int V;
    static int E;
    static int k;

    // fonte: https://www.geeksforgeeks.org/greedy-approximate-algorithm-for-k-centers-problem/
    static int maxindex(int[] dist, int n) {
        int mi = 0;
        for (int i = 0; i < n; i++) {
            if (dist[i] > dist[mi])
                mi = i;
        }
        return mi;
    }
    // fonte: https://www.geeksforgeeks.org/greedy-approximate-algorithm-for-k-centers-problem/
    static void selectKcities(int n, int weights[][], int k) {
        int[] dist = new int[n];
        ArrayList<Integer> centers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
        }

        // Index of city having the
        // maximum distance to its
        // closest center
        int max = 0;
        for (int i = 0; i < k; i++) {
            centers.add(max);
            for (int j = 0; j < n; j++) {

                // Updating the distance
                // of the cities to their
                // closest centers
                dist[j] = Math.min(dist[j],
                        weights[max][j]);
            }

            // Updating the index of the
            // city with the maximum
            // distance to its closest center
            max = maxindex(dist, n);
        }

        // Printing the maximum distance
        // of a city to a center
        // that is our answer
        System.out.println("O raio aproximado para " + k + " centros é: " + dist[max]);

        // Printing the cities that
        // were chosen to be made
        // centers
        System.out.print("Centros escolhidos: ");
        for (int i = 0; i < centers.size(); i++) {
            System.out.print((centers.get(i) + 1) + " "); // Adicionando 1 para ajustar o índice para 1 baseado
        }
        System.out.print("\n");
    }

    // Algoritmo de Floyd-Warshall
    private static void floydWarshall() {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (graph[i][j] > graph[i][k] + graph[k][j]) {
                        graph[i][j] = graph[i][k] + graph[k][j];
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // Ler dados do arquivo
        String fileName = "pmed30.txt";
        Scanner sc = new Scanner(new File(fileName));

        V = sc.nextInt();
        E = sc.nextInt();
        k = sc.nextInt();

        graph = new int[V][V];
        for (int[] row : graph) {
            Arrays.fill(row, Integer.MAX_VALUE / 2); // Preencher com um valor alto para evitar overflow
        }

        for (int i = 0; i < V; i++) {
            graph[i][i] = 0;
        }

        for (int i = 0; i < E; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            int cost = sc.nextInt();
            graph[u][v] = cost;
            graph[v][u] = cost;
        }

        sc.close();

        // Aplicar o algoritmo de Floyd-Warshall para obter a matriz de distâncias
        floydWarshall();

        // Encontrar uma solução aproximada para o problema do k-center
        selectKcities(V, graph, k);
    }
}
