package br.com.fernando.chapter06_jsonProcessing.part01_streamingAPI;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;

public class ProdusingJSON {

    // The Streaming API provides a way to generate well-formed JSON to a stream by writing one event at a time.

    public static void test01() {
        // JsonGenerator contains writeXXX methods to write name/value pairs in JSON objects and values in JSON arrays:

        // The factory can be configured with the specified map of provider-specific configuration properties.
        // Any unsupported configuration properties specified in the map are ignored.
        // In this case, null properties are passed during the creation of the generator factory
        JsonGeneratorFactory factory = Json.createGeneratorFactory(null);

        // A JsonGenerator is obtained from JsonGeneratorFactory and configured to write the output to System.out.
        JsonGenerator gen = factory.createGenerator(System.out);

        // An empty object, with no name/value pairs, is created and written to the configured output stream.
        // An object is started when the writeStartObject method is called, and ended with the writeEnd method.
        gen.writeStartObject().writeEnd();

        // The generated JSON structure is:
        // { }
        //
        // An object with two name/value pairs can be generated:
        gen.writeStartObject() //
                .write("apple", "red") //
                .write("banana", "yellow") //
                .writeEnd();
        // The generated JSON structure is:
        /**
         * <pre>
         * {
         *  "apple":"red",
         *  "banana":"yellow"
         * }
         * </pre>
         */
        //
        // An array with two objects with each object with a name/value pair can be generated:

        gen.writeStartArray() //
                .writeStartObject() //
                .write("apple", "red") //
                .writeEnd() // end object
                //
                .writeStartObject() //
                .write("banana", "yellow") //
                .writeEnd() // end object
                //
                .writeEnd();
        // A new array is started when the writeStartArray method is called and ended when the writeEnd method is called.
        // An object within an array is written via the writeStar tObject and writeEnd methods.
        /**
         * <pre>
         * [
         *  { "apple":"red" },
         *  { "banana":"yellow" }
         * ]
         * </pre>
         */
    }

    public static void test02() {
        JsonGeneratorFactory factory = Json.createGeneratorFactory(null);
        JsonGenerator gen = factory.createGenerator(System.out);

        // A nested structure with two name/value pairs and a named array can be generated:
        gen.writeStartObject() //
                .write("title", "The Matrix") //
                .write("year", 1999) //
                .writeStartArray("cast") // startArray
                //
                .write("Keanu Reeves") //
                .write("Laurence Fishburne")//
                .write("Carrie-Anne Moss") //
                .writeEnd() // endArray
                //
                .writeEnd();
        // A named array is started via writeStartArray.
        // Each element of the array is written via the write method, which can take values of the type BigDecimal, BigInteger,
        // boolean, double, int, long, String, and JsonValue.

        /**
         * <pre>
         * {
         *   "title":"The Matrix",
         *   "year":1999,
         *   "cast":[
         *     "Keanu Reaves",
         *     "Laurence Fishburne",
         *     "Carrie-Anne Moss"
         *   ]
         * }
         * </pre>
         */
    }

    // =======================================================================================================================
    public static void main(String[] args) {
        test01();
    }
}
