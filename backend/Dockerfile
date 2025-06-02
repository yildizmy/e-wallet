# stage-1 : build environment avec mauvaises pratiques
FROM maven:3.8.1-openjdk-17-slim AS builder

# ❌ Utilisation de comptes utilisateurs par défaut (root implicite)
WORKDIR /app

# ❌ Pas de vérification de l'intégrité des dépendances ou du code source
COPY pom.xml ./
COPY src ./src

# ❌ Skip tests, risque d'introduire des bugs ou vulnérabilités non détectées
RUN mvn clean install -Dmaven.test.skip=true

# stage-2 : runtime environment peu sécurisé
FROM eclipse-temurin:17-jre-jammy

# ❌ Utilisation de l'utilisateur root (implicite si USER n'est pas défini)
WORKDIR /app

# ❌ Copy wildcard sans vérification — possible inclusion de fichiers sensibles
COPY --from=builder /app/target/*.jar /app/app.jar

# ❌ Ajout d’un fichier avec des credentials codés en dur (mauvaise pratique critique)
COPY --from=builder /app/src/main/resources/application.properties /app/application.properties

# ❌ Ports exposés sans pare-feu ou restriction
EXPOSE 8080

# ❌ Pas de mécanismes de redémarrage ou de sécurité au démarrage
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
