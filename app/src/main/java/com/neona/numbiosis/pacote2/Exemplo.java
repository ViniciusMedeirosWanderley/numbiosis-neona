package com.neona.numbiosis.pacote2;

import Jama.Matrix;
import java.text.NumberFormat;


public class Exemplo {

    public void testGaussJordan() {
        double[][] a = {{2,1,-1},
                        {5,2,2},
                        {3,1,1}};

        double[] b = {1,-4,5};

        int m = 3;
        int n = 3;

        Matrix A = new Matrix(a);
        Matrix B = new Matrix(b, 3);

        // nossa solucao
        System.out.println("GaussJordan\n");

        GaussJordan gj = new GaussJordan(A, B);
        gj.resolve();

        System.out.println("Normaliza");
        A.print(5, 2);
        System.out.println("Soluçao");
        B.print(5, 2);
    }

   public void testNewtonNaoLinear() {
        String[] funcoes = {"x + y -3",
                            "x^2 + y^2 -9"};

        String[][] variaveis = {{"x","y"},{"x","y"}};
        double[] x0 = {1.0,5.0};
        double eps = 1e-5;

        NewtonNaoLinear nt = new NewtonNaoLinear(funcoes, variaveis, x0,eps,eps);

        // acha a solucao
        double[] raiz = nt.resolve();

        // imprimindo a solucao apenas
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(3);
        System.out.println("\nRaiz:");
        for (double d : raiz) {
            System.out.println("\t"+nf.format(d));
        }
    }
}
