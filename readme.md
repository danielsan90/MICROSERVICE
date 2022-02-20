1) TI CREI UN PROGETTO PARENT CON ARCHETYPE MAVEN

mvn archetype:generate -DgroupId=com.daniele -DartifactId=my-app-services -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

2) entra nella cartella creata del progetto e lancia il comando tree per vedere se ha creato bene la struttura (src main e test);

3)apriti il progetto da intellij:

Quello che abbiamo creato è il parent project;
Per lavorare con i microservizi occorre il maven multi-module.
Significa che puoi cancellare la cartella source all'interno del progetto.
Il pom deve venire cosi: (ricordati che queste dipendenze saranno incluse nei sottoprogetti .. noi abbiamo messo lombook e starter test)

```
<xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.daniele</groupId>
  <artifactId>my-app-services</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>my-app-services</name>
  <!-- FIXME change it to the project's website -->
  <url>https://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <spring.boot.maven.plugin.version>2.5.7</spring.boot.maven.plugin.version>
    <spring.boot.dependecies.version>2.5.7</spring.boot.dependecies.version>
  </properties>

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>${spring.boot.dependecies.version}</version>
        <scope>import</scope>
        <type>pom</type>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <dependencies>
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
    </dependency>
  </dependencies>

  <build>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <plugin>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-maven-plugin</artifactId>
          <version>${spring.boot.maven.plugin.version}</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>
```
****
4) CLICK DESTRO SU PARENT PROJECT -> new .> module -> nomeModulo

Se vedi il pom del parent project noterai che è stato aggiunto il tag:

  <modules>
    <module>customer</module>
  </modules>

QUINDI PER OGNI MICROSERVIZIO AVREI UN NEW MODULE;
*************

Per far comunicare i microservizi è stato utilizzato RestTemplate ma si puo fare molto di meglio..

**
EUREKA SERVER   http://localhost:8761/

Abbiamo messo un nuovo microservizio eureka-server.. NEW MODULE EUREKA-SERVER

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

nella classe main aggiungi @EnableEurekaServer

Poi nel file properties

spring:
  application:
    name: eureka-server

server:
  port: 8761
eureka:
  client:
    fetch-registry: false
    register-with-eureka: false

A questo punto se lanci l'applicazione e vai su http://localhost:8761/ c'è la dashboard del server.

****

A questo punto dobbiamo registrare il client quindi aggiungiamo nel pom dei microservizi:

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

e nella classe application  main @EnableEurekaClient

Infine nel file di properties aggiungi:

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka

Lancia il microservizio e vedrai nei log -> DiscoveryClient_CUSTOMER/LAPTOP-H2S3U16Q:customer:8081 - registration status: 204

Se ora torni sulla pagine del server eureka vedrai che è stato registrato il microservizio sotto "Instances currently registered with Eureka"

***

@LoadBalanced

A questo punto hai registrato i client quindi non solo puoi avere più istanze del client(vedi come si fa) ma puoi anche specificare
il nome del servizio quando usi l'oggetto RestTemplate al posto dell'url

Es -> FraudResponse fraudResponse=restTemplate.getForObject(
                     "http://localhost:8082/api/v1/fraud-check/{customerId}",
                    ..
             );
       Diventa -> FraudResponse fraudResponse=restTemplate.getForObject(
                                       "http://FRAUD/api/v1/fraud-check/{customerId}",
                                       ..

Ora però che succede..
Se richiami il servizio che usa RestTemplate ti dà errore -> java.net.UnknownHostException: FRAUD

perche? -> RestTemplate è confuso. Non sa a quale istanza di fraud è indirizzata la richiesta(puoi avere piu istanze).
Per fixare nella configurazione di RestTemplate usi @LoadBalanced (https://www.studytonight.com/post/load-balancing-spring-boot-microservices-using-netflixs-ribbon)


**OPEN FEIGN

Feign makes writing java http clients easier -> https://github.com/OpenFeign/feign   https://spring.io/projects/spring-cloud-openfeign#overviewv

blablavla



