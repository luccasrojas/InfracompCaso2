import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/* Caso 2 de infraestructura computacional ISIS-2203
Autores: 
- Andres Arevalo Fajardo 201923853
- Alejandro Salgado 201923134
- Luccas Rojas 201923052
Este caso se compone de 4 clases, 2 Threads, el main y el de envejecimiento y 2 clases como monitores, 
la TLB y la TP.

App-> Clase principal, se encarga de leer el archivo de entrada, crear el hilo de envejecimiento, traducir y cargar
      las referencias dependiendo de su localizacion bien sea en la TLB, RAM o ROM y dar el resultado final.
*/

public class App {
    public static void main(String[] args) throws Exception 
    {
        //Inicializacion de ambos contadores temporales
        long tiempoTraduccion = 0;
        long tiempoCarga= 0;
        //Lista de referencias para ser procesada 
        ArrayList<Integer> referencias = new ArrayList<Integer>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Caso 2 Infracomp: Simulación proceso lectura y uso de datos de referencias \n\n");
        //Lectura Entradas TLB y Marcos de Pagina RAM
        System.out.println("Ingrese el número de entradas de la TLB:");
        int tlbEntradas = scanner.nextInt();

        System.out.println("Ingrese el número de marcos de pagina de la RAM:");
        int tabla = scanner.nextInt();
        //Creacion de la TLB y la TP a partir de sus respectivos tamanios, en el caso de la TP, el tamanio de la RAM
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
        Envejecimiento envejecimiento = new Envejecimiento(tp);
        envejecimiento.start();

        //Simulacion del proceso de referencias
        for(Integer referencia: referencias)
        {
            //Dormir el thread para simular clock de 2 ms
            try 
            {
                Thread.sleep(2,0);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }

            //Algoritmo de traduccion de direcciones
            boolean estaEnRAM = tp.consultarMarcoPagina(referencia);
            if(!tlb.buscarEntrada(referencia))
            {
                //La referencia no esta en la TLB
                if(!estaEnRAM)
                {
                    //La referencia no esta en la RAM -> Fallo de pagina
                    tiempoTraduccion+=60;
                    tiempoCarga+=10000000;
                    //Se agrega la referencia a la TLB y saca la referencia segun el algorimto de envejecimiento
                    Integer paginaSale = tp.modifyTP(referencia);
                    if (paginaSale != null && tlb.buscarEntrada(paginaSale))
                    {
                        //Se saca la referencia de la TLB si la referencia que sale de la TP se encontraba en la TLB
                        tlb.deleteFromTLB(paginaSale);
                    } 
                }
                else
                {
                    //La referencia esta en la RAM
                    tiempoCarga+=30;
                    tiempoTraduccion+=30;
                    //Esta en RAM
                }
                //Se simula la carga para el algoritmo de envejecimiento
                tlb.cargarEntrada(referencia);
            }
            else
            {
                //Esta en TLB y en RAM
                tiempoCarga+=30;
                tiempoTraduccion+=2;
            }            
        }

        //Al terminar el proceso se debe acabar con la ejecucion del hilo de envejecimiento
        envejecimiento.interrupt();
        //Salida del programa con tiempos registrados
        System.out.println("La carga de referencias fue exitosa \n");
        System.out.println("Tiempo carga: "+tiempoCarga);
        System.out.println("Tiempo traduccion: "+tiempoTraduccion);
    }
}
