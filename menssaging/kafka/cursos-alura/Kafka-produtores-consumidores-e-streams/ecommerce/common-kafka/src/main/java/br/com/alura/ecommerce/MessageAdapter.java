package br.com.alura.ecommerce;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MessageAdapter<T> implements JsonSerializer<Message<T>>, JsonDeserializer<Message<T>> {

    @Override
    public JsonElement serialize(Message<T> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", src.getPayload().getClass().getName());
        obj.add("payload", context.serialize(src.getPayload()));
        obj.add("correlationId", context.serialize(src.getId()));
        return obj;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Message<T> deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        final var obj = json.getAsJsonObject();
        final var payloadType = obj.get("type").getAsString();
        final var correlationId = (CorrelationId) context.deserialize(obj.get("correlationId"), CorrelationId.class);
        try {
            final var payload = context.deserialize(obj.get("payload"), Class.forName(payloadType));

            return new Message(correlationId, payload);
        } catch (ClassNotFoundException ex) {
            throw new JsonParseException(ex);
        }

    }

}
