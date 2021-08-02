package com.apress.springbootrecipes.calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class CalculatorApplicationTests {

    @Autowired
    private Calculator calculator;

    @Test
    public void doingMultiplicationShouldSucceed(final CapturedOutput output) {
	calculator.calculate(12, 13, '*');

	assertThat(output).contains("12 * 13 = 156");
    }

    @Test
    public void doingAdditionShouldSucceed(final CapturedOutput output) {
	calculator.calculate(12, 13, '+');
	assertThat(output).contains("12 + 13 = 25");
    }

    @Test
    public void doingDivisionShouldFail() {

	assertThatThrownBy(() -> {

	    calculator.calculate(12, 13, '/');

	}).isInstanceOf(IllegalArgumentException.class);
    }

}
