package com.conferencia;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        while (true) {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    String cadenaEntrada = Utilidades.leerArchivo("entrada.txt");
                    if (cadenaEntrada == null) {
                        System.out.println("Error al leer el archivo.");
                    } else {
                        List<Token> listaTokens = Analizador_lexico.escanear(cadenaEntrada);
                
                        Analizador_sintactico analizadorSintactico = new Analizador_sintactico();
                        analizadorSintactico.parsear(listaTokens);
                    }
                    break;
                case 2:
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
        }
    }

    public static void mostrarMenu() {
        System.out.println("===========================");
        System.out.println("     Menu Principal        ");
        System.out.println("===========================");
        System.out.println("1. Analizar");
        System.out.println("2. Salir");
        System.out.print("Seleccione una opcion: ");
    }
}
