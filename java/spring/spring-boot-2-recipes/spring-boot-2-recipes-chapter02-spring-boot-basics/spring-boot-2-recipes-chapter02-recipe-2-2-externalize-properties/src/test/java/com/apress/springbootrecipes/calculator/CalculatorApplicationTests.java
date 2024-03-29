package com.apress.springbootrecipes.calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class CalculatorApplicationTests {

    @MockBean(name = "division")
    private Operation mockOperation;

    @Autowired
    private Calculator calculator;

    @Test
    public void calculatorShouldHave3Operations() {
	Object operations = ReflectionTestUtils.getField(calculator, "operations");

	assertThat((Collection<?>) operations).hasSize(3);
    }

    @Test
    public void mockDivision(final CapturedOutput output) {
	when(mockOperation.handles('/')).thenReturn(true);
	when(mockOperation.apply(14, 7)).thenReturn(2);

	calculator.calculate(14, 7, '/');

	assertThat(output).contains("14 / 7 = 2");
    }

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
