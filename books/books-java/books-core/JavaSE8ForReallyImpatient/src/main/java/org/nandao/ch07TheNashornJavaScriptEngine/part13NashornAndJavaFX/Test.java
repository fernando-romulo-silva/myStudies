package org.nandao.ch07TheNashornJavaScriptEngine.part13NashornAndJavaFX;

// Nashorn provides a convenient way of launching JavaFX applications. Simply
// put the instructions that you would normally put into the start method of the
// Application subclass into the script. Use $STAGE for the Stage parameter. You don’t
// even have to call show on the Stage object—that is done for you.
//
// Run the script with the -fx option, like this:
// jjs -fx myJavaFxApp.js
public class Test {

    public static String test1() {
        return "/part13NashornAndJavaFX1.js";
    }

    public static String test2() {
        return "/part13NashornAndJavaFX2.js";
    }

}
