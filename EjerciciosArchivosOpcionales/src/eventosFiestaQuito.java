
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;
public class eventosFiestaQuito {
    public static void main(String[] args) {
        int m = 20;
        int n = 4; 
        String datos[][] = leerArchivo("EventosIn.csv", m, n);
        double recau[] = calcularRecaudacion(datos);
        persistirArchivo("EventosOut.csv", datos, recau);
    }
    public static String[][] leerArchivo(String nombreArchivo, int m, int n) {
        String datos[][] = new String[m][n];
        try {
            Scanner fin = new Scanner(new File(nombreArchivo));
            fin.nextLine(); 
            int i = 0;
            int nroEventos = 0;
            while (fin.hasNextLine()) {
                String columnas[] = fin.nextLine().split(";");
                datos[i][0] = columnas[0].trim(); 
                datos[i][1] = columnas[1].trim(); 
                datos[i][2] = columnas[2].trim(); 
                datos[i][3] = columnas[3].trim(); 
                i++;
                nroEventos++;
            }
            fin.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return datos;
    }
    public static double[] calcularRecaudacion(String datos[][]) {
        double recau[] = new double[datos.length];
        for (int i = 0; i < datos.length && datos[i][0] != null; i++) {
            int asistentes = Integer.parseInt(datos[i][2]);
            double costo = Double.parseDouble(datos[i][3]);
            recau[i] = asistentes * costo;
        }
        return recau;
        }
    public static void persistirArchivo(String nombreArchivoOut, String datos[][], double recau[]) {
        int nroEventos = 0;
        while (nroEventos < datos.length && datos[nroEventos][0] != null) {
            nroEventos++;
        }
        try {
            Formatter fout = new Formatter(new File(nombreArchivoOut));
            fout.format("%s\n","EVENTO,PARROQUIA,ASISTENTES,COSTO,RECAUDACION");
            for (int i = 0; i < datos.length && datos[i][0] != null; i++) {
                fout.format("%s,%s,%s,%s,%.2f\n",
                        datos[i][0], datos[i][1], datos[i][2], datos[i][3], recau[i]);
            }
        int recauMax = EventoMayorRecaudacion(recau, nroEventos);
        String parroquia = parroquiaMasEventos(datos, nroEventos);
        double prom = promedioAsistentes(datos,nroEventos);
        fout.format("\nRESUMEN\n");
        fout.format("Evento con mayor recaudacion,%s\n", datos[recauMax][0]);
        fout.format("Parroquia con mas eventos,%s\n", parroquia);
        fout.format("Promedio general de asistentes,%.2f\n", prom);
        fout.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
    }
    public static int EventoMayorRecaudacion(double recau[], int nroEventos) {
        int mayor = 0;
        for (int i = 1; i < nroEventos; i++)
            if (recau[i] > recau[mayor]) 
                mayor = i;
        return mayor;
    }
    public static String parroquiaMasEventos(String datos[][], int nroEventos) {
        String parroquiaMas = datos[0][1];
        int max = 0;
        for (int i = 0; i < nroEventos; i++) {
            int vecesRepite = 0;
            if (datos[i][1] == null) continue; 
            for (int j = 0; j < nroEventos; j++){
                if (datos[i][1].equalsIgnoreCase(datos[j][1])) {
                    vecesRepite++;
                }
            }
            if (vecesRepite > max) {
                max = vecesRepite;
                parroquiaMas = datos[i][1];
            }
        } 
        return parroquiaMas;
    }
    public static double promedioAsistentes(String datos[][], int nroEventos) {
        int suma = 0;
        for (int i = 0; i < nroEventos; i++)
            suma += Integer.parseInt(datos[i][2]);
        return (double) suma / nroEventos;
    }
}
