import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TP 
{
    private static final int N = 30;
    //Attributes
    private HashMap<Integer,Long> tp = new HashMap<Integer, Long>();
    private Integer numeroMarcos;
    private Integer pageToRemove;
    private long tiempoCarga= 0;

    //Método Constructor TP
    public TP(int numeroMarcos)
    {
        //TODO
        this.numeroMarcos = numeroMarcos;
        for (int i=0; i<64; i++)
        {
            tp.put(i, null);
        }
    }

    //Getters
    public synchronized long getTiempoCarga()
    {
        return tiempoCarga;
    }

    //Metodo que al consultar una pagina se notifica si se encuentra en la TP o si no. Teniendo en cuenta que eso implica
    //un costo de tiempo de respuesta
    public synchronized boolean consultarMarcoPagina(Integer pagina)
    {
        // System.out.println("El marco: "+pagina+"su valor es de: "+tp.get(pagina));
        boolean esta = tp.get(pagina) != null; 
        if (esta)
        {
            //Se le suma 1 al valor consultado
            Long value = tp.get(pagina);
            //System.out.println(pagina+"----------------before--------"+value);
            // imprimirTP();
            value =  (value + (long) Math.pow(2,N));
            tp.put(pagina, value);
            //System.out.println(pagina+"----------after----------"+value);
            // imprimirTP();  
        }
        return esta;
    }
    
    public synchronized void hacerCorrimiento()
    {
        // System.out.println("Tp antes de hacer el corrimiento : \n");
        // imprimirTP();

        Long valor;
        Long min = (long) Math.pow(2,N);
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

        // System.out.println("Tp despues de hacer el corrimiento: \n");
        // imprimirTP();
    }

    public synchronized Integer modifyTP(Integer marcoEntra)
    {
        //Se busca la pagina que sale de la memoria
        if(numeroMarcos<=0)
        {
            tp.put(this.pageToRemove, null);
            tp.put(marcoEntra,(long) Math.pow(2,N));
            // System.out.println("Marco que entra: " + marcoEntra);
            // System.out.println("Marco que sale: " + this.pageToRemove);
        }
        else
        {
            tp.put(marcoEntra, (long) Math.pow(2,N));
            numeroMarcos--;
        }
        // imprimirTP();
        return this.pageToRemove;
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
