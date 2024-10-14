# Prueba Técnica
Implementación de dos microservicios agrupando (Clientes, Persona) y (Cuenta, Movimentos) contemplar comunicación asíncrona entre los dos microservicios

# Arquitectura propuesta para la solución 
![imagen](https://file.notion.so/f/f/b9664e7a-d593-464f-88e0-4e248b735e18/d425dd87-c72e-4a3b-ad2d-034ef17d60ab/image.png?table=block&id=72dc06ae-e955-419a-add5-b2cd0a3263f5&spaceId=b9664e7a-d593-464f-88e0-4e248b735e18&expirationTimestamp=1728979200000&signature=YeRvPb0yEyM5TETwQ-9c-r0N2SPFvoLUOB-LN7fZY5s&downloadName=image.png)
Por el momento solo se implementa los dos microservicios con su respectiva base de datos y el broker de mensajes
## Microservicio de clientes
Este microservicio gestiona clientes.
### Stack tecnologico
* Java
* Spring boot
* PostgreSQL
## Microservicio  de transacciones
Este microservicio gestiona cuentas y movimientos de los clientes
### Stack tecnologico
* Java
* Spring boot
* PostgreSQL

## Despliegue
Los microservicios se despliegan en contenedores Docker mediante archivos YAML de configuración de Docker Compose, en este archivo de definen todos los servicios a ser ejecutados y levantar todos los microservios, base de datos y sistema de mensajeria a través de un solo comando. 

## Levantar servicios
``` bash
    docker-compose up -d
```
Mediante este comando se crean los siguientes contenedores:
* ms-transactions (cuentas y movimientos)
* ms-client-management (clientes)
* rabbitmq
* transactions_db
* clients_db

## Coleccion de Postman
En este archivo se encuentran todos los endpoints requeridos en el reto para su respectiva validación
## Detener servicios
``` bash
    docker-compose down
```

