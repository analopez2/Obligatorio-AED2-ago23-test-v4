package dominio;

import interfaz.TipoViajero;

import java.util.Objects;
import java.util.regex.Pattern;

public class Viajero {
    private String cedula;
    private  String nombre;
    private int edad;
    private TipoViajero tipo;

    public Viajero(String cedula, String nombre, int edad, TipoViajero tipo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.tipo = tipo;
    }
    public boolean isValid() {
        return this.cedula != null && this.nombre != null && this.edad != 0 && this.tipo!=null;
    }

    public boolean validarCedula (){
        return Pattern.matches("^([1-9][.])?[0-9]{3}[.][0-9]{3}-[0-9]$", this.cedula);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Viajero viajero = (Viajero) o;
        return Objects.equals(cedula, viajero.cedula);
    }

}
