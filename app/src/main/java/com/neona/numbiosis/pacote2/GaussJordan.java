package com.neona.numbiosis.pacote2;

import Jama.Matrix;

/**
 *
 * @author Leonardo Villeth
 */
public class GaussJordan {

    private final double EPSILON = 1e-8;

    Matrix A;
    Matrix B;
    double[][] a;
    double[][] b;
    int N_LINHAS; // qtd de linhas
    int N_COLUNAS; // qtd de colunas

    public GaussJordan(Matrix A, Matrix B) {
        this.A = A;
        this.B = B;
        this.N_LINHAS = A.getRowDimension();
        this.N_COLUNAS = A.getColumnDimension();

        if(N_LINHAS != B.getRowDimension()){
            ;// ERROR
        }

        this.a = A.getArray();
        this.b = B.getArray();
    }

    // Sem pivoteamento, pode ocorrer divisao por zero
    public void resolve(){
        int m = A.getRowDimension();
        int n = A.getColumnDimension();

        if(Math.abs(A.det()) <= EPSILON)
            return; // det == 0, eh uma Matriz Singular, nao tem solucao

        double[][] a = A.getArray();
        double[][] b = B.getArray();

        System.out.println("Matriz A:");
        A.print(6, 2);

        //linha
        for (int i = 0; i < m; i++) {
            pivo(i,i);
            System.out.println("Iteracao "+(i+1));
            A.print(6, 2);
        }

        // normalizo com o pivo
        for (int i = 0; i < N_LINHAS; i++) {
            b[i][0] /= a[i][i];
            a[i][i] = 1.0; // divisao por ele mesmo
        }

    }

    private void pivo(int p_lin, int p_col){
        double pivo = a[p_lin][p_col];
        double alpha;

        // faco a fatoracao
        for (int i = 0; i < N_LINHAS; i++) {

            if(i == p_lin)
                continue;

            alpha = a[i][p_col]/pivo;

            for (int j = 0; j < N_COLUNAS; j++) {
                a[i][j] -= alpha * a[p_lin][j];
            }

            // atualizo matriz B
            b[i][0] -= alpha * b[p_lin][0];
        }
    }

}

