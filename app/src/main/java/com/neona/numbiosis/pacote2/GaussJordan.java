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
    public String passos = "";
    public int numeroDeIterações;

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
    public void resolve(){ //resolveComPivoteamento();return;
        int m = A.getRowDimension();
        int n = A.getColumnDimension();

        if(Math.abs(A.det()) <= EPSILON)
            throw new ArithmeticException("Matriz A é singular."); // det == 0, eh uma Matriz Singular, nao tem solucao

        double[][] a = A.getArray();
        double[][] b = B.getArray();

        System.out.println("Matriz A | Matriz B:");
        //passos += "Matriz A | Matriz B:" + "\n\n";
        A.print(6, 2);

        for(int i =0 ; i<N_LINHAS; i++){
            for (int j=0 ; j<N_COLUNAS ; j++){
               passos += a[i][j];
               passos += " ";
            }
            passos += "\n";
        }

        passos += "proximaIteracao";

        for(int i = 0; i<N_LINHAS;i++){
            passos += b[i][0];
            passos += "\n";
        }

        passos += "proximaIteracao";

        //linha

        int i=0;
        for (i = 0; i < m; i++) {

            pivoteamento(i);

            pivo(i,i);
            System.out.println("Iteracao "+(i+1));
            //passos += "Iteracao " + (i+1) + "\n" ;
            A.print(6, 2);

            for(int k =0 ; k<N_LINHAS; k++){
                for (int j=0 ; j<N_COLUNAS ; j++){
                    passos += a[k][j];
                    passos += " ";
                }

                passos += "\n";
            }

            passos += "proximaIteracao";

            for(int l = 0; l<N_LINHAS;l++){
                passos += b[l][0];
                passos += "\n";
            }

            passos += "proximaIteracao";

        }

        numeroDeIterações = (i+1+1); //quantidade de iterações + uma operaçao de normalização + uma das matrizes iniciais

        // normalizo com o pivo
        for (i = 0; i < N_LINHAS; i++) {
            b[i][0] /= a[i][i];
            a[i][i] = 1.0; // divisao por ele mesmo
        }

        //passos += "Normaliza" + "\n\n";

        for(int k =0 ; k<N_LINHAS; k++){
            for (int j=0 ; j<N_COLUNAS ; j++){
                passos += a[k][j];
                passos += " ";
            }
            passos += "\n";
        }

        passos += "proximaIteracao";

        for(int l = 0; l<N_LINHAS;l++){
            passos += b[l][0];
            passos += "\n";
        }

        passos += "proximaIteracao";

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


    private void pivoteamento(int linha){
        int i = linha;
        // acho o maior para fazer pivoteamento
        int maior = i;
        for (int j = i+1; j < N_COLUNAS; j++) {
            if(Math.abs(a[i][i]) < Math.abs(a[i][j]))
                maior = j;
        }
        if(i != maior)
            troca(i,maior);
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

}

