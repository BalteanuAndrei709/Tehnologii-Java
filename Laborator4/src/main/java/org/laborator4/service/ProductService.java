package org.laborator4.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.laborator4.database.DatabaseConnector;
import org.laborator4.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ProductService {

    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";

    // Injecting DatabaseConnector to manage connections
    @Inject
    private DatabaseConnector databaseConnector;

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = Product.builder()
                        .product_id(resultSet.getInt("product_id"))
                        .product_name(resultSet.getString("product_name"))
                        .description(resultSet.getString("description"))
                        .quantity(resultSet.getInt("quantity"))
                        .build();

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving products from the database.", e);
        }
        return products;
    }

    public String goToClients() {
        return "clients";
    }
}
