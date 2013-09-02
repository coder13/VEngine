package com.virtuel.util;

public class StringPair<A> {

	public String Name;
	public A A;
	
	public StringPair(String name, A a) {
		Name = name;
		A = a;
	}
	
	@Override
	public String toString(){
		return String.format("[%s: %s]", Name, A);
	}
	
}
