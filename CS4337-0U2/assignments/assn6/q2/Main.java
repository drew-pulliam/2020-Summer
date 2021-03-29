import java.io.*;

public class Main {
    /*
    4337.0U2
    Assignment 6 - Q2
    Drew Pulliam
    dtp180003
    */

    public static void main(String[] args) {
        int[][] mat = {{2,1},{0,1}};
        int[][] mat2 = {{1,0},{0,1}};
        Matrix m1 = new Matrix(mat);
        Matrix m2 = new Matrix(mat2);

        System.out.println("\nstart\n");
        System.out.print("m1\n"+m1);
        System.out.print("m2\n"+m2);
        System.out.println();
        Matrix m3 = m1.matrixAdd(m2);
        System.out.println("add m1 + m2 = m3");
        System.out.print("m3\n"+m3);
        System.out.println();
        Matrix m4 = m1.matrixSub(m2);
        System.out.println("subtract m1 - m2 = m4");
        System.out.print("m4\n"+m4);
        System.out.println();
        System.out.println("multiply m1 * m2 = m5");
        Matrix m5 = m1.matrixMul(m2);
        System.out.print("m5\n"+m5);
    }
    
    static class Matrix {

        int[][] matrixArr;
        int rows;
        int cols;

        Matrix(int rows, int cols){
            matrixArr = new int[rows][cols];    
            this.rows = rows;           
            this.cols = cols;                
        }
        Matrix(int[][] matrixArr){
            this.matrixArr = matrixArr.clone();        
            this.rows = matrixArr.length;           
            this.cols = matrixArr[0].length;        
        }

        public int get(int i, int j){
            return matrixArr[i][j];
        }

        public void set(int i, int j, int val){
            matrixArr[i][j] = val;
        }

        public Matrix matrixAdd(Matrix newMatrix){
            Matrix m = new Matrix(this.rows,this.cols);
            if(this.rows == newMatrix.rows && this.cols == newMatrix.cols){
                // matrices are same size, and can be added
                for(int i = 0; i < this.rows; i++){
                    for(int j = 0; j < this.cols; j++){
                        int val = this.get(i, j) + newMatrix.get(i, j);
                        m.set(i, j, val);
                    }
                }
                return m;
            }else{
                System.out.println("incompatible matrices cannot be added.");
                return null;
            }
        }

        public Matrix matrixSub(Matrix newMatrix){
            Matrix m = new Matrix(this.rows,this.cols);
            if(this.rows == newMatrix.rows && this.cols == newMatrix.cols){
                // matrices are same size, and can be added
                for(int i = 0; i < this.rows; i++){
                    for(int j = 0; j < this.cols; j++){
                        int val = this.get(i, j) - newMatrix.get(i, j);
                        m.set(i, j, val);
                    }
                }
                return m;
            }else{
                System.out.println("incompatible matrices cannot be subtracted.");
                return null;
            }
        }
    
        public Matrix matrixMul(Matrix newMatrix){
            Matrix m = new Matrix(this.rows,newMatrix.cols);
            if(this.cols == newMatrix.rows){
                for(int i = 0; i < this.rows; i++){
                    for(int j = 0; j < newMatrix.cols; j++){
                        for(int k = 0; k < this.cols; k++){
                            int val = this.get(i, k) * newMatrix.get(k, j);
                            val += m.get(i, j);
                            m.set(i, j, val);
                        }
                    }
                }
                return m;
            }else{
                System.out.println("incompatible matrices cannot be multiplied.");
                return null;
            }
        }

        @Override
        public String toString(){
            String result = "";
            for(int i = 0; i < this.rows; i++){
                for(int j = 0; j < this.cols; j++){
                    result += this.get(i, j) + " ";
                }
                result += "\n";
            }
            return result;
        }
    }
}