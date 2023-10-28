FROM openjdk:17-ea-3-jdk-slim-buster
VOLUME /tmp
ADD target/apiVacina-0.0.1-SNAPSHOT.jar apiVacina-0.0.1-SNAPSHOT.jar 
EXPOSE 8087
RUN bash -c 'touch /apiVacina-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/apiVacina-0.0.1-SNAPSHOT.jar"]