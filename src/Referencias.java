import java.util.ArrayList;

public class Referencias extends Thread 
{
    private ArrayList<Integer> referencias= new ArrayList<Integer>();
    private TLB tlb;
    private TP tp;

    public Referencias(ArrayList<Integer> referencias,TLB tlb,TP tp)
    {
        this.referencias = referencias;
        this.tlb = tlb;
        this.tp = tp;
    }

    public void run()
    {
        for(Integer referencia: referencias)
        {
            try 
            {
                Thread.sleep(2,0);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }

            if(!tlb.buscarEntrada(referencia))
            {
                if(!tp.consultarMarcoPagina(referencia))
                {
                    //Fallo de pagina
                    //Envejecimiento
                }
                else
                {
                    //Esta en RAM
                }
            }
        }
    }
}
