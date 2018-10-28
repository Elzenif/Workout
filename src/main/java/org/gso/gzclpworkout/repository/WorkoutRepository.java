package org.gso.gzclpworkout.repository;

import org.gso.gzclpworkout.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

}
