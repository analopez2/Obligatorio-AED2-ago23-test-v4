package dominio;

import java.util.Objects;

public class Ciudad implements Comparable<Ciudad> {
    private String codigo;
    private String nombre;

    public Ciudad(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public boolean isValidCodigo() {
        return this.codigo.length() >= 5 && this.codigo.matches("[A-Z0-9]+");
    }

    public boolean isValid() {
        return this.nombre != null && !this.nombre.isEmpty() && this.codigo != null && !this.codigo.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ciudad ciudad = (Ciudad) o;
        return Objects.equals(codigo, ciudad.codigo);
    }


    @Override
    public int compareTo(Ciudad o) {
        return this.codigo.compareTo(o.codigo);
    }

    @Override
    public String toString() {
        return codigo + ";" + nombre;
    }
}
