package com.apress.prospring5.ch3.annotated;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.annotation.Resource;

@Service("injectCollection")
public class CollectionInjection {

    /**
     * @Resource(name="map") is equivalent with @Autowired @Qualifier("map")
     */
    //
    // A combination of @Autowired and @Qualifier can be used to fulfill the same purpose, but it is always preferable to use one annotation and not two.
    // In the following code snippet, you can see the equivalent configuration to inject a collection using its bean name by using @Autowired and @Qualifier.
    @Autowired
    @Qualifier("map")
    private Map<String, Object> map;

    // Why Resource?
    // @Autowired annotation is semantically defined in a way that it always treats arrays, collections,
    // and maps as sets of corresponding beans, with the target bean type derived from the declared collection value type.
    @Resource(name = "props")
    private Properties props;

    @Resource(name = "set")
    private Set set;

    @Resource(name = "list")
    private List list;

    public void displayInfo() {
	System.out.println("Map contents:\n");
	map.entrySet().stream().forEach(e -> System.out.println("Key: " + e.getKey() + " - Value: " + e.getValue()));

	System.out.println("\nProperties contents:\n");
	props.entrySet().stream().forEach(e -> System.out.println("Key: " + e.getKey() + " - Value: " + e.getValue()));

	System.out.println("\nSet contents:\n");
	set.forEach(obj -> System.out.println("Value: " + obj));

	System.out.println("\nList contents:\n");
	list.forEach(obj -> System.out.println("Value: " + obj));
    }

    public static void main(String... args) {
	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
	ctx.load("classpath:spring/app-context-annotation.xml");
	ctx.refresh();

	CollectionInjection instance = (CollectionInjection) ctx.getBean("injectCollection");
	instance.displayInfo();

	ctx.close();
    }

}
