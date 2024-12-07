package compulsory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.laborator5.model.Product;

import java.time.LocalDateTime;

public class TestProductPersistence {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Laborator5PU");
        EntityManager em = emf.createEntityManager();

        Product product = new Product();
        product.setProduct_name("Laptop");
        product.setDescription("High-performance laptop with 16GB RAM and 1TB SSD.");
        product.setQuantity(10);
        product.setLastModifiedBy("Andrei");
        product.setLastModifiedTimestamp(LocalDateTime.now());

        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();

        em.close();
        emf.close();

        System.out.println("Product saved successfully!");
    }
}
