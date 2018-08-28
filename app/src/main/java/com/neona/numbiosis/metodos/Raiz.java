package com.neona.numbiosis.metodos;

import com.jjoe64.graphview.series.DataPoint;

import org.mariuszgromada.math.mxparser.*;

public class Raiz {

    private static DataPoint[] dpsX;
    private static DataPoint[][] secantes;
    private static double menorX;
    private static double maiorX;
    private static int nIteracoes;

    public static final double TOL = 1e-5;
    public static final double EPSILON = Float.MIN_NORMAL;
    public static final int N = 100;

    public static double muller(String funcao, double x0, double x1, double x2, double tol, int n){
        double x3 = 0,
                fx0,fx1,fx2,
                h0,h1,
                d0,d1,
                a,b,c,
                disc,
                erro;


        Argument arg_x = new Argument("x");
        Expression _f = new Expression(funcao, arg_x);

        for (int i = 0; i < n; i++) {

            h0 = x1 - x0;
            h1 = x2 - x1;

            // calculo fx0, fx1, fx2
            arg_x.setArgumentValue(x0);
            fx0 = _f.calculate();
            arg_x.setArgumentValue(x1);
            fx1 = _f.calculate();
            arg_x.setArgumentValue(x2);
            fx2 = _f.calculate();

            d0 = (fx1 - fx0) / (x1 - x0);
            d1 = (fx2 - fx1) / (x2 - x1);

            a = (d1 - d0) / (h1 + h0);
            b = a*h1 + d1;
            c = fx2;

            disc = Math.sqrt(b*b - 4*a*c);

            x3 = x2 + (-2*c) / (b + Math.signum(b)*disc);

            // checar erro
            erro = erro(x2, x3, true);
            if(erro < tol)
                return x3;

            // prepara para prox iteracao
            x0 = x1;
            x1 = x2;
            x2 = x3;
        }

        return x3;
    }


    public static double falsaPosicao(String funcao, double a, double b, double x, double tol, int n){
        double xm = 0,
                fa, fb, fxm,
                erro;

        Argument arg_x = new Argument("x");
        Expression _f = new Expression(funcao, arg_x);

        for (int i = 0; i < n; i++) {
            arg_x.setArgumentValue(a);
            fa = _f.calculate();

            arg_x.setArgumentValue(b);
            fb = _f.calculate();

            arg_x.setArgumentValue(xm);
            fxm = _f.calculate();

            xm = (a*fb - b*fa) / (fb - fa);

            erro = erro(x, xm, true);

            x = xm;

            if (erro < tol) {
                return xm;
            }

            if(fxm * fb > 0)
                b = xm;
            else if(fxm * fb < 0)
                a = xm;
            else
                return xm;

        }

        return xm;
    }


    public static double secante(String funcao, double x0, double x1, double tol, int n) {
        double xk = 0,
                fa, fb,
                a = x0,
                b = x1,
                erro;

        Argument arg_x = new Argument("x");
        Expression _f = new Expression(funcao, arg_x);

        // inicializar maior e menor
        menorX = x0 < x1 ? x0 : x1;
        maiorX = x0 > x1 ? x0 : x1;

        // inicializo Arrays
        dpsX = new DataPoint[n];
        secantes = new DataPoint[n][2];

        for (int i = 0; i < n; i++) {

            arg_x.setArgumentValue(a);
            fa = _f.calculate();

            arg_x.setArgumentValue(b);
            fb = _f.calculate();

            xk = (a*fb - b*fa) / (fb - fa + EPSILON);

            erro = erro(b, xk, true);

            // atualizo maior e menor
            if(xk > maiorX) maiorX = xk;
            if(xk < menorX) menorX = xk;

            // adiciono datapoints aos arrays
            dpsX[i] = new DataPoint(xk,0);
            secantes[i][0] = new DataPoint(a,fa);
            secantes[i][1] = new DataPoint(b,fb);

            // salvo a qtd de xk achados
            nIteracoes  = i + 1;

            if (erro < tol) {
                return xk;
            }

            a = b;
            b = xk;
        }
        return xk;
    }

    /**
     *
     * @param x valor anterior
     * @param xk valor encontrado
     * @param relativo true para usar erro relativo, falso para erro absoluto
     * @return O erro
     */
    private static double erro(double x, double xk, boolean relativo) {
        if (!relativo) {
            return Math.abs(x - xk);
        } else {
            return Math.abs(x - xk) / Math.abs(xk + EPSILON);
        }

    }

    public static double getMenorX(){ return menorX; }
    public static double getMaiorX(){ return maiorX; }
    public static DataPoint[] getDataPointsX() { return dpsX; }
    public static DataPoint[][] getSecantes() { return secantes; }
    public static int getNumeroIteracoes(){ return nIteracoes; }
}
