package br.com.fernando.chapter06_jsonProcessing.part01_streamingAPI;

import java.io.StringReader;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

public class ConsumingJSON {

    private static final ClassLoader CONTEXT_CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    // JsonParser contains methods to parse JSON data using the streaming model.
    //
    // Json Parser provides forward, read-only access to JSON data using the pull parsing programming model.
    public static void test01() {
        // JsonParser can be created from an InputStream:
        //
        // JsonParser parser = Json.createParser(new FileInputStream(...));

        JsonParser parser01 = Json.createParser(CONTEXT_CLASS_LOADER.getResourceAsStream("chapter06_jsonProcessing/01_empty.json"));
        System.out.println(parser01);

        // JsonParser can also be created from a Reader:
        JsonParser parser02 = Json.createParser(new StringReader("{ \"apple\":\"red\", \"banana\":\"yellow\" }"));
        System.out.println(parser02.toString());

        // You can create multiple parser instances using JsonParserFactory:
        JsonParserFactory factory = Json.createParserFactory(null);

        JsonParser parser03 = factory.createParser(new StringReader("{ \"apple\":\"red\", \"banana\":\"yellow\" }"));
        System.out.println(parser03.toString());

        JsonParser parser04 = factory.createParser(new StringReader("{ \"apple\":\"red\", \"banana\":\"yellow\" }"));
        System.out.println(parser04.toString());

        // The factory can be configured with the specified map of provider-specific configuration properties.
        // Any unsupported configuration properties specified in the map are ignored.
        // In this case, null properties are passed during the creation of the parser factory.
        //
        // The pull-parsing programming model is used to to parse the JSON.
        //
        // The next method returns the event for the next parsing state, which could be any of the following types
        //
        // * START_ARRAY
        // * END_ARRAY
        // * START_OBJECT
        // * END_OBJECT
        // * KEY_NAME
        // * VALUE_STRING
        // * VALUE_NUMBER
        // * VALUE_TRUE
        // * VALUE_FALSE
        // * VALUE_NULL
        //
        // The parser generates START_OBJECT and END_OBJECT events for an empty JSON object { }.
        //
        // For an object with two name/value pairs:
        /**
         * <pre>
         * {
         *  "apple":"red",
         *  "banana":"yellow"
         * }
         * </pre>
         */
        //
        // The events generated are shown in UP:
        /**
         * <pre>
         * {START_OBJECT
         *    "apple"KEY_NAME:"red"VALUE_STRING,
         *    "banana"KEY_NAME:"yellow"VALUE_STRING
         * END_OBJECT}
         * </pre>
         */
    }

    public static void test02() {

        JsonParser parser01 = Json.createParser(CONTEXT_CLASS_LOADER.getResourceAsStream("chapter06_jsonProcessing/01_empty.json"));
        // Just
        //
        /**
         * <pre>
         * {START_OBJECT
         * END_OBJECT}
         * </pre>
         */
        System.out.println(parser01.next() == JsonParser.Event.START_OBJECT);
        System.out.println(parser01.next() == JsonParser.Event.END_OBJECT);

        // --------------------------------------------------------------------------------------------
        JsonParser parser02 = Json.createParser(CONTEXT_CLASS_LOADER.getResourceAsStream("chapter06_jsonProcessing/02_objectFruits.json"));
        //
        /**
         * <pre>
         * {
         *   "apple":"red",
         *   "banana":"yellow"
         * }
         * </pre>
         * 
         * To
         * 
         * <pre>
         * {START_OBJECT                           
         *    "apple"KEY_NAME:"red"VALUE_STRING,   
         *    "banana"KEY_NAME:"yellow"VALUE_STRING
         * END_OBJECT}
         * </pre>
         */
        System.out.println(JsonParser.Event.START_OBJECT == parser02.next());
        System.out.println(JsonParser.Event.KEY_NAME == parser02.next());
        System.out.println(JsonParser.Event.VALUE_STRING == parser02.next());
        System.out.println(JsonParser.Event.KEY_NAME == parser02.next());
        System.out.println(JsonParser.Event.VALUE_STRING == parser02.next());
        System.out.println(JsonParser.Event.END_OBJECT == parser02.next());
    }

    public static void test03() {

        String json = "[{\"apple\":\"red\"},{\"banana\":\"yellow\"}]";

        final JsonParser parser = Json.createParser(new StringReader(json));

        /**
         * <pre>
         * [
         *   { "apple":"red" },
         *   { "banana":"yellow" }
         * ]
         * </pre>
         * 
         * To
         * 
         * <pre>
         * [START_ARRAY
         *   {START_OBJECT "apple"KEY_NAME:"red"VALUE_STRING END_OBJECT},
         *   {START_OBJECT "banana"KEY_NAME:"yellow"VALUE_STRING END_OBJECT}
         * END_ARRAY]
         * </pre>
         */
        System.out.println(JsonParser.Event.START_ARRAY == parser.next());
        System.out.println(JsonParser.Event.START_OBJECT == parser.next());
        System.out.println(JsonParser.Event.KEY_NAME == parser.next());
        System.out.println(JsonParser.Event.VALUE_STRING == parser.next());
        System.out.println(JsonParser.Event.END_OBJECT == parser.next());
        System.out.println(JsonParser.Event.START_OBJECT == parser.next());
        System.out.println(JsonParser.Event.KEY_NAME == parser.next());
        System.out.println(JsonParser.Event.VALUE_STRING == parser.next());
        System.out.println(JsonParser.Event.END_OBJECT == parser.next());
        System.out.println(JsonParser.Event.END_ARRAY == parser.next());
    }

    public static void test04() {

        String json = "{\"title\":\"The Matrix\",\"year\":1999,\"cast\":[\"Keanu Reaves\",\"Laurence Fishburne\",\"Carrie-Anne Moss\"]}";
        JsonParser parser = Json.createParser(new StringReader(json));
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
         * 
         * To
         * 
         * <pre>
         * {START_OBJECT
         *  "title"KEY_NAME:"The Matrix"VALUE_STRING,
         *  "year"KEY_NAME:1999VALUE_NUMBER,
         *  "cast"KEY_NAME:[START_ARRAY
         *  "Keanu Reeves"VALUE_STRING,
         *  "Laurence Fishburne"VALUE_STRING,
         *  "Carrie-Anne Moss"VALUE_STRING
         *  ]END_ARRAY
         * }END_OBJECT
         * </pre>
         */
        System.out.println(JsonParser.Event.START_OBJECT == parser.next());
        System.out.println(JsonParser.Event.KEY_NAME == parser.next());
        System.out.println(JsonParser.Event.VALUE_STRING == parser.next());
        System.out.println(JsonParser.Event.KEY_NAME == parser.next());
        System.out.println(JsonParser.Event.VALUE_NUMBER == parser.next());
        System.out.println(JsonParser.Event.KEY_NAME == parser.next());
        System.out.println(JsonParser.Event.START_ARRAY == parser.next());
        System.out.println(JsonParser.Event.VALUE_STRING == parser.next());
        System.out.println(JsonParser.Event.VALUE_STRING == parser.next());
        System.out.println(JsonParser.Event.VALUE_STRING == parser.next());
        System.out.println(JsonParser.Event.END_ARRAY == parser.next());
        System.out.println(JsonParser.Event.END_OBJECT == parser.next());
    }

    // =======================================================================================================================
    public static void main(String[] args) {
        test02();
    }
}
