package br.com.fernando.ch15_Blending_OOP_and_FP.part01_Introduction_to_Scala

import scala.sys.process.ProcessBuilder.Source
import scala.sys.process.ProcessBuilder.Source
import scala.io.Source

class TestS {

  def main(args: Array[String]) {
    var n: Int = 2

    while (n <= 6) {
      println(s"Hello ${n} bootles of beer") // String interpolation
      n += 1
    }
  }

  object Beer {
    def main(args: Array[String]) {

      2 to 6 foreach { n => println(s"Hello ${n} bottles of beer") }

      // Creating collections
      val authorsToAge = Map("Raoul" -> 23, "Mario" -> 40, "Alan" -> 53)

      val authors = List("Raoul", "Mario", "Alan")

      val numbers = Set(2, 3, 5)

      // One important property to keep in mind is that the collections created previously are immutable by default.
      //
      // So how can you update an immutable collection in Scala? To come back to the terminology used
      // in the previous chapter, such collections in Scala are said to be persistent: updating a collection
      // produces a new collection that shares as much as possible with its previous version

      // Here + is a method taht will add 9 to the Set, creating a new Set object as a result.
      val newNumbers = numbers + 8

      // (2, 3, 5, 8)
      println(newNumbers)

      // (2, 3, 5)
      println(numbers)

      val fileLines = Source.fromFile("data.txt").getLines()

      // Stream-like operations with Scala’s List
      // Don’t worry about the first line; it basically transforms a file into a list of strings consisting of
      // the lines in the file (similar to what Files.readAllLines provides in Java 8).
      // The second line creates a pipeline of two operations:
      //
      val linesLongUpper = fileLines //
        .filter(l => l.length() > 10) // A filter operation that selects only the lines that have a length greater than 10
        .map(l => l.toUpperCase()) // A map operation that transforms these long lines to uppercase

      // Tuples
      //
      val raoul = ("Raoul", "+ 44 887007007")
      val alan = ("Alan", "+44 883133700")

      // Scala supports arbitrary-sized[5] tuples, so the following are all possible:
      // Tuples have a limitation of 23 elements maximum.

      val book = (2014, "Java 8 in Action", "Meanning") // A tuple of type (Int, String, String)
      println(book._1) // 2014

      val numbersTuple = (42, 1337, 0, 3, 14) //  A tuple of type ( Int, Int, Int, Int, Int)
      println(numbersTuple._4)

    }
  }

  def getCarInsuranceName(person: Option[Person], minAge: Int) =
    person.filter(_.getAge() >= minAge) //
      .flatMap(_.getCar) //In Scala parentheses aren’t required when calling a method that takes no parameters.
      .flatMap(_.getInsurance) //
      .map(_.getName) //
      .getOrElse("Unknown")

}