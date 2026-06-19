# ===== Etapa 1: build (compila o JAR) =====
# Versao do Maven fixada em 3.9.11 (a 3.9.9 empacota o JAR sem os recursos).
FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
# baixa as dependencias primeiro (cache melhor)
COPY pom.xml .
RUN mvn -q -e dependency:go-offline
# compila o projeto
COPY src ./src
RUN mvn -q clean package -DskipTests

# ===== Etapa 2: runtime (so o necessario pra rodar) =====
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# A porta real vem da variavel PORT (Render define automaticamente)
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
