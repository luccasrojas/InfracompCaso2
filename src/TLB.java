import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class TLB 
{
    HashMap<Integer,Integer> paginasTlb = new HashMap<Integer, Integer>();
    Integer tamanioPagina;
    Queue<Integer> tlb = new LinkedList<>();

    public TLB(int tamanioPagina, int numPaginas)
    {
        this.tamanioPagina = tamanioPagina;
        for(int i = 0; i < numPaginas*tamanioPagina; i++)
        {
            paginasTlb.put(i, null);
            tlb.add(i);
        }
    }

    public synchronized void cargarPagina(Integer pagina)
    {
        Integer direccionPrimera = tlb.remove();
        paginasTlb.put(direccionPrimera,pagina);
        tlb.add(direccionPrimera);
    }

    public synchronized boolean buscarPagina(Integer pagina)
    {
        return paginasTlb.containsValue(pagina);
    }
}
