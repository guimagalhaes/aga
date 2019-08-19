#! /bin/sh

echo Compiling *.java
javac -cp "./libs/jdom.jar:./libs/crimson.jar:./libs/jaxp.jar" *.java

if [ $? = 1 ]; then
    exit -1;
fi

echo Creating jar file
jar cf aga.jar *.class

echo Moving jar
#mv aga.jar /var/www/html/aga

echo Cleaning up
rm -f *.class

echo Done
