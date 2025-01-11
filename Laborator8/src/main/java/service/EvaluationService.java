package service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import cdi.Logged;
import entities.Evaluation;
import entities.User;
import filters.CacheFilter;

import java.util.List;

@ApplicationScoped
@Logged
public class EvaluationService implements EvaluationServiceInterface {

    @PersistenceContext(unitName = "EvaluationPU")
    private EntityManager entityManager;

    /**
     * Persists the given evaluation entity into the database and resets the cache.
     *
     * @param evaluation the evaluation to persist
     */
    @Transactional
    public void submitEvaluation(Evaluation evaluation) {
        entityManager.persist(evaluation);
        CacheFilter.resetCache(); // Clear the cache when an evaluation is submitted
    }

    /**
     * Finds evaluations for a specific teacher.
     *
     * @param teacher the teacher entity
     * @return a list of evaluations associated with the teacher
     */
    public List<Evaluation> getEvaluationsForTeacher(User teacher) {
        TypedQuery<Evaluation> query = entityManager.createQuery(
                "SELECT e FROM Evaluation e WHERE e.teacher = :teacher", Evaluation.class);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

    /**
     * Retrieves all evaluations from the database.
     *
     * @return a list of all evaluations
     */
    public List<Evaluation> findAllEvaluations() {
        return entityManager.createQuery("SELECT e FROM Evaluation e", Evaluation.class).getResultList();
    }

    /**
     * Updates the given evaluation entity in the database and resets the cache.
     *
     * @param evaluation the evaluation to update
     */
    @Transactional
    public void updateEvaluation(Evaluation evaluation) {
        entityManager.merge(evaluation);
        CacheFilter.resetCache(); // Clear the cache when an evaluation is updated
    }

    /**
     * Deletes an evaluation with the given ID from the database and resets the cache.
     *
     * @param id the ID of the evaluation to delete
     */
    @Transactional
    public void deleteEvaluation(Long id) {
        Evaluation evaluation = findById(id);
        if (evaluation != null) {
            entityManager.remove(evaluation);
            CacheFilter.resetCache(); // Clear the cache when an evaluation is deleted
        }
    }

    /**
     * Finds an evaluation by its ID.
     *
     * @param id the ID of the evaluation
     * @return the evaluation entity, or null if not found
     */
    public Evaluation findById(Long id) {
        return entityManager.find(Evaluation.class, id);
    }
}
