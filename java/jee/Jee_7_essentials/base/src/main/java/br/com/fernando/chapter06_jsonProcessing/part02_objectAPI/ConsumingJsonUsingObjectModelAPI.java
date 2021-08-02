package br.com.fernando.chapter06_jsonProcessing.part02_objectAPI;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.stream.JsonParser;

public class ConsumingJsonUsingObjectModelAPI {

    private static final ClassLoader CONTEXT_CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    public static void test01() {
        // JsonReader contains methods to read JSON data using the object model from an input source.
        // JsonReader can be created from InputStream :
        //
        // JsonReader reader = Json.createReader(new FileInputStream(...));
        JsonReader reader = Json.createReader(CONTEXT_CLASS_LOADER.getResourceAsStream("chapter06_jsonProcessing/01_empty.json"));
        System.out.println(reader);
        //
        // This code shows how to create a new parser from an InputStream obtained from a new FileInputStream .
        // JsonReader can also be created from Reader :

        JsonParser parser = Json.createParser(new StringReader("{}"));
        System.out.println(parser);

        // You can create multiple parser instances using JsonReaderFactory :
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader parser1 = factory.createReader(new StringReader("{}"));
        JsonReader parser2 = factory.createReader(new StringReader("{}"));

        // The factory can be configured with the specified map of provider-specific configuration properties.
        // Any unsupported configuration properties specified in the map are ignored.
        // In this case, null properties are passed during the creation of the reader factory.

        System.out.println(parser1);
        System.out.println(parser2);
    }

    public static void test02() {
        JsonReader jsonReader = Json.createReader(new StringReader("{}"));
        JsonObject jsonEmpty = jsonReader.readObject();
        System.out.println(jsonEmpty);

        // In this code, a JsonReader is initialized via StringReader , which reads the empty JSON object.
        // Calling the readObject method returns an instance of JsonObject .

        jsonReader = Json.createReader(new StringReader("{ \"apple\":\"red\", \"banana\":\"yellow\"}"));
        JsonObject json = jsonReader.readObject();
        System.out.println(json.getString("apple"));
        System.out.println(json.getString("banana"));

        // In this code, the getString method returns the string value for the specific key in the object.
        // Other getXXX methods can be used to access the value based upon the data type.
        //
        // An array with two objects with each object with a name/value pair can be read as:

        jsonReader = Json.createReader(new StringReader("[" //
                + " { \"apple\":\"red\" }," //
                + " { \"banana\":\"yellow\" }" //
                + "]")); //
        JsonArray jsonArray = jsonReader.readArray();
        System.out.println(jsonArray);

        // This interface has convenience methods to get boolean , integer , and String values at a specific index.
        //
        // This interface extends from java.util.List , so usually the list operations are available as well.
        //
        jsonReader = Json.createReader(new StringReader("{" //
                + " \"title\":\"The Matrix\"," //
                + " \"year\":1999," //
                + " \"cast\":[" //
                + " \"Keanu Reeves\"," //
                + " \"Laurence Fishburne\"," //
                + " \"Carrie-Anne Moss\"" //
                + " ]" //
                + "}"));
        json = jsonReader.readObject();

        System.out.println(json);
    }

    public static void test03() {
        JsonReader jsonReader = Json.createReader(CONTEXT_CLASS_LOADER.getResourceAsStream("chapter06_jsonProcessing/04_objectMovie.json"));
        JsonObject json = jsonReader.readObject();
        System.out.println(json);

        System.out.println(json.isEmpty()); // false

        System.out.println(json.containsKey("title"));
        System.out.println("The Matrix".equals(json.getString("title")));
        System.out.println(json.containsKey("year"));

        System.out.println(1999 == json.getInt("year"));

        System.out.println(json.containsKey("cast"));
        JsonArray jsonArr = json.getJsonArray("cast");
        System.out.println(jsonArr);
        System.out.println(3 == jsonArr.size());

        System.out.println(jsonArr.toString());
    }

    // =======================================================================================================================
    public static void main(String[] args) {
        test03();
    }
}
