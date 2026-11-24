Практическая работа №11
Файлы, исключения и устойчивый пакетный импорт


Windows: запустите run_selfcheck.bat из этой папки.
Командная строка: javac -encoding UTF-8 -d out (список всех .java файлов), затем java -cp out ru.edu.pr08.SelfCheck.

Импорт читает CSV через Files.newBufferedReader и try-with-resources.
Ошибка открытия файла передаётся вызывающему коду как IOException.
Ошибки отдельных строк записываются в ImportResult, после чего цикл продолжается.
Повторный ИНН отклоняется.
