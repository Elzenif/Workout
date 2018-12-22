package org.gso.gzclpworkout.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Getter
@Setter
@Builder()
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString(exclude = "days")
@Entity
@Table(name = "week", uniqueConstraints = @UniqueConstraint(name = "UK_week__workout_id__number",
        columnNames = {"workout_id", "number"}))
public class Week {

    @Setter(AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workout_id", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_week__workout_id"))
    private Workout workout;

    @Column(nullable = false)
    private Integer number;

    @OneToMany(mappedBy = "week", orphanRemoval = true)
    private List<Day> days;

    public static Week buildWeek(Workout workout, int weekNumber) {
        return Week.builder()
                .workout(workout)
                .number(weekNumber)
                .build();
    }
}

