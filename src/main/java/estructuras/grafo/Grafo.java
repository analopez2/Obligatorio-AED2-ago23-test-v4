package estructuras.grafo;

import dominio.Ciudad;
import estructuras.cola.Cola;
import estructuras.lista.Lista;
import estructuras.lista.ListaImp;

public class Grafo {

    //Definir atributos
    private int cantidad;
    private int tope;
    private Ciudad[] vertices;
    private Arista[][] matAdy;

    public Grafo(int cantMaxDeVertices, boolean esDirigido) {
        cantidad = 0;
        tope = cantMaxDeVertices;
        vertices = new Ciudad[tope];
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
            if(vertices[i] == null){
                return i;
            }
        }
        return -1;
    }

    private int obtenerPos(Ciudad vert) {
        for (int i = 0; i < tope; i++) {
            if(vertices[i].equals(vert)){
                return i;
            }
        }
        return -1;
    }

    // PRE: !esLleno && !existeVertice
    public void agregarVertice(Ciudad vert) {
        int pos = obtenerPosLibre();
        vertices[pos] = vert;
        cantidad++;
    }

    // PRE: existeVertice
    public void borrarVertice(Ciudad vert) {
        int pos = obtenerPos(vert);
        vertices[pos] = null;
        for (int k = 0; k < tope; k++) {
            matAdy[pos][k].setExiste(false);
            matAdy[k][pos].setExiste(false);
        }
        cantidad--;
    }

    public boolean existeVertice(Ciudad vert) {
        return obtenerPos(vert) != -1;
    }

    // existeVertice(origen) && existeVertice(destino) && !existeArista
    public void agregarArista(Ciudad origen, Ciudad destino, int peso) {
        int posOrigen = obtenerPos(origen);
        int posDestino = obtenerPos(destino);
        matAdy[posOrigen][posDestino].setExiste(true);
        matAdy[posOrigen][posDestino].setPeso(peso);
    }

    // existeVertice(origen) && existeVertice(destino)
    public boolean existerArista(Ciudad origen, Ciudad destino) {
        int posOrigen = obtenerPos(origen);
        int posDestino = obtenerPos(destino);
        return matAdy[posOrigen][posDestino].isExiste();
    }

    // existeVertice(origen) && existeVertice(destino) && existeArista
    public void borrarArista(Ciudad origen, Ciudad destino) {
        int posOrigen = obtenerPos(origen);
        int posDestino = obtenerPos(destino);
        matAdy[posOrigen][posDestino].setExiste(false);
    }

    public Lista<Ciudad> verticesAdyacentes(Ciudad vert) {
        Lista<Ciudad> listaDeAdyacentes = new ListaImp<>();
        int pos = obtenerPos(vert);
        for (int j = 0; j < tope; j++) {
            if(matAdy[pos][j].isExiste()){
                listaDeAdyacentes.insertar( vertices[j] );
            }
        }
        return listaDeAdyacentes;
    }

    // Pre: existeVertice(vert)
    public Lista<Ciudad> verticesIncidentes(Ciudad vert) {
        Lista<Ciudad> lista = new ListaImp<>();
        int pos = obtenerPos(vert);
        for (int i = 0; i < tope; i++) {
            if(matAdy[i][pos].isExiste()){
                lista.insertar( vertices[i] );
            }
        }
        return lista;
    }

    //Pre existeVertice(vert)
    public void dfs(Ciudad vert){
        int posInicial = obtenerPos(vert);
        System.out.println("DFS ; inicio " + vert);
        boolean[] visitados = new boolean[tope]; //por defecto todo en false
        dfsRec(posInicial, visitados);
    }

    private void dfsRec(int pos, boolean[] visitados){
        System.out.println( vertices[pos] );
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
            System.out.println( vertices[pos] );
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
            System.out.println( vertices[pos] +  " : " + nivel );
            for (int j = 0; j < tope; j++) {
                if( matAdy[pos][j].isExiste() && !visitados[j]){
                    cola.encolar(new Tupla(j,nivel+1));
                    visitados[j]=true;
                }
            }
        }
    }

}
