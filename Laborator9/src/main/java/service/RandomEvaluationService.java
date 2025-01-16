package service;

import entities.AcademicYear;
import entities.Course;
import entities.Evaluation;
import entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class RandomEvaluationService {

    @PersistenceContext(unitName = "EvaluationPU")
    private EntityManager entityManager;

    private final Random random = new Random();

    /**
     * Generates a random evaluation for a teacher and course with the given sentiment.
     *
     * @param sentiment The sentiment of the evaluation: "positive", "neutral", "negative".
     * @return The generated Evaluation.
     */
    @Transactional
    public Evaluation generateRandomEvaluation(String sentiment) {
        List<User> teachers = entityManager.createQuery("SELECT u FROM User u WHERE u.role = 'TEACHER'", User.class).getResultList();
        List<User> students = entityManager.createQuery("SELECT u FROM User u WHERE u.role = 'STUDENT'", User.class).getResultList();
        List<Course> courses = entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        List<AcademicYear> academicYears = entityManager.createQuery("SELECT a FROM AcademicYear a", AcademicYear.class).getResultList();

        if (teachers.isEmpty() || students.isEmpty() || courses.isEmpty() || academicYears.isEmpty()) {
            throw new IllegalStateException("Cannot generate evaluation: missing data.");
        }

        User randomTeacher = teachers.get(random.nextInt(teachers.size()));
        User randomStudent = students.get(random.nextInt(students.size()));
        Course randomCourse = courses.get(random.nextInt(courses.size()));
        AcademicYear randomYear = academicYears.get(random.nextInt(academicYears.size()));

        Evaluation evaluation = new Evaluation();
        evaluation.setStudent(randomStudent);
        evaluation.setTeacher(randomTeacher);
        evaluation.setCourse(randomCourse);
        evaluation.setAcademicYear(randomYear);
        evaluation.setGrade(generateGradeBasedOnSentiment(sentiment));
        evaluation.setRegistrationNumber("REG" + random.nextInt(10000));
        evaluation.setTimestamp(LocalDateTime.now());
        evaluation.setSentiment(sentiment);

        entityManager.persist(evaluation);
        return evaluation;
    }

    private int generateGradeBasedOnSentiment(String sentiment) {
        switch (sentiment.toLowerCase()) {
            case "positive":
                return random.nextInt(3) + 8; // Grades 8, 9, or 10
            case "neutral":
                return random.nextInt(3) + 5; // Grades 5, 6, or 7
            case "negative":
                return random.nextInt(5) + 1; // Grades 1 through 5
            default:
                throw new IllegalArgumentException("Invalid sentiment: " + sentiment);
        }
    }
}
