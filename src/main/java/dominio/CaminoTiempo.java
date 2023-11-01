package dominio;

public class CaminoTiempo {
    private String camino;
    private double tiempoMinimo;

    public CaminoTiempo(String camino, double tiempoMinimo) {
        this.camino = camino;
        this.tiempoMinimo = tiempoMinimo;
    }

    public String getCamino() {
        return camino;
    }

    public double getTiempoMinimo() {
        return tiempoMinimo;
    }

}
