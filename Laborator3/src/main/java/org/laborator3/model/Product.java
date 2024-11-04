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
@Table(name = "products", schema = "public")
@AllArgsConstructor
public class Product {
    @Id
    private Integer product_id;
    private String product_name;
    private String description;
    private Integer quantity;
}
