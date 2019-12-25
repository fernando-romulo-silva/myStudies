package br.com.fernando.chapter06_jsonProcessing.part02_objectAPI;

import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

public class ProducingJSONUsingObjectModelAPI {

    // JsonObjectBuilder can be used to create models that represent JSON objects.
    // The resulting model is of type JsonObject.

    public static void test01() {
        // Similarly, JsonArrayBuilder can be used to create models that represent JSON arrays where the
        // resulting model is of type JsonArray :

        JsonObject jsonObject = Json.createObjectBuilder().build();

        // In this code, a JsonObjectBuilder is used to create an empty object.
        // An empty object, with no name/value pairs, is created. The generated JSON structure is:

        System.out.println(jsonObject); // {}

        // Multiple builder instances can be created via JsonBuilderFactory :
        //
        // The factory can be configured with the specified map of provider-specific configuration properties.
        // Any unsupported configuration properties specified in the map are ignored.
        // In this case, null properties are passed during the creation of the reader factory.
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonArrayBuilder arrayBuilder = factory.createArrayBuilder();
        System.out.println(arrayBuilder);
        JsonObjectBuilder objectBuilder = factory.createObjectBuilder();
        System.out.println(objectBuilder);

        // The generated JsonObject can be written to an output stream via JsonWriter :
        Json.createWriter(System.out).writeObject(jsonObject);
        //
        // or

        StringWriter w = new StringWriter();
        try (JsonWriter writer = Json.createWriter(w)) {
            writer.write(jsonObject);
        }

        // In this code, a new JsonWriter instance is created and configured to write to System.out.
        // The previously created jsonObject is then written when the writeObject method is called.
        //
        // An object with two name/value pairs can be generated:

        Json.createObjectBuilder() //
                .add("apple", "red") //
                .add("banana", "yellow") //
                .build();
        /**
         * <pre>
         * {
         *  "apple":"red",
         *  "banana":"yellow"
         * }
         * </pre>
         */
        // The value can be BigDecimal, BigInteger, boolean, double, int, long, String, JsonValue, JsonObjectBuilder, or JsonArrayBuilder.
        // Specifying the value as JsonObjectBuilder and JsonArrayBuilder allows us to create nested objects and arrays

        Json.createArrayBuilder() //
                .add(Json.createObjectBuilder().add("apple", "red")) //
                .add(Json.createObjectBuilder().add("banana", "yellow")) //
                .build();

        // You start a new array by creating a JsonArrayBuilder.
        // You write an object within an array by calling the add method and creating a new object using the JsonObjectBuilder method.

        /**
         * <pre>
         * [
         *  { "apple":"red" },
         *  { "banana":"yellow" }
         * ]
         * </pre>
         */

        // A nested structure with two name/value pairs and a named array can be generated:
        // You start a named array by calling the add method, passing the name of the array, and creating a new array by calling the Json.createArrayBuilder method.

        Json.createArrayBuilder() //
                .add(Json.createObjectBuilder() //
                        .add("title", "The Matrix") //
                        .add("year", 1999) //
                        .add("cast", Json.createArrayBuilder() //
                                .add("Keanu Reaves") //
                                .add("Laurence Fishburne") //
                                .add("Carrie-Anne Moss"))) //
                .build();
        /**
         * <pre>
         * {
         *  "title":"The Matrix",
         *  "year":1999,
         *  "cast":[
         *  "Keanu Reeves",
         *  "Laurence Fishburne",
         *  "Carrie-Anne Moss"
         *  ]
         * }
         * </pre>
         */
    }

    // =======================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
