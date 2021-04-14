package com.nilcaream.activities;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static org.assertj.core.api.Assertions.assertThat;

class DefinitionsReaderTest {

    private DefinitionsReader underTest = new DefinitionsReader();

    @Test
    void shouldReadTestFile() throws IOException {
        // give
        File file = new File("src/test/resources/definitions.txt");

        // when
        List<Definition> actual = underTest.load(file);

        // then
        assertThat(actual).hasSize(3);

        assertThat(actual.get(0).getDaysOfWeek()).containsExactly(MONDAY, TUESDAY, WEDNESDAY);
        assertThat(actual.get(0).getFrom()).isEqualToIgnoringSeconds(LocalTime.of(7, 0));
        assertThat(actual.get(0).getTo()).isEqualToIgnoringSeconds(LocalTime.of(13, 0));
        assertThat(actual.get(0).getWindowTitle().pattern()).isEqualTo(".*test123.*");

        assertThat(actual.get(1).getDaysOfWeek()).containsExactly(SUNDAY);
        assertThat(actual.get(1).getFrom()).isEqualToIgnoringSeconds(LocalTime.of(0, 0));
        assertThat(actual.get(1).getTo()).isEqualToIgnoringSeconds(LocalTime.of(7, 45));
        assertThat(actual.get(1).getWindowTitle().pattern()).isEqualTo(".*");

        assertThat(actual.get(2).getDaysOfWeek()).containsExactly(TUESDAY, THURSDAY, SUNDAY);
        assertThat(actual.get(2).getFrom()).isEqualToIgnoringSeconds(LocalTime.of(21, 0));
        assertThat(actual.get(2).getTo()).isEqualToIgnoringSeconds(LocalTime.of(23, 59));
        assertThat(actual.get(2).getWindowTitle().pattern()).isEqualTo("EXACT");
    }
}