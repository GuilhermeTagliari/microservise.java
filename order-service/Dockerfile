# Etapa de build
FROM maven:3-eclipse-temurin-21-alpine AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas os arquivos de configuração primeiro (para aproveitar cache do Docker)
COPY pom.xml .
COPY src ./src

# Executa o build (sem testes)
RUN mvn clean package -DskipTests

# Etapa final, com imagem JRE mais enxuta
FROM eclipse-temurin:21-jre-alpine

# Copia apenas o JAR gerado da etapa de build
COPY --from=build /app/target/*.jar /app/app.jar

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]