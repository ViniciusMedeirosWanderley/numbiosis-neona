package com.neona.numbiosis.pacote2;

/**
 *
 * @author jayme
 */
public class SplineNatural {

    public void resolve() {
        // tabela x e y
        float[] x = {0f, 0.5f, 1.0f, 1.5f, 2.0f};
        float[] y = {3f, 1.8616f, -0.5571f, -4.1987f, -9.0536f};

        //float[] x = {0.9f, 1.3f, 1.9f, 2.1f, 2.6f, 3.0f, 3.9f, 4.4f, 4.7f, 5.0f, 6.0f};
        //float[] y = {1.3f, 1.5f, 1.85f, 2.1f, 2.6f, 2.7f, 2.4f, 2.15f, 2.05f, 2.1f, 2.25f};

        int n = x.length - 1;
        float[] h = new float[n+1];
        float[] alfa = new float[n+1]; //Vetor para armazenar os valores das 2ª derivadas.

        //Calculando os Delta x's.
        for (int i = 0; i <= n-1; i++) {
            h[i] = x[i+1] - x[i]; //Variaçao do x seguinte com o atual.
        }

        //Calculo da 2ª derivada
        for (int i = 1; i <= n-1; i++) {
            alfa[i] = ((3/h[i]) * (y[i+1] - y[i]) - (3/h[i-1]) * (y[i] - y[i-1]));
        }

        /*Os vetores l, u e z, sao para a resoluçao
        *de um sistema tridiagonal linear com base no
        algoritmo da fatoraçao de Crout*/
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

        for (int i = 1; i <= n; i++) {
            System.out.printf("s[%d] = %.5f(x-%.2f)^3 + %.5f(x-%.2f)^2 + %.5f(x-%.2f) + %.5f \n", i, a1[i], x[i], b[i], x[i], c[i], x[i], y[i]);
        }
    }
}
