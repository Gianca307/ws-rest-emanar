# Emanar Bebidas - API REST - (Aplicación de mi autoría)

Este proyecto es una Api Rest Java desarrollada con el framework **Spring Boot**. \
Api para la administración y gestión de ventas, compras, clientes, y proveedores de un negocio de bebidas.

## Tecnologías Utilizadas
- **Java 17**
- **Maven**
- **Continuos Integration** (GitHub workflows)
- **Spring Boot Versión 3.5.0**
  - **Spring Boot Versión Starter Web**
  - **Spring Boot Starter Data JPA**: Proporciona soporte para Java Persistence API (JPA) para el acceso a datos.
  - **Spring Boot Starte Test / Junit**
  - **Spring Boot Starter Validation**
- **Spring Security + JWT**: Autenticación y autorización con tokens JWT:
  - jjwt-api, jjwt-impl, jjwt-jackson 
- **H2 Database**: DB en memoria utilizada para desarrollo y pruebas. Configuración de la consola interactiva.
- **SQL**
- **Lombok**
- **Springdoc OpenAPI**: Para generar documentación Swagger.

## Construcción del proyecto
### 1. Clonar repo y entrar al directorio
```bash
git clone https://github.com/Gianca307/ws-rest-emanar.git
cd ws-rest-emanar
```

### 2. Compilar y empaquetar el proyecto
```bash
mvn clean package
```


## Live Demo
### Acceder a Swagger UI

### [Click para ingresar a la demo 🖱️](https://ws-rest-emanar.onrender.com/swagger-ui.html)

## Endpoints principales
La interfaz Swagger muestra todos los endpoints documentados.\
Para acceder a los endpoints, en líneas generales los métodos GET requieren del usuario USER ya preconfigurado.\
El **usuario** preconfigurado **ADMIN** tiene las **autorizaciones de todos los endpoints**.

### Autenticación
#### Usuarios de prueba:
- admin (pass: *admin*)
- user (pass: *user*)
  
> [!IMPORTANT]
> Puedes usar Postman para acceder a los endpoints, sin olvidar colocar el token generado despues de loguearse.

## Online Report Result Tests
### [Click para ingresar Result Tests! 🖱️](https://gianca307.github.io/ws-rest-emanar/)

## Autor
**Portaluppi Giancarlo**\
[Portfolio](https://gianca307.github.io/portfolio/)\
[LinkedIn](www.linkedin.com/in/giancarlo-portaluppi-b33637228)

Para cualquier información adicional o consultas: <gianca307@gmail.com>

<p align="center"><b>¡Muchas gracias!</b></p>
