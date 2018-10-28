package org.gso.gzclpworkout.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Workout exercise
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Exercise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(nullable = false, unique = true)
    private String name;
    /**
     * A Primary exercise is an alternating T1/T2. Other T2 and T3 are secondary
     */
    private boolean isPrimary = false;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String description;

}
