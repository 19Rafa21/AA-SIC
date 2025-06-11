# Stage 1: Build the application
FROM maven:3.9.7-eclipse-temurin-21 AS build

WORKDIR /app

# Copia tudo (podes limitar a pasta se quiseres)
COPY . .

# Faz o build do WAR (e ignora testes se quiseres)
RUN mvn clean package -DskipTests

# Usa imagem WildFly base (ou FROM jboss/wildfly:26.1.3.Final se tiveres no Docker Hub)
FROM quay.io/wildfly/wildfly:26.1.2.Final-jdk17

# Copia o teu module para dentro da imagem
COPY modules/orm /opt/jboss/wildfly/modules/com/orm

# Copia o teu WAR para deployments
COPY target/tastycheck.war /opt/jboss/wildfly/standalone/deployments/

# Expõe a porta HTTP
EXPOSE 8080

# Comando de entrada
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]