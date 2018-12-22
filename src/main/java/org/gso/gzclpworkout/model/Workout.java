package org.gso.gzclpworkout.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Workout entity
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "weeks")
@Entity
@Table(name = "workout", uniqueConstraints = @UniqueConstraint(name = "UK_workout__name", columnNames = "name"))
public class Workout {

    @Setter(AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "workout", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Week> weeks;

    public void setNWeeks(Integer n) {
        setWeeks(IntStream.rangeClosed(1, n)
                .mapToObj(i -> Week.buildWeek(this, i))
                .collect(Collectors.toList()));
    }
}
