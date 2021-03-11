package br.com.fernando.enthuware.ImplementRestServicesJaxRsAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Question07 {

    // Which class do you use to handle an incoming JSON Message as a stream?
    //
    // A - JsonReader
    //
    // B - JsonObjectBuilder
    //
    // C - JsonParser
    //
    // D - JsonObject
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    // The correct answer is A
    //
    // The javax.json package contains a reader interface, a writer interface, and a model builder interface for the object model.
    //
    // The javax.json.JsonReader reads JSON data from a stream and creates an object model in memory.
    //
    // Choice B is incorrect. JsonObjectBuilder create an object model or an array model in memory by adding elements from application code.
    //
    // Choice C is incorrect. javax.json.stream.JsonParser represents an event-based parser that can read JSON data from a stream or from an object model.
    //
    // Choice D is incorrect. JsonObject represent an object in JSON data. It is subtype of JsonStructure.
    public static void main(String a[]) {

	File jsonInputFile = new File("/Users/java2novice/jsonInput.txt");

	try (InputStream is = new FileInputStream(jsonInputFile)) {

	    // Create JsonReader from Json.
	    JsonReader reader = Json.createReader(is);

	    // Get the JsonObject structure from JsonReader.
	    JsonObject empObj = reader.readObject();
	    reader.close();

	    // read string data
	    System.out.println("Emp Name: " + empObj.getString("emp_name"));

	    // read integer data
	    System.out.println("Emp Id: " + empObj.getInt("emp_id"));

	    // read inner json element
	    JsonObject addrObj = empObj.getJsonObject("address");

	    System.out.println("City: " + addrObj.getString("city"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
