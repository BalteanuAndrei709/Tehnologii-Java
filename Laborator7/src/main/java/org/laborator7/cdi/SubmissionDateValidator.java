package org.laborator7.cdi;

import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;
import org.laborator7.entities.Evaluation;
import org.laborator7.entities.User;
import org.laborator7.service.EvaluationServiceInterface;

@Decorator
public class SubmissionDateValidator implements EvaluationServiceInterface {

    @Inject
    @Delegate
    private EvaluationServiceInterface evaluationService;

    @Override
    public void submitEvaluation(Evaluation evaluation) {
        if (evaluation.getGrade() < 0 || evaluation.getGrade() > 10) {
            throw new IllegalArgumentException("Grade must be between 0 and 10.");
        }

        evaluationService.submitEvaluation(evaluation);
    }
}

