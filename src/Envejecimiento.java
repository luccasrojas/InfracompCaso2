public class Envejecimiento extends Thread
{
    private static final int N = 16;
    //this.contadorEnvejecimiento = this.contadorEnvejecimiento >> 1;
    private TLB tlb;
    private TP tp;
    public Envejecimiento (TLB tlb, TP tp)
    {
        this.tlb = tlb;
        this.tp = tp;
    }

    public void run()
    {
        int veces = 0;
        while(true)
        {
            try 
            {
                Thread.sleep(1,0);
                //Hacer el corrimiento de bits de la pagina
                tp.hacerCorrimiento();
                veces++;
            } 
            catch (InterruptedException e) 
            {
                //System.out.println("Veces que se ha hecho el corrimiento: "+veces);
                break;
            } 
        }
    }

}
