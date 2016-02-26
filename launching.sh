#!/bin/bash
#processors to be applied
paths=(PlusMinus GELE IncDec);

#creating dependency
cd ./Mutator
mvn clean install

#launching test
cd ../SourcesUnderTest

#launching tests with one processor
for p in ${!paths[@]}  
do
	mvn -q clean test -Dp_chemin_1=processor."${paths[p]}"
	cd ../Mutator
	mvn -q exec:java -Dexec.mainClass="main.Main" -Dnom_fichier="${paths[p]}"
done

#launching tests with two processors
for p in ${!paths[@]}  
do
	for pp in ${!paths[@]}  
	do
		mvn -q clean test -Dp_chemin_1=processor."${paths[p]}" -Dp_chemin_2=processor."${paths[pp]}"
		cd ../Mutator
		mvn -q exec:java -Dexec.mainClass="main.Main" -Dnom_fichier="${paths[p]}""${paths[pp]}"
		done
done