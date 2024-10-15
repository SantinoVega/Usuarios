# Microservicio Usuarios

_El microservicio Usuarios contiene los metodos necesarios para hacer CRUD con los datos de un usuario._



<p align="center"><img src="https://i.postimg.cc/Y0VV4XgS/javaspring.png"/></p> 

## Comenzando 🚀

_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en una computadora con sistema operativo Windows para propósitos de pruebas._


### Pre-requisitos 📋

* Tener instalado java: https://www.java.com/es/download/help/ie_online_install.html

* Tener instalado maven: https://maven.apache.org/install.html

* Tener instalado git: https://git-scm.com/book/es/v2/Inicio---Sobre-el-Control-de-Versiones-Instalaci%C3%B3n-de-Git


### Instalación 🔧

_Abrir una terminal o cmd y posicionarse en un directorio donde se desee descargar el repositorio._

_Ejecutar el siguiente comando_

```
git clone https://github.com/SantinoVega/Usuarios.git

```

_En una terminal o cmd posicionarse en el directorio donde se encuentra el archivo **pom.xml** del proyecto Usuarios y ejecutar los siguientes comandos:_

```
mvn clean package
```
_El comando anteriror compila y empaqueta el proyecto generando un archivo .jar . A continuacion entrar al directorio **target** donde se genero el archivo .jar_

```
cd target
```

_Ejecutar el siguiente comando para levantar el microservicio **Usuarios**_

```
java -jar usuarios-1.0.0.jar
```


_Al terminar de levantar el microservicio que esta configurado en el puerto **8085**. Se puede revisar el Swagger. Donde se puede consultar la descripcion y las especificaciones de los metodos del API de usuarios._

http://localhost:8085/v1/swagger-ui/index.html


## Ejecutando las pruebas ⚙️

_Para realizar peticiones puedes utilizar algun programa como Postman, SoapUI, Insomnia, etc. o directamente desde el Swagger_

<p align="center"><img src="https://i.postimg.cc/xdxPTXLf/Captura-de-pantalla-2024-10-15-005532.png"/></p> 


_otro ejemplo con insomnia_

<p align="center"><img src="https://i.postimg.cc/dVdMCK5c/Captura-de-pantalla-2024-10-15-005814.png"/></p> 


## Construido con 🛠️

_El proyecto se desarrollo utilizando una **Arquitectura MVC**. Las herramientas utilizadas para desarrollar este proyecto se enlistan a continuacion._

* [Java](https://docs.oracle.com/en/java/) - El lenguaje de programacion usado
* [SpringBoot](https://docs.spring.io/spring-boot/documentation.html) - Framewor utilizado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [JPA](https://www.ibm.com/docs/es/was-liberty/nd?topic=liberty-java-persistence-api-jpa/) - Java Persistence API
* [PostgreSQL](https://www.postgresql.org/) - Base de Datos

_En la carpeta JavaDoc que se encuentra en la raiz del proyecto se encuentran algunos javadocs que se generaron del microservicio Usuarios._

## Versionado 📌
### 1.0.0

## 🎁
* Hola, buen dia. Espero que sea de su agrado y tener noticias de ustedes.
* Me encantaria colaborar en algun momento o proyecto con ustedes, saludos.

---
⌨️ con ❤️ por [DIMR](https://github.com/SantinoVega) 😊

