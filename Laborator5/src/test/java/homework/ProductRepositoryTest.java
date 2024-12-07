package homework;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import org.laborator5.model.Product;
import org.laborator5.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ProductRepositoryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private ProductRepository productRepository;

    @BeforeAll
    static void setup() {
        emf = Persistence.createEntityManagerFactory("Laborator5PU");
    }

    @BeforeEach
    void init() {
        em = emf.createEntityManager();
        productRepository = new ProductRepository();
        productRepository.em = em;
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
        Product product = new Product();
        product.setProduct_name("Phone");
        product.setDescription("Smartphone with 128GB storage");
        product.setQuantity(50);
        product.setLastModifiedBy("TestUser");
        product.setLastModifiedTimestamp(LocalDateTime.now());

        em.getTransaction().begin();
        productRepository.save(product);
        em.getTransaction().commit();

        Product found = productRepository.findById(product.getProduct_id());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("Phone", found.getProduct_name());
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setProduct_name("Laptop");
        product1.setDescription("High-performance laptop");
        product1.setQuantity(30);
        product1.setLastModifiedBy("TestUser");
        product1.setLastModifiedTimestamp(LocalDateTime.now());

        Product product2 = new Product();
        product2.setProduct_name("Headphones");
        product2.setDescription("Noise-canceling headphones");
        product2.setQuantity(100);
        product2.setLastModifiedBy("TestUser");
        product2.setLastModifiedTimestamp(LocalDateTime.now());

        em.getTransaction().begin();
        productRepository.save(product1);
        productRepository.save(product2);
        em.getTransaction().commit();

        List<Product> products = productRepository.findAll();
        Assertions.assertTrue(products.size() >= 2);
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        product.setProduct_name("Tablet");
        product.setDescription("10-inch tablet with 64GB storage");
        product.setQuantity(20);
        product.setLastModifiedBy("TestUser");
        product.setLastModifiedTimestamp(LocalDateTime.now());

        em.getTransaction().begin();
        productRepository.save(product);
        em.getTransaction().commit();

        Product savedProduct = productRepository.findById(product.getProduct_id());
        Assertions.assertNotNull(savedProduct);

        // Update the product's details
        savedProduct.setDescription("Updated tablet with 128GB storage");
        savedProduct.setQuantity(25);

        em.getTransaction().begin();
        productRepository.update(savedProduct);
        em.getTransaction().commit();

        Product updatedProduct = productRepository.findById(product.getProduct_id());
        Assertions.assertEquals("Updated tablet with 128GB storage", updatedProduct.getDescription());
        Assertions.assertEquals(25, updatedProduct.getQuantity());
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setProduct_name("Monitor");
        product.setDescription("27-inch 4K monitor");
        product.setQuantity(10);
        product.setLastModifiedBy("TestUser");
        product.setLastModifiedTimestamp(LocalDateTime.now());

        em.getTransaction().begin();
        productRepository.save(product);
        em.getTransaction().commit();

        Product savedProduct = productRepository.findById(product.getProduct_id());
        Assertions.assertNotNull(savedProduct);

        // Delete the product
        em.getTransaction().begin();
        productRepository.delete(product.getProduct_id());
        em.getTransaction().commit();

        Product deletedProduct = productRepository.findById(product.getProduct_id());
        Assertions.assertNull(deletedProduct);
    }
}
