package data;

import java.io.Serializable;

public class Matrix implements Serializable{
	private static final long serialVersionUID = 130851295791956975L;
	private Vector[] matrix;    

    public Matrix(int n) {
        matrix = new Vector[n];
        for (int i = 0; i < n; i++) {
            matrix[i] = new Vector(n);
        }
    }
    
    public Matrix(int n, int m) {
    	//TODO: check right size: n or m 
    	matrix = new Vector[m];
        for (int i = 0; i < m; i++) {
            matrix[i] = new Vector(n);
        }
    }
    
    public int getLength() {
        return matrix.length;
    }

    public long get(int i, int j) {
        return matrix[i].get(j);
    }

    public void set(long e, int i, int j) {
        matrix[i].set(j, e);
    }


    public Matrix copy() {
        Matrix copyMatrix = new Matrix(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                copyMatrix.set(matrix[i].get(j), i, j);
            }
        }
        return copyMatrix;
    }

    public void fillWithOnes() {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i].fillWithOnes();
        }
    }
    
    public Vector getVector(int position) {    	
    	return this.matrix[position];
    }
    public void setVector(int position, Vector value) {
    	this.matrix[position] = value;
    }
    
    public Matrix getPartMatrix(int start, int count) {
    	Matrix partMatrix = new Matrix(this.getLength() , count);    	
    	for (int i=0; i<count; i++) {
    		partMatrix.setVector(i, this.getVector(i+start));
    	}    	
    	return partMatrix;
    }

}
