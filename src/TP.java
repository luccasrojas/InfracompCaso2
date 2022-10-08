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
            //Hacer el corrimiento de bits de la pagina
            for(Integer valor: tp.values())
            {
                if (valor != null)
                {
                    valor = valor >> 1;
                }
            }
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
        
        //Hacer el corrimiento de bits de la pagina
        for(Integer valor: tp.values())
        {
            if (valor != null)
            {
                valor = valor >> 1;
            }
        }
        tp.put(marcoSale, null);
        tp.put(marcoEntra, 2^N);
    }



}
