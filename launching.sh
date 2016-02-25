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
	mvn clean test -Dp_chemin_1=processor."${paths[p]}"
	cd ../Analysor
	mvn clean install
	mvn exec:java -Dexec.mainClass="main.Main" -Dnom_fichier="${paths[p]}"
done

#launching tests with two processors
for p in ${!paths[@]}  
do
	for pp in ${!paths[@]}  
	do
		mvn clean test -Dp_chemin_1=processor."${paths[p]}" -Dp_chemin_2=processor."${paths[pp]}"
		cd ../Analysor
		mvn clean install
		mvn exec:java -Dexec.mainClass="main.Main" -Dnom_fichier="${paths[p]}""${paths[pp]}"
		done
done

#launching tests with three processors
for p in ${!paths[@]}  
do
	for pp in ${!paths[@]}  
	do
		for ppp in ${!paths[@]}  
		do
			mvn clean test -Dp_chemin_1=processor."${paths[p]}" -Dp_chemin_2=processor."${paths[pp]}" -Dp_chemin_3=processor."${paths[ppp]}"
			cd ../Analysor
			mvn clean install
			mvn exec:java -Dexec.mainClass="main.Main" -Dnom_fichier="${paths[p]}""${paths[pp]}""${paths[ppp]}"
		done
	done
done




