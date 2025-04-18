FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .

# Baixa as dependências necessárias para o build de modo offline e em batch
RUN mvn dependency:go-offline -B

COPY src ./src

# Compila a aplicação Spring Boot e pula os testes
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ARG TOKEN_SECRET
ENV TOKEN_SECRET=${TOKEN_SECRET}

ARG SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
