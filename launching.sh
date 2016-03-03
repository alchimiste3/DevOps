#!/bin/bash
#processors to be applied
paths=(PlusMinus GELE IncDec);
rm -f ./results/result.html
#creating dependency
cd ./Mutator

mvn clean install
mvn exec:java -Dexec.mainClass=modificateurPom.main.Main -Dexec.args="PlusMinus"

#launching test
cd ../SourcesUnderTest
mvn clean test

cd ../Mutator
mvn exec:java -Dexec.mainClass=analyseur.main.Main -Dexec.args="../SourcesUnderTest/target/surefire-reports/ ../results/ result.html"

#launching tests with one processor
#for p in ${!paths[@]}  
#do
#	mvn -q clean test -Dp_chemin_1=processor."${paths[p]}"
#	cd ../Mutator
#	mvn -q exec:java -Dexec.mainClass="main.Main" -Dnom_fichier="${paths[p]}"
#done