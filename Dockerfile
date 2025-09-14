FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia seu código
COPY public ./public
COPY src ./src
COPY Servidor.java ./

# Compila
RUN javac src/Servidor.java

# Define variável de ambiente
ENV PORT 8080

# Comando para rodar
CMD ["java", "-cp", "src", "Servidor"]
