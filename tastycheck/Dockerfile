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
COPY --from=build /app/target/tastycheck.war /opt/jboss/wildfly/standalone/deployments/

# Copia o script de entrypoint
COPY init.sh /opt/jboss/entrypoint.sh
#RUN chmod +x /opt/jboss/entrypoint.sh
#RUN ls -la /opt/jboss/

# Expõe a porta HTTP
EXPOSE 8080

# Define o novo entrypoint com warm-up
CMD ["/opt/jboss/entrypoint.sh"]