// Clase Nodo
class Nodo {
    String nombre;
    Nodo izquierda, derecha;

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.izquierda = null;
        this.derecha = null;
    }
}

// Clase Arbol
class Arbol {
    private Nodo raiz;

    public Arbol() {
        raiz = null;
    }

    // Verifica si el árbol está vacío
    public boolean vacio() {
        return raiz == null;
    }

    // Insertar un nodo en el árbol (ordenado por nombre)
    public void insertar(String nombre) {
        raiz = insertarRec(raiz, nombre);
    }

    private Nodo insertarRec(Nodo actual, String nombre) {
        if (actual == null) {
            return new Nodo(nombre);
        }
        if (nombre.compareToIgnoreCase(actual.nombre) < 0) {
            actual.izquierda = insertarRec(actual.izquierda, nombre);
        } else if (nombre.compareToIgnoreCase(actual.nombre) > 0) {
            actual.derecha = insertarRec(actual.derecha, nombre);
        }
        return actual;
    }

    public Nodo buscarNodo(String nombre) {
        return buscarRec(raiz, nombre);
    }

    private Nodo buscarRec(Nodo actual, String nombre) {
        if (actual == null || actual.nombre.equalsIgnoreCase(nombre)) {
            return actual;
        }
        if (nombre.compareToIgnoreCase(actual.nombre) < 0) {
            return buscarRec(actual.izquierda, nombre);
        } else {
            return buscarRec(actual.derecha, nombre);
        }
    }

    public void imprimirArbol() {
        imprimirInOrden(raiz);
        System.out.println();
    }

    private void imprimirInOrden(Nodo actual) {
        if (actual != null) {
            imprimirInOrden(actual.izquierda);
            System.out.print(actual.nombre + " ");
            imprimirInOrden(actual.derecha);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Arbol arbol = new Arbol();

        // Insertando nodos
        arbol.insertar("Luis");
        arbol.insertar("Fernando");
        arbol.insertar("Campos");
        arbol.insertar("Lopez");
        arbol.insertar("Pedro");

        System.out.print("Árbol en inorden: ");
        arbol.imprimirArbol();

        Nodo buscado = arbol.buscarNodo("Luis");
        if (buscado != null) {
            System.out.println("Nombre encontrado: " + buscado.nombre);
        } else {
            System.out.println("Nombre no encontrado");
        }

        System.out.println("¿vacío? " + arbol.vacio());
    }
}

