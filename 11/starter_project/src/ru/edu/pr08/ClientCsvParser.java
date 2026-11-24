package ru.edu.pr08;

public class ClientCsvParser {
    public ClientDraft parse(String line, int lineNumber) {
        if (line == null) {
            throw new IllegalArgumentException("Пустая строка");
        }

        String[] fields = line.split(";", -1);
        if (fields.length != 3) {
            throw new IllegalArgumentException(
                "Ожидается 3 поля: ИНН;название;email"
            );
        }

        return new ClientDraft(
            fields[0].trim(),
            fields[1].trim(),
            fields[2].trim()
        );
    }
}
