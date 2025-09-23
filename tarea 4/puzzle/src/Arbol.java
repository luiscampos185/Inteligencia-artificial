
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Arbol {
    Nodo raiz;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo realizarBusquedaEnAnchura(String objetivo){
        Queue<Nodo> cola = new LinkedList<Nodo>();
        HashSet<String> visitados = new HashSet<String>();
        cola.add(raiz);
        visitados.add(raiz.estado);
        boolean encontrado = false;
        Nodo actual = null;
        while(!encontrado && cola.size() > 0){
            actual = cola.poll();
            System.out.println("Procesando => "+actual.estado);
            //Función objetivo
            if(actual.estado.equals(objetivo)){
                encontrado = true;
            }else{
                List<String> sucesores = actual.obtenerSucesores();
                for(String sucesor: sucesores){
                    //Si ya fue procesado, ignorar
                    if(visitados.contains(sucesor))
                        continue;
                    System.out.println("Agergando a cola => "+sucesor);
                    cola.add(new Nodo(sucesor, actual));
                    visitados.add(sucesor);
                }
            }
            //Procesar
        }
        //Usar las variables de clase
        return actual;
    }


    public Nodo realizarBusquedaEnProfundidad(String objetivo){
        Stack<Nodo> pila = new Stack<Nodo>();
        HashSet<String> visitados = new HashSet<String>();
        pila.push(raiz);
        visitados.add(raiz.estado);
        boolean encontrado = false;
        Nodo actual = null;
        while(!encontrado && pila.size() > 0){
            actual = pila.pop();
            System.out.println("Procesando => "+actual.estado);
            //Función objetivo
            if(actual.estado.equals(objetivo)){
                encontrado = true;
            }else{
                List<String> sucesores = actual.obtenerSucesores();
                for(String sucesor: sucesores){
                    //Si ya fue procesado, ignorar
                    if(visitados.contains(sucesor))
                        continue;
                    System.out.println("Agregando a pila => "+sucesor);
                    pila.push(new Nodo(sucesor, actual));
                    visitados.add(sucesor);
                }
            }
        }
        return actual;
    }

    public Nodo realizarBusquedaCostoUniforme(String objetivo) {
    // Una cola de prioridad que ordena los nodos en función de su costo
    PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(n -> n.costo));
    HashSet<String> visitados = new HashSet<>();
    raiz.costo = 0; // El nodo raíz tiene un costo de 0
    colaPrioridad.add(raiz);
    visitados.add(raiz.estado);

    Nodo actual = null;
    while (!colaPrioridad.isEmpty()) {
        actual = colaPrioridad.poll();
        System.out.println("Procesando => " + actual.estado + " (Costo: " + actual.costo + ")");

        // Comprobar si el estado actual es el estado objetivo
        if (actual.estado.equals(objetivo)) {
            return actual; // Objetivo encontrado, devolver el nodo
        }

        // Generar sucesores
        List<String> sucesores = actual.obtenerSucesores();
        for (String sucesor : sucesores) {
            // Comprobar si el sucesor ha sido visitado
            if (!visitados.contains(sucesor)) {
                Nodo nuevoNodo = new Nodo(sucesor, actual);
                nuevoNodo.costo = actual.costo + 1; // Cada movimiento tiene un costo de 1
                System.out.println("Agregando a cola de prioridad => " + sucesor + " (Costo: " + nuevoNodo.costo + ")");
                colaPrioridad.add(nuevoNodo);
                visitados.add(sucesor);
            }
        }
    }
    return actual; // No se encontró solución
}

public Nodo realizarBusquedaEnProfundidadLimitada(String objetivo, int limite) {
    Stack<Nodo> pila = new Stack<Nodo>();
    HashSet<String> visitados = new HashSet<String>();
    raiz.profundidad = 0; // Se inicializa la profundidad del nodo raíz
    pila.push(raiz);
    visitados.add(raiz.estado);

    Nodo actual = null;
    while (!pila.isEmpty()) {
        actual = pila.pop();
        System.out.println("Procesando => " + actual.estado + " (Profundidad: " + actual.profundidad + ")");

        // Función objetivo
        if (actual.estado.equals(objetivo)) {
            return actual; // Objetivo encontrado
        }

        // Si la profundidad actual es menor que el límite, continúa la búsqueda
        if (actual.profundidad < limite) {
            List<String> sucesores = actual.obtenerSucesores();
            for (String sucesor : sucesores) {
                if (!visitados.contains(sucesor)) {
                    Nodo nuevoNodo = new Nodo(sucesor, actual);
                    nuevoNodo.profundidad = actual.profundidad + 1; // Actualiza la profundidad
                    System.out.println("Agregando a pila => " + sucesor);
                    pila.push(nuevoNodo);
                    visitados.add(sucesor);
                }
            }
        }
    }
    return null; // No se encontró solución dentro del límite
}

 public Nodo realizarBusquedaAEstrella(String objetivo) {
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(
            Comparator.comparingInt(n -> n.costo + n.distanciaManhattan)
        );
        HashSet<String> visitados = new HashSet<>();

        raiz.costo = 0;
        raiz.calcularDistanciaManhattan(objetivo);
        colaPrioridad.add(raiz);
        visitados.add(raiz.estado);

        while (!colaPrioridad.isEmpty()) {
            Nodo actual = colaPrioridad.poll();
            System.out.println("Procesando => " + actual.estado + " (Costo: " + actual.costo + ", Heurística: " + actual.distanciaManhattan + ")");

            if (actual.estado.equals(objetivo)) {
                return actual;
            }

            List<String> sucesores = actual.obtenerSucesores();
            for (String sucesor : sucesores) {
                if (!visitados.contains(sucesor)) {
                    Nodo nuevoNodo = new Nodo(sucesor, actual);
                    nuevoNodo.costo = actual.costo + 1; // Incrementa el costo
                    nuevoNodo.calcularDistanciaManhattan(objetivo); // Calcula la heurística
                    
                    System.out.println("Agregando a cola de prioridad => " + sucesor + " (Costo total: " + (nuevoNodo.costo + nuevoNodo.distanciaManhattan) + ")");
                    colaPrioridad.add(nuevoNodo);
                    visitados.add(sucesor);
                }
            }
        }
        return null; // No se encontró solución
    }

}
