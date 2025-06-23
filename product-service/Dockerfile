FROM openjdk:23-jdk-slim

WORKDIR /app

# Créer utilisateur non-root
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copier le JAR
COPY target/product-service-0.0.1-SNAPSHOT.jar app.jar

# Changer les permissions
RUN chown appuser:appgroup app.jar

USER appuser
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]