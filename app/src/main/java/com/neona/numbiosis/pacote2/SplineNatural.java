package com.neona.numbiosis.pacote2;

/**
 *
 * @author jayme
 */
public class SplineNatural {

    public float[] resolve(float[] x, float[] y) {
        // tabela x e y
        //float[] x = new float[5];
        //float[] y = new float[5];

        //float[] x = {0, 0.5, 1.0, 1.5, 2.0};
        //float[] y = {3, 1.8616, -0.5571, -4.1987, -9.0536};

        //float[] x = {0.9, 1.3, 1.9, 2.1, 2.6, 3.0, 3.9, 4.4, 4.7, 5.0, 6.0};
        //float[] y = {1.3, 1.5, 1.85, 2.1, 2.6, 2.7, 2.4, 2.15, 2.05, 2.1, 2.25};

        int n = x.length - 1;
        float[] h = new float[n+1];
        float[] alfa = new float[n+1]; //Vetor para armazenar os valores das 2ª derivadas.
        float[] resultado = new float[12]; //Armazena os coeficientes, a1, a2, a3, b1, b2, b3, c1, c2, c3, d1, d2, d3

        //Calculando os Delta x's.
        for (int i = 0; i <= n-1; i++) {
            h[i] = x[i+1] - x[i]; //Variacao do x seguinte com o atual.
        }

        //Calculo da 2ª derivada
        for (int i = 1; i <= n-1; i++) {
            alfa[i] = ((3/h[i]) * (y[i+1] - y[i]) - (3/h[i-1]) * (y[i] - y[i-1]));
        }

            /*Os vetores l, u e z, sao para a resolucao
            *de um sistema tridiagonal linear com base no
            algoritmo da fatoraÃ§ao de Crout*/
        float[] l = new float[n+1];
        float[] u = new float[n+1];
        float[] z = new float[n+1];

        l[0] = 1;
        u[0] = 0;
        z[0] = 0;

        for (int i = 1; i <= n-1; i++) {
            l[i] = ((2 * (x[i+1] - x[i-1])) - (h[i-1] * u[i-1]));
            u[i] = h[i] / l[i];
            z[i] = ((alfa[i] - (h[i-1] * z[i-1])) / l[i]);
        }

        l[n] = 1;
        z[n] = 0;

        //Vetores para os coeficientes a, b e c.
        float[] b = new float[n+1];
        float[] c = new float[n+1];
        float[] a = new float[n+1];
        float[] a1 = new float[n+1];
        b[n] = 0;

        //Calculo dos coeficientes a, b e c.
        for (int i = n-1; i >= 0; i--) {
            b[i] = z[i] - (u[i]*b[i+1]);
            c[i] = (((y[i+1] - y[i]) / h[i]) - ((h[i] * (b[i+1] + 2 * b[i])) / 3));
            a[i] = ((b[i+1] - b[i]) / (3 * h[i]));
        }

        for (int i = 0; i <= n-1; i++) {
            a1[i+1] = a[i];
        }

        for (int i = 0; i < 3; i++) {
            resultado[i] = a1[i+1];
            resultado[i+3] = b[i+1];
            resultado[i+6] = c[i+1];
            resultado[i+9] = y[i+1];
        }



        return resultado;
    }
}
