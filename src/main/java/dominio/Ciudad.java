package dominio;

import java.util.Objects;

public class Ciudad {
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
        return this.nombre!=null && !this.nombre.isEmpty() && this.codigo!=null && !this.codigo.isEmpty();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ciudad ciudad = (Ciudad) o;
        return Objects.equals(codigo, ciudad.codigo);
    }
}
