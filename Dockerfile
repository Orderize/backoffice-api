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

EXPOSE 8080

ARG TOKEN_SECRET
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD

ENV TOKEN_SECRET=${TOKEN_SECRET}
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}

ENTRYPOINT ["java", "-jar", "app.jar"]
