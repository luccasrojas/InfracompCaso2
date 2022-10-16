public class Envejecimiento extends Thread
{
    /* Clase de envejecimiento de las referencias que estan en RAM, se encarga de cada 1ms actualizar en la 
       TP todas las referencias que se encuentran en RAM, haciendo el debido corrimiento de bits por cada iteracion
    */
    private TP tp;
    //Constructor recibe la TP
    public Envejecimiento ( TP tp)
    {
        this.tp = tp;
    }

    public void run()
    {
        while(true)
        {
            //Dormir el thread para simular clock de 1 ms
            try 
            {
                Thread.sleep(1,0);
                //Hacer el corrimiento de bits de las paginas de forma sincronizada sobre la TP
                tp.hacerCorrimiento();
            } 
            catch (InterruptedException e) 
            {
                //Finalizar la ejecucion del thread cuando se interrumpe al acabar con todas las referencias
                break;
            } 
        }
    }

}
