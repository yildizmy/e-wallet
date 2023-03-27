package com.github.yildizmy.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for listing validation error messages
 */
public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }
}
