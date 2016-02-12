cp pom.xml pom2.xml;
#variable contenant les processeurs à exécuter
paths=(p1_path p2_path p3_path);
paths_len= ${#paths[@]} #length of paths

#validation du projet et compilation du code source
mvn -f pom2.xml validate;
mvn -f pom2.xml compile;
 
 #génération du code source des mutants et confrontation au banc de tests
for ((i=0;i<${paths_len};i++));
do
	for ((j=0;j<${paths_len};j++));
	do
		cp pom2.xml pom_tmp.xml;
		mvn -f pom_tmp.xml -Dp1=${paths[$i]} -Dp2==${paths[$j]}; 
		mvn -f pom_tmp.xml generate-sources;  
		mvn -f pom_tmp.xml test;
		rm pom_tmp.xml;
	done
done
