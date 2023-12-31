package estructuras.grafo;

import dominio.Ciudad;
import dominio.Conexion;
import dominio.CaminoTiempo;
import estructuras.ABB.ABB;
import estructuras.cola.Cola;
import estructuras.lista.Lista;
import estructuras.lista.ListaImp;

public class Grafo {

    //Definir atributos
    private int cantidad;
    private int tope;
    private Ciudad[] ciudades;
    private Arista[][] matAdy;

    public Grafo(int cantMaxCiudades, boolean esDirigido) {
        cantidad = 0;
        tope = cantMaxCiudades;
        ciudades = new Ciudad[tope];
        matAdy = new Arista[tope][tope];
        if (esDirigido) {
            for (int i = 0; i < tope; i++) {
                for (int j = 0; j < tope; j++) {
                    matAdy[i][j] = new Arista();
                }
            }
        } else {
            for (int i = 0; i < tope; i++) {
                for (int j = i; j < tope; j++) {
                    Arista objAux = new Arista();
                    matAdy[i][j] = objAux;
                    matAdy[j][i] = objAux;
                }
            }
        }
    }

    public boolean esLleno() {
        return cantidad == tope;
    }

    public boolean esVacio() {
        return cantidad == 0;
    }

    // PRE: !esLleno()
    private int obtenerPosLibre() {
        for (int i = 0; i < tope; i++) {
            if (ciudades[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private int obtenerPos(Ciudad ciudad) {
        for (int i = 0; i < tope; i++) {
            if (ciudades[i] != null && ciudades[i].equals(ciudad)) {
                return i;
            }
        }
        return -1;
    }

    // PRE: !esLleno && !existeCiudad
    public void agregarCiudad(Ciudad ciudad) {
        int pos = obtenerPosLibre();
        ciudades[pos] = ciudad;
        cantidad++;
    }

    // PRE: existeCiudad
    public void borrarCiudad(Ciudad ciudad) {
        int pos = obtenerPos(ciudad);
        ciudades[pos] = null;
        for (int k = 0; k < tope; k++) {
            matAdy[pos][k].setExiste(false);
            matAdy[k][pos].setExiste(false);
        }
        cantidad--;
    }

    public boolean existeCiudad(Ciudad ciudad) {
        return obtenerPos(ciudad) != -1;
    }

    // existeCiudad(origen) && existeCiudad(destino) && !existeConexion
    public void agregarConexion(Ciudad origen, Ciudad destino, Conexion con) {
        int posOrigen = obtenerPos(origen);
        int posDestino = obtenerPos(destino);
        matAdy[posOrigen][posDestino].setExiste(true);
        matAdy[posOrigen][posDestino].agregarConexion(con);
    }

    public void actualizarConexion(Ciudad origen, Ciudad destino, Conexion con) {
        int posOrigen = obtenerPos(origen);
        int posDestino = obtenerPos(destino);
        matAdy[posOrigen][posDestino].actualizarConexion(con);
    }

    // existeCiudad(origen) && existeCiudad(destino)
    public boolean existeConexion(Ciudad origen, Ciudad destino, Conexion con) {
        int posOrigen = obtenerPos(origen);
        int posDestino = obtenerPos(destino);

        Arista conexiones = matAdy[posOrigen][posDestino];
        return conexiones.isExiste() && conexiones.existeConexion(con);
    }

    // existeCiudad(origen) && existeCiudad(destino) && existeConexion
    public void borrarConexion(Ciudad origen, Ciudad destino) {
        int posOrigen = obtenerPos(origen);
        int posDestino = obtenerPos(destino);
        matAdy[posOrigen][posDestino].setExiste(false);
    }

    public Lista<Ciudad> ciudadesAdyacentes(Ciudad ciudad) {
        Lista<Ciudad> listaDeAdyacentes = new ListaImp<>();
        int pos = obtenerPos(ciudad);
        for (int j = 0; j < tope; j++) {
            if (matAdy[pos][j].isExiste()) {
                listaDeAdyacentes.insertar(ciudades[j]);
            }
        }
        return listaDeAdyacentes;
    }

    // Pre: existeCiudad(ciudad)
    public Lista<Ciudad> ciudadesIncidentes(Ciudad vert) {
        Lista<Ciudad> lista = new ListaImp<>();
        int pos = obtenerPos(vert);
        for (int i = 0; i < tope; i++) {
            if (matAdy[i][pos].isExiste()) {
                lista.insertar(ciudades[i]);
            }
        }
        return lista;
    }

    //Pre existeCiudad(ciudad)
    public void dfs(Ciudad ciudad) {
        int posInicial = obtenerPos(ciudad);
        System.out.println("DFS ; inicio " + ciudad);
        boolean[] visitados = new boolean[tope]; //por defecto todo en false
        dfsRec(posInicial, visitados);
    }

    private void dfsRec(int pos, boolean[] visitados) {
        System.out.println(ciudades[pos]);
        visitados[pos] = true;
        //obtener los adyacentes no visitados y llamar recursivo
        for (int j = 0; j < tope; j++) {
            if (matAdy[pos][j].isExiste() && !visitados[j]) {
                dfsRec(j, visitados);
            }
        }
    }

    public String listarCiudadesCantTrasbordos(Ciudad ciudad, int cantidad) {
        ABB<Ciudad> retorno = new ABB<>();
        Cola<Tupla> cola = new Cola<>();
        int inicio = obtenerPos(ciudad);
        boolean[] visitados = new boolean[tope];
        cola.encolar(new Tupla(inicio, 0));
        visitados[inicio] = true;
        while (!cola.esVacia()) {
            Tupla tupla = cola.desencolar();
            int pos = tupla.getPos();
            int nivel = tupla.getNivel();
            if (nivel <= cantidad) {
                retorno.insertar(ciudades[pos]);
            }
            for (int j = 0; j < tope; j++) {
                if (matAdy[pos][j].isExiste() && !visitados[j]) {
                    cola.encolar(new Tupla(j, nivel + 1));
                    visitados[j] = true;
                }
            }
        }
        return retorno.listarAsc();
    }

    public CaminoTiempo viajeCostoMinimo(Ciudad origen, Ciudad destino) {
        int posOrigen = obtenerPos(origen);
        int posDestino = obtenerPos(destino);
        //Estructuras
        boolean[] visitados = new boolean[tope];
        Ciudad[] anterior = new Ciudad[tope];
        double[] costos = new double[tope];
        String[] conexiones = new String[tope];
        //Inicializar valores de las estructuras
        for (int i = 0; i < tope; i++) {
            costos[i] = Double.MAX_VALUE;
            conexiones[i] = "";
        }

        //Definir costo de origen en 0
        costos[posOrigen] = 0;

        for (int i = 0; i < cantidad; i++) {
            int pos = obtenerSiguienteNoVisitadoDeMenorTiempo(costos, visitados);
            if (pos != -1) {
                visitados[pos] = true;

                for (int j = 0; j < tope; j++) {
                    if (matAdy[pos][j].isExiste() && !visitados[j]) {
                        Conexion conexion = matAdy[pos][j].viajeCostoMinimo();
                        double tiempoNuevo = costos[pos] + conexion.getTiempo();
                        if (tiempoNuevo < costos[j]) {
                            costos[j] = tiempoNuevo;
                            anterior[j] = ciudades[pos];
                            conexiones[j] = conexion.getTipo() + "";
                        }

                    }
                }
            }
        }
        String camino = "";
        int pos = posDestino;

        while (anterior[pos] != null) {
            Ciudad ciudadActual = ciudades[pos];
            camino = "|" + conexiones[pos] + "|" + ciudadActual.toString() + camino;
            pos = obtenerPos(anterior[pos]);
        }

        camino = ciudades[posOrigen] + camino;
        return new CaminoTiempo(camino, costos[posDestino]);
    }

    private int obtenerSiguienteNoVisitadoDeMenorTiempo(double[] tiempos, boolean[] visitados) {
        int pos = -1;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < tope; i++) {
            if (!visitados[i] && tiempos[i] < min) {
                min = tiempos[i];
                pos = i;
            }
        }
        return pos;
    }
}
