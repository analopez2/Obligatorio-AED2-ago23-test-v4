package estructuras.grafo;

public class Tupla {
    private int pos;
    private int nivel;

    public Tupla(int pos, int nivel) {
        this.pos = pos;
        this.nivel = nivel;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
