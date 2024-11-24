

package TDA;

public class Pila<T> {
    private Nodo<T> tope;

    public Pila() {
        this.tope = null;
    }

    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.ptr = tope;
        tope = nuevo;
    }

    public T desapilar() {
        if (estaVacia()) return null;
        T dato = tope.data;
        tope = tope.ptr;
        return dato;
    }

    public T mirarTope() {
        if (estaVacia()) return null;
        return tope.data;
    }

    public boolean estaVacia() {
        return tope == null;
    }
}
