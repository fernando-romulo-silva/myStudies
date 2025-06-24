package br.com.fernando.code06_object_oriented_programming

class Bird implements FlyingAbility, SpeakingAbility {

	@Override
	public String speak() {
		"It is speaking"
	}
}

trait FlyingAbility {
	
	String propertyA = "Property A"
	
	String fly() {
		"It is flying!"
	}
}

trait SpeakingAbility {
	
	abstract String speak()
	
	private String complaining() {
		"It is complaining"
	}
}


def bird = new Bird();

println bird.fly()
println bird.speak()
//println bird.complaining()

println bird.propertyA

