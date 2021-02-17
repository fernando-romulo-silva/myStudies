package org.agoncal.fascicle.quarkus.http.jsonb.dflt;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/books")
public class BookResource {

    @Path("/string")
    @GET
    @Produces(APPLICATION_JSON)
    public String getBookAsString() {

	Book book = new Book().title("H2G2").price(12.5F).isbn("1-84023-742-2");
	Jsonb jsonb = JsonbBuilder.create();
	String json = jsonb.toJson(book);

	return json;
    }

    @Path("/stringresp")
    @GET
    @Produces(APPLICATION_JSON)
    public Response getBookAsResponseString() {

	Book book = new Book().title("H2G2").price(12.5F).isbn("1-84023-742-2");
	Jsonb jsonb = JsonbBuilder.create();
	String json = jsonb.toJson(book);

	return Response.ok(json).build();
    }

    @Path("/book")
    @GET
    @Produces(APPLICATION_JSON)
    public Book getBook() {

	Book book = new Book().title("H2G2").price(12.5F).isbn("1-84023-742-2");

	return book;
    }

    @Path("/bookresp")
    @GET
    @Produces(APPLICATION_JSON)
    public Response getBookAsResponse() {

	Book book = new Book().title("H2G2").price(12.5F).isbn("1-84023-742-2");

	return Response.ok(book).build();
    }
}
