package org.gso.gzclpworkout.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer sets;
    private Integer reps;
    private String description;
    @ManyToOne
    @JoinColumn(name = "exercise_id", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_routine__exercise_id"))
    private Exercise exercise;
    @ManyToOne
    @JoinColumn(name = "day_id", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_routine__day_id"))
    private Day day;

}
