package org.laborator7.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false) // Foreign key column in the evaluation table
    private User student;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false) // Foreign key column in the evaluation table
    private User teacher;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private String registrationNumber;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
