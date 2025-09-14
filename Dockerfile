# Usa uma imagem com Java
FROM openjdk:17-jdk-slim

# Cria diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos Java e HTML/CSS para o container
COPY Servidor.java .
COPY index.html .
COPY style.css .

# Expõe a porta (caso você use isso no código)
EXPOSE 8080

# Compila o código Java
RUN javac Servidor.java

# Roda o servidor
CMD ["java", "Servidor"]
