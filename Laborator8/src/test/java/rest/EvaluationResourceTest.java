package rest;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import entities.Evaluation;
import service.EvaluationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvaluationResourceTest {

    @Mock
    private EvaluationService evaluationService;

    @InjectMocks
    private EvaluationResource evaluationResource;

    @Test
    public void testGetAllEvaluations() {
        // Arrange
        List<Evaluation> mockEvaluations = new ArrayList<>();
        mockEvaluations.add(new Evaluation()); // Add a dummy Evaluation object
        when(evaluationService.findAllEvaluations()).thenReturn(mockEvaluations);

        // Act
        Response response = evaluationResource.getAllEvaluations();

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(1, ((List<?>) response.getEntity()).size());
        verify(evaluationService, times(1)).findAllEvaluations(); // Corrected the method name
    }

    @Test
    public void testAddEvaluation() {
        // Arrange
        Evaluation evaluation = new Evaluation();

        // Act
        Response response = evaluationResource.addEvaluation(evaluation);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        verify(evaluationService, times(1)).submitEvaluation(evaluation); // Ensure the method was called once
    }

    @Test
    public void testUpdateEvaluation() {
        // Arrange
        Long evaluationId = 1L;
        Evaluation existingEvaluation = new Evaluation();
        existingEvaluation.setId(evaluationId);
        when(evaluationService.findById(evaluationId)).thenReturn(existingEvaluation);

        Evaluation updatedEvaluation = new Evaluation();
        updatedEvaluation.setGrade(9);
        updatedEvaluation.setRegistrationNumber("reg456");

        // Act
        Response response = evaluationResource.updateEvaluation(evaluationId, updatedEvaluation);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(evaluationService, times(1)).updateEvaluation(existingEvaluation);
    }

    @Test
    public void testUpdateEvaluationNotFound() {
        // Arrange
        Long evaluationId = 1L;
        when(evaluationService.findById(evaluationId)).thenReturn(null);

        Evaluation updatedEvaluation = new Evaluation();

        // Act
        Response response = evaluationResource.updateEvaluation(evaluationId, updatedEvaluation);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(evaluationService, never()).updateEvaluation(any());
    }

    @Test
    public void testDeleteEvaluation() {
        // Arrange
        Long evaluationId = 1L;
        Evaluation evaluation = new Evaluation();
        evaluation.setId(evaluationId);
        when(evaluationService.findById(evaluationId)).thenReturn(evaluation);

        // Act
        Response response = evaluationResource.deleteEvaluation(evaluationId);

        // Assert
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(evaluationService, times(1)).deleteEvaluation(evaluationId);
    }

    @Test
    public void testDeleteEvaluationNotFound() {
        // Arrange
        Long evaluationId = 1L;
        when(evaluationService.findById(evaluationId)).thenReturn(null);

        // Act
        Response response = evaluationResource.deleteEvaluation(evaluationId);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        verify(evaluationService, never()).deleteEvaluation(any());
    }
}
