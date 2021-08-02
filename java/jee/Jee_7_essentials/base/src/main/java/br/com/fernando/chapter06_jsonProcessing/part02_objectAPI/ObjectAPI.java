package br.com.fernando.chapter06_jsonProcessing.part02_objectAPI;

public class ObjectAPI {

    // The Object Model API is a high-level API that provides immutable object models for JSON object and array structures.
    // These JSON structures are represented as object models via the Java types JsonObject and JsonArray.
    // JsonObject provides a Map view to access the unordered collection of zero or more name/value pairs from the model.
    // Similarly, JsonArray provides a List view to access the ordered sequence of zero or more values from the model.
    //
    // This programming model is most flexible and enables processing that requires random access to the complete contents of the tree.
    //
    // However, it is often not as efficient as the streaming model and requires more memory.
    //
    // The Object Model API is similar to the DOM API for XML and uses builder patterns to create these object models.
    //
    // It consists of the interfaces JsonReader (for consuming JSON) and JsonObjectBuilder and JsonArrayBuilder (for producing JSON).
}
