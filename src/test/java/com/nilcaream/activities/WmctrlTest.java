package com.nilcaream.activities;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WmctrlTest {

    private Wmctrl underTest = new Wmctrl();

    @Test
    void shouldReturnAnyKindOfData() {
        // when
        List<Window> actual = underTest.get();

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSizeGreaterThan(1);
        assertThat(actual.get(0).getPid()).isNotEqualTo(0);
        assertThat(actual.get(0).getTitle()).isNotBlank();
        assertThat(actual.get(0).getWid()).isNotBlank();
    }
}