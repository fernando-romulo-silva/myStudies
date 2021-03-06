package org.jespanol.client.conference;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("conferences")
@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON+ "; charset=UTF-8")
public interface ConferenceService {

    @GET
    List<Conference> findAll();

    @GET
    @Path("{id}")
    Conference findById(@PathParam("id") String id);

    @PUT
    @Path("{id}")
    Conference update(@PathParam("id") String id, Conference conference);

    @DELETE
    @Path("{id}")
    Response remove(@PathParam("id") String id);

    @POST
    Conference insert(Conference conference);
}
