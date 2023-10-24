package sistema;

import dominio.Ciudad;
import dominio.Viajero;
import estructuras.ABB.ABB;
import estructuras.grafo.Grafo;
import interfaz.*;

public class ImplementacionSistema implements Sistema {

    private Grafo grafoCiudades;
    private ABB<Viajero> viajeros;
    private final ABB<Viajero>[] viajerosTipo = new ABB[3];
    @Override
    public Retorno inicializarSistema(int maxCiudades) {
        if (maxCiudades <= 5) {
            return Retorno.error1("El máximo de ciudades debe ser mayor a 5");
        }

        this.grafoCiudades = new Grafo(maxCiudades, true);
        this.viajeros = new ABB<>();
        this.viajerosTipo[TipoViajero.ESTANDAR.getIndice()] = new ABB<>();
        this.viajerosTipo[TipoViajero.PREMIUM.getIndice()] = new ABB<>();
        this.viajerosTipo[TipoViajero.CASUAL.getIndice()] = new ABB<>();
        return Retorno.ok();
    }

    @Override
    public Retorno registrarViajero(String cedula, String nombre, int edad, TipoViajero tipo) {
        Viajero v = new Viajero(cedula, nombre, edad, tipo);
        if (!v.isValid()) {
            return Retorno.error1("Parametros invalidos");
        }
        if (!v.validarCedula()) {
            return Retorno.error2("Cedula invalida");
        }
        if (this.viajeros.obtener(v) != null) {
            return Retorno.error3("Ya existe un viajero con esa cedula");
        }
        this.viajeros.insertar(v);
        this.viajerosTipo[tipo.getIndice()].insertar(v);

        return Retorno.ok();
    }

    @Override
    public Retorno buscarViajero(String cedula) {
        Viajero buscado = new Viajero(cedula,"",0,TipoViajero.PREMIUM);
        if (buscado.validarCedula()){
            return Retorno.error1("Cedula invalida");
        }
        Viajero v = this.viajeros.obtener(buscado);
        int pasadas = this.viajeros.getIteraciones();

        if (v == null){
            return Retorno.error2("No existe viajero registrado con dicha cedula");
        }
        return Retorno.ok(pasadas,v.toString());
    }

    @Override
    public Retorno listarViajerosAscendente() {
        viajeros.listarAsc();
        //TODO verificar formato en el que se listan porque tiene que tener una barra recta
        // entre los elementos
        return Retorno.ok();
    }

    @Override
    public Retorno listarViajerosDescendente() {
        viajeros.listarAsc();
        //TODO verificar formato en el que se listan porque tiene que tener una barra recta
        // entre los elementos
        return Retorno.ok();
    }

    @Override
    public Retorno listarViajerosPorTipo(TipoViajero tipo) {
        if (tipo == null) {
            return Retorno.error1("El tipo es obligatorio");
        }

        this.viajerosTipo[tipo.getIndice()].listarAsc();
        return Retorno.ok();
    }

    @Override
    public Retorno registrarCiudad(String codigo, String nombre) {
        if(grafoCiudades.esLleno()){
            return Retorno.error1("No se pueden ingresar mas ciudades");
        }
        Ciudad c = new Ciudad(codigo, nombre);
        if(c.isValid()){
            return Retorno.error2("Los datos no pueden ser vacion o nulos");
        }
        if(c.isValidCodigo()){
            return Retorno.error3("Codigo invalido");
        }
        if (grafoCiudades.existeVertice(c)){
            return Retorno.error4("Ya existe una ciudad con ese codigo");
        }
        this.grafoCiudades.agregarVertice(c);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno actualizarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listadoCiudadesCantTrasbordos(String codigo, int cantidad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimo(String codigoCiudadOrigen, String codigoCiudadDestino) {
        return Retorno.noImplementada();
    }
}
