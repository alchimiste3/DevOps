#creating dependency
cd ./Mutator
mvn clean install

#launching test
cd ../SourcesUnderTest
mvn clean test -Dp_chemin=processor.PlusMinus

#launching junit analyser
cd ../Analysor
mvn clean install
mvn exec:java -Dexec.mainClass="main.Main" -e

#cleaning?
