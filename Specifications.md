#**I ) Chaîne de build (Description de la chaîne de build en positionnant les artefacts manipulés et les outils associés)**

##**1 ) Un pom alternatif**

Au lieu d’utiliser le fichier pom.xml original du projet, nous allons utiliser un pom2.xml alternatif et d’appeler l’option “ -f ” de maven pour l’appeler. 
Nous voulons utiliser un fichier différent afin de garder le fichier original non modifié; ainsi, le programme peut se compiler correctement.

Ce pom2.xml permettra à Maven d’utiliser Spoon en incluant dans le xml des processors indique à Maven les class à utiliser pour faire muter le code source.
On utilisera des variables dynamiques afin de pouvoir ajouter autant de processor que l’on veut et donc de faire varier le nombre de mutation que l’on applique.

Le fichier pom2.xml contient ainsi deux plugins, qui s’exécutent tous deux à la phase de génération de code “generate-sources” dans l’ordre suivant : 
-> le plugin properties-maven-plugin, permettant d’exporter les “properties” du pom2.xml vers un fichier qui pourra être utilisé dans le code java.
-> le plugin spoon-maven-plugin permettant d’utiliser Spoon.

##**2 ) La chaîne de build**
Nous gérerons la chaîne de build en appelant des commande maven via un script bash.Voici notre idée globale de ce fichier (idée qui sera très probablement affinée lors du développement):

    #variable contenant les processeurs à exécuter
    paths=(p1\_path p2\_path p3_path);
    paths_len= ${#paths[@]} #length of paths
    #validation du projet et compilation du code source
    mvn -f pom2.xml validate
    mvn -f pom2.xml compile
    #génération du code source des mutants et confrontation au banc de tests
    for ((i=0;i<${paths_len};i++));
        do
        for ((j=0;j<${paths_len};j++));
            do
            cp pom2.xml pom_tmp.xml 
            mvn -f pom_tmp.xml -Dp1=${paths[$i]} -Dp2==${paths[$j]} 
            mvn -f pom_tmp.xml generate-sources  
            mvn -f pom_tmp.xml test
            rm pom_tmp.xml 
          done
        done
    #analyse et affichage donnees dans un fichier html grâce à un programme java
    java analyse_donnees

###**a ) Validation du projet :**
Cette première étape sert à vérifier que toutes les informations nécessaires à la compilation sont disponibles.

###**b) Compilation du code source :**
Dans cette étape, on compile le code source fourni en entrée du fichier  pom2.xml .

###**c) Génération du code source des mutants et confrontation au banc de tests**
Nous allons boucler sur la liste de chemins des processeurs afin d’obtenir à chaque itération un mutant différent (l’exemple de boucle donné ici peut très bien être étendu à plusieurs autres boucles, afin d’avoir des suites d’autres nombres de mutation).

Dans une itération de boucle, nous commençons par copier le fichier pom2.xml afin de travailler sur une copie propre de celui-ci.
Nous allons ensuite ajouter à ce fichier temporaire pom_tmp.xml les chemins correspondants aux processeurs qu’on souhaite utiliser pour définir le mutant de l’itération.

Le pom2.xml (et donc sa copie pom\_tmp.xml) contient déjà une variable p1\_path (dont la valeur est donnée à l’exécution) et un processeur processor1 (du plugin de spoon) dont le chemin est p1_path.

Lors de la phase generate-sources, voici ce qui va se passer :
Le plugin properties-maven-plugin va transférer les propriétés du pom_tmp.xml vers un fichier accessible via java.

Dans la classe qui définit un processeur donné, on aura pris soin d’overrider la méthode processingDone() : dans celle-ci , on va chercher si il existe encore dans le fichier de propriétés une variable dont le nom est pK\_path (K un nombre).Si c’est le cas, on prend la première variable trouvée, on crée un nouveau processeur dont le chemin correspond à cette variable et on exécute ce nouveau processeur. On prend ensuite le soin d’effacer la variable pK_path du fichier de propriétés afin de ne pas réutiliser un processeur deux fois sans le vouloir.

Après avoir généré le mutant, on le confronte enfin au banc de tests. Le résultat de cette confrontation est stocké dans un fichier xml qu’on analysera à la prochaine étape.

Pour finir l’itération, le fichier temporaire pom_tmp.xml est supprimé.


###**d) Récupération et affichage des résultats**
Lorsque la boucle a généré et testé les mutants, on récupère les informations des .xml en sortie de maven et on analyse les résultats grâce à une parser JAVA lancer par le script bash; on les affiches ensuite de plusieurs manières via des graphiques dans un fichier .html : pour un test en particulier, regarder par exemple le nombre de mutant qui ont échoué.
Pour cette étape nous utiliserons JavaFX.

#**II ) Mutations choisies (Quelles mutations, où les appliquer, comment les appliquer ?)**
Pour l'instant on ne fait qu'une seule modification dans le code a chaque mutation. Par exemple si on modifie int en float, on prend un seul int dans le code et on le modifie. Nous amplifierons le nombre de modifications après avoir une meilleure prise en main de l'outil (et après nous être assurés qu'il n'y a pas de risque d'obtenir un code non compilable)

###**1 ) float -> int ou int -> float**

 Où : partout

###**2 ) changer la valeur à l’initialisation d’un entier : +1**

 Où : partout

###**3 ) modifier l'incrémentation (++) en décrémentation (--) et inversement.**

 Où : dans les déclaration de for

###**4 ) remplacement des String par une chaîne alternative (chaîne vide?””)**

 Où : partout

###**5 ) remplacer les String XXX = “”; par String XXX;**

 Où : dans les string déclarés localement dans des methodes

###**6 ) remplacement de méthode au même nombre d’argument et qui renvoient la même chose : String.contains(CharSequence c) par String.contentEquals(CharSequence c).**

 Où:partout

###**7 ) remplacer les <= ou >= par < ou > et inversement.**

 Où : dans les cas d’arrêt des boucle for lorsqu’on parcourt une liste

###**8 ) inverser l’ordre de 2 conditions**

 Ex : if (  !liste.isNull() && liste.size() < 10 ) 
Où : dans les if et les while qui contiennent au moins 2 conditions liées par ET ou OU.

###**9 ) Remplacer les ET par des OU et vice versa**

Où : partout


