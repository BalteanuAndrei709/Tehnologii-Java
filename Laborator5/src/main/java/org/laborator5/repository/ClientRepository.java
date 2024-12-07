package org.laborator5.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.laborator5.model.Client;

import java.util.List;

public class ClientRepository {

    @PersistenceContext
    public EntityManager em;

    @Transactional
    public void save(Client client) {
        em.persist(client);
    }

    public Client findById(Integer id) {
        return em.find(Client.class, id);
    }

    public List<Client> findAll() {
        return em.createNamedQuery("Client.findAll", Client.class).getResultList();
    }

    @Transactional
    public void update(Client client) {
        em.merge(client);
    }

    @Transactional
    public void delete(Integer id) {
        Client client = findById(id);
        if (client != null) {
            em.remove(client);
        }
    }
}
