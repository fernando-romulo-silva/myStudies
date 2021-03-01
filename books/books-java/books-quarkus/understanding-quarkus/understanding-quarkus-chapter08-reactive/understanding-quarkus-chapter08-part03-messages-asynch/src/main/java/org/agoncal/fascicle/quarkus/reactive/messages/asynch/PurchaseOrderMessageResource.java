package org.agoncal.fascicle.quarkus.reactive.messages.asynch;

import org.agoncal.fascicle.quarkus.reactive.messages.asynch.model.PurchaseOrder;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Random;

/**
 * <pre>
 * curl -X POST -H "Content-Type: application/json" -d '{"id":"123"}' http://localhost:8080/po -v
 *     emitter.send(po).whenComplete((x,e) -> {
 *         if (e != null ) e.printStackTrace();
 *     });
 * </pre>
 */
@Path("/pomsg")
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PurchaseOrderMessageResource {

    private static final Logger LOGGER = Logger.getLogger(PurchaseOrderMessageResource.class);

    String tmpId = "tmp" + Math.abs(new Random().nextInt());

    @Inject
    @Channel("purchase-orders-msg")
    Emitter<Message<PurchaseOrder>> emitter;

    @POST
    public Response create(PurchaseOrder po) {

	LOGGER.info(">>>>>>>>>>>>");

	emitter.send(Message.of(po));

	URI temporaryPO = UriBuilder.fromResource(PurchaseOrderMessageResource.class).path(tmpId).build();

	LOGGER.info("<<<<<<<<<<<<");

	return Response.temporaryRedirect(temporaryPO).build();
    }
}
