import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws Exception 
    {
        
        long tiempoTraduccion = 0;
        long tiempoCarga= 0;

        ArrayList<Integer> referencias = new ArrayList<Integer>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Caso 2 Infracomp: Simulación proceso lectura y uso de datos de referencias \n\n");
        //Lectura Entradas TLB y Marcos de Pagina RAM
        System.out.println("Ingrese el número de entradas de la TLB:");
        int tlbEntradas = scanner.nextInt();
        System.out.println("Ingrese el número de marcos de pagina de la RAM:");
        int tabla = scanner.nextInt();
        TLB tlb = new TLB(tlbEntradas);
        TP tp = new TP(tabla);

        //Obtener nombre archivo 
        System.out.println("Ingrese el nombre del archivo, sin el .txt, del cual se obtendran las referencias (Esta almacenado en la carpeta data): \n");
        Scanner scanner2 = new Scanner(System.in);
        String archivo = scanner2.nextLine();

        scanner.close();
        scanner2.close();

        try {
            //Se obtiene el archivo 
            File archivoReferencias = new File("./data/" + archivo + ".txt");
            System.out.println("Se obtuvo el archivo: " + archivoReferencias.getName());
            Scanner scannerRef = new Scanner(archivoReferencias);

            //Se lee el archivo hasta que no hayan lineas de texto, que contienen la referencia 
            int r = 0;
            while(scannerRef.hasNextLine() == true)
            {
                //Se obtiene la referencia que se esta buscando
                Integer referencia = Integer.parseInt(scannerRef.nextLine());
                //Se agrega la referencia a un arreglo que contiene todas las referencias
                referencias.add(referencia);
            }
            scannerRef.close();
        }
        catch (Exception e) 
        {
            System.err.println("No se encontro el archivo");
        }
        //Se crea el hilo que se encarga de envejecer las referencias
        Envejecimiento envejecimiento = new Envejecimiento(tlb,tp);
        envejecimiento.start();


        //Simulacion del proceso de referencias
        for(Integer referencia: referencias)
        {
            //Dormir el thread para simular clock
            try 
            {
                Thread.sleep(2,0);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
            //Buscar 
            if(!tlb.buscarEntrada(referencia))
            {
                if(!tp.consultarMarcoPagina(referencia))
                {
                    tiempoCarga+=10000000;
                    tp.modifyTP(referencia);
                }
                else
                {
                    tiempoCarga+=30;
                    tiempoTraduccion+=30;
                    //Esta en RAM
                }
                tlb.cargarEntrada(referencia);
            }
            else
            {
                //Esta en TLB
                tiempoTraduccion+=2;
            }
            
        }
        //Al terminar el proceso acabar con la ejecucion del hilo de envejecimiento
        envejecimiento.interrupt();
        System.out.println("Tiempo carga: "+tiempoCarga);
        System.out.println("Tiempo traduccion: "+tiempoTraduccion);
    }
}
