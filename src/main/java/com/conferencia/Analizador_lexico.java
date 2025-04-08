package com.conferencia;

import java.util.ArrayList;
import java.util.List;

public class Analizador_lexico {
    public static List<Token> escanear(String entrada) {
        List<Token> listaTokens = new ArrayList<>();
        entrada += "#";
        StringBuilder auxLex = new StringBuilder();
        int i = 0, estado = 0;
        int length = entrada.length();

        while (i < length) {
            char c = entrada.charAt(i);
            i++;

            switch (estado) {
                case 0:
                    if (Character.isDigit(c)) {
                        auxLex.append(c);
                        estado = 1;
                    } else if (c == '+') {
                        listaTokens.add(new Token(Token.SIGNO_MAS, "+"));
                    } else if (c == '-') {
                        listaTokens.add(new Token(Token.SIGNO_MEN, "-"));
                    } else if (c == '*') {
                        listaTokens.add(new Token(Token.SIGNO_POR, "*"));
                    } else if (c == '/') {
                        listaTokens.add(new Token(Token.SIGNO_DIV, "/"));
                    } else if (c == '(') {
                        listaTokens.add(new Token(Token.PARENTESIS_IZQ, "("));
                    } else if (c == ')') {
                        listaTokens.add(new Token(Token.PARENTESIS_DER, ")"));
                    } else if (c == 32 || c == 10 || c == 9 || c == 13) {// Ignorar espacios, saltos de linea, tabulaciones, retornos de carro
                    } else if (c == '#' && i == length) {
                        System.out.println("Hemos concluido el analisis lexico satisfactoriamente");
                    } else {
                        System.out.println("Error lexico con: " + c);
                    }
                    break;
                case 1:
                    if (Character.isDigit(c)) {
                        auxLex.append(c);
                    } else {
                        listaTokens.add(new Token(Token.NUMERO, auxLex.toString()));
                        auxLex.setLength(0);
                        i--; // retrocede para procesar de nuevo este caracter
                        estado = 0;
                    }
                    break;
            }
        }
        return listaTokens;
    }
}
