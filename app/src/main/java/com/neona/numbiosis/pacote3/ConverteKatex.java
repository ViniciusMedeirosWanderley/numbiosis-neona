package com.neona.numbiosis.pacote3;

public class ConverteKatex {

    public String toSistemaKatex(String[] funcoes){
        //\begin{cases} f(x) \\ g(x)\end{cases}

        String[] fKatex = new String[funcoes.length];
        String sistema = "$$";

        for (int i = 0; i < fKatex.length; i++) {
            fKatex[i] = toFuncaoKatex(funcoes[i]);
        }

        sistema += "\\begin{cases}";
        for (int i = 0; i < fKatex.length; i++) {
            sistema += fKatex[i];
            if(i < fKatex.length - 1)
                sistema += "\\\\";
        }
        sistema+= "\\end{cases}";

        sistema+= "$$";
        return sistema;
    }

    public String toFuncaoKatex(String funcao){
        funcao = funcao.replaceAll("\\(","{(");
        funcao = funcao.replaceAll("\\)",")}");
        funcao = funcao.replaceAll("\\*","\\\\\\cdot ");
        return funcao;
    }
}
