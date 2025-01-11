package rest;

import entities.Evaluation;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.RandomEvaluationService;

@Path("/random-evaluation")
public class RandomEvaluationResource {

    @Inject
    private RandomEvaluationService randomEvaluationService;

    @POST
    @Path("/{sentiment}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateRandomEvaluation(@PathParam("sentiment") String sentiment) {
        try {
            Evaluation evaluation = randomEvaluationService.generateRandomEvaluation(sentiment);
            return Response.ok(evaluation).build();
        } catch (IllegalStateException | IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
