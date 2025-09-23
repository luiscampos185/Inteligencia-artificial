import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {

    public static void main(String a[]){
        String estadoInicial ="7245 6831";
        String estadoFinal =" 12345678";

        long inicio = System.nanoTime();

        //TODOS LOS METODOS DE BUSQUEDA. DESCOMENTAR EL QUE SE VAYA A USAR Y TAPAR LOS DEMAS
        Arbol arbol = new Arbol(new Nodo(estadoInicial, null));
        //Nodo resultado = arbol.realizarBusquedaEnProfundidadLimitada(estadoFinal,300);
        //Nodo resultado = arbol.realizarBusquedaEnProfundidad(estadoFinal);
        //Nodo resultado = arbol.realizarBusquedaCostoUniforme(estadoFinal);
        Nodo resultado = arbol.realizarBusquedaAEstrella(estadoFinal);

        if (resultado != null) {
            imprimirCaminoComoPuzzle(resultado);
        } else {
            System.out.println("No se encontró solución.");
        }

        long fin = System.nanoTime();
        long duracion = fin - inicio;
        System.out.println("Tiempo de ejecución: " + duracion / 1_000_000_000.0 + " segundos");
        System.out.println("Nodos generados: " + Nodo.getContadorNodos());
    }

    private static void imprimirCaminoComoPuzzle(Nodo resultado) {
        List<Nodo> camino = new ArrayList<>();
        Nodo actual = resultado;

        while (actual != null) {
            camino.add(actual);
            actual = actual.padre;
        }
        Collections.reverse(camino); 

        int paso = 0;
        for (Nodo n : camino) {
            System.out.println("Paso " + paso + ":");
            System.out.println(formarTablero3x3(n.estado));
            System.out.println("-----------------");
            paso++;
        }
    }

    private static String formarTablero3x3(String estado) {
        String s = estado;
        if (s.length() < 9) {
            s = String.format("%-9s", s);
        }
        if (s.length() > 9) {
            s = s.substring(0, 9);
        }

        StringBuilder sb = new StringBuilder();
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                char c = s.charAt(fila * 3 + col);
                if (c == ' ') sb.append(' ');  // espacio = hueco
                else sb.append(c);
                if (col < 2) sb.append(' ');
            }
            if (fila < 2) sb.append("\n");
        }
        return sb.toString();
    }

}
