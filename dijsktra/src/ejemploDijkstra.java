public class ejemploDijkstra {
    static final int valor = 9999; 

    public static void dijkstra(int[][] grafo, int inicio) {
        int n = grafo.length;
        int[] dist = new int[n];   // distancias mínimas
        boolean[] visitado = new boolean[n]; // nodos visitados

        for (int i = 0; i < n; i++) {
            dist[i] = grafo[inicio][i];
        }
        dist[inicio] = 0;
        visitado[inicio] = true;

        for (int i = 1; i < n; i++) {
            int min = valor, u = -1;

            for (int j = 0; j < n; j++) {
                if (!visitado[j] && dist[j] < min) {
                    min = dist[j];
                    u = j;
                }
            }

            visitado[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visitado[v] && grafo[u][v] != valor) {
                    if (dist[u] + grafo[u][v] < dist[v]) {
                        dist[v] = dist[u] + grafo[u][v];
                    }
                }
            }
        }

        // Imprimir resultados
        System.out.println("distancias desde el nodo " + inicio + ":");
        for (int i = 0; i < n; i++) {
            System.out.println("Hacia " + i + " = " + dist[i]);
        }
    }

    public static void main(String[] args) {
        int valor = 9999;
        int[][] grafo = {
            {0, 4, 2},   // A
            {4, 0, 1},   // B
            {2, 1, 0}    // C
        };

        dijkstra(grafo, 0); 
    }

}
