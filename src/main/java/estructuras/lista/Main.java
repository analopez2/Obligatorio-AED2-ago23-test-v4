package estructuras.lista;

public class Main {

    public static void main(String[] args) {
        
        Lista<Integer> lista = new ListaImp<>();
        lista.insertar(5);
        lista.insertar(4);
        lista.insertar(3);
        lista.insertar(2);
        lista.insertar(1);
        
        for(int num : lista){
            System.out.println("Número: " + num);
        }
        System.out.println(lista);
        


    }
    
}
