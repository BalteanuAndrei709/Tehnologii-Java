package org.laborator4.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.laborator4.database.DatabaseConnector;
import org.laborator4.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ClientService {

    @Getter
    @Setter
    private Client client = new Client();

    // Injecting DatabaseConnector to manage connections
    @Inject
    private DatabaseConnector databaseConnector;

    public String saveClient() {
        String sql = "INSERT INTO clients (firstName, lastName, status, age) VALUES (?, ?, ?, ?)";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getLastName());
            statement.setString(3, client.getStatus());
            statement.setInt(4, client.getAge());

            statement.executeUpdate();
            System.out.println("Client saved: " + client);

            // Reset client object after saving
            client = new Client();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving client to the database.", e);
        }

        return "clients"; // Redirect to the clients page
    }

    public String goToAddClient() {
        return "addClient";
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Client client = Client.builder()
                        .id(resultSet.getInt("id"))
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .status(resultSet.getString("status"))
                        .age(resultSet.getInt("age"))
                        .build();
                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving clients from the database.", e);
        }

        return clients;
    }
}
