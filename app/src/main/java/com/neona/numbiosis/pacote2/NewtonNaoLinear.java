package com.neona.numbiosis.pacote2;

import Jama.Matrix;
import org.mariuszgromada.math.mxparser.*;

/**
 * Dados: o vetor inicial x (0) ; ε1 , ε2 > 0:
    Passo I: computar F(xk) e J(xk)
    Passo II: se ||F(xk)|| menor que ε1 , x ∗ = xk e pare;
    senão
    Passo III: obter s(k) , solução do sistema J*s(k) = −F
    Passo IV: atualizar x(k+1) = xk + s(k)
    Passo V: se ||x(k+1) − xk || menor que ε2, x ∗ = x(k+1) e pare;
    senão
    Passo VI: atualizar k = k + 1
 *
 * @author Leonardo Villeth
 */
public class NewtonNaoLinear {

    private String[] gradientes;
    private String[][] variaveis;

    private String[][] J; // derivadas

    private double[] Fx;
    private double[][] Jx; // matriz jacobiana

    private double EPSILON_1 = 1e-4;
    private double EPSILON_2 = 1e-4;
    private double[] x0;
    private String[] funcoes;

    private double[][] k_Fx;
    private double[][][] k_Jx;
    private double[] k_normaFx;
    private double[] k_norma;
    private double[][] k_xk;
    private double[][] k_s;
    private int nIteracoes;

    /**
     *
     * @param funcoes array com todas as funcoes do sistema.
     * @param variaveis array bidimensional com todas as variaveis em cada funcao do sistema.
     * @param x0 array com as estimativas iniciais.
     * @param epsilon1 tolerancia para ||F(xk)||
     * @param epsilon2 tolerancia para ||x(k+1) − xk ||
     */
    public NewtonNaoLinear(String[] funcoes, String[][] variaveis, double[] x0, double epsilon1, double epsilon2) {
        this.funcoes = funcoes;
        this.gradientes = new String[funcoes.length];
        this.variaveis = variaveis;
        this.x0 = x0;
        this.EPSILON_1 = epsilon1;
        this.EPSILON_2 = epsilon2;

        this.J = new String[funcoes.length][x0.length];
        this.Jx = new double[funcoes.length][x0.length];
        this.Fx = new double[x0.length];

        // Passo a Passo
        k_Fx = new double[100][x0.length];
        k_Jx = new double[100][funcoes.length][x0.length];
        k_normaFx = new double[100];
        k_norma = new double[100];
        k_xk = new double[100][x0.length];
        k_s = new double[100][x0.length];
    }

    /**
     * Resolve o sistema e retorna o vetor com a solucao do sistema.
     * @return vetor solucao.
     */
    public double[] resolve(){
        initGrads();

        for(int k = 0; k < 100; k++){
            nIteracoes = k + 1;

            System.out.println("\nIteracao ["+(k+1)+"]");

            Expression f = new Expression();
            //passo 1
            //computar F(xk) e J(xk)
            for (int i = 0; i < x0.length; i++) {
               switch(i){
                    case 0:
                        f.defineArgument("x", x0[i]);
                        break;
                    case 1:
                        f.defineArgument("y", x0[i]);
                        break;
                    case 2:
                        f.defineArgument("z", x0[i]);
                        break;
                    case 3:
                        f.defineArgument("w", x0[i]);
                        break;
                }
            }

            System.out.println("\nFx:");
            for (int i = 0; i < funcoes.length; i++) {
                f.setExpressionString(funcoes[i]);
                Fx[i] = f.calculate();

                k_Fx[k][i] = Fx[i]; // Passo a Passo

                System.out.println(Fx[i]);
                Fx[i] = -Fx[i];
            }

            System.out.println("\nJx:");
            for (int i = 0; i < J.length; i++) {
                for (int j = 0; j < J[i].length; j++) {

                    if(J[i][j] == null){// alterar construçao de J[][] para retirar essa checagem
                        Jx[i][j] = 0;
                        continue;
                    }

                    f.setExpressionString(J[i][j]);
                    Jx[i][j] = f.calculate();
                    System.out.print(Jx[i][j]+"  ");

                    k_Jx[k][i][j] = Jx[i][j]; // Passo a Passo
                }
                System.out.println();
            }

            // passo 2
            // se || Fx || > Epsilon1, pare
            double norma = norma(Fx);
            System.out.println("\nNorma Fx: "+ norma);
            if(norma < EPSILON_1)
                return x0; // eh a raiz

            k_normaFx[k] = norma; // Passo a Passo


            // passo 3
            // J*s = -Fx
            // resolvo o sistema e descubro 's'
            Matrix A = new Matrix(Jx);
            Matrix B = new Matrix(Fx,Fx.length);
            Matrix s  = A.solve(B);

            System.out.println("\ns: ");
            s.print(4, 3);

            k_s[k] = s.getRowPackedCopy(); // Passo a Passo

            // passo 4
            // descubro xk
            // xk = x0 + s;
            Matrix xk = new Matrix(x0, x0.length);
            xk.plusEquals(s);

            System.out.println("\nxk: ");
            xk.print(4, 3);

            k_xk[k] = xk.getRowPackedCopy(); // Passo a Passo

            // passo 5
            // acho a norma ||xk - x(k-1)||
            norma = norma(xk.getColumnPackedCopy(),x0);
            System.out.println("Norma: "+norma);
            if(norma < EPSILON_2)
                return xk.getColumnPackedCopy(); // eh a raiz

            k_norma[k] = norma; // Passo a Passo

            // passo 6
            // vou para proxima iteracao
            // k = k + 1
            x0 = xk.getRowPackedCopy();
        }
        return null;
    }

    /**
     * Inicializo o array de funcoes de gradiente (String).
     */
    public void initGrads(){
        int j;
        for (int i = 0; i < funcoes.length; i++) {
            StringBuilder sb1 = new StringBuilder();

            // crio a matriz Jacobiana
            j = 0;
            for (double d: x0) {
                String var;
                switch (j){
                    case 0:
                        var = "x";
                        break;
                    case 1:
                        var = "y";
                        break;
                    case 2:
                        var = "z";
                        break;
                    case 3:
                        var = "w";
                        break;
                    default:
                        var = "x";
                }

                StringBuilder sb = new StringBuilder();
                sb.append("der(".concat(funcoes[i]));
                sb.append(",").append(var).append(")");
                J[i][j] = sb.toString();
                j++;

                sb1.append(J[i][j - 1]);
                if(j < J[i].length)
                    sb1.append(" + ");
            }

            // salvo as derivadas (expressao) de cada funcao
            gradientes[i] = sb1.toString();
            System.out.println(gradientes[i]);
        }
    }



    /**
     * Norma para um vetor.
     * Usado para calcular a norma de Fx.
     * @param fx
     * @return o maior valor absoluto no vetor Fx.s
     */
    private double norma(double[] fx){
        double maior = Math.abs(fx[0]);
        double abs_x;

        for (int i = 1; i < fx.length; i++) {
            abs_x = Math.abs(fx[i]);
            if(abs_x > maior)
                maior = abs_x;
        }
        return maior;
    }

    /**
     * Usado para calcular a norma do vetor solucao ( x ).
     * @param xk
     * @param x
     * @return
     */
    private double norma(double[] xk, double[] x){
        double[] dif = new double[xk.length];
        double maior = -Double.MAX_VALUE;

        for (int i = 0; i < xk.length; i++) {
            dif[i] = Math.abs(xk[i] - x[i]);
            if(dif[i] > maior)
                maior = dif[i];
        }

        return maior;
    }

    public String[] getFuncoes() {
        return funcoes;
    }

    public String[] getGradientes() {
        return gradientes;
    }

    public String[][] getVariaveis() {
        return variaveis;
    }

    public String[][] getJ() {
        return J;
    }

    public double[] getFx() {
        return Fx;
    }

    public double[][] getJx() {
        return Jx;
    }

    public double getEPSILON_1() {
        return EPSILON_1;
    }

    public double getEPSILON_2() {
        return EPSILON_2;
    }

    public double[] getX0() {
        return x0;
    }

    // Passo a Passo
    public double[][] getK_Fx() {
        return k_Fx;
    }

    public double[][][] getK_Jx() {
        return k_Jx;
    }

    public double[] getK_normaFx() {
        return k_normaFx;
    }

    public double[] getK_norma() {
        return k_norma;
    }

    public double[][] getK_xk() {
        return k_xk;
    }

    public double[][] getK_s() {
        return k_s;
    }

    public int getnIteracoes() {
        return nIteracoes;
    }

}