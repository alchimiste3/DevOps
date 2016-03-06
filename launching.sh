#!/bin/bash
pathOfProject="/home/user/DevOps/DevOps/SourcesUnderTest/"
pathOfPom="/home/user/DevOps/DevOps/SourcesUnderTest/pom.xml"
pathOfSurefireReports="/home/user/DevOps/DevOps/SourcesUnderTest/target/surefire-reports/"
pathOfResultsDirectory="/home/user/DevOps/DevOps/results/"
pathOfXMLMutant="/home/user/DevOps/DevOps/Mutator/listeMutant.xml"
pathOfXMLConf="/home/user/DevOps/DevOps/conf.xml"

nameOfResultFile="result.html"

#removing last test
rm -f ./results/result.html
#creating dependency
cd ./Mutator
mvn clean install

mvn exec:java -Dexec.mainClass=analyseur.main.MainInitiale -Dexec.args="$pathOfResultsDirectory $nameOfResultFile $pathOfXMLMutant"

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
	mvn exec:java -Dexec.mainClass=analyseur.main.MainAnalyseur -Dexec.args="$pathOfSurefireReports $pathOfResultsDirectory $nameOfResultFile $nameOfTest"
done

mvn exec:java -Dexec.mainClass=analyseur.main.MainFinal -Dexec.args="$pathOfResultsDirectory $nameOfResultFile $pathOfXMLMutant $pathOfXMLConf"
