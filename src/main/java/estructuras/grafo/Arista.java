package estructuras.grafo;

import dominio.Conexion;
import estructuras.ABB.ABB;

public class Arista {

    private boolean existe;
    private ABB<Conexion> conexiones;

    public Arista() {
        this.existe = false;
        this.conexiones = new ABB<>();
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public ABB<Conexion> getConexiones() {
        return conexiones;
    }

    public void setConexiones(ABB<Conexion> conexiones) {
        this.conexiones = conexiones;
    }

    public void agregarConexion(Conexion con) {
        this.conexiones.insertar(con);
    }

    public void actualizarConexion(Conexion con) {
        Conexion buscada = this.conexiones.obtener(con);
        buscada.setCosto(con.getCosto());
        buscada.setTipo(con.getTipo());
        buscada.setTiempo(con.getTiempo());
    }

    public void viajeCostoMinimo(){
        //Aplanar las conexiones y ver cual es la de menor tiempo
        //utilizar Djkstra
    }
}



