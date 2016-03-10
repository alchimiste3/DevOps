#!/bin/bash

#path of the directory where are Mutator and the project you want to test
pathOfDirectory="/home/user/DevOps/DevOps/"
#name of your project
nameOfProject="SourcesUnderTest/"

#do not modify below
pathOfProject=$pathOfDirectory$nameOfProject
pathOfPom=$pathOfProject"pom.xml"
pathOfSurefireReports=$pathOfProject"target/surefire-reports/"
pathOfResultsDirectory=$pathOfDirectory"results/"
pathOfXMLMutant=$pathOfDirectory"Mutator/listeMutant.xml"
pathOfXMLConf=$pathOfDirectory"conf.xml"
pathOfProcTxt=$pathOfDirectory"processors.txt"
nameOfResultFile="result.html"

#removing last test
rm -f ./results/result.html
#creating dependency
cd ./Mutator
mvn clean install

mvn exec:java -Dexec.mainClass=analyseur.main.MainInitiale -Dexec.args="$pathOfResultsDirectory $nameOfResultFile $pathOfXMLMutant $pathOfXMLConf $pathOfProcTxt"

#launching tests with one processor
for (( i=1; i<=$(wc -l < ../processors.txt) ; i++ ))
do
	#get the i-th line of processors.txt
	paths=$(sed -n "$((i))p;" < ../processors.txt)
	#defining the name of the sery of tests 
	nameOfTest=$( echo "$paths" | tr ' ' '+')

	#modifying pom
	mvn -q exec:java -Dexec.mainClass=modificateurPom.main.Main -Dexec.args="$pathOfPom $paths"


	cd ../SourcesUnderTest
	#compiling
	mvn compile
	if [ $? -eq 0 ] 
	then
		#launching test
		mvn -q -e clean test
		
		#analysing
		cd ../Mutator
		mvn exec:java -Dexec.mainClass=analyseur.main.MainAnalyseur -Dexec.args="$pathOfSurefireReports $pathOfResultsDirectory $nameOfResultFile $nameOfTest"
	
	else 
		cd ../Mutator
		mvn exec:java -Dexec.mainClass=analyseur.main.MainMortsNes -Dexec.args="$pathOfSurefireReports $pathOfResultsDirectory $nameOfResultFile $nameOfTest"
	fi
done

mvn exec:java -Dexec.mainClass=analyseur.main.MainFinal -Dexec.args="$pathOfResultsDirectory $nameOfResultFile $pathOfXMLMutant $pathOfXMLConf"

