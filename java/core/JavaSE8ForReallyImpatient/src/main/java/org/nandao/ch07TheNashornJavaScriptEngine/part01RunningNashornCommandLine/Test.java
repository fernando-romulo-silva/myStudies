package org.nandao.ch07TheNashornJavaScriptEngine.part01RunningNashornCommandLine;

// Nashorn is the German word for rhinoceros—literally, nose-horn. (You get extra karma
// for pronouncing it nas-horn, not na-shorn.) Nashorn is very fast, and it lets you
// integrate Java with JavaScript on a highly performant virtual machine. It is
// also incredibly compliant with the ECMAScript standard for JavaScript

public class Test {

    // Java 8 ships with a command-line tool called jjs. 
    // Simply launch it, and issue JavaScript commands.
    // $ jjs
    // jjs> 'Hello, World'
    // Hello, World
    // You get what’s called a “read-eval-print” loop, or REPL, in the world of Lisp,
    // Scala, and so on. Whenever you enter an expression, its value is printed.
    // jjs> 'Hello, World!'.length
    // 13
    //
    // You can define functions and call them:
    // jjs> function factorial(n) { return n <= 1 ? 1 : n * factorial(n - 1) }
    // function factorial(n) { return n <= 1 ? 1 : n * factorial(n - 1) }
    // jjs> factorial(10)
    // 3628800
    //
    // You can call Java methods:
    // var input = new java.util.Scanner(
    // new java.net.URL('http://horstmann.com').openStream())
    // input.useDelimiter('$')
    // var contents = input.next()
    public static void main(final String[] args) {

    }

}
