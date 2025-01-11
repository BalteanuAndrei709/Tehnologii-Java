package web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import entities.Evaluation;
import service.EvaluationService;

import java.util.List;

@Getter
@Setter
@Named
@RequestScoped
public class AdminBean {

    @Inject
    private EvaluationService evaluationService;

    private List<Evaluation> evaluations; // List of all evaluations
    private int totalEvaluations;         // Total number of evaluations
    private double averageGrade;          // Average grade
    private int highestGrade;             // Highest grade
    private int lowestGrade;              // Lowest grade

    public List<Evaluation> getEvaluations() {
        if (evaluations == null) {
            evaluations = evaluationService.findAllEvaluations();
        }
        return evaluations;
    }

    public int getTotalEvaluations() {
        return getEvaluations().size();
    }

    public double getAverageGrade() {
        return getEvaluations().stream()
                .mapToInt(Evaluation::getGrade)
                .average()
                .orElse(0.0);
    }

    public int getHighestGrade() {
        return getEvaluations().stream()
                .mapToInt(Evaluation::getGrade)
                .max()
                .orElse(0);
    }

    public int getLowestGrade() {
        return getEvaluations().stream()
                .mapToInt(Evaluation::getGrade)
                .min()
                .orElse(0);
    }
}
