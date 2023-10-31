package estructuras.ABB;

import estructuras.lista.Lista;
import estructuras.lista.ListaImp;

public class ABB<T extends Comparable<T>> {

    private Nodo<T> raiz;
    private int iteraciones;

    public void insertar(T dato) {
        if (this.raiz == null) {
            this.raiz = new Nodo<>(dato);
        } else {
            insertarRec(raiz, dato);
        }
    }

    //pre nodo != null
    private void insertarRec(Nodo<T> nodo, T dato) {
        if (dato.compareTo(nodo.getDato()) > 0) {
            if (nodo.getDer() == null) {
                nodo.setDer(new Nodo<>(dato));
            } else {
                insertarRec(nodo.getDer(), dato);
            }
        } else {
            if (nodo.getIzq() == null) {
                nodo.setIzq(new Nodo<>(dato));
            } else {
                insertarRec(nodo.getIzq(), dato);
            }
        }
    }

    public boolean existe(T dato) {
        return existeRec(raiz, dato);
    }

    private boolean existeRec(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return false;
        } else if (nodo.getDato().equals(dato)) {
            return true;
        } else {
            if (dato.compareTo(nodo.getDato()) > 0) {
                return existeRec(nodo.getDer(), dato);
            } else {
                return existeRec(nodo.getIzq(), dato);
            }
        }
    }

    public T obtener(T dato) {
        this.iteraciones = 0;
        return obtener(raiz, dato);
    }

    private T obtener(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        } else if (nodo.getDato().equals(dato)) {
            return nodo.getDato();
        } else {
            this.iteraciones += 1;
            if (dato.compareTo(nodo.getDato()) > 0) {
                return obtener(nodo.getDer(), dato);
            } else {
                return obtener(nodo.getIzq(), dato);
            }
        }
    }

    public String listarAsc() {
        String resultado = listarAsc(raiz, "");
        return resultado.isEmpty() ? resultado : resultado.substring(0, resultado.length() - 1);
    }

    private String listarAsc(Nodo<T> nodo, String resultado) {
        if (nodo != null) {
            resultado = listarAsc(nodo.getIzq(), resultado);
            resultado += nodo.getDato() + "|";
            resultado = listarAsc(nodo.getDer(), resultado);
        }
        return resultado;
    }

    public String listarDes() {
        String resultado = listarDes(raiz, "");
        return resultado.isEmpty() ? resultado : resultado.substring(0, resultado.length() - 1);
    }

    private String listarDes(Nodo<T> nodo, String resultado) {
        if (nodo != null) {
            resultado = listarDes(nodo.getDer(), resultado);
            resultado += nodo.getDato() + "|";
            resultado = listarDes(nodo.getIzq(), resultado);
        }
        return resultado;
    }

    public Lista<T> aplanarCreciente() {
        Lista<T> lista = new ListaImp<>();
        aplanar(raiz, lista);
        return lista;
    }

    private void aplanar(Nodo<T> nodo, Lista<T> lista) {
        if (nodo != null) {
            aplanar(nodo.getIzq(), lista);
            lista.insertar(nodo.getDato());
            aplanar(nodo.getDer(), lista);
        }
    }

    public int getIteraciones() {
        return iteraciones;
    }

    private class Nodo<T> {
        private T dato;
        private Nodo<T> der;
        private Nodo<T> izq;

        public Nodo(T dato) {
            this.dato = dato;
        }

        public Nodo(T dato, Nodo<T> der, Nodo<T> izq) {
            this.dato = dato;
            this.der = der;
            this.izq = izq;
        }

        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public Nodo<T> getDer() {
            return der;
        }

        public void setDer(Nodo<T> der) {
            this.der = der;
        }

        public Nodo<T> getIzq() {
            return izq;
        }

        public void setIzq(Nodo<T> izq) {
            this.izq = izq;
        }
    }

}