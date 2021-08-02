package br.com.fernando.ch15_Blending_OOP_and_FP.part02_Functions

class TestS {

  // First-class functions in Scala
  def isJavaMentioned(tweet: String): Boolean = tweet.contains("Java")

  def isShortTweet(tweet: String): Boolean = tweet.length() < 20

  val tweets = List(
    "How's it going?",
    "I love the new features in Java 8",
    "An SQL query walks into a bar, sees two tables and says 'Can I join you?'")

  tweets.filter(isJavaMentioned).foreach(println)
  tweets.filter(isShortTweet).foreach(println)

  // Anonymous functions and closures

  val isLongTweet: String => Boolean // A variable of function type String to Boolean 
  = (tweet: String) => tweet.length() > 60 // An anonymous function

  // The previous code is syntactic sugar for declaring an anonymous class of
  // type scala.Function1 (a function of one parameter), which provides the implementation of the
  // method apply:
  val isLongTweet2: String => Boolean = new Function1[String, Boolean] {
    def apply(tweet: String): Boolean = tweet.length() > 60
  }

  // Because the variable isLongTweet holds an object of type Function1, you can call the method
  // apply, which can be seen as calling the function:
  isLongTweet.apply("A very short tweet") // returns false

  // Another cool trick in Scala is that you can call the method apply using syntactic sugar that looks
  // more like a function call:

  isLongTweet("A very short twwet")

  // Closures

  def main(args: Array[String]) {
    var count = 0
    val inc = () => count + 1 // A closure captureing and incrementing count
    inc()
    println(count) // print 1
    inc()
    println(count) // print 2
  }
  
  // Currying
  
  // Simple function
  def multiply(x : Int, y: Int) = x * y 
  
  val r = multiply(2, 10); 
  
  // Curried Function
  def multiplyCurry(x : Int) (y : Int) = x * y // Defining a curried function
  
  val r2 = multiplyCurry(2)(10) // invoking a curried function
  
  val multiplyByTwo : Int => Int = multiplyCurry(2) // ignore second arg  
  
  val r3 = multiplyByTwo(10) /// 20
  

}