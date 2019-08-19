@echo off

echo Compiling *.java
javac *.java

if errorlevel 1 goto exit

echo Creating jar file
jar cf aga.jar *.class

echo Moving jar
move aga.jar C:\WebSer~1\Apache~1\Apache\htdocs\aga

echo Cleaning up
del *.class

echo Done
:exit