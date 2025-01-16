package rest;

import entities.Evaluation;
import entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import service.EvaluationService;
import service.UserService;

import java.util.List;

@Path("/evaluations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Evaluation Management", description = "APIs for managing evaluations.")
public class EvaluationResource {

    @Inject
    private EvaluationService evaluationService;

    @Inject
    private UserService userService;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/all")
    @RolesAllowed({"admin"})
    @Operation(summary = "Get all evaluations", description = "Retrieve all evaluations stored in the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved evaluations.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluation.class))),
            @ApiResponse(responseCode = "403", description = "Access denied."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response getAllEvaluations() {
        List<Evaluation> evaluations = evaluationService.findAllEvaluations();
        return Response.ok(evaluations).build();
    }

    @POST
    @RolesAllowed("student")
    @Operation(summary = "Add a new evaluation", description = "Add a new evaluation to the database.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evaluation successfully created.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluation.class),
                            examples = @ExampleObject(value = """
                                {
                                    "student": { "id": 1 },
                                    "teacher": { "id": 2 },
                                    "grade": 8,
                                    "registrationNumber": "reg123",
                                    "timestamp": "2024-11-28T10:00:00"
                                }
                                """))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload.")
    })
    public Response addEvaluation(Evaluation evaluation) {
        evaluationService.submitEvaluation(evaluation);
        return Response.status(Response.Status.CREATED).entity(evaluation).build();
    }

    @GET
    @Path("/byTeacher/{teacherId}")
    @RolesAllowed("teacher")
    @Operation(summary = "Get evaluations by teacher", description = "Retrieve evaluations associated with a specific teacher.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved evaluations.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluation.class))),
            @ApiResponse(responseCode = "403", description = "Access denied."),
            @ApiResponse(responseCode = "404", description = "Teacher not found.")
    })
    public Response getEvaluationsByTeacher(@PathParam("teacherId") Long teacherId) {
        User teacher = userService.findById(teacherId);
        if (teacher == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Evaluation> evaluations = evaluationService.getEvaluationsForTeacher(teacher);
        return Response.ok(evaluations).build();
    }
}
