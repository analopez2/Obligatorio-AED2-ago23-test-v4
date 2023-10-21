package estructuras.cola;

public class NodoCola<T> {
	private T dato;
	private NodoCola<T> sig;

	public NodoCola(T dato, NodoCola<T> sig) {
		this.dato = dato;
		this.sig = sig;
	}
	
	public NodoCola(T dato) {
		this.dato = dato;
	}

	public T getDato() {
		return dato;
	}

	public void setDato(T dato) {
		this.dato = dato;
	}

	public NodoCola<T> getSig() {
		return sig;
	}

	public void setSig(NodoCola<T> sig) {
		this.sig = sig;
	}

	@Override
	public String toString() {
		return dato + "";
	}

}
