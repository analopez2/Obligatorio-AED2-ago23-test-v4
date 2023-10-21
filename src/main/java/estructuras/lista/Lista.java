package estructuras.lista;

public interface Lista<T> extends Iterable<T>{

    public void insertar(T dato);

    public int largo();

    public boolean esVacia();

}
