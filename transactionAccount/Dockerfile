# Cambia esto por la versión correcta
FROM gradle:8.10-jdk17 AS builder

WORKDIR /app

# Copia todo el código fuente
#COPY build.gradle.kts ./
#COPY src ./src

COPY transactionAccount/build.gradle.kts ./
COPY transactionAccount/src ./src

# Ejecuta la construcción del JAR y muestra información adicional
RUN gradle build -x test --info

# Verifica el contenido del directorio de construcción
RUN ls -l /app/build/

# Verifica el contenido del directorio de libs
RUN ls -l /app/build/libs/

# Ahora usa una imagen de JDK para ejecutar la aplicación
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copia el JAR construido desde la imagen anterior
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "/app/app.jar"]
