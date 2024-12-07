package homework;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.laborator5.model.Client;
import org.laborator5.repository.ClientRepository;

import java.util.List;

public class ClientRepositoryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private ClientRepository clientRepository;

    @BeforeAll
    static void setup() {
        emf = Persistence.createEntityManagerFactory("Laborator5PU");
    }

    @BeforeEach
    void init() {
        em = emf.createEntityManager();
        clientRepository = new ClientRepository();
        clientRepository.em = em;
    }

    @AfterEach
    void cleanup() {
        em.close();
    }

    @AfterAll
    static void teardown() {
        emf.close();
    }

    @Test
    void testSaveAndFind() {
        Client client = new Client();
        client.setFirstName("Jane");
        client.setLastName("Doe");
        client.setStatus("Active");
        client.setAge(28);
        client.setLastModifiedBy("TestUser");
        client.setLastModifiedTimestamp(java.time.LocalDateTime.now());

        em.getTransaction().begin();
        clientRepository.save(client);
        em.getTransaction().commit();

        Client found = clientRepository.findById(client.getId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("Jane", found.getFirstName());
    }

    @Test
    void testFindAll() {
        Client client1 = new Client();
        client1.setFirstName("Alice");
        client1.setLastName("Smith");
        client1.setStatus("Inactive");
        client1.setAge(35);
        client1.setLastModifiedBy("TestUser");
        client1.setLastModifiedTimestamp(java.time.LocalDateTime.now());

        Client client2 = new Client();
        client2.setFirstName("Bob");
        client2.setLastName("Johnson");
        client2.setStatus("Active");
        client2.setAge(42);
        client2.setLastModifiedBy("TestUser");
        client2.setLastModifiedTimestamp(java.time.LocalDateTime.now());

        em.getTransaction().begin();
        clientRepository.save(client1);
        clientRepository.save(client2);
        em.getTransaction().commit();

        List<Client> clients = clientRepository.findAll();
        Assertions.assertTrue(clients.size() >= 2);
    }

    @Test
    void testUpdate() {
        Client client = new Client();
        client.setFirstName("Charlie");
        client.setLastName("Brown");
        client.setStatus("Active");
        client.setAge(30);
        client.setLastModifiedBy("TestUser");
        client.setLastModifiedTimestamp(java.time.LocalDateTime.now());

        em.getTransaction().begin();
        clientRepository.save(client);
        em.getTransaction().commit();

        Client savedClient = clientRepository.findById(client.getId());
        Assertions.assertNotNull(savedClient);

        // Update the client's details
        savedClient.setLastName("UpdatedBrown");
        savedClient.setStatus("Inactive");

        em.getTransaction().begin();
        clientRepository.update(savedClient);
        em.getTransaction().commit();

        Client updatedClient = clientRepository.findById(client.getId());
        Assertions.assertEquals("UpdatedBrown", updatedClient.getLastName());
        Assertions.assertEquals("Inactive", updatedClient.getStatus());
    }

    @Test
    void testDelete() {
        Client client = new Client();
        client.setFirstName("David");
        client.setLastName("Lee");
        client.setStatus("Active");
        client.setAge(40);
        client.setLastModifiedBy("TestUser");
        client.setLastModifiedTimestamp(java.time.LocalDateTime.now());

        em.getTransaction().begin();
        clientRepository.save(client);
        em.getTransaction().commit();

        Client savedClient = clientRepository.findById(client.getId());
        Assertions.assertNotNull(savedClient);

        // Delete the client
        em.getTransaction().begin();
        clientRepository.delete(client.getId());
        em.getTransaction().commit();

        Client deletedClient = clientRepository.findById(client.getId());
        Assertions.assertNull(deletedClient);
    }
}
