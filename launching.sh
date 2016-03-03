#!/bin/bash
#processors to be applied : reading file processors.txt for getting them : USER FRIENDLY <3
value=$(<processors.txt)
echo "$value"
#TODO : IL FAUT MODIFIER LA BOUCLE POUR PLACER DANS LE TABLEAU PATH CE QU ON VEUT.UNE LIGNE
#DU FICHIER PROCESSORS CORRESPOND A UNE ITERATION .
paths=(PlusMinus GELE IncDec);
#removing last test
rm -f ./results/result.html
#creating dependency
cd ./Mutator
mvn clean install

#launching tests with one processor
for p in ${!paths[@]}  
do
	#modifying pom
	mvn exec:java -Dexec.mainClass=modificateurPom.main.Main -Dexec.args="${paths[p]}"

	#launching test
	cd ../SourcesUnderTest
	mvn clean test

	#analysing
	cd ../Mutator
	mvn exec:java -Dexec.mainClass=analyseur.main.Main -Dexec.args="../SourcesUnderTest/target/surefire-reports/ ../results/ result.html"
done