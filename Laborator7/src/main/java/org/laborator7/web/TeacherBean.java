package org.laborator7.web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.laborator7.entities.Evaluation;
import org.laborator7.entities.User;
import org.laborator7.service.EvaluationService;
import org.laborator7.service.UserService;

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
