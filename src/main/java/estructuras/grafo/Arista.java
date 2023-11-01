package estructuras.grafo;

import dominio.Conexion;
import estructuras.ABB.ABB;
import estructuras.lista.Lista;

import java.util.Iterator;

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

    public boolean existeConexion(Conexion con) {
        return this.conexiones.existe(con);
    }

    public Conexion viajeCostoMinimo() {
        double tiempoMinimo = Double.MAX_VALUE;
        Lista<Conexion> conexiones = this.conexiones.aplanarCreciente();
        Iterator<Conexion> it = conexiones.iterator();

        Conexion conexionMinima = null;

        while (it.hasNext()) {
            Conexion conexion = it.next();
            double tiempoConexion = conexion.getTiempo();

            if (tiempoConexion < tiempoMinimo) {
                tiempoMinimo = tiempoConexion;
                conexionMinima = conexion;
            }
        }

        return conexionMinima;
    }

}



