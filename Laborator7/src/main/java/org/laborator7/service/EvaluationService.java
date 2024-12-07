package org.laborator7.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.laborator7.cdi.Logged;
import org.laborator7.entities.Evaluation;
import org.laborator7.entities.User;

import java.util.List;

@ApplicationScoped
@Logged
public class EvaluationService implements EvaluationServiceInterface  {

    @PersistenceContext(unitName = "EvaluationPU")
    private EntityManager entityManager;

    /**
     * Saves the evaluation entity to the database.
     *
     * @param evaluation the evaluation to persist
     */
    @Transactional
    public void submitEvaluation(Evaluation evaluation) {
        entityManager.persist(evaluation);
    }

    public List<Evaluation> getEvaluationsForTeacher(User teacher) {
        TypedQuery<Evaluation> query = entityManager.createQuery(
                "SELECT e FROM Evaluation e WHERE e.teacher = :teacher", Evaluation.class);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

    public List<Evaluation> findAllEvaluations() {
        return entityManager.createQuery("SELECT e FROM Evaluation e", Evaluation.class).getResultList();
    }
}
