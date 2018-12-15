package org.gso.gzclpworkout.repository;

import org.gso.gzclpworkout.model.Workout;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringCacheAnnotationsOnInterfaceInspection")
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @NotNull
    @Override
    @Cacheable("allWorkouts")
    List<Workout> findAll();

    @NotNull
    @Override
    @Cacheable("allWorkouts")
    Optional<Workout> findById(@NotNull Long id);

    @NotNull
    @Override
    @CachePut("allWorkouts")
    <S extends Workout> S save(@NotNull S entity);
}
