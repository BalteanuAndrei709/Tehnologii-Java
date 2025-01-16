package web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import entities.Evaluation;
import entities.User;
import service.EvaluationService;
import service.UserService;

import java.util.List;

@Getter
@Setter
@Named
@RequestScoped
public class TeacherBean {

    @Inject
    private UserService userService;

    @Inject
    private EvaluationService evaluationService;

    /**
     * Fetch evaluations for the logged-in teacher.
     *
     * @return List of evaluations for the teacher.
     */
    public List<Evaluation> getEvaluations() {
        User loggedInTeacher = userService.getLoggedInUser();
        return evaluationService.getEvaluationsForTeacher(loggedInTeacher);
    }
}
