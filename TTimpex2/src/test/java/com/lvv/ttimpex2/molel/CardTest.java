package com.lvv.ttimpex2.molel;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Vitalii Lypovetskyi
 */
public class CardTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void idIsNull() {
        Card card = new Card(null, "Ivan", "Ivan", "Ivan", "0");

        Set<ConstraintViolation<Card>> constraintViolations = validator.validate(card);

        assertEquals(1, constraintViolations.size());
        assertEquals("не должно равняться null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void idTooShort() {
        Card card = new Card("1", "Ivan", "Ivan", "Ivan", "0");

        Set<ConstraintViolation<Card>> constraintViolations = validator.validate(card);

        assertEquals(1, constraintViolations.size());
        assertEquals("размер должен находиться в диапазоне от 4 до 4", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void idIsValid() {
        Card card = new Card("0001", "Ivan", "Ivan", "Ivan", "0");

        Set<ConstraintViolation<Card>> constraintViolations = validator.validate(card);

        assertEquals(0, constraintViolations.size());
    }

}