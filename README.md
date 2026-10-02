# GLL – Morpion

Jeu de morpion (tic-tac-toe) en ligne de commande, écrit en Java.
Projet support du cours de Génie Logiciel Libre (M2 MIAGE 2026-2027).

## Prérequis

- Un JDK (version 8 ou supérieure).

## Compilation

~~~bash
javac -d out src/Morpion.java
~~~

## Lancement

~~~bash
java -cp out Morpion
~~~

## Règles

- Chaque joueur choisit son symbole (X ou O) au début de la partie.
- Les cases sont numérotées de 1 à 9. Saisir le numéro d'une case libre pour y jouer.
- La partie se termine quand un joueur aligne trois symboles ou quand la grille est pleine.

## Contribuer

1. Ouvrir une issue décrivant le bug ou l'amélioration.
2. Forker le dépôt et créer une branche depuis `main`.
3. Ouvrir une pull request qui référence l'issue (`Closes #N`).

Les contributeurs sont listés dans [AUTHORS.md](AUTHORS.md).
