package compulsory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.laborator5.model.Client;

import java.time.LocalDateTime;

public class TestClientPersistence {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Laborator5PU");
        EntityManager em = emf.createEntityManager();

        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setStatus("Active");
        client.setAge(30);
        client.setStatus("Active");
        client.setLastModifiedBy("Andrei");
        client.setLastModifiedTimestamp(LocalDateTime.now());

        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
