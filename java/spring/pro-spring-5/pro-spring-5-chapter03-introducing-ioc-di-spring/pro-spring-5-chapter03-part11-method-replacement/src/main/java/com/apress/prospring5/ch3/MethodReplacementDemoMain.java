package com.apress.prospring5.ch3;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.StopWatch;

// Using method replacement, you can replace the implementation of any method on any beans arbitrarily 
// without having to change the source of the bean you are modifying. 
//
// Internally, you achieve this by creating a subclass of the bean class dynamically. 
// You use CGLIB and redirect calls to the method you want to replace to another bean that implements the MethodReplacer interface.
//
// Method replacement can prove quite useful in a variety of circumstances, especially when you want to override only a particular 
// method for a single bean rather than all beans of the same type.
//
// With that said, we still prefer using standard Java mechanisms for overriding methods rather than depending on runtime bytecode enhancement.
// If you are going to use method replacement as part of your application, we recommend you use one Method-Replacer per method or group of overloaded methods. 
// Avoid the temptation to use a single MethodReplacer for lots of unrelated methods;
public class MethodReplacementDemoMain {

    public static void main(String... args) {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-xml.xml");
	ctx.refresh();

	ReplacementTarget replacementTarget = (ReplacementTarget) ctx.getBean("replacementTarget");
	ReplacementTarget standardTarget = (ReplacementTarget) ctx.getBean("standardTarget");

	displayInfo(replacementTarget);
	displayInfo(standardTarget);

	ctx.close();
    }

    private static void displayInfo(ReplacementTarget target) {
	System.out.println(target.formatMessage("Thanks for playing, try again!"));

	StopWatch stopWatch = new StopWatch();
	stopWatch.start("perfTest");

	for (int x = 0; x < 1000000; x++) {
	    String out = target.formatMessage("No filter in my head");
	    // commented to not pollute the console
	    // System.out.println(out);
	}

	stopWatch.stop();

	System.out.println("1000000 invocations took: " + stopWatch.getTotalTimeMillis() + " ms");
    }
}
