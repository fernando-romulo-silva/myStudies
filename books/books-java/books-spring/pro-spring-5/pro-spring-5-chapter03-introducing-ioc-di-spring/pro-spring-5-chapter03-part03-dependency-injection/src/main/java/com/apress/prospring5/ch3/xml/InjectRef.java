package com.apress.prospring5.ch3.xml;

import org.springframework.context.support.GenericXmlApplicationContext;
import com.apress.prospring5.ch3.Oracle;

public class InjectRef {

    // As you have already seen, it is possible to inject one bean into another by using the ref tag.
    // The next code snippet shows a class that exposes a setter to allow a bean to be injected

    private Oracle oracle;

    public void setOracle(Oracle oracle) {
	this.oracle = oracle;
    }

    public static void main(String... args) {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-xml.xml");
	ctx.refresh();

	InjectRef injectRef = (InjectRef) ctx.getBean("injectRef");
	System.out.println(injectRef);

	ctx.close();
    }

    public String toString() {
	return oracle.defineMeaningOfLife();
    }
}
