FROM openjdk:21-jdk-slim

# Crée le répertoire de travail
WORKDIR /app

# Copie le script d’attente et donne les permissions
COPY wait-for.sh /wait-for.sh

# Crée un utilisateur non-root, rend le script exécutable, et minimise les couches
RUN chmod +x /wait-for.sh && \
    addgroup --system appgroup && \
    adduser --system --ingroup appgroup appuser

# Copie le fichier JAR
COPY target/*.jar app.jar

# Change l’utilisateur
USER appuser

EXPOSE 8083

# Commande par défaut (ajuste selon ton projet)
ENTRYPOINT ["sh", "/wait-for.sh"]




# Démarrage : attends MySQL avant de lancer Spring Boot
#ENTRYPOINT ["/wait-for.sh", "mysql", "3306", "--", "java", "-jar", "/app.jar"]
