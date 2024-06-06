# Charge McQueen 🏎️
Bienvenue dans Charge McQueen ⚡️, votre application de gestion de bornes de recharge.

## Table des matières
- [Installation](#installation)
- [Utilisation](#utilisation)
- [À propos](#a-propos)

## [Installation](#installation)
Pour utiliser l'application vous aurez besoin de [Java21](https://www.oracle.com/fr/java/technologies/downloads/#java21) et de [Maven](https://maven.apache.org/download.cgi).

Pour installer Charge McQueen vous devez suivre les étapes suivantes : 
1. Récupérez le contenu du projet depuis le dépôt [GitHub](https://github.com/nicolemoucas/chargemcqueen.git) et dézippez le dossier.
2. Ouvrez le dossier avec un environnement de développement intégré tel que [Eclipse](https://eclipseide.org/), [Visual Studio Code](https://code.visualstudio.com/download) ou [IntelliJ](https://www.jetbrains.com/idea/download/?section=mac).
3. Ouvrez le fichier Documentation/protocole.txt et suivez les étapes indiquées pour installer PostgreSQL afin de créer la base de données.
4. Lancez la commande `mvn install` pour installer les dépendances du projet, le compiler et lancer les tests. 

## [Utilisation](#utilisation)
Pour lancer l'application il faut suivre les étapes suivantes : 
1. Ouvrez une fenêtre du terminal (CLI) et lancez le serveur de la base de données PostgreSQL. Sur Mac vous pouvez le faire avec la commande Homebrew `brew services start postgresql@16`. 
2. Déplacez vous dans le dossier bin avec `cd bindist/bin`.
3. Utilisez la commande `./mcqueen` pour lancer l'application.
4. Le menu principal sera présenté, choisissez une option et bienvenue chez Charge McQueen !
5. Pour quitter l'application il suffit de choisir l'option STOP dans le menu principal.
6. N'oubliez pas d'arrêter le serveur PostgreSQL avec la commande `brew services stop postgresql@16`.

## [À propos](#a-propos)
Ce projet a été réalisé dans le cadre du cours de Génie Logiciel du Master en Méthodes Informatiques Appliquées à la Gestion des Entreprises I de l'Université de Lorraine.

Il a été développé par :
- CASTRO MOUCHERON Nicole
- FRASELLE Nadège
- VINOT Mathieu

© 2024