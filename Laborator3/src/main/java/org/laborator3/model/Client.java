package org.laborator3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "clients", schema = "public")
@AllArgsConstructor
public class Client {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String status;
    private Integer age;
}
