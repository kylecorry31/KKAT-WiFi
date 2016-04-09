package com.teamwifi.kkatwifi.lann.activation;

public class Softmax extends Activation{

	@Override
	public double activate(double x) {
		return Math.pow(Math.E, x);
	}

	@Override
	public double derivative(double x) {
		return Math.pow(Math.E, x) - x;
	}

}
