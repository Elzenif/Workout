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
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString(exclude = "routines")
@Entity
@Table(name = "day", uniqueConstraints = @UniqueConstraint(name = "UK_day__week_id__number",
        columnNames = {"week_id", "number"}))
public class Day {

    @Setter(AccessLevel.PRIVATE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "week_id", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_day__week_id"))
    private Week week;

    @Column(nullable = false)
    private Integer number;

    @OneToMany(mappedBy = "day", orphanRemoval = true)
    private List<Routine> routines;

}
