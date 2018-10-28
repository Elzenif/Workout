package org.gso.gzclpworkout.model;

import lombok.*;

import javax.persistence.*;
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

}
