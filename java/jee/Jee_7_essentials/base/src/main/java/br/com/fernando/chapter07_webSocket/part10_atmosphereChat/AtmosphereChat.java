package br.com.fernando.chapter07_webSocket.part10_atmosphereChat;

import java.io.IOException;
import java.util.Date;

import org.atmosphere.config.managed.Decoder;
import org.atmosphere.config.managed.Encoder;
import org.atmosphere.config.service.Disconnect;
import org.atmosphere.config.service.ManagedService;
import org.atmosphere.config.service.Ready;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtmosphereChat {

    public static class Message {

        private String message;

        private String author;

        private long time;

        public Message() {
            this("", "");
        }

        public Message(String author, String message) {
            this.author = author;
            this.message = message;
            this.time = new Date().getTime();
        }

        public String getMessage() {
            return message;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }

    public class JacksonDecoder implements Decoder<String, Message> {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public Message decode(String s) {
            try {
                return mapper.readValue(s, Message.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class JacksonEncoder implements Encoder<Message, String> {

        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public String encode(Message m) {
            try {
                return mapper.writeValueAsString(m);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Simple annotated class that demonstrate the power of Atmosphere. This class supports all transports, support message length guarantee, heart beat, message cache thanks to the @ManagedAService. The client will first try with WebSocket and then fallback using the client's preference.
     */
    @ManagedService(path = "/chat")
    public static class ChatEndpoint {

        private final Logger logger = LoggerFactory.getLogger(ChatEndpoint.class);

        /**
         * *
         * 
         * @param r
         */
        @Ready
        public void onReady(final AtmosphereResource r) {
            logger.info("Browser {} connected.", r.uuid());
        }

        /**
         * Invoked when the client disconnect or when an unexpected closing of the underlying connection happens.
         *
         * @param event
         */
        @Disconnect
        public void onDisconnect(AtmosphereResourceEvent event) {
            if (event.isCancelled()) {
                logger.info("Browser {} unexpectedly disconnected", event.getResource().uuid());
            } else if (event.isClosedByClient()) {
                logger.info("Browser {} closed the connection", event.getResource().uuid());
            }
        }

        /**
         * Simple annotated class that demonstrate how {@link org.atmosphere.config.managed.Encoder} and {@link org.atmosphere.config.managed.Decoder can be used.
         *
         * @param message
         *            an instance of {@link Message}
         * @return
         * @throws IOException
         */
        @org.atmosphere.config.service.Message(encoders = { JacksonEncoder.class }, decoders = { JacksonDecoder.class })
        public Message onMessage(Message message) throws IOException {
            logger.info("{} just send {}", message.getAuthor(), message.getMessage());
            return message;
        }
    }

}
