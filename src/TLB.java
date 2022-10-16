import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class TLB 
{
    /* Clase de TLB, funciona como un monitor para que los otros 2 Threads puedan actuar sobre las 
       referencias de la TLB de forma sincronizada
    */

    //Llave: Dirección de memoria TLB
    //Valor: Direccion virtual
    private HashMap<Integer,Integer> paginasTlb = new HashMap<Integer, Integer>();

    //Tamanio de la TLB
    private Integer numeroEntradas;

    //Cola que determina el orden en que entro cada uno de los elementos para aplicar el algoritmo FIFO
    private Queue<Integer> tlb = new LinkedList<>();

    //Metodo constructor TLB recibe el numero de entradas que puede tener la TLB
    public TLB(int numeroEntradas)
    {
        this.numeroEntradas = numeroEntradas;
        //Inicializa todas las direcciones virtuales en null
        for(int i = 0; i < this.numeroEntradas; i++)
        {
            paginasTlb.put(i, null);
            tlb.add(i);
        }
    }
    
    //Metodos que carga una nueva entrada en la TLB en la direccion de TLB que le corresponde segun FIFO  
    public synchronized void cargarEntrada(Integer entrada)
    {
        Integer direccionPrimera = tlb.remove();
        paginasTlb.put(direccionPrimera,entrada);
        tlb.add(direccionPrimera);
    }
    //Metodo que consulta si una direccion de memoria virtual esta en la TLB
    public synchronized boolean buscarEntrada(Integer entrada)
    {
        return paginasTlb.containsValue(entrada);
    }
    //Metodo que elimina una entrada de la TLB, esto sucede cuando hay fallo de pagina y la pagina es sacada de la RAM
    public synchronized void deleteFromTLB(Integer entrada)
    {
        //Se busca la direccion de memoria TLB que contiene la direccion virtual que se quiere eliminar
        Integer direccionTLB = null;
        for (Integer key: paginasTlb.keySet())
        {
            if (paginasTlb.get(key) == entrada)
            {
                paginasTlb.put(key, null);
                direccionTLB = key;
            }
        }
        //Se elimina la direccion y se crea una copia de la cola con la direccion de TLB de primeras
        Queue<Integer> tlbCopy = new LinkedList<>();
        tlb.remove(direccionTLB);
        tlbCopy.add(direccionTLB);
        //Se rellena la cola nueva con las direcciones de TLB que quedaron
        for (Integer key: tlb)
        {
            if (key != direccionTLB)
            {
                tlbCopy.add(key);
            }
        }
        //Se reemplaza la cola vieja por la nueva
        tlb = tlbCopy;
    }

    //Imprimir el estado de la TLB
    public void printTLB()
    {
        System.out.println("TLB: ");
        for(Integer key: this.paginasTlb.keySet())
        {
           System.out.println("Key: "+key+" Value: "+paginasTlb.get(key));
        }
    }
}