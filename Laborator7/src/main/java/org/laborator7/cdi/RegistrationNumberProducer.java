package org.laborator7.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.UUID;

@ApplicationScoped
public class RegistrationNumberProducer {

    @Produces
    @RegistrationNumber
    public String generateRegistrationNumber() {
        return UUID.randomUUID().toString();
    }
}
