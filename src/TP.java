import java.util.ArrayList;
import java.util.HashMap;

/* Clase de Tabla de Paginas, funciona como un monitor para que los otros 2 Threads puedan actuar sobre las 
   referencias de la talba de paginas de forma sincronizada
*/
public class TP 
{
    //Attributes

    //Tamanio de bits para el algoritmo de corrimiento
    private static final int N = 30;
    /* La tabla de paginas la modelamos como un HashMap, donde la llave es la direccion de memoria virtual y 
       el valor es bien sea un long que representa los bits de corrimiento (Es decir esta en RAM) o null, que 
       representa que la referencia se encuentra en memoria secundaria o disco.*/
    private HashMap<Integer,Long> tp = new HashMap<Integer, Long>();
    //Numero de marcos de pagina de la RAM
    private Integer numeroMarcos;
    //Pagina que esta en RAM y tiene el menor numero de envejecimiento (Candidata para ser reemplazada)
    private Integer pageToRemove;

    //Método Constructor TP, recibe cuantos de sus 64 marcos pueden estar llenos, es decir estar en RAM
    public TP(int numeroMarcos)
    {
        this.numeroMarcos = numeroMarcos;
        //Inicializa todas las direcciones virtuales en null
        for (int i=0; i<64; i++)
        {
            tp.put(i, null);
        }
    }

    //Metodo para consultar si una pagina se encuentra en la RAM o no, si si incrementa el numero de envejecimiento 
    public synchronized boolean consultarMarcoPagina(Integer pagina)
    {
        //Revisa si la pagina esta en RAM
        boolean esta = tp.get(pagina) != null; 
        if (esta)
        {
            //Se le anade 1 en la posicion mas significativa del numero de envejecimiento
            Long value = tp.get(pagina);
            value =  (value + (long) Math.pow(2,N));
            tp.put(pagina, value);
        }
        return esta;
    }
    //Metodo que hace el corrimiento de todas las referencias de la TP que estan en RAM (Algoritmo de envejecimiento)
    public synchronized void hacerCorrimiento()
    {
        //Lista de todos los numeros de envejecimiento menores
        ArrayList<Integer> minimos = new ArrayList<Integer>();
        Long valor;
        Long min = (long) Math.pow(2,N);
        //Se encuentra cual es el numero de envejecimiento menor
        for(Integer key: this.tp.keySet())
        {
            valor = tp.get(key);
            if (valor != null)
            {
                valor = valor >>> 1;
                tp.put(key, valor);
                if(valor <= min)
                {
                    min = valor;
                }
            }
        }
        //Se guardan todas las paginas que tienen el numero de envejecimiento menor
        for(Integer key: this.tp.keySet())
        {
            valor = tp.get(key);
            if (valor != null)
            {
                if(valor == min)
                {
                    minimos.add(key);
                }
            }
        }
        //Obtener aleatoriamente un valor de la lista de minimos para ser la pagina candidata a salir
        if (minimos.size() > 0)
        {
            int randomIndex = (int) (Math.random() * minimos.size());
            //Se guarda la pagina candidata a salir
            this.pageToRemove = minimos.get(randomIndex);
        }
    }
    //Metodo que saca una pagina de la RAM (La que indique el algorimto de envejecimiento) y carga una nueva
    public synchronized Integer modifyTP(Integer marcoEntra)
    {
        //Si la RAM esta llena debe sacar la pagina que indique el algoritmo de envejecimiento
        if(numeroMarcos<=0)
        {
            tp.put(this.pageToRemove, null);
            tp.put(marcoEntra,(long) Math.pow(2,N));
            return this.pageToRemove;
        }
        //Si la RAM no esta llena solo carga la nueva pagina
        else
        {
            tp.put(marcoEntra, (long) Math.pow(2,N));
            numeroMarcos--;
            return null;
        }        
    }

    //Imprimir la TP
    public void imprimirTP()
    {
        for(Integer key: this.tp.keySet())
        {
            System.out.println("Pagina: " + key + " Marco: " + tp.get(key));
        }
        System.out.println("------------------------------------------------");
    }
}
