package data;

import java.io.Serializable;

public class Vector implements Serializable{

	private static final long serialVersionUID = -6681845212542066599L;
	private long[] vector;

    public Vector(int n) {
        vector = new long[n];
    }

    public long get(int position) {
        return vector[position];
    }

    public void set(int position, long value) {
        vector[position] = value;
    }

    public int getLength() {
        return vector.length;
    }

    public Vector copy() {
        Vector copyVector = new Vector(vector.length);
        for (int i = 0; i < vector.length; i++) {
            copyVector.set(i, vector[i]);
        }
        return copyVector;
    }

    public void fillWithOnes() {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = 1;
        }
    }

}

