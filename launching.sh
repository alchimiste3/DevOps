#creating dependency
cd ./Mutator
mvn clean install

#launching test
cd ../SourcesUnderTest
mvn clean test -Dp_chemin=processor.BinaryOperatorMutator

#launching junit ananlyser

#cleaning?
