import java.util.HashMap;

public class RAM 
{
    //Llave: Dirección de memoria 
    //Valor: Valor de la dirección de memoria(Numero default)
    HashMap<Integer,Integer> ram = new HashMap<Integer, Integer>();
    Integer tamanioPagina;

    public RAM(int marcosPagina, int tamanioPagina)
    {
        this.tamanioPagina = tamanioPagina;
        for(int i = 0; i < marcosPagina*tamanioPagina; i++)
        {
            ram.put(i, null);
        }
    }


} 
