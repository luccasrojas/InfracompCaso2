import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.lang.*;

public class TLB 
{

    //Llave: Dirección de memoria 
    //Valor: Valor de la dirección de memoria(Pagina)
    private HashMap<Integer,Integer> paginasTlb = new HashMap<Integer, Integer>();

    //Tamanio de la pagina que maneja la TLB
    private Integer numeroEntradas;

    //Cola que determina el orden en que entro cada uno de los elementos para aplicar el 
    //Algoritmo FIFO
    private Queue<Integer> tlb = new LinkedList<>();

    //Metodo constructor TLB -----------------------------------------------------------
    public TLB(int numeroEntradas)
    {
        this.numeroEntradas = numeroEntradas;
        for(int i = 0; i < this.numeroEntradas; i++)
        {
            paginasTlb.put(i, null);
            tlb.add(i);
        }
    }
    
    //Metodos --------------------------------------------------------------------------    
    public synchronized void cargarEntrada (Integer entrada)
    {
        Integer direccionPrimera = tlb.remove();
        paginasTlb.put(direccionPrimera,entrada);
        tlb.add(direccionPrimera);
    }
    
    public synchronized boolean buscarEntrada(Integer entrada)
    {
        //Se consulta si la pagina esta en el TLB al acceder al valor del hash
        boolean esta = paginasTlb.containsValue(entrada);   
        if (esta)
        {
            //Tiempo de respuesta de 2ns para consultar direccion que esta en la TLB
            try {
                Thread.sleep(0,2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return paginasTlb.containsValue(entrada);
    }

}