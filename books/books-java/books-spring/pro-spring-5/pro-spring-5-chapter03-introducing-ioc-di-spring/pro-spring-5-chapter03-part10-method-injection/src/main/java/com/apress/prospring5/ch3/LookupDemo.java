package com.apress.prospring5.ch3;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.StopWatch;

public class LookupDemo {

    // Lookup Method Injection provides another mechanism by which a bean can obtain one of its dependencies.

    public static void main(String... args) {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-xml.xml");
	ctx.refresh();

	DemoBean abstractBean = ctx.getBean("abstractLookupBean", DemoBean.class);
	DemoBean standardBean = ctx.getBean("standardLookupBean", DemoBean.class);

	displayInfo("abstractLookupBean", abstractBean);
	displayInfo("standardLookupBean", standardBean);

	ctx.close();
    }

    public static void displayInfo(String beanName, DemoBean bean) {
	Singer singer1 = bean.getMySinger();
	Singer singer2 = bean.getMySinger();

	System.out.println("[" + beanName + "]: Singer Instances the Same?  " + (singer1 == singer2));

	StopWatch stopWatch = new StopWatch();
	stopWatch.start("lookupDemo");

	for (int x = 0; x < 100000; x++) {
	    Singer singer = bean.getMySinger();
	    singer.sing();
	}

	stopWatch.stop();
	System.out.println("100000 gets took " + stopWatch.getTotalTimeMillis() + " ms");
    }

    // Lookup Method Injection is intended for use when you want to work with two beans of different life cycles.
    //
    // Avoid the temptation to use Lookup Method Injection when the beans share the same life cycle, especially if they are singletons.
    //
    // The output of running the previous example shows a noticeable difference in performance between using Method Injection to obtain
    // new instances of a dependency and using standard DI to obtain a single instance of a dependency.
    //
    // Also, make sure you don’t use Lookup Method Injection needlessly, even when you have beans of different life cycles.
}
