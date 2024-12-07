package org.laborator7.cdi;

import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;
import org.laborator7.entities.Evaluation;
import org.laborator7.service.EvaluationServiceInterface;

import java.time.LocalDateTime;

@Decorator
@WithinSubmissionTime
public class SubmissionTimeValidator implements EvaluationServiceInterface {

    @Inject
    @Delegate
    private EvaluationServiceInterface evaluationService;

    private final LocalDateTime startTime = LocalDateTime.of(2024, 11, 28, 8, 0); // Start time
    private final LocalDateTime endTime = LocalDateTime.of(2024, 11, 28, 20, 0);  // End time

    @Override
    public void submitEvaluation(Evaluation evaluation) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            throw new IllegalStateException("Submissions are only allowed between " + startTime + " and " + endTime);
        }
        evaluationService.submitEvaluation(evaluation);
    }
}
