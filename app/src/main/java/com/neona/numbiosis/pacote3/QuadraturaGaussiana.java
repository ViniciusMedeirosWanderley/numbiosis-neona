package com.neona.numbiosis.pacote3;

import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

public class QuadraturaGaussiana {
    // n = [0,7]
    private static double[][] nos =
            {{0.0},
            {-0.57735026919, 0.57735026919},
            {-0.774596669241, 0.0, 0.774596669241},
            {-0.861136311594, -0.339981043585, 0.339981043585, 0.861136311594},
            {-0.906179845939, -0.538469310106, 0.0, 0.538469310106, 0.906179845939},
            {-0.932469514203, -0.661209386466, -0.238619186083, 0.238619186083, 0.661209386466, 0.932469514203},
            {-0.949107912343, -0.741531185599, -0.405845151377, 0.0, 0.405845151377, 0.741531185599, 0.949107912343},
            {-0.960289856498, -0.796666477414, -0.525532409916, -0.183434642496, 0.183434642496, 0.525532409916, 0.796666477414, 0.960289856498}};

    private static double[][] pesos =
            {{2.0},
            {1.0, 1.0},
            {0.555555555556, 0.888888888889, 0.555555555556},
            {0.347854845137, 0.652145154863, 0.652145154863, 0.347854845137},
            {0.236926885056, 0.478628670499, 0.568888888889, 0.478628670499, 0.236926885056},
            {0.171324492379, 0.360761573048, 0.467913934573, 0.467913934573, 0.360761573048, 0.171324492379},
            {0.129484966169, 0.279705391489, 0.381830050505, 0.417959183673, 0.381830050505, 0.279705391489, 0.129484966169},
            {0.10122853629, 0.222381034453, 0.313706645878, 0.362683783378, 0.362683783378, 0.313706645878, 0.222381034453, 0.10122853629}};


    private String funcao;
    private int n;
    private int nPontos;  // numero de pontos
    private int p;        // grau do polinomio
    private int a,b;      // intervalos de integracao
    private Expression eval;

    public QuadraturaGaussiana(String funcao, int n, int a, int b) {
        this.funcao = funcao;
        this.nPontos = n + 1;
        this.p = 2*n + 1;
        this.n = n;
        this.a = a;
        this.b = b;
        this.eval = new Expression();
    }

    public double calcular(){
        if((n > 7) || (n < 0))
            throw new IllegalArgumentException("Excedeu o nº de pontos.");

        double[] no = nos[n];
        double[] peso = pesos[n];

        double resultado = 0;

        String var = ""+funcao.replaceAll("[^a-z]", "")
                            .replaceAll("e", "")
                            .replaceAll("pi","").charAt(0);

        Argument arg = new Argument(var);

        if((a != -1) || (b!= 1))
            transformaIntervalo(var);

        eval.setExpressionString(funcao);
        eval.addArguments(arg);

        for (int i = 0; i < nPontos; i++) {
            arg.setArgumentValue(no[i]);
            resultado += peso[i]*eval.calculate();
        }

        return resultado;
    }

    private void transformaIntervalo(String var) {
        //0.5*(b-a)t + 0.5*(a+b)
        double alpha = 0.5*(b-a);
        double c = 0.5*(a+b);

        String t = "("+alpha+"*x + "+c+")";
        String dt = "der("+t+","+var+",1.0)";

        String nova_funcao = funcao.replaceAll(var, t);

        eval.setExpressionString(dt);
        double d_dt = eval.calculate();

        funcao = "("+nova_funcao+")*"+d_dt;
    }

}
