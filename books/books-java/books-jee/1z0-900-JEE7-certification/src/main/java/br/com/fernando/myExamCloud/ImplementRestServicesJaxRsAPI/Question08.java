package br.com.fernando.myExamCloud.ImplementRestServicesJaxRsAPI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

public class Question08 {

    // What are entity providers?
    // [ Choose two ]
    //
    // Choice A
    // JAX-RS Provider
    //
    // Choice B
    // MessageBodyReader
    //
    // Choice C
    // MessageBodyWriter
    //
    // Choice D
    // Webservice container provider
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
    // Choice B and C are correct answers.
    //
    // Entity providers supply mapping services between representations and their associated Java types.
    //
    // Entity providers come in two flavors: MessageBodyReader and MessageBodyWriter.
    //
    //
    @Path("endpoint")
    public static class EndPoint {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MyObject echoObject(final MyObject mo) {
	    return mo;
	}
    }

    public static class MyObject {

	private String name;
	private int age;

	public MyObject() {
	}

	public MyObject(String name, int age) {
	    this.name = name;
	    this.age = age;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public int getAge() {
	    return age;
	}

	public void setAge(int age) {
	    this.age = age;
	}

	@Override
	public String toString() {
	    return "MyObject: age" + age + " name:" + name;
	}
    }

    @Provider
    @Consumes(MediaType.APPLICATION_JSON)
    public static class MyReader implements MessageBodyReader<MyObject> {

	@Override
	public boolean isReadable(final Class<?> type, final Type type1, final Annotation[] antns, final MediaType mt) {
	    return MyObject.class.isAssignableFrom(type);
	}

	@Override
	public MyObject readFrom(Class<MyObject> type, //
		final Type type1, //
		final Annotation[] antns, //
		final MediaType mt, MultivaluedMap<String, String> mm, //
		final InputStream in) throws IOException, WebApplicationException { //

	    final MyObject mo = new MyObject();
	    final JsonParser parser = Json.createParser(in);

	    while (parser.hasNext()) {
		switch (parser.next()) {
		case KEY_NAME:
		    String key = parser.getString();
		    parser.next();
		    switch (key) {
		    case "name":
			mo.setName(parser.getString());
			break;
		    case "age":
			mo.setAge(parser.getInt());
			break;
		    default:
			break;
		    }
		    break;
		default:
		    break;
		}
	    }

	    return mo;
	}
    }

    @Provider
    @Produces(MediaType.APPLICATION_JSON)
    public static class MyWriter implements MessageBodyWriter<MyObject> {

	@Override
	public boolean isWriteable(final Class<?> type, final Type type1, final Annotation[] antns, final MediaType mt) {
	    return MyObject.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(final MyObject t, final Class<?> type, final Type type1, final Annotation[] antns, final MediaType mt) {
	    // As of JAX-RS 2.0, the method has been deprecated and the
	    // value returned by the method is ignored by a JAX-RS runtime.
	    // All MessageBodyWriter implementations are advised to return -1 from
	    // the method.
	    return -1;
	}

	@Override
	public void writeTo(MyObject t, //
		final Class<?> type, //
		final Type type1, //
		final Annotation[] antns, //
		final MediaType mt, //
		final MultivaluedMap<String, Object> mm, //
		final OutputStream out) throws IOException, WebApplicationException {

	    JsonGenerator gen = Json.createGenerator(out);

	    gen.writeStartObject().write("name", t.getName()).write("age", t.getAge()).writeEnd();

	    gen.flush();
	}
    }

}
