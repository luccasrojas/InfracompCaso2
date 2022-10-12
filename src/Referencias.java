import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Referencias extends Thread 
{
    private ArrayList<Integer> referencias= new ArrayList<Integer>();
    private TLB tlb;
    private TP tp;
    private CyclicBarrier barrier;

    public Referencias(ArrayList<Integer> referencias,TLB tlb,TP tp, CyclicBarrier barrier)
    {
        this.referencias = referencias;
        this.tlb = tlb;
        this.tp = tp;
        this.barrier = barrier;
    }


    public void run()
    {
        int fallos=0;
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
                    fallos++;
                    //System.out.println("Hay falla de pagina " + fallos);
                    Integer marcoSale = tp.getPageToRemove();
                    tp.modifyTP(marcoSale, referencia);
                }
                else
                {
                    //Esta en RAM
                }
                tlb.cargarEntrada(referencia);
            }
        }
        try {
            this.barrier.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Tiempo de carga " + tp.getTiempoCarga()/1000000 + " ms");
    }
}
