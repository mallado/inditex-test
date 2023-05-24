[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=mallado_inditex-test)

[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=mallado_inditex-test&metric=ncloc)](https://sonarcloud.io/dashboard?id=mallado_inditex-test)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=mallado_inditex-test&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=mallado_inditex-test)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=mallado_inditex-test&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=mallado_inditex-test)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=mallado_inditex-test&metric=security_rating)](https://sonarcloud.io/dashboard?id=mallado_inditex-test)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=mallado_inditex-test&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=mallado_inditex-test)

<!-- PROJECTO -->

# Test Inditex 2020
Tarifas aplicables a un producto de una cadena</h3>

## Índice

- [Inditex test 2020](#test-inditex-2020)
	* [Índice](#test-inditex-2020)
	* [Enunciado](#enunciado)
	* [Sobre el proyecto](#sobre-el-proyecto)
	* [Empezando](#empezando)
		+ [Prerequisitos](#prerequisitos)
		+ [Installación](#prerequisitos)
		+ [Pruebas](#pruebas)
		+ [Notas](#notas)
	* [Contacto](#contacto)

<!-- SOBRE EL PROYECTO -->

## Enunciado

En la base de datos de comercio electrónico de la compañía disponemos de la tabla PRICES que refleja el precio final (pvp) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas. A continuación se muestra un ejemplo de la tabla con los campos relevantes:
 
### TABLA DE PRECIOS
 
| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|---------------------|---------------------|------------|------------|----------|-------|------|
| 1        | 2020-06-14-00.00.00 | 2020-12-31-23.59.59 | 1          | 35455      | 0        | 35.50 | EUR  |
| 1        | 2020-06-14-15.00.00 | 2020-06-14-18.30.00 | 2          | 35455      | 1        | 25.45 | EUR  |
| 1        | 2020-06-15-00.00.00 | 2020-06-15-11.00.00 | 3          | 35455      | 1        | 30.50 | EUR  |
| 1        | 2020-06-15-16.00.00 | 2020-12-31-23.59.59 | 4          | 35455      | 1        | 38.95 | EUR  |
 
### Campos: 

* **BRAND_ID:** foreign key de la cadena del grupo (1 = ZARA).
* **START_DATE , END_DATE:** rango de fechas en el que aplica el precio tarifa indicado.
* **PRICE_LIST:** Identificador de la tarifa de precios aplicable.
* **PRODUCT_ID:** Identificador código de producto.
* **PRIORITY:** Desambiguador de aplicación de precios. Si dos tarifas coinciden en un rago de fechas se aplica la de mayor * prioridad (mayor valor numérico).
* **PRICE:** precio final de venta.
* **CURR:** iso de la moneda.

### Se pide:
Construir una aplicación/servicio en SpringBoot que provea una end point rest de consulta  tal que:
 
* Acepte como parámetros de entrada: fecha de aplicación, identificador de producto, identificador de cadena.
* Devuelva como datos de salida: identificador de producto, identificador de cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.
* Se debe utilizar una base de datos en memoria (tipo h2) e inicializar con los datos del ejemplo, (se pueden cambiar el nombre de los campos y añadir otros nuevos si se quiere, elegir el tipo de dato que se considere adecuado para los mismos).           
* Desarrollar unos test al endpoint rest que  validen las siguientes peticiones al servicio con los datos del ejemplo:                                                                                   
	+ Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA).
	+ Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA).
	+ Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA).
	+ Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA).
	+ Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA).
 
### Se valorará:

* Diseño y construcción del servicio.
* Calidad de Código.
* Resultados correctos en los test.

## Sobre el proyecto

### Decisiones tomadas durante el diseño

La prueba es sencilla, se pide a partir de una fecha, un producto y un identificador de cadana devolver el identificador del producto, el identificador de cadena, la tarifa a aplicar, las fechas de aplicación y el precio final a aplicar. 

Para un proyecto tan sencillo como este no hace falta implementar una arquitectura muy compleja. No obstante, he preferido realizar una implementación a priori más compleja siguiendo los patrones de diseño DDD y arquitectura hexagonal, con funcionalidad que no se solicita en el enunciado, con el objetivo de demostrar la capacidad de llevar a cabo una posible implementación real en la que los requisitos del proyecto sean mayores, adaptandose a lo que podría ser un proyecto real.

Para la implementación de este proyecto he decidido implementar todo de forma muy purista. Absolutamente, nada de la capa de dominio o aplicación dependen de elementos de infraestructura o elementos del framework. Por este motivo he sobreescrito la configuración establecida de forma predeterminada por String Boot para poder alojar todas las clases relacionadas con dicho framework dentro del paquete de clases de "infrastructure".

Esta decisión tiene sus ventajas e inconvenientes. Entre sus ventajas está la de que será una aplicación facil de testear, muy flexible, escalable y mantenible. Pudiendo cambiar por ejemplo la implementación de cualquiera de los repositorios de manera trivial y aislada. Si en un futuro tomamos la decisión de sustituir el repositorio de productos que tenemos en una base de datos alojada en memoria por una MongoDB o PostgreSQL, o se actualiza la librería de persistencia, solo sería necesario modificar o crear un nuevo adaptador para el puerto correspondiente. Sin afectar al resto del componentes del proyecto.

Por otro lado, el principal inconveniente es que la arquitectura del proyecto se vuelve más pesada y confusa a la hora de aplicar los diferentes frameworks, aumentando con ello la complejidad del proyecto. Por otro lado también existe una sobrecarga del código y puede ser más dificil de diseñar inicialmente, ya que requiere una compresión profunda de la lógica de negocio y de la infraestructura. 

Estas decisiones en relación a la arquitectura que se usará en un proyecto tienen por tanto un gran impacto en el mismo, y es algo que hay que analizar y sopesar muy detenidamente antes de su inicio, teniendo en cuenta diferentes factores:

* Dimensión del proyecto.
* Tiempo de vida del proyecto
* Necesidades de escalabilidad a futuro
* etc...


### Construido con:

* [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)
* [Spring Boot 3.0.4](https://spring.io/projects/spring-boot)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#using.devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web)
* [Maven](https://maven.apache.org/)
* [JUnit 5](https://junit.org/junit5/)
* [GitHub](https://github.com/)
* [Spring Tools 4 for Eclipse](https://spring.io/tools)  
* [SonarCloud](https://sonarcloud.io)

<!-- GETTING STARTED -->

## Empezando
### Prerequisitos
Debe tener instalado el siguiente software 
* Java 17
* Maven 3.8.1 o superior 
* GitHub console

### Installación
1. Clonar el repositorio
   ```sh
   git clone https://github.com/mallado/inditex-test.git
   ```
2. Compilar y arrancar el proyecto
   ```sh
   mvn spring-boot:run
   ```
   
### Pruebas
Para poder realizar llamadas al API puede acceder a swagger mediante la siguiente URL: http://localhost:8080/swagger-ui/index.html o importar el fichero [OpenAPI_definition.json](api/OpenAPI_definition.json) en Postman o similar. 


### Notas
Si desea ejecutar el proyecto desde eclipse es necesario que tenga instalada la extensión de Lombok. Para ello es necesario realizar los sigueintes pasos:
1. Vaya a la carpeta donde se encuentre el jar de Lombok (por ejemplo ~/.m2/repository/org/projectlombok/lombok/1.18.26/lombok-1.18.26.jar)
2. Ejecútelo (Ejemplo: java -jar lombok-1.18.26.jar)
3. Debería aparecer una ventana, busque la ubicación de eclipse.exe.
4. Haga clic en instalar.
5. Inicie Eclipse y actualice la configuración del proyecto 

<!-- CONTACT -->

## Contacto

Francisco José Mallado Muñoz [![image](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mallado@gmail.com) [![image](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/fmallado/) [![image](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/mallado) 

Project Link: [https://github.com/mallado/inditex-test](https://github.com/mallado/inditex-test) - [Javadoc](https://github.com/mallado/inditex-test/tree/master/doc)
