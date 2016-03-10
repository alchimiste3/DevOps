## Nos choix : 

Nous avons choisi d’articuler notre chaîne de build autour de l'exécution d’un script shell, launcher.sh, avec quatre étapes principales qui s'enchaînent : generate-sources , compile, test et finalement l’analyse des resultats.
Le fichier shell lance les différentes étapes de la chaîne de build : 

* Initialisation : Le shell lance un premier programme java qui initialise la liste des processeurs à appliquer pour chaque mutant grâce au fichier de configuration “conf.xml”, puis initialise les fichier listeMutant.xml (servant à stocker les données à chaque confrontation d’un mutant au banc de tests) et results.html (le fichier html résultat).
* Modification pom.xml : Puis le shell lance une boucle for : il lance un programme java qui modifie le pom.xml du projet à tester dans le but d’inclure les processeurs choisis .
* Génération de sources : Il applique ces  processeurs au code pour générer un mutant.
* Compilation : Il compile ce mutant .Si la compilation échoue, le mutant est mort né : on l’indique sur le fichier .html résultat et on incrémente le nombre de morts nés dans listeMutants.xml . On a alors terminé l’itération et on passe à la suivante .
* Tests : Sinon,il confronte ce mutant au banc de tests du projet .
* Analyse du test : On analyse les fichiers .xml de sortie pour concaténer les informations voulues dans le fichier results.html de sortie ainsi que dans le fichier listeMutant.xml qui sauvegarde les tests de chaque boucle avant le début des nouveaux tests. 
* Analyse finale : En sortie de la boucle, le shell lance un autre programme qui effectue une analyse complète de tout les résultats des tests effectués sur des mutants grâce au fichier listeMutant.xml . Ce programme complète le fichier de sortie avec une analyse des mutants morts nés, morts mais surtout des mutants vivants en indiquant les mutations spécifiques qui ont fait réussir les tests là où ils n’auraient pas dû.



Nous avons donc bien une chaîne de build complète qui effectue les tests demandés par l’utilisateur et qui fournit un rapport html contenant des informations pertinentes pour les développeurs afin qu’ils puissent rendre leurs tests unitaires plus robustes.

## Justifications de nos choix : 

Nous avons choisi d’utiliser un script shell dans le but d’avoir une meilleure maîtrise et compréhension de nos actions,et de nous concentrer sur les technologies de Maven et Spoon . Du côté Dev,le shell est de plus compréhensible par tous les développeurs et peut être récupéré par n’importe qui et modifié facilement. 

Nous avons décidé d’implémenter des processeurs et sélecteurs relativement basiques pour nous focaliser sur la façon dont la chaîne de build est gérée et comment rendre le programme le plus simple côté opérationnel (installer,configurer) et côté développement (modifier) possible :
l’installation du projet se fait en un lancement de script build.sh .
la chaîne de build peut s’appliquer à n’importe quel projet,à condition qu’on lui donne le chemin de ce projet et qu’on place le plugin spoon et les dépendances nécessaires dans le pom.xml (voir HowTo.md). 
les processeurs héritent d’une classe mère abstraite AbstractProjectProcessor, où sont définies toutes les méthodes communes aux processeurs du projet (lecture sur conf.xml des endroits où appliquer les mutateurs) . Un processeur lié à conf.xml est alors facilement mis en place .

Nous avons choisie d’implémenter des mutations basique car la plupart des développeurs font leur test unitaire sur les partie du code les plus complexes et ne test pas les fonctions basics. On a donc plus de chance de garder un mutant vivant et donc d’indiquer une faille au développeur sur son code. 

## Forces et faiblesses

### Forces

Notre analyse des tests sur les mutants est minimale mais remplit son rôle informatif. 
Notre chaîne de build est facilement modifiable et maintenable . Des processeur peuvent également facilement être implémentés .
L’utilisateur peut configurer comme il le souhaite (avec les processeurs et sélecteurs disponibles) la chaîne de build, ainsi que son projet à tester .

###  Faiblesses
Nous aurions pu avec plus de temps améliorer l’affichage et le nombre d’informations disponibles : la ligne où un processeur mort né est mort, les endroits où sont appliqués les tests (bien que cette information a été définie par l’utilisateur dans conf.xml),...
Un code ne donnant pas de lui-même des indications de son fonctionnement (par ligne de commande par exemple) . Le code étant destiné à des informaticiens, nous n’avons pas insisté sur ce point .
Des processeurs un peu trop simples. 
