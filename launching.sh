#!/bin/bash
pathOfProject="/home/sualty/Bureau/DEVOPS/V2/DevOps/SourcesUnderTest/"
pathOfPom="/home/sualty/Bureau/DEVOPS/V2/DevOps/SourcesUnderTest/pom.xml"
pathOfSurefireReports="/home/sualty/Bureau/DEVOPS/V2/DevOps/SourcesUnderTest/target/surefire-reports/"
pathOfResultsDirectory="/home/sualty/Bureau/DEVOPS/V2/DevOps/results/"
nameOfResultFile="result.html"

#removing last test
rm -f ./results/result.html
#creating dependency
cd ./Mutator
mvn -q clean install

#launching tests with one processor
for (( i=0; i<=$(wc -l < ../processors.txt) ; i++ ))
do
	#get the i-th line of processors.txt
	paths=$(sed -n "$((i+1))p;" < ../processors.txt)
	#defining the name of the sery of tests 
	nameOfTest=$( echo "$paths" | tr ' ' '+')

	#modifying pom
	mvn -q exec:java -Dexec.mainClass=modificateurPom.main.Main -Dexec.args="$pathOfPom $paths"

	#launching test
	cd ../SourcesUnderTest
	mvn -q clean test

	#analysing
	cd ../Mutator
	mvn -q exec:java -Dexec.mainClass=analyseur.main.Main -Dexec.args="$pathOfSurefireReports $pathOfResultsDirectory $nameOfResultFile $nameOfTest"
done