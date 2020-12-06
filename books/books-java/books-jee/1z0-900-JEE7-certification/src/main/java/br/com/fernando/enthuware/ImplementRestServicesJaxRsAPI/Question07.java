package br.com.fernando.enthuware.ImplementRestServicesJaxRsAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    // C- JsonParser
    //
    // D -JsonObject
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
    public static void main(String a[]) {

	File jsonInputFile = new File("/Users/java2novice/jsonInput.txt");

	try {
	    InputStream is = new FileInputStream(jsonInputFile);

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
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

}
