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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "week", uniqueConstraints = @UniqueConstraint(name = "UK_week__workout_id__number",
        columnNames = {"workout_id", "number"}))
public class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "workout_id", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_week__workout_id"))
    private Workout workout;
    private Integer number;
    @OneToMany(mappedBy = "week")
    private List<Day> days;

}

