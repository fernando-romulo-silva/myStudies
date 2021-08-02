package br.com.fernando.ch15_Blending_OOP_and_FP.part03_Classes_and_traits;

public class Test {

    public static class Student {

	private String name;

	private int id;

	public Student(String name) {
	    this.name = name;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public int getId() {
	    return id;
	}

	public void setId(int id) {
	    this.id = id;
	}
    }

}
