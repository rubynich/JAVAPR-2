package ru.edu.pr08;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    public List<String> validate(ClientDraft draft) {
        List<String> errors = new ArrayList<>();

        if (draft.inn() == null || !draft.inn().matches("\\d{10}|\\d{12}")) {
            errors.add("ИНН должен содержать 10 или 12 цифр");
        }

        if (draft.name() == null || draft.name().isBlank()) {
            errors.add("Название клиента не должно быть пустым");
        }

        if (!isValidEmail(draft.email())) {
            errors.add("Email должен содержать один символ @ и непустые части слева и справа");
        }

        return errors;
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        int at = email.indexOf('@');
        return at > 0
            && at == email.lastIndexOf('@')
            && at < email.length() - 1;
    }
}
