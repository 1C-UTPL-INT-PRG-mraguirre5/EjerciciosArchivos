import java.io.File;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;
public class ProcesarNotas {
    public static int m = 10, n = 6;
    public static void main(String[] args) {
        String notasIn [][] = leerArchivo_Matriz("NotasEntrada.csv", m, 3);
        String notasOut [][] = procesarNotas_Matriz(notasIn, 3);
        persistirResultados("NotasOut.csv", notasIn, notasOut);
        double promedioGeneral = CalcularPromedioGeneral(notasOut);
    }
    public static double CalcularPromedioGeneral(String notasOut[][]){
        double suma = 0;
        int nroEstudiantes = notasOut.length;
        for (int i = 0; i < nroEstudiantes && notasOut[i][0] != null; i++) {
            suma += Double.valueOf(notasOut[i][0]);      
        }
        return suma/nroEstudiantes;
    }
    public static int EstudiantesReprobados(String notasOut[][]){
        int cntRepro = 0;
        for (int i = 0; i < notasOut.length && notasOut[i][2] != null; i++) {
            if (notasOut[i][2].equalsIgnoreCase("Reprobado")){
                cntRepro++;
            }    
        }
        return cntRepro;
    }
    public static int EstudiantesAprobados(String notasOut[][]){
        int cntApro = 0;
        for (int i = 0; i < notasOut.length && notasOut[i][2] != null; i++) {
            if (notasOut[i][2].equalsIgnoreCase("Aprobado")){
                cntApro++;
            }    
        }
        return cntApro;
    }
    public static void persistirResultados(String nombreArchivoOut, String notasIn [][], String notasOut [][]){
        try {
            Formatter fout = new Formatter(new File(nombreArchivoOut));
            fout.format("%s\n", "NOMRES;NOTAS1;NOTAS2;PROMEDIO;SUPLE;ESTADO");
            for (int i = 0; i < notasIn.length && notasIn[i][0] != null; i++) {
                for (int j = 0; j < notasIn[0].length; j++) 
                    fout.format("%s;", notasIn[i][j]);
                for (int j = 0; j < notasOut[0].length; j++) 
                    fout.format("%s;", notasOut[i][j]);
                fout.format("%s", "\n");
            }
            double promedioGeneral = CalcularPromedioGeneral(notasOut);
            int aprobados = EstudiantesAprobados(notasOut);
            int reprobados = EstudiantesReprobados(notasOut);
            fout.format("\n%s%d\n","Estudiantes Aprobados = ", aprobados);
            fout.format("%s%d\n","Estudiantes Reprobados = ", reprobados);
            fout.format("\nESTUDIANTES CON PROMEDIOS DEBAJO DE LA MEDIA\n");
            fout.format("%s%.2f\n","Promedio general del curso = ", promedioGeneral);
            fout.format("Nombres\tPromedio\n");
            double promIndividual = 0;
            for (int i = 0; i < notasOut.length && notasOut[i][0] != null; i++) {
                promIndividual = Double.parseDouble(notasOut[i][0]);
                if (promIndividual < promedioGeneral){
                    fout.format("%s\t\t%.2f\n", notasIn[i][0], promIndividual);
                }   
            }
            fout.close();
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }
    public static String [][] procesarNotas_Matriz(String datosNotas[][], int n){
        String datosOut[][] = new String[datosNotas.length][n];
        Random ale = new Random();
        for (int i = 0; i < datosNotas.length && datosNotas[i][0] != null; i++) {
            double promedio = (Double.parseDouble(datosNotas[i][1]) + Double.parseDouble(datosNotas[i][2])) / 2;
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
            while(fin.hasNext()&& i < m){
                String notas[] = fin.nextLine().split(";");
                datos[i][0] = notas[0]; 
                datos[i][1] = notas[1];  
                datos[i][2] = notas[2]; 
                i++;
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return datos;
    }
}
