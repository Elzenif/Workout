package org.gso.gzclpworkout.repository;

import org.gso.gzclpworkout.model.Exercise;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

@SuppressWarnings("SpringCacheAnnotationsOnInterfaceInspection")
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    @NonNull
    @Override
//    @Cacheable("allExercises")
    List<Exercise> findAll();

//    @Cacheable("exercise")
    List<Exercise> findByNameContaining(String name);

    @NotNull
    @Override
//    @CacheEvict({"allExercises", "exercise"})
    <S extends Exercise> S save(@NotNull S s);

    @Override
//    @CacheEvict({"allExercises", "exercise"})
    void delete(@NotNull Exercise exercise);
}
