import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TP 
{
    private static final int N = 8;
    //Attributes
    private HashMap<Integer,Integer> tp = new HashMap<Integer, Integer>();
    private Integer numeroMarcos;

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
    public synchronized Integer getNumeroMarcos()
    {
        return numeroMarcos;
    }

    public synchronized Collection<Integer> getValues()
    {
        return tp.values();
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
    
    public synchronized void modifyTP(Integer marcoSale, Integer marcoEntra)
    {
        //Tiempo de respuesta de 30ns para consultar direccion
        try 
        {
            Thread.sleep(0,30);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
        //Se busca la pagina que sale de la memoria
        tp.put(marcoSale, null);
        tp.put(marcoEntra, 2^N);
    }

    public synchronized Integer obtenerMenorValor()
    {
        Integer menor = 2^N;
        Integer pagina = null;
        for(Integer valor: tp.values())
        {
            if (valor != null)
            {
                if (valor < menor)
                {
                    menor = valor;
                    pagina = tp.get(valor);
                }
            }
        }
        return pagina;
    }


}
