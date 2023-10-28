FROM openjdk:17-ea-3-jdk-slim-buster
VOLUME /tmp
ADD target/apiPaciente-0.0.1-SNAPSHOT.jar apiPaciente-0.0.1-SNAPSHOT.jar 
EXPOSE 8085
RUN bash -c 'touch /apiPaciente-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/apiPaciente-0.0.1-SNAPSHOT.jar"]