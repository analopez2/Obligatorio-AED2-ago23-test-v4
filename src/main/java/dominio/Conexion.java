package dominio;

import interfaz.TipoConexion;

public class Conexion implements Comparable<Conexion> {
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

    public boolean validarDoubles() {
        return this.costo > 0 && this.tiempo > 0;
    }

    public boolean validarStringsAndType() {
        return this.getCodigoCiudadDestino != null && !this.getCodigoCiudadDestino.isEmpty() && this.codigoCiudadOrigen != null && !this.codigoCiudadOrigen.isEmpty() && this.tipo != null;
    }

    @Override
    public int compareTo(Conexion o) {
        return Integer.compare(identificadorConexion, o.identificadorConexion);
    }

    public String getCodigoCiudadOrigen() {
        return codigoCiudadOrigen;
    }

    public void setCodigoCiudadOrigen(String codigoCiudadOrigen) {
        this.codigoCiudadOrigen = codigoCiudadOrigen;
    }

    public String getGetCodigoCiudadDestino() {
        return getCodigoCiudadDestino;
    }

    public void setGetCodigoCiudadDestino(String getCodigoCiudadDestino) {
        this.getCodigoCiudadDestino = getCodigoCiudadDestino;
    }

    public int getIdentificadorConexion() {
        return identificadorConexion;
    }

    public void setIdentificadorConexion(int identificadorConexion) {
        this.identificadorConexion = identificadorConexion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public TipoConexion getTipo() {
        return tipo;
    }

    public void setTipo(TipoConexion tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conexion conexion = (Conexion) o;
        return identificadorConexion == conexion.identificadorConexion;
    }
}
