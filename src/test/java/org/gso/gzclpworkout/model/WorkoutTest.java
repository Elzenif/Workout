package org.gso.gzclpworkout.model;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkoutTest {

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    private Workout workout;

    @Before
    public void setUp() {
        workout = new Workout();
        workout.setName("My workout");
        workout.setNWeeks(3);
    }

    @Test
    public void testWorkoutGeneration_BasicFields() {
        softly.assertThat(workout.getId()).isNull();
        softly.assertThat(workout.getName()).isEqualTo("My workout");
    }

    @Test
    public void testWorkoutGeneration_Weeks() {
        softly.assertThat(workout.getWeeks()).hasSize(3);
        softly.assertThat(workout.getWeeks()).extracting("id").containsOnlyNulls();
        softly.assertThat(workout.getWeeks()).extracting("workout").containsOnly(workout);
        softly.assertThat(workout.getWeeks()).extracting("number").containsExactly(1, 2, 3);
        softly.assertThat(workout.getWeeks()).allSatisfy(week -> assertThat(week.getDays()).isNull());
    }

}