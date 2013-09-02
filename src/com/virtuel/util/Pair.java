package com.virtuel.util;

public class Pair<A, B> {

	public A A;
	public B B;
	
	public Pair(A a, B b){
		A = a;
		B = b;
	}
	
	@Override
	public String toString(){
		return String.format("[%s,%s]", A, B);
	}
	
}
