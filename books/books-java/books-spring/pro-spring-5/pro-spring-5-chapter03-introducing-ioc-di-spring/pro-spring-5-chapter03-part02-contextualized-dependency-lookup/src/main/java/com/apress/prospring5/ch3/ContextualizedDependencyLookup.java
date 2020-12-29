package com.apress.prospring5.ch3;


//  Contextualized dependency lookup (CDL) is similar, in some respects, to dependency pull, but in CDL, lookup is performed against the container 
// that is managing the resource, not from some central registry, and it is usually performed at some set point.
public class ContextualizedDependencyLookup implements ManagedComponent {
    private Dependency dependency;

    @Override
    public void performLookup(Container container) {
	
	// The real question is this: given the choice, which method should you use, injection or lookup? The answer is most definitely injection. 
	// If you look at the code in the previous code samples, you can clearly see that using injection has zero impact on your components’ code
	
	
        this.dependency = (Dependency) container.getDependency("myDependency"); 
    }

    @Override
    public String toString() {
    	return dependency.toString();
    }
}
