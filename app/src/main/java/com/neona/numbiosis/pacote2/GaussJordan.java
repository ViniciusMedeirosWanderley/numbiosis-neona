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

    double [][][] kA;
    double [][][] kB;
    double [][] normalB;
    boolean [] pivotea;
    int [][] trocas;
    private int k;

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

        this.kA = new double[N_LINHAS][a.length][a[0].length];
        this.kB = new double[N_LINHAS][b.length][b[0].length];
        this.normalB = new double[b.length][b[0].length];
        this.trocas = new int[N_LINHAS][2];
        this.pivotea = new boolean[N_LINHAS];
    }

    // Com pivoteamento
    public void resolve(){
        int m = A.getRowDimension();
        int n = A.getColumnDimension();

        if(Math.abs(A.det()) <= EPSILON)
            throw new ArithmeticException("Matriz A é singular."); // det == 0, eh uma Matriz Singular, nao tem solucao

        double[][] a = A.getArray();
        double[][] b = B.getArray();

        System.out.println("Matriz A:");
        A.print(6, 2);

        k = 0;

        //linha
        for (int i = 0; i < m; i++) {

            pivotea[i] = pivoteamento(i);

            pivo(i,i);
            System.out.println("Iteracao "+(i+1));
            A.print(6, 2);

            // salvo matriz A e B na iteracao
            for (int linha = 0; linha < a.length; linha++) {
                for (int coluna = 0; coluna < a[0].length; coluna++) {
                    kA[i][linha][coluna] = a[linha][coluna];
                }

                for (int colunaB = 0; colunaB < b[0].length; colunaB++) {
                    kB[i][linha][colunaB] = b[linha][colunaB];
                }
            }

            k++;
        }

        // normalizo com o pivo
        for (int i = 0; i < N_LINHAS; i++) {
            b[i][0] /= a[i][i];
            a[i][i] = 1.0; // divisao por ele mesmo

            // salvo Matriz B normalizada
            normalB[i][0] = b[i][0];
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


    private boolean pivoteamento(int linha){
        int i = linha;
        // acho o maior para fazer pivoteamento
        int maior = i;
        for (int j = i+1; j < N_COLUNAS; j++) {
            if(Math.abs(a[i][i]) < Math.abs(a[i][j]))
                maior = j;
        }
        if(i != maior) {
            troca(i, maior);
            trocas[k] = new int[]{i,maior};
            return true;
        }
        return false;
    }

    // troca linhas (A e B)
    private void troca(int linha1, int linha2) {
        double[] temp = a[linha1];
        a[linha1] = a[linha2];
        a[linha2] = temp;

        temp = b[linha1];
        b[linha1] = b[linha2];
        b[linha2] = temp;
    }


    public double[][][] getAIteracoes(){
        return kA;
    }

    public double[][][] getBIteracoes(){
        return kB;
    }

    public double[][] getBFinal(){
        return normalB;
    }
}

