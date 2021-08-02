package br.com.fernando.chapter05_creatingImagesWithJavaApplications.part14_env;

public class Part01 {

	// ENV is a Dockerfile instruction that sets the environment variable <key> to the value <value>. 
	// You have two options for using ENV:
	//
	// The first one, ENV <key> <value>, will set a single variable to a value. 
	// The entire string after the first space will be treated as the <value>.
	// Ex:
	// 
	// ENV JAVA_HOME /var/lib/java8
	//
	// The second one, with an equal sign, is ENV <key>=<value>. 
	// This form allows setting multiple environment variables at once.
	// If you need to provide spaces in the values, you will need to use quotes. 
	// If you need quotes in the values, use backslashes:
	// Ex:
	//
	// ENV CONFIG_TYPE=file CONFIG_LOCATION="home/Jarek/my \app/config.json"
	//
	// Note that you can use ENV to update the PATH environment variable, and then CMD parameters will be aware of that setting. 
	// This will result in a cleaner form of CMD parameters in the Dockerfile. 
	// For example, set the following:
	//
	// ENV PATH /var/lib/tomcat8/bin:$PATH
	
	
}
