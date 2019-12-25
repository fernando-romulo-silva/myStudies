package br.com.fernando.ch15_Blending_OOP_and_FP.part03_Classes_and_traits

class TestS {

  // Less verbosity with Scala classes
  class Hello {
    def sayThankYou() {
      println("Thanks for reading our book")
    }
  }

  val h = new Hello()
  h.sayThankYou()

  // Getters and setters

  class Student(var name: String, var id: Int)
  val s = new Student("Raoul", 1) // Initialize a Student object
  println(s.name) // get the name print Raoul
  s.id = 1337 // Set the id
  println(s.id) // print 1337

  // Scala traits vs. Java 8 interfaces

  trait Sized {
    var size: Int = 0 // A field called size

    def isEmpty() = size == 0 // A method called isEmpty with a default implementation
  }

  // You can now compose it at declaration time with a class, here an Empty class that always has 0.
  
  class Empty extends Sized // A class inhereiting from the trait Sized

  println(new Empty().isEmpty()) // prints true
  
  // Interestingly, compared to Java interfaces, traits can also be composed at object instantiation
  // time (but it’s still a compile-time operation). For example, you can create a Box class and decide
  // that one specific instance should support the operations defined by the trait Sized:
  
  
  class Box // A simple class
  
  val b1 = new Box() with Sized // Composing the trait at object instantion time
  println(b1.isEmpty()) // prints true
  
  val b2 = new Box()
  // b2.isEmpty() Compile error: the Box class declaration doesn't inherit from Sized
  

}