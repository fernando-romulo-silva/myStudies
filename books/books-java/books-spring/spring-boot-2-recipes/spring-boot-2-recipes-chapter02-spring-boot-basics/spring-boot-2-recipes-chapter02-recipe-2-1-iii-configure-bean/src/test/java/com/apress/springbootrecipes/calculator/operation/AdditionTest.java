package com.apress.springbootrecipes.calculator.operation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class AdditionTest {

    private final Addition addition = new Addition();

    @Test
    public void shouldMatchPlusSign() {
        assertThat(addition.handles('+')).isTrue();
        assertThat(addition.handles('/')).isFalse();
    }

    @Test
    public void shouldCorrectlyApplyFormula() {
        assertThat(addition.apply(2, 2)).isEqualTo(4);
        assertThat(addition.apply(12, 88)).isEqualTo(100);
    }
}