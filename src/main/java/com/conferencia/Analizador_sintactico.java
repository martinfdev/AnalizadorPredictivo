package com.conferencia;

import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Analizador_sintactico {
    private List<Token> listaTokens;
    private int indice;
    private Token preanalisis;
    // token de sincroizacion
    private final Set<Integer> sincronizacionGlobal = new HashSet<>(
            Arrays.asList(Token.PARENTESIS_IZQ));

    public void parsear(List<Token> tokens) {
        listaTokens = tokens;
        indice = 0;
        if (!tokens.isEmpty()) {
            preanalisis = listaTokens.get(indice);
            try {
                E();
                System.out.println("Cadena aceptada sintacticamente.");
            } catch (Exception e) {
                System.out.println("Error sintactico: " + e.getMessage());
            }
        } else {
            System.out.println("Lista de tokens vacia.");
        }
    }

    private void avanzar() {
        if (++indice < listaTokens.size()) {
            preanalisis = listaTokens.get(indice);
        } else {
            preanalisis = null;
        }
    }

    // sincroizacion para el modo panico
    private void sincronizar(Set<Integer> syncSet) {
        System.out.println("Iniciando modo pánico. Buscando token de sincronización...");
        while (preanalisis != null && !syncSet.contains(preanalisis.tipo)) {
            System.out.println("Descartando token: " + preanalisis.valor);
            avanzar();
        }
        // Si se encontró un token de sincronización, se retorna y el parser continúa.
        if (preanalisis != null) {
            System.out.println("Token de sincronización encontrado: " + preanalisis.valor);
        }
    }

    private void emparejar(int tipoEsperado) throws Exception {
        if (preanalisis != null && preanalisis.tipo == tipoEsperado) {
            avanzar();
        } else {
            String tokenEncontrado = (preanalisis == null) ? "null" : preanalisis.valor;
            System.out.println("Error sintáctico: Se esperaba " + getTipoTokenString(tipoEsperado) +
                    " pero se encontró: '" + tokenEncontrado + "'. Se descarta este token y se continúa.");
            // Modo pánico: sincronización
            // Se agrega el token esperado a la lista de sincronización
            Set<Integer> sync = new HashSet<>(sincronizacionGlobal);
            sync.add(tipoEsperado);
            sincronizar(sync);
        }
    }

    private String getTipoTokenString(int p) {
        String res = "";
        switch (p) {
            case Token.SIGNO_MAS:
                res = "+";
                break;
            case Token.SIGNO_MEN:
                res = "-";
                break;
            case Token.SIGNO_POR:
                res = "*";
                break;
            case Token.SIGNO_DIV:
                res = "/";
                break;
            case Token.PARENTESIS_IZQ:
                res = "(";
                break;
            case Token.PARENTESIS_DER:
                res = ")";
                break;
            case Token.NUMERO:
                res = "NUMERO";
                break;
            case Token.EOF:
                res = "EOF";
                break;
            default:
                res = "desconocido";
                break;
        }
        return res;
    }

    // E -> T EP
    private void E() throws Exception {
        T();
        EP();
    }

    // EP -> + T EP | - T EP | ε
    private void EP() throws Exception {
        switch (preanalisis.tipo) {
            case Token.SIGNO_MAS:
                emparejar(Token.SIGNO_MAS);
                T();
                EP();
                break;
            case Token.SIGNO_MEN:
                emparejar(Token.SIGNO_MEN);
                T();
                EP();
                break;
        }
    }

    // T -> F TP
    private void T() throws Exception {
        F();
        TP();
    }

    // TP -> * F TP | / F TP | ε
    private void TP() throws Exception {
        switch (preanalisis.tipo) {
            case Token.SIGNO_POR:
                emparejar(Token.SIGNO_POR);
                F();
                TP();
                break;
            case Token.SIGNO_DIV:
                emparejar(Token.SIGNO_DIV);
                F();
                TP();
                break;
        }
    }

    // F -> ( E ) | NUMERO
    private void F() throws Exception {
        if (preanalisis == null){
            System.out.println("Error sintáctico: se esperaba '(' o NUMERO, pero no hay más tokens.");
            return;
        }
        switch (preanalisis.tipo) {
            case Token.PARENTESIS_IZQ:
                emparejar(Token.PARENTESIS_IZQ);
                E();
                emparejar(Token.PARENTESIS_DER);
                break;
            case Token.NUMERO:
                emparejar(Token.NUMERO);
                break;
            default:
                System.out.println("Error sintáctico en F(): token inesperado '" + preanalisis.valor
                        + "'. Iniciando modo pánico para recuperar.");
                // Modo pánico: sincronización
                sincronizar(sincronizacionGlobal);
                break;
        }
    }
}
