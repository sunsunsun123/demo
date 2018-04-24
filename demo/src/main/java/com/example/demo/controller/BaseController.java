package com.example.demo.controller;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class BaseController {

    /**
     *
     * @param t
     * @param <T>
     * @throws Exception
     */
    protected  <T> void validateModel(T t) throws Exception {
        List<String> validateError = new ArrayList<>();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        for (ConstraintViolation<T> c : constraintViolations) {
            validateError.add(c.getMessage());
        }

        if (!validateError.isEmpty()) {
            throw new Exception(validateError.get(0));
        }
    }
}
