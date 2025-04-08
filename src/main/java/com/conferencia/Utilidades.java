package com.conferencia;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utilidades {
    public static String leerArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            return br.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
