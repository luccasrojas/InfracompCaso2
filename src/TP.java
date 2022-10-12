import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TP 
{
    private static final int N = 32;
    //Attributes
    private HashMap<Integer,Integer> tp = new HashMap<Integer, Integer>();
    private Integer numeroMarcos;
    private Integer pageToRemove;
    private long tiempoCarga= 0;

    //Método Constructor TP
    public TP(int numeroMarcos)
    {
        this.numeroMarcos = numeroMarcos;
        for (int i=0; i<64; i++)
        {
            tp.put(i, null);
        }
    }

    //Getters
    public Integer getPageToRemove() {
        return pageToRemove;
    }
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

        //Tiempo de respuesta de 30ns para consultar direccion
        try 
        {
            Thread.sleep(0,30);
            tiempoCarga += 30;
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }

        if (esta)
        {
            //Se el suma 1 al valor consultado
            Integer value = tp.get(pagina);
            value = value + 2^N;
            tp.put(pagina, value);
        }
        return esta;
    }
    
    public synchronized void hacerCorrimiento()
    {
        Integer valor;
        Integer min = 2^32;
        
        for(Integer key: this.tp.keySet())
            {
                valor = tp.get(key);
                if (valor != null)
                {
                    valor = valor >> 1;
                    if(valor <= min)
                    {
                        min = valor;
                        this.pageToRemove = key;
                    }
                }
            }
    }

    public synchronized void modifyTP(Integer marcoSale, Integer marcoEntra)
    {
        //Tiempo de respuesta de 30ns para consultar direccion
        try 
        {
            Thread.sleep(10,30);
            tiempoCarga += 10000030;
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        //Se busca la pagina que sale de la memoria
        if(numeroMarcos<=0)
        {
            tp.put(marcoSale, null);
            tp.put(marcoEntra, 2^N);
        }
        else
        {
            tp.put(marcoEntra, 2^N);
            numeroMarcos--;
            //System.out.println("Nuemro de marcos: " + numeroMarcos);
        }
            
    }

}
