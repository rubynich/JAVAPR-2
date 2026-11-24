@echo off
cd /d "%~dp0"
if exist out rmdir /s /q out
mkdir out
javac -encoding UTF-8 -d out src\ru\edu\pr07\*.java
if errorlevel 1 goto error
java -cp out ru.edu.pr07.SelfCheck
echo.
pause