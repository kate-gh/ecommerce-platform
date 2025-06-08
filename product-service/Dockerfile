FROM openjdk:21

# Copie le script d’attente
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh

# Copie ton fichier JAR
COPY target/*.jar app.jar

EXPOSE 8081

# Démarrage : attends MySQL avant de lancer Spring Boot
ENTRYPOINT ["/wait-for.sh", "mysql", "3306", "--", "java", "-jar", "/app.jar"]
