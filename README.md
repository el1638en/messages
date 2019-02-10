# Spring Boot

Spring Boot est un framework Spring qui permet de simplifier le démarrage des applications Web
avec une meilleure gestion des configurations (plus besoin des nombreux fichiers XML de configuration, etc...).
Il embarque un serveur Tomcat et gère mieux les dépendances avec l'import des modules "starter".
Dans ce cas pratique, nous utilisons Spring Boot pour produire une API Backend Spring Rest.

  1. Le façade Web (module maven messages-api)

  Le façade Web est la partie accessible aux clients de l'API. Elle expose les APIs REST et n'effectue aucun traitement métier.
  Elle utilise les beans service  de la couche métier.Les API REST sont construits avec le framework
[Spring REST](https://projects.spring.io/spring-restdocs/ "link to Spring REST").
  Spring REST fournit les annotations pour contruire les façades REST :
* `@RestController` : elle est présente dans tous les contrôleurs qui exposent des APIs.
* `@RequestMapping` : Elle permet de faire le mapping entre une URL d'API et la méthode chargée de traiter ladite URL.
* `@RequestParam` : cette annotation faire un mapping entre un paramètre de l'API et le paramètre de la méthode qui traite l'API.
* etc....
Pour connaître les annotations et autres outils fournis par Spring REST, consulter le module messages-api.
Par ailleurs, certains APIs sont sécurisés. Elles ne sont accessibles qu'aux clients authentifiés et disposant des droits nécessaires.
Pour gérer la sécurité des APIs, nous utilisons :
* une authentification par Token : Il s'agit d'une authentification de type Basic avec un login:Mot de passe en Base64.
Si les credentials sont connus, un token de connection est fournis par l'API. Ce token sera rajouté aux headers des requêtes vers les APIs sécurisés.
* [Spring Security](https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/ "link to Spring Security") : En utilisant l'annotation @Secured et d'autres mécanismes de sécurité, les APIs sécurisés
ne sont accessibles qu'aux clients disposant de tokens valides et des droits les autorisant à exploiter les APIs.


2. Les services et les accès à la bdd (module maven messages-services)

    - La couche métier (package `com.syscom.service`): contient les contrats d'interface des services métiers et leurs implémentations. Elle est disponible dans le module db-service. Les implémentations des services métiers sont des beans de service Spring (annotation `@Service`). Aussi c'est dans cette couche que se gère les transactions(`@Transactional`) et les exceptions fonctionnelles. Pour consulter/modifier des données de la la base de données, elle s'appuie sur les repositories de la couche DAO.

    - La couche DAO (package `com.syscom.dao`). Elle est composée des interfaces Spring Data qui permet d'accéder aux données de l'applications.Spring Data est un framework d'accès aux données fourni par l'écosystème Spring. Pourquoi utiliser ce framework ? Il fournit des méthodes d'accès aux données sans qu'on écrive une ligne de données. Il facilite donc grandement la tâche du développeur. Par exemple, Spring Data fournit déjà des méthodes telles que la recherche/suppression d'une ligne d'une table par ID, compter le nombre de ligne dans une table, la persistence d'une ligne, etc... Avec ces méthodes fournies, Spring Data nous permet de gagner du temps et de diminuer la taille des repositories d'accès aux données. En plus, Spring Data peut être combiner avec plusieurs SGBD relationnels ou de type NoSQL.

    - La couche ORM(JPA/Hibernate, package `com.syscom.beans`). Cette couche réalise le mapping entre les objets Java et les tables de la base de données grâce aux annotations JPA (Java Persistence API). Elle est une interface de la communauté Java qui définit des normes, des règles à respecter pour réaliser le mapping Objet-Relationnel. Hibernate est une implémentation de JPA qui respecte les règles, le standard défini par cette interface.
    Dans toutes les classes réalisant les mappings Objet-Relationnel, nous utilisons les annotations du package `javax.persistence`. Quelques exemples d'annotations :
      * @Entity : au dessus d'une classe, elle indique que celle-ci est une classe persistante qui sera enregistrée en base de données.
      * @Table : elle contient un paramètre qui correspond au nom d'une table en base de données. Elle indique que la classe est le reflet Java d'une table en base de données.
      * @Column : elle est utilisée au dessus des attributs des classes et indique à quelle colonne de table l'attribut correspond. On y retrouve des paramètres telles que le nom de la colonne, le type, la longueur.


    - Le versionning des scripts SQL

      Le versionning des scripts SQL est géré par le framework [flywaydb](https://flywaydb.org "link to flyway").
      Tous les scripts SQL sont dans le module db-scripts, dans le sous-répertoire des ressources. Au démarrage du serveur Tomcat embarqué, les scripts SQL sont exécutés
      et la base de données est mise à jour automatiquement avec les nouveaux scripts SQL.
