@echo off
cd /d "%~dp0"
if exist out rmdir /s /q out
mkdir out
javac -encoding UTF-8 -d out src\ru\edu\pr09\*.java
if errorlevel 1 goto end
java -cp out ru.edu.pr09.SelfCheck
:end
pause
