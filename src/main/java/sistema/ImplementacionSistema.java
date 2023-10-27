package sistema;

import dominio.Ciudad;
import dominio.Conexion;
import dominio.Viajero;
import estructuras.ABB.ABB;
import estructuras.grafo.Grafo;
import estructuras.grafo.Arista;
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
        Viajero buscado = new Viajero(cedula, "", 0, TipoViajero.PREMIUM);
        if (!buscado.validarCedula()) {
            return Retorno.error1("Cedula invalida");
        }
        Viajero v = this.viajeros.obtener(buscado);
        int pasadas = this.viajeros.getIteraciones();

        if (v == null) {
            return Retorno.error2("No existe viajero registrado con dicha cedula");
        }
        return Retorno.ok(pasadas, v.toString());
    }

    @Override
    public Retorno listarViajerosAscendente() {
        String res = viajeros.listarAsc();
        return Retorno.ok(res);
    }

    @Override
    public Retorno listarViajerosDescendente() {
        String res = viajeros.listarDes();
        return Retorno.ok(res);
    }

    @Override
    public Retorno listarViajerosPorTipo(TipoViajero tipo) {
        if (tipo == null) {
            return Retorno.error1("El tipo es obligatorio");
        }

        String res = this.viajerosTipo[tipo.getIndice()].listarAsc();
        return Retorno.ok(res);
    }

    @Override
    public Retorno registrarCiudad(String codigo, String nombre) {
        if (grafoCiudades.esLleno()) {
            return Retorno.error1("No se pueden ingresar mas ciudades");
        }
        Ciudad c = new Ciudad(codigo, nombre);
        if (!c.isValid()) {
            return Retorno.error2("Los datos no pueden ser vacion o nulos");
        }
        if (!c.isValidCodigo()) {
            return Retorno.error3("Codigo invalido");
        }
        if (grafoCiudades.existeCiudad(c)) {
            return Retorno.error4("Ya existe una ciudad con ese codigo");
        }
        this.grafoCiudades.agregarCiudad(c);
        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        Conexion c = new Conexion(codigoCiudadOrigen, codigoCiudadDestino, identificadorConexion, costo, tiempo, tipo);
        Retorno rtConexion = validarConexion(c);
        if (!rtConexion.isOk()) {
            return rtConexion;
        }

        Ciudad ciudadOrigen = new Ciudad(codigoCiudadOrigen, "");
        Ciudad ciudadDestino = new Ciudad(codigoCiudadDestino, "");

        Retorno rtCiudades = validarCiudades(ciudadOrigen, ciudadDestino);
        if (!rtCiudades.isOk()) {
            return rtCiudades;
        }

        if (this.grafoCiudades.existeConexion(ciudadOrigen, ciudadDestino, c)) {
            return Retorno.error6("Ya existe una conexión entre las ciudades con el mismo identificador");
        }

        this.grafoCiudades.agregarConexion(ciudadOrigen, ciudadDestino, c);
        return Retorno.ok();
    }

    @Override
    public Retorno actualizarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        Conexion c = new Conexion(codigoCiudadOrigen, codigoCiudadDestino, identificadorConexion, costo, tiempo, tipo);
        Retorno rtConexion = validarConexion(c);
        if (!rtConexion.isOk()) {
            return rtConexion;
        }

        Ciudad ciudadOrigen = new Ciudad(codigoCiudadOrigen, "");
        Ciudad ciudadDestino = new Ciudad(codigoCiudadDestino, "");

        Retorno rtCiudades = validarCiudades(ciudadOrigen, ciudadDestino);
        if (!rtCiudades.isOk()) {
            return rtCiudades;
        }

        if (!this.grafoCiudades.existeConexion(ciudadOrigen, ciudadDestino, c)) {
            return Retorno.error6("No existe una conexión entre las ciudades con el mismo identificador");
        }

        this.grafoCiudades.actualizarConexion(ciudadOrigen, ciudadDestino, c);
        return Retorno.ok();
    }

    private Retorno validarConexion(Conexion c) {
        if (!c.validarDoubles()) {
            return Retorno.error1("Costo y tiempo no pueden ser menores a 0");
        }

        if (!c.validarStringsAndType()) {
            return Retorno.error2("Codigos de ciudades y tipo de conexión son obligatorios");
        }

        return Retorno.ok();
    }

    private Retorno validarCiudades(Ciudad origen, Ciudad destino) {
        if (!origen.isValidCodigo() || !destino.isValidCodigo()) {
            return Retorno.error3("Codigos de ciudades inválidos");
        }

        if (!this.grafoCiudades.existeCiudad(origen)) {
            return Retorno.error4("No existe la cidudad de origen");
        }

        if (!this.grafoCiudades.existeCiudad(destino)) {
            return Retorno.error5("No existe la cidudad de destino");
        }

        return Retorno.ok();
    }

    @Override
    public Retorno listadoCiudadesCantTrasbordos(String codigo, int cantidad) {
        if (cantidad < 0) {
            return Retorno.error1("La cantidad de trasbordos no puede ser menor a 0");
        }
        if (codigo == null || codigo.isEmpty()) {
            return Retorno.error2("El código no puede ser nulo");
        }

        Ciudad c = new Ciudad(codigo, "");
        if (!c.isValidCodigo()) {
            return Retorno.error3("El código de ciudad no es válido");
        }

        if (!this.grafoCiudades.existeCiudad(c)) {
            return Retorno.error4("La ciudad no existe en el sistema");
        }

        //Esto es con bfs2 de grafo hay que devolver ordenado
        //agregar en un abb y retornar una lista de strings
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimo(String codigoCiudadOrigen, String codigoCiudadDestino) {
        if (codigoCiudadDestino == null || codigoCiudadOrigen == null || codigoCiudadOrigen.isEmpty() || codigoCiudadDestino.isEmpty()) {
            return Retorno.error1("Codigos de ciudades son obligatorios");
        }

        Ciudad origen = new Ciudad(codigoCiudadOrigen, "");
        Ciudad destino = new Ciudad(codigoCiudadDestino, "");
        if (!origen.isValidCodigo() || !destino.isValidCodigo()) {
            return Retorno.error2("Códigos de ciudad deben ser válidos");
        }

        if (!this.grafoCiudades.existeCiudad(origen)) {
            return Retorno.error4("No existe la ciudad de origen");
        }

        if (!this.grafoCiudades.existeCiudad(destino)) {
            return Retorno.error5("No existe la ciudad de destino");
        }

        //TODO si no hay camino error 3

        //Esto es con djkstra

        return Retorno.noImplementada();
    }
}
