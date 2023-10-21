package dominio;

import interfaz.TipoConexion;

public class Conexion {
    private String codigoCiudadOrigen;
    private String getCodigoCiudadDestino;
    private int identificadorConexion;
    private double costo;
    private double tiempo;
    private TipoConexion tipo;

    public Conexion(String codigoCiudadOrigen, String getCodigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        this.codigoCiudadOrigen = codigoCiudadOrigen;
        this.getCodigoCiudadDestino = getCodigoCiudadDestino;
        this.identificadorConexion = identificadorConexion;
        this.costo = costo;
        this.tiempo = tiempo;
        this.tipo = tipo;
    }
    public boolean validarDoubles(){
        return this.costo>0 && this.tiempo>0;
    }
    public boolean validarStrings(){
        return this.getCodigoCiudadDestino!=null && !this.getCodigoCiudadDestino.isEmpty() && this.codigoCiudadOrigen!=null && !this.codigoCiudadOrigen.isEmpty();
    }
    public boolean validarTipo(){
        return this.tipo!=null;
    }
}
