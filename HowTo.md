Ce répertoire est composé de deux projets Maven : Mutator et SourceUnderTest .
Mutator est le projet utilisé pour mettre en place les mutations choisies par l’utilisateur et analyser les résultats des tests effectués sur les mutants d’un projet  .
SourcesUnderTest est un projet exemple à tester.

Pour appliquer la chaîne de build sur votre propre projet maven, vous devez :

* 1) Copiez-collez les dépendances et plugin ci-dessou dans le pom de votre projet à tester pour que celui-ci puisse utiliser spoon.
* 2) Dans le fichier launching.sh, modifiez les deux premières lignes : 
** pathOfDirectory indique le chemin du répertoire où se situent Mutator et votre projet . 
** nameOfProject indique le nom de votre projet (par défaut, SourcesUnderTest)

Nous avons 7 types de processeurs actuellement, en voici la liste :
IncDec : Permet de transformer les post-incrémentations en post-décrémentations et inversement.
IntDouble : Permet de modifier le typage des variable double en int.
IfInverter : Permet d’inverser une condition dans un if : si la condition est définie comme vraie elle sera alors évaluée comme fausse et inversement.
LoopInverter : Permet d’inverser les conditions des boucles : while et for
GELE : Permet de d’inverser les inégalités non stricte “>=” et “<=".
PlusMinus : Permet de transformer l'opération binaire + en - et inversement. 
PublicToPrivate : Permet de de tranformer les méthodes publiques en méthodes privées .

On peut choisir d’appliquer ces processeurs tout le temps ou un certain nombre donné de fois, partout ou dans des packages donnés, des classes données, ou des méthodes données .

Pour indiquer les séries de processeurs que nous souhaitons appliquer, et comment nous voulons les appliquer, il faut aller dans le fichier conf.xml . Il est déjà rempli avec un exemple :
**code**
<?xml version="1.0" encoding="UTF-8"?>
<!-- liste des mutants à appliquer-->
<configuration>
  <mutants>
    <mutant>
          <processor>IncDec</processor>
          <processor>PlusMinus</processor>
    </mutant>
    <mutant>
          <processor>PlusMinus</processor>
    </mutant>
    <mutant>
          <processor>GELE</processor>
          <processor>PlusMinus</processor>
          <processor>IncDec</processor>
    </mutant>
  </mutants>

  <processors>
    <processor>
          <nom>PlusMinus</nom><!-- s'applique sur la classe Classe1-->
          <package>
                <nom>package1</nom>
        <classe>
<nom>Classe1</nom>
<methode>methode()</methode>
<methode>methode(int)</methode>
</classe>

          </package>
          <applications>4</applications>
    </processor>
  </processors>
</configuration>

À l’intérieur de la balise “mutants”, sont situées les différentes séries de processeurs que l’on souhaite exécuter sur notre projet pour obtenir des mutants à tester . Dans l’exemple ci-dessus, on effectuera les séries de processeurs IncDec puis PlusMinus, PlusMinus, GELE puis PlusMinus puis IncDec .

À  l’intérieur de la balise “processors”, on définit les configurations souhaitées pour chaque processeur. Par défaut, si aucune  configuration n’est présente, le processeur s’applique sur tous les éléments lui correspondant (par exemple + et - pour PlusMinus), tout le temps.
Les balises package, classe et methode indiquent l’endroit où le code doit être appliqué . La balise applications permet d’indiquer le nombre maximum de fois où le processeur peut être appliqué sur un mutant.
