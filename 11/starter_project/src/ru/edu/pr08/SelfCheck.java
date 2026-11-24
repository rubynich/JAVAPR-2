package ru.edu.pr08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SelfCheck {
    private static int passed;
    private static int failed;

    public static void main(String[] args) throws IOException {
        check("корректный ИНН из 10 цифр",
            new ClientValidator().validate(
                new ClientDraft("7701234567", "Alpha", "a@b.ru")
            ).isEmpty());

        check("корректный ИНН из 12 цифр",
            new ClientValidator().validate(
                new ClientDraft("123456789012", "Beta", "b@c.ru")
            ).isEmpty());

        check("неверная длина ИНН",
            !new ClientValidator().validate(
                new ClientDraft("123", "Name", "a@b.ru")
            ).isEmpty());

        check("пустое название",
            !new ClientValidator().validate(
                new ClientDraft("7701234567", " ", "a@b.ru")
            ).isEmpty());

        check("email без @",
            !new ClientValidator().validate(
                new ClientDraft("7701234567", "Name", "ab.ru")
            ).isEmpty());

        check("email с двумя @",
            !new ClientValidator().validate(
                new ClientDraft("7701234567", "Name", "a@@b.ru")
            ).isEmpty());

        ClientRepository repository = new ClientRepository();
        Path file = Files.createTempFile("clients-check", ".csv");
        try {
            Files.writeString(file,
                "inn;name;email\n"
                + "7701234567;Alpha;alpha@example.ru\n"
                + "123;Bad;bad@example.ru\n"
                + "7701234567;Duplicate;dup@example.ru\n"
                + "123456789012;Beta;beta@example.ru\n");

            ImportResult result = new ClientImporter(repository).importFile(file);
            check("пакетный импорт принимает две корректные записи",
                result.accepted() == 2);
            check("ошибочные строки не останавливают импорт",
                result.rejected() == 2);
            check("повторный ИНН отклоняется",
                result.errors().stream().anyMatch(
                    e -> e.message().contains("уже существует")));
            check("в репозитории две записи", repository.size() == 2);
        } finally {
            Files.deleteIfExists(file);
        }

        System.out.println();
        System.out.println("ИТОГО: " + passed + " пройдено, " + failed + " ошибок");
        if (failed > 0) System.exit(1);
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("PASS " + name);
        } else {
            failed++;
            System.out.println("FAIL " + name);
        }
    }
}
