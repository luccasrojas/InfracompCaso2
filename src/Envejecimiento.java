public class Envejecimiento extends Thread
{
    private static final int N = 32;
    //this.contadorEnvejecimiento = this.contadorEnvejecimiento >> 1;
    private TLB tlb;
    private TP tp;
    private boolean centinela=true;
    private Integer min;
    private Integer pageToRemove;
    public Envejecimiento (TLB tlb, TP tp)
    {
        this.tlb = tlb;
        this.tp = tp;
    }
    public void run()
    {
        long tiempo =0;
        while(centinela)
        {
            try 
            {
                Thread.sleep(1,0);
                this.min = 2^32;
                this.pageToRemove = null;
                Integer valor;
                //Hacer el corrimiento de bits de la pagina
                long start = System.nanoTime();
                tp.hacerCorrimiento();
                long end = System.nanoTime();
                tiempo += end - start;
                long start1 = System.nanoTime();
                tp.setPageToRemove(this.pageToRemove);
                long end1 = System.nanoTime();
                tiempo += end1 - start1;
            } 
            catch (InterruptedException e) 
            {
                break;
            } 
        }
        System.out.println("Tiempo de envejecimiento " + tiempo/1000000 + " ms");
    }

}


