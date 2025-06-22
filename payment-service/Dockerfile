FROM openjdk:21-jdk-slim

# Installe netcat pour la connectivité réseau
RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY wait-for.sh /wait-for.sh

RUN chmod +x /wait-for.sh && \
    addgroup --system appgroup && \
    adduser --system --ingroup appgroup appuser

# Corrige le nom du JAR
COPY target/*.jar app.jar

USER appuser
EXPOSE 8083

ENTRYPOINT ["/wait-for.sh", "mysql", "3306", "--", "java", "-jar", "app.jar"]