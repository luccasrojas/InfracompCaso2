public class Envejecimiento extends Thread
{
    private static final int N = 16;
    //this.contadorEnvejecimiento = this.contadorEnvejecimiento >> 1;
    private TLB tlb;
    private TP tp;
    private Integer min;
    public Envejecimiento (TLB tlb, TP tp)
    {
        this.tlb = tlb;
        this.tp = tp;
    }

    public void run()
    {
        while(true)
        {
            try 
            {
                Thread.sleep(1,0);
                this.min = 2^N;
                //Hacer el corrimiento de bits de la pagina
                tp.hacerCorrimiento();
            } 
            catch (InterruptedException e) 
            {
                break;
            } 
        }
    }

}
