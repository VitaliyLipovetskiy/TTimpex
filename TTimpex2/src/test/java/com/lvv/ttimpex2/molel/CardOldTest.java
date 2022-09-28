package com.lvv.ttimpex2.molel;

import com.lvv.ttimpex2.molel.old.CardOld;
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
public class CardOldTest {
//    private static Validator validator;
//
//    @BeforeClass
//    public static void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//    @Test
//    public void idIsNull() {
//        CardOld cardOld = new CardOld(null, "Ivan", "Ivan", "Ivan", "0");
//
//        Set<ConstraintViolation<CardOld>> constraintViolations = validator.validate(cardOld);
//
//        assertEquals(1, constraintViolations.size());
//        assertEquals("не должно равняться null", constraintViolations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void idTooShort() {
//        CardOld cardOld = new CardOld("1", "Ivan", "Ivan", "Ivan", "0");
//
//        Set<ConstraintViolation<CardOld>> constraintViolations = validator.validate(cardOld);
//
//        assertEquals(1, constraintViolations.size());
//        assertEquals("размер должен находиться в диапазоне от 4 до 4", constraintViolations.iterator().next().getMessage());
//    }
//
//    @Test
//    public void idIsValid() {
//        CardOld cardOld = new CardOld("0001", "Ivan", "Ivan", "Ivan", "0");
//
//        Set<ConstraintViolation<CardOld>> constraintViolations = validator.validate(cardOld);
//
//        assertEquals(0, constraintViolations.size());
//    }

}