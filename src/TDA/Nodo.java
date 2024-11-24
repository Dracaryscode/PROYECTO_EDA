

package TDA;


public class Nodo<T> {
    T data;
    Nodo<T> ptr;

    public Nodo(T data) {
        this.data = data;
        this.ptr = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Nodo<T> getPtr() {
        return ptr;
    }

    public void setPtr(Nodo<T> ptr) {
        this.ptr = ptr;
    }
    
    
}