package org.gso.gzclpworkout.repository;

import org.gso.gzclpworkout.model.Exercise;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

@SuppressWarnings("SpringCacheAnnotationsOnInterfaceInspection")
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @NonNull
    @Override
    @Cacheable("allExercises")
    List<Exercise> findAll();

    @Cacheable("allExercises")
    List<Exercise> findByNameContaining(String name);

    @NotNull
    @Override
    @CachePut("allExercises")
    <S extends Exercise> S save(@NotNull S s);

    @Override
    @CacheEvict("allExercises")
    void delete(@NotNull Exercise exercise);
}
