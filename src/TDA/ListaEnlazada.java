

package TDA;


public class ListaEnlazada<T> {
    public Nodo<T> cabeza;
    private Nodo<T> cola;


    public ListaEnlazada() {
        this.cabeza = null;
    }
/*    
    public int getsize(){
        int count = 0;
        while(cabeza != null) {
            System.out.println(cabeza.value());             
            cabeza = cabeza.next();
        }
    }
*/
    public Nodo<T> getCabeza() {
        return cabeza;
    }
    public void agregarInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.ptr = cabeza;
        cabeza = nuevo;
    }

    public void agregarFinal(T dato) {
    if (dato == null) {
        System.out.println("Dato nulo no permitido. Operación ignorada.");
        return;
    }
    Nodo<T> nuevo = new Nodo<>(dato);
    if (cabeza == null) {
        cabeza = nuevo;
        cola = nuevo;
    } else {
        cola.ptr = nuevo;
        cola = nuevo;
    }
}

    public boolean eliminar(T dato) {
        if (cabeza == null) return false;
        if (cabeza.data.equals(dato)) {
            cabeza = cabeza.ptr;
            return true;
        }
        Nodo<T> actual = cabeza;
        while (actual.ptr != null && !actual.ptr.data.equals(dato)) {
            actual = actual.ptr;
        }
        if (actual.ptr == null) return false;
        actual.ptr = actual.ptr.ptr;
        return true;
    }

    public void imprimir() {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            System.out.print(actual.data + " -> ");
            actual = actual.ptr;
        }
        System.out.println("null");
    }

    public boolean estaVacia() {
        return cabeza == null;
    }
    

}
