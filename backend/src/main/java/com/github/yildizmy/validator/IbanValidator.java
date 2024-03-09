package com.github.yildizmy.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.yildizmy.common.Constants.*;

/**
 * Used for validating IBAN numbers
 */
@Slf4j(topic = "IbanValidator")
@RequiredArgsConstructor
@Component
public class IbanValidator implements ConstraintValidator<ValidIban, String> {

    @Override
    public boolean isValid(String iban, ConstraintValidatorContext context) {
        String trimmed = iban.trim();
        if (trimmed.length() < IBAN_MIN_SIZE || trimmed.length() > IBAN_MAX_SIZE) {
            return false;
        }
        String reformat = trimmed.substring(4) + trimmed.substring(0, 4);
        long total = 0;

        for (int i = 0; i < reformat.length(); i++) {
            int charValue = Character.getNumericValue(reformat.charAt(i));

            if (charValue < 0 || charValue > 35)
                return false;

            total = (charValue > 9 ? total * 100 : total * 10) + charValue;

            if (total > IBAN_MAX)
                total = (total % IBAN_MODULUS);
        }
        return (total % IBAN_MODULUS) == 1;
    }
}
