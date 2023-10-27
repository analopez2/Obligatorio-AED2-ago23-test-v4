package estructuras.grafo;

import dominio.Ciudad;
import dominio.Conexion;
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
            if(ciudades[i] == null){
                return i;
            }
        }
        return -1;
    }

    private int obtenerPos(Ciudad ciudad) {
        for (int i = 0; i < tope; i++) {
            if(ciudades[i] != null  && ciudades[i].equals(ciudad)){
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

    // existeCiudad(origen) && existeCiudad(destino)
    public Arista buscarConexion(Ciudad origen, Ciudad destino) {
        int posOrigen = obtenerPos(origen);
        int posDestino = obtenerPos(destino);

        Arista ar = matAdy[posOrigen][posDestino];
        if (ar.isExiste()) {
            return ar;
        }
        return null;
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
            if(matAdy[pos][j].isExiste()){
                listaDeAdyacentes.insertar( ciudades[j] );
            }
        }
        return listaDeAdyacentes;
    }

    // Pre: existeCiudad(ciudad)
    public Lista<Ciudad> ciudadesIncidentes(Ciudad vert) {
        Lista<Ciudad> lista = new ListaImp<>();
        int pos = obtenerPos(vert);
        for (int i = 0; i < tope; i++) {
            if(matAdy[i][pos].isExiste()){
                lista.insertar( ciudades[i] );
            }
        }
        return lista;
    }

    //Pre existeCiudad(ciudad)
    public void dfs(Ciudad ciudad){
        int posInicial = obtenerPos(ciudad);
        System.out.println("DFS ; inicio " + ciudad);
        boolean[] visitados = new boolean[tope]; //por defecto todo en false
        dfsRec(posInicial, visitados);
    }

    private void dfsRec(int pos, boolean[] visitados){
        System.out.println( ciudades[pos] );
        visitados[pos] = true;
        //obtener los adyacentes no visitados y llamar recursivo
        for (int j = 0; j < tope; j++) {
            if( matAdy[pos][j].isExiste() && !visitados[j]){
                dfsRec(j, visitados);
            }
        }
    }

    public void bfs(Ciudad vert){
        System.out.println("BFS ; inicio " + vert);
        Cola<Integer> cola = new Cola<>();
        int inicio = obtenerPos(vert);
        boolean[] visitados = new boolean[tope];
        cola.encolar(inicio);
        visitados[inicio] = true;
        while( !cola.esVacia() ){
            int pos = cola.desencolar();
            System.out.println( ciudades[pos] );
            for (int j = 0; j < tope; j++) {
                if( matAdy[pos][j].isExiste() && !visitados[j]){
                    cola.encolar(j);
                    visitados[j]=true;
                }
            }
        }
    }

    public void bfs2(Ciudad vert){
        System.out.println("BFS ; inicio " + vert);
        Cola<Tupla> cola = new Cola<>();
        int inicio = obtenerPos(vert);
        boolean[] visitados = new boolean[tope];
        cola.encolar(new Tupla(inicio,0));
        visitados[inicio] = true;
        while( !cola.esVacia() ){
            Tupla tupla = cola.desencolar();
            int pos = tupla.getPos();
            int nivel = tupla.getNivel();
            System.out.println( ciudades[pos] +  " : " + nivel );
            for (int j = 0; j < tope; j++) {
                if( matAdy[pos][j].isExiste() && !visitados[j]){
                    cola.encolar(new Tupla(j,nivel+1));
                    visitados[j]=true;
                }
            }
        }
    }

}
