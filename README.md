# Les Boulangers - CDN

Dans le cadre du projet du cours AMT (Application multi-tiers) à la HEIG-VD, nous sommes chargés de créer un site web de e-commerce décomposé en microservices. Ce repository gère le service de CDN.

Le repo central, ainsi que le wiki est disponible via ce lien : https://github.com/LeonardBesseau/AMT-Project 

## Description

Ce microservice de CDN privé est également basé sur Quarkus.

## Pré-requis

- [Postgresql](https://www.postgresql.org/download/) (v13 ou supérieure)
- [Java 11](https://adoptopenjdk.net/installation.html)
- [Docker](https://docs.docker.com/get-docker/) et [docker-compose](https://docs.docker.com/compose/install/) (optionnels)
- [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) + accès à un bucket s3

## Déploiement

- Configurer les informations de connexion pour [AWS](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-quickstart.html).

- Télécharger la release et l'extraire. Ajouter les données de connexion à la base de données dans le fichier `config/application.properties` (il peut être nécessaire de le créer).
- Lancer le serveur avec `java -jar target/quarkus-app/quarkus-run.jar` 


## Installation 

Les étapes ci-dessous permettent de mettre en place l'environnement de développement en local afin de travailler sur le projet :

1. Cloner le repository. 

2. Mettre en place la base de données

   - Standalone

   Si vous disposez déjà de postgres, vous pouvez créez une nouvelle base de données ou en utiliser une existante. Les scripts pour créer les tables se trouvent dans `sql/tables`. 

   Une fois la configuration terminée, vous pouvez mettre les informations de connexions dans le fichier `config/application.properties`. (L'utilisateur à fournir doit avoir des droits de lecture et d'écriture)

   - Docker
     1. `docker-compose up` dans le dossier `docker`

3.  Configurer les informations de connexion pour [AWS](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-quickstart.html).

4. Lancer l'application en mode *dev* avec `mvn compile quarkus:dev`.

5. Se connecter avec un navigateur à sur localhost:8082.


## Contribution

Les Pull Requests sont les bienvenues. Pour des changements majeurs, ouvrez s'il vous plaît une issue pour discuter de ce que vous souhaitez changer.
Soyez sûrs de mettre à jour les tests si nécessaire.

## Licence

[MIT](https://choosealicense.com/licenses/mit/)

## Auteurs

- Léonard Besseau
- Alexandra Cerottini
- Miguel Do Vale Lopes
- Fiona Gamboni
- Nicolas Ogi
