package com.teamwifi.kkatwifi.lann;

import java.util.Arrays;

public class Matrix implements Cloneable {

	public static abstract class Function {
		public abstract double function(double x);
	}

	private double[][] values;

	public Matrix(double[][] values) {
		this.values = values;
	}

	public Matrix(int rows, int cols) {
		values = new double[rows][cols];
	}

	public Matrix(int rows, int cols, double defaultValue) {
		values = new double[rows][cols];
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++)
				values[row][col] = defaultValue;
	}

	public Matrix map(Function fn) {
		Matrix mapped = new Matrix(getNumRows(), getNumCols());
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				mapped.set(row, col, fn.function(mapped.get(row, col)));
		return mapped;
	}

	public Matrix dot(Matrix mat) {
		Matrix product = new Matrix(getNumRows(), mat.getNumCols());
		if (getNumCols() != mat.getNumRows())
			return null;
		Matrix trans = mat.transpose();
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < trans.getNumRows(); col++)
				product.set(row, col, dotProduct(getRow(row), trans.getRow(col)));
		return product;
	}

	public Matrix power(double power) {
		Matrix exp = new Matrix(getNumRows(), getNumCols());
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				exp.set(row, col, Math.pow(get(row, col), power));
		return exp;
	}

	public Matrix multiply(Matrix mat) {
		if (getNumRows() != mat.getNumRows() || getNumCols() != mat.getNumCols())
			return null;
		Matrix scaled = new Matrix(getNumRows(), getNumCols());
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				scaled.set(row, col, get(row, col) * mat.get(row, col));
		return scaled;
	}

	private double dotProduct(double[] a, double[] b) {
		double sum = 0;
		if (a.length == b.length) {
			for (int i = 0; i < a.length; i++) {
				sum += a[i] * b[i];
			}
		}
		return sum;
	}

	public Matrix multiply(double value) {
		Matrix scaled = new Matrix(getNumRows(), getNumCols());
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				scaled.set(row, col, get(row, col) * value);
		return scaled;
	}

	public Matrix transpose() {
		Matrix mat = new Matrix(getNumCols(), getNumRows());
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				mat.set(col, row, get(row, col));
		return mat;
	}

	public Matrix add(Matrix mat) {
		if (getNumRows() != mat.getNumRows() || getNumCols() != mat.getNumCols())
			return null;
		Matrix sum = new Matrix(getNumRows(), getNumCols());
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				sum.set(row, col, get(row, col) + mat.get(row, col));
		return sum;
	}

	public Matrix add(double value) {
		Matrix scaled = new Matrix(getNumRows(), getNumCols());
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				scaled.set(row, col, get(row, col) + value);
		return scaled;
	}

	public Matrix subtract(Matrix mat) {
		return add(mat.multiply(-1));
	}

	public Matrix subtract(double value) {
		return add(-value);
	}

	public Matrix normalize() {
		double max = max();
		return multiply(1 / max);
	}

	public Matrix oneHot() {
		int[] hot = find(max());
		Matrix oneHot = new Matrix(getNumRows(), getNumCols());
		oneHot.set(hot[0], hot[1], 1);
		return oneHot;
	}

	public double max() {
		double max = values[0][0];
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				if (get(row, col) > max)
					max = get(row, col);
		return max;
	}

	public double min() {
		double min = values[0][0];
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				if (get(row, col) < min)
					min = get(row, col);
		return min;
	}

	public int[] find(double value) {
		for (int row = 0; row < getNumRows(); row++)
			for (int col = 0; col < getNumCols(); col++)
				if (get(row, col) == value)
					return new int[] { row, col };
		return new int[] { -1, -1 };
	}

	public double sum() {
		double sum = 0;
		for (double[] row : values)
			for (double value : row)
				sum += value;
		return sum;
	}

	public double[][] getValues() {
		return values;
	}

	public void setValues(double[][] values) {
		this.values = values;
	}

	public int getNumRows() {
		return values.length;
	}

	public int getNumCols() {
		if (values.length > 0)
			return values[0].length;
		return 0;
	}

	public double get(int row, int col) {
		return values[row][col];
	}

	public void set(int row, int col, double value) {
		values[row][col] = value;
	}

	public double[] getRow(int row) {
		return values[row];
	}

	@Override
	public String toString() {
		String str = "";
		for (double[] row : getValues())
			str += Arrays.toString(row) + "\n";
		str = str.substring(0, str.length());
		return str;
	}

	@Override
	public Object clone() {
		double[][] valClone = new double[getNumRows()][getNumCols()];
		for (int row = 0; row < valClone.length; row++)
			for (int col = 0; col < valClone[row].length; col++)
				valClone[row][col] = values[row][col];
		return new Matrix(valClone);
	}

}
