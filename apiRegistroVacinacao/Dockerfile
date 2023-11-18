FROM openjdk:17-ea-3-jdk-slim-buster
VOLUME /tmp
ADD target/RegistroVacinacao-0.0.1-SNAPSHOT.jar RegistroVacinacao-0.0.1-SNAPSHOT.jar 

RUN bash -c 'touch /RegistroVacinacao-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/RegistroVacinacao-0.0.1-SNAPSHOT.jar"]

