package com.conferencia;
public class Token {
    public static final int NUMERO = 1;
    public static final int SIGNO_MAS = 2;
    public static final int SIGNO_MEN = 3;
    public static final int SIGNO_POR = 4;
    public static final int SIGNO_DIV = 5;
    public static final int PARENTESIS_IZQ = 6;
    public static final int PARENTESIS_DER = 7;
    public static final int EOF = 8; // Fin de archivo

    int tipo;
    String valor;

    public Token(int tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public int getType() {
        return tipo;
    }

    public String getValue() {
        return valor;
    }
}
