@echo off
if exist out rmdir /s /q out
mkdir out
for /R src\main\java %%f in (*.java) do echo %%f>>sources.txt
javac -d out @sources.txt
del sources.txt
if errorlevel 1 pause
if not errorlevel 1 java -cp out com.disaster.Main
