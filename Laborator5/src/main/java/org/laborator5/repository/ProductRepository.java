package org.laborator5.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.laborator5.model.Product;

import java.util.List;

public class ProductRepository {

    @PersistenceContext
    public EntityManager em;

    @Transactional
    public void save(Product product) {
        em.persist(product);
    }

    public Product findById(Integer id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createNamedQuery("Product.findAll", Product.class).getResultList();
    }

    @Transactional
    public void update(Product product) {
        em.merge(product);
    }

    @Transactional
    public void delete(Integer id) {
        Product product = findById(id);
        if (product != null) {
            em.remove(product);
        }
    }
}

