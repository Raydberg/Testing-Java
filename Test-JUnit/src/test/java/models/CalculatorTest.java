package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math Populations class")
class CalculatorTest {

    @Test
    @DisplayName("Test 4/2 = 2")
    void integerDivision() {
        Calculator calculator = new Calculator();
        int result = calculator.integerDivision(4, 2);
        assertEquals(2, result);
    }

    @DisplayName("Test 4-2 = 2")
    @Test
    void integerSubtraction() {
        Calculator calculator = new Calculator();
        int result = calculator.integerSubtraction(4, 2);
        assertEquals(2, result);

    }
}