package org.gso.gzclpworkout.repository;

import org.gso.gzclpworkout.model.Workout;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @NotNull
    @Override
    @Transactional(readOnly = true)
    List<Workout> findAll();

    @NotNull
    @Override
    @Transactional(readOnly = true)
    Optional<Workout> findById(@NotNull Long id);

    @NotNull
    @Override
    @Transactional
    <S extends Workout> S save(@NotNull S entity);
}
