package org.gso.gzclpworkout.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * Workout entity
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "workout", uniqueConstraints = @UniqueConstraint(name = "UK_workout__name", columnNames = "name"))
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "workout")
    private List<Week> weeks;
}
