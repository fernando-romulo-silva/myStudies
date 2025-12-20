package br.com.alura.ecommerce.consumer;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.alura.ecommerce.Message;
import br.com.alura.ecommerce.MessageAdapter;

public class GsonDeserializer<T> implements Deserializer<Message<T>> {

    public static final String TYPE_CONFIG = "br.com.alura.ecommerce.type_config";

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new MessageAdapter<T>()).create();

    @SuppressWarnings("unchecked")
    @Override
    public Message<T> deserialize(String string, byte[] bytes) {
        return gson.fromJson(new String(bytes), Message.class);
    }

}
