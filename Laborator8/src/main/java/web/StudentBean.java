package web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import cdi.RegistrationNumber;
import entities.Evaluation;
import entities.User;
import service.EvaluationService;
import service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Named
@RequestScoped
public class StudentBean {

    @Inject
    private UserService userService;

    @Inject
    private EvaluationService evaluationService;

    @Inject
    @RegistrationNumber
    private String registrationNumber;
    private Long selectedTeacherId; // ID of the selected teacher
    private int grade;            // Grade entered by the student

    public List<User> getTeacherList() {
        return userService.findTeachers(); // Fetch all teachers
    }

    public void submitEvaluation() {
        User selectedTeacher = userService.findById(selectedTeacherId); // Resolve User by ID
        User student = userService.getLoggedInUser();

        Evaluation evaluation = new Evaluation();
        evaluation.setStudent(student);
        evaluation.setTeacher(selectedTeacher);
        evaluation.setGrade(grade);
        evaluation.setRegistrationNumber(registrationNumber);
        evaluation.setTimestamp(LocalDateTime.now());

        evaluationService.submitEvaluation(evaluation);
    }
}
