

package TDA;


public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> finalCola;

    public Cola() {
        this.frente = null;
        this.finalCola = null;
    }

    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (finalCola != null) {
            finalCola.ptr = nuevo;
        }
        finalCola = nuevo;
        if (frente == null) {
            frente = finalCola;
        }
    }

    public T desencolar() {
        if (estaVacia()) return null;
        T dato = frente.data;
        frente = frente.ptr;
        if (frente == null) {
            finalCola = null;
        }
        return dato;
    }

    public T mirarFrente() {
        if (estaVacia()) return null;
        return frente.data;
    }

    public boolean estaVacia() {
        return frente == null;
    }
}
