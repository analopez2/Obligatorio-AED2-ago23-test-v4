package estructuras.cola;

public class Cola<T> {

	private NodoCola<T> inicio;
	private NodoCola<T> fin;
	private int largo;

	public Cola() {
//		this.inicio = null;
//		this.fin = null;
//		this.largo = 0;
	}

	public void encolar(T dato) {
		if (this.inicio == null) {
//			inicio = fin = new NodoCola<T>(dato); // Alternativa 
			inicio = new NodoCola<T>(dato);
			fin = inicio;
		} else {
			fin.setSig(new NodoCola<T>(dato));
			fin = fin.getSig();
		}
		this.largo++;
	}

	// Pre: !esVacia()
	public T desencolar() {
		T dato = this.inicio.getDato();
		inicio = inicio.getSig();
		this.largo--;
		if(this.inicio == null) {
			fin = null;
		}
		return dato;
	}

	public boolean esVacia() {
		return this.largo == 0;
	}

}
