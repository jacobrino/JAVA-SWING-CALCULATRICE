**Application Calculatrice (Java Swing)**

**Auteur: ANDRIANJARA Jacob Rino**

Cette application est une calculatrice desktop développée en Java Swing et packagée sous forme de fichier exécutable .jar. Elle a été réalisée dans le cadre d’un projet académique visant à maîtriser la création d’interfaces graphiques en Java, la programmation événementielle et l’implémentation d’un algorithme de calcul d’expressions mathématiques.

L’application offre une interface claire composée d’un afficheur et d’un clavier numérique, tout en prenant en charge la saisie via les boutons et le clavier. Elle permet de construire une expression mathématique sous forme de chaîne puis d’en afficher le résultat, en respectant les règles de priorité opératoire et la gestion des parenthèses.


**1) Contexte**

Ce projet a été réalisé dans un cadre universitaire(académique) (L1) afin de travailler les notions suivantes : conception d’interface utilisateur en Java Swing, gestion des événements (clics / clavier), organisation du code (séparation UI / logique), et packaging sous forme de JAR pour permettre l’exécution sur n’importe quel système possédant Java.

L’un des points centraux du projet à titre d'amélioration personnel concerne également l’évaluation correcte d’expressions mathématiques saisies par l’utilisateur, avec un focus sur la performance et la stabilité.
Projet dévéloppé en 2019 mais archivé et publié sur GitHub en Décembre 2025 dans le cadre d’un portfolio personnel. 

![Workflow](https://github.com/jacobrino/JAVA-SWING-CALCULATRICE/blob/develop/docs/2%20R%C3%A9sultat%20op%C3%A9ration.png) ![Workflow](https://github.com/jacobrino/JAVA-SWING-CALCULATRICE/blob/develop/docs/1%20Op%C3%A9ration.png)

**2) Objectifs**

L’objectif principal était de construire une calculatrice fonctionnelle et ergonomique. Cela implique la gestion complète de l’affichage, des entrées utilisateur et des opérations, mais aussi la mise en place d’un calcul fiable capable de traiter une expression entière, incluant opérateurs, parenthèses et décimaux. Enfin, l’application devait être distribuable facilement via un fichier .jar.


**3) Fonctionnalités**

L’application permet notamment :

- la réalisation des opérations de base : addition, soustraction, multiplication et division ;
- la saisie d’expressions contenant des parenthèses ;
- la prise en charge des nombres décimaux ;
- une utilisation via souris (boutons) et clavier ;
- la suppression d’un caractère (S) et la suppression totale (C) ;
- la validation du calcul via le bouton = ou la touche Entrée ;
- le contrôle des caractères autorisés lors de la saisie au clavier.


**4) Évaluation d’expressions et optimisation du calcul**

Le calcul ne se limite pas à une opération simple “étape par étape” : l’application traite l’expression saisie en entier, comme par exemple :

(2+3)*5 - 4/2

Une première approche basée sur l’évaluation via un moteur de scripting Java (du type engine.eval("string_expression_a_calculer")) a été envisagée pour calculer automatiquement les expressions sous forme de chaîne. Toutefois, cette solution a rapidement montré ses limites : un constat de lenteur a été observé lors de l’évaluation d’expressions en chaîne, et l’approche via API de scripting s’est avérée moins adaptée en termes de performances.

Pour cette raison, nous avons décidé de réécrire l’algorithme de calcul afin d’avoir un traitement plus rapide, plus contrôlé et plus robuste. Cette réécriture permet :

- de mieux gérer les priorités opératoires (* et / avant + et -) ;
- de traiter correctement les parenthèses ;
- d’éviter une dépendance à un moteur externe ;
- d’améliorer la performance sur les expressions longues ou répétées.

Le cœur de cette logique se trouve principalement dans la classe Calculer.java.


**5) Structure du projet**

Le projet est organisé de manière claire afin de séparer les ressources, le code et le livrable final :

```text
.
├── docs/                 (captures écran / documentation)
├── dst/                  (fichier .jar généré)
│   └── Calculatrice.jar
├── src/                  (code source + classes compilées + ressources)
│   ├── Calculer.java
│   ├── Programme.java
│   ├── MainPrincipale.java
│   └── res/
│       ├── tete.jpg
│       └── fond_autorisation.png
└── MANIFEST.MF
```

**6) Exécution**

Lancer directement l'application (JAR)

Depuis la racine du projet :

  **java -jar dst/Calculatrice.jar**


**7) Générer le JAR (Linux)**

Les commandes ci-dessous permettent de compiler puis générer un fichier Calculatrice.jar exécutable :

  **rm -f src/*.class**
  **javac -d src src/*.java**
  **echo >> MANIFEST.MF**
  **jar cfm dst/Calculatrice.jar MANIFEST.MF -C src .**

Puis exécution :

  **java -jar dst/Calculatrice.jar**


**8) Aperçus**

Captures d’écran disponibles dans le dossier docs et aussi un fichier de rapport en pdf "Programmation calculatrice, ANDRIANJARA Jacob Rino.pdf":
- 1 Opération.png
- 2 Résultat opération.png


**9) Ce que j'ai appris**

Ce projet nous a permis de consolider plusieurs compétences importantes :

- la programmation événementielle en Java ;
- la conception d’interfaces en Swing ;
- l’organisation et la structuration d’un projet Java ;
- l’évaluation optimisée d’expressions mathématiques ;
- la génération et la distribution d’un programme sous forme de JAR.
