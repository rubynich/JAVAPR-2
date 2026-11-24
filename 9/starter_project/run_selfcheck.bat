@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist out mkdir out
echo Компиляция проекта...
javac -encoding UTF-8 -d out src\ru\edu\pr06\*.java
if errorlevel 1 (
  echo ОШИБКА КОМПИЛЯЦИИ
  pause
  exit /b 1
)
echo Запуск SelfCheck...
java -cp out ru.edu.pr06.SelfCheck
echo.
pause
