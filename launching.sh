#!/bin/bash

#removing last test
rm -f ./results/result.html
#creating dependency
cd ./Mutator
mvn clean install

#launching tests with one processor
for (( i=0; i<=$(wc -l < ../processors.txt) ; i++ ))
do
	paths=$(sed -n "$((i+1))p;" < ../processors.txt)
	#modifying pom
	mvn exec:java -Dexec.mainClass=modificateurPom.main.Main -Dexec.args="$paths"

	#launching test
	cd ../SourcesUnderTest
	mvn clean test

	#analysing
	cd ../Mutator
	mvn exec:java -Dexec.mainClass=analyseur.main.Main -Dexec.args="../SourcesUnderTest/target/surefire-reports/ ../results/ result.html"
done