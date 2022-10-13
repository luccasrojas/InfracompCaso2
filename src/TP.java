import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TP 
{
    private static final int N = 16;
    //Attributes
    private HashMap<Integer,Integer> tp = new HashMap<Integer, Integer>();
    private Integer numeroMarcos;
    private Integer pageToRemove;
    private long tiempoCarga= 0;

    //Método Constructor TP
    public TP(int numeroMarcos)
    {
        this.numeroMarcos = numeroMarcos;
        //TODO era 64
        for (int i=0; i<10; i++)
        {
            tp.put(i, null);
        }
    }

    //Getters
    public synchronized long getTiempoCarga()
    {
        return tiempoCarga;
    }

    //Setters
    public synchronized void setPageToRemove(Integer pageToRemove)
    {
        this.pageToRemove = pageToRemove;
    }

    //Metodo que al consultar una pagina se notifica si se encuentra en la TP o si no. Teniendo en cuenta que eso implica
    //un costo de tiempo de respuesta
    public synchronized boolean consultarMarcoPagina(Integer pagina)
    {
        boolean esta = tp.get(pagina) != null; 
        if (esta)
        {
            //Se le suma 1 al valor consultado
            Integer value = tp.get(pagina);
            value = (int) (value + Math.pow(2,16));
            tp.put(pagina, value);
        }
        return esta;
    }
    
    public synchronized void hacerCorrimiento()
    {
        System.out.println("Tp antes de hacer el corrimiento : \n");
        imprimirTP();

        Integer valor;
        Integer min = (int) Math.pow(2,16);
        for(Integer key: this.tp.keySet())
        {
            valor = tp.get(key);
            if (valor != null)
            {
                valor = valor >> 1;
                tp.put(key, valor);
                if(valor <= min)
                {
                    min = valor;
                    this.pageToRemove = key;
                }
            }
        }

        System.out.println("Tp despues de hacer el corrimiento: \n");
        imprimirTP();
    }

    public synchronized void modifyTP(Integer marcoEntra)
    {
        //Se busca la pagina que sale de la memoria
        if(numeroMarcos<=0)
        {
            tp.put(this.pageToRemove, null);
            tp.put(marcoEntra,(int) Math.pow(2,16));
        }
        else
        {
            tp.put(marcoEntra, (int) Math.pow(2,16));
            numeroMarcos--;
        }
        System.out.println("Marco que entra: " + marcoEntra);
        System.out.println("Marco que sale: " + this.pageToRemove);
        imprimirTP();
    }
    //Imprimir la TP
    public void imprimirTP()
    {
        for(Integer key: this.tp.keySet())
        {
            System.out.println("Pagina: " + key + " Marco: " + tp.get(key));
        }
    }
    

}
