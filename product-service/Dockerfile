FROM openjdk:21-jdk-slim


# Copie le script d’attente
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh

# Crée un utilisateur non root
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copie ton fichier JAR
COPY target/*.jar app.jar

# Change l'utilisateur
USER appuser

EXPOSE 8081

# Démarrage : attends MySQL avant de lancer Spring Boot
ENTRYPOINT ["/wait-for.sh", "mysql", "3306", "--", "java", "-jar", "/app.jar"]
