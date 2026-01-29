import java.io.File;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;
public class ProcesarNotas {
    public static void main(String[] args) {
        String notasIn [][] = leerArchivo_Matriz("NotasEntrada.csv", m, n);
        String notasOut [][] = procesarNotas_Matriz(notasIn, 3);
        persistirResultados("NotasOut.csv", notasIn, notasOut);
    }
    public static void persistirResultados(String nombreArchivoOut, String notasIn [][], String notasOut [][]){
        try {
            Formatter fout = new Formatter(new File(nombreArchivoOut));
            fout.format("%s\n", "NOMRES;NOTAS1;NOTAS2;PROMEDIO;SUPLE;ESTADO");
            for (int i = 0; i < notasIn.length; i++) {
                for (int j = 0; j < notasIn[0].length; j++) 
                    fout.format("%s;", notasIn[i][j]);
                for (int j = 0; j < notasOut[0].length; j++) 
                    fout.format("%s;", notasOut[i][j]);
                fout.format("%s", "\n");
            }
            fout.close();
        } catch (Exception e) {
        }
    }
    public static String [][] procesarNotas_Matriz(String datosNotas[][], int n){
        String datosOut[][] = new String[datosNotas.length][n];
        Random ale = new Random();
        for (int i = 0; i < datosNotas.length; i++) {
            double promedio = (Double.valueOf(datosNotas[i][1]) + Double.valueOf(datosNotas[i][2])) / 2;
            double suple = (promedio < 6.5) ? ale.nextDouble(10) : 0 ;
            String estado = (promedio >= 6.5) ? "Aprobado" : (suple >= 6.5) ? "Aprobado": "ReProbado" ;
            datosOut[i][0] = String.format("%.2f", promedio); 
            datosOut[i][1] = String.format("%.2f", suple); 
            datosOut[i][2] = estado; 
        }
        return datosOut;
    }
    public static String [][] leerArchivo_Matriz(String nombreArchivo, int m, int n){
        String datos[][] = new String[m][n];
        try {
            Scanner fin = new Scanner(new File(nombreArchivo));
            fin.nextLine();
            int i = 0;
            while(fin.hasNext()){
                String notas[] = fin.nextLine().split(";");
                datos[i][0] = notas[0]; //Nomb. Est.
                datos[i][1] = notas[1]; //Nota1 
                datos[i][2] = notas[2]; //Nota2
                i++;
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return datos;
    }
}
