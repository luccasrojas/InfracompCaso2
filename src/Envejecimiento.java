public class Envejecimiento extends Thread
{
    //this.contadorEnvejecimiento = this.contadorEnvejecimiento >> 1;
    private TLB tlb;
    private TP tp;
    private boolean centinela=true;
    public Envejecimiento (TLB tlb, TP tp)
    {
        this.tlb = tlb;
        this.tp = tp;
    }
    public void run()
    {
        while(centinela)
        {
            try 
            {
                Thread.sleep(1);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
            //Hacer el corrimiento de bits de la pagina
            for(Integer valor: tp.getValues())
            {
                if (valor != null)
                {
                    valor = valor >> 1;
                }
            }
        }
    }
}


