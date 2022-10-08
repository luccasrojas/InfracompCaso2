import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

// public static void main(String[] args)throws Exception 
// 	{
// 		primos instancia = new primos();
// 		try (InputStreamReader is = new InputStreamReader(System.in); 
// 				BufferedReader br = new BufferedReader(is);)
// 		{
// 			String line = br.readLine();
// 			int casos = Integer.parseInt(line);
// 			line = br.readLine();
// 			for (int i = 0; i < casos && line != null && line.length() > 0 && !"0".equals(line); i++)
// 			{
// 				final String[] dataStr = line.split(" ");
// 				final int[] numeros = Arrays.stream(dataStr).mapToInt(f -> Integer.parseInt(f)).toArray();
// 				System.out.println(instancia.euclides(numeros));
// 				line = br.readLine();
// 			}
// 		}


public class App {
    public static void main(String[] args) throws Exception 
    {
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
        String archivo = scanner.nextLine();

        scanner.close();


        try {
            //Se obtiene el archivo 
            File archivoReferencias = new File("./data/" + archivo + ".txt");
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
        catch (Exception e) {

            System.err.println("No se encontro el archivo");
        
        }


        Envejecimiento envejecimiento = new Envejecimiento(tlb,tp);
        envejecimiento.start();
        
        Referencias instancia = new Referencias(referencias,tlb,tp);
        instancia.start(); 

    }
}
