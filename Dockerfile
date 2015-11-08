FROM java:openjdk-8u66-jre
MAINTAINER Pascal Grimaud <pascalgrimaud@gmail.com>

COPY target/hello-jhipster-2.23.0.war hello-jhipster-2.23.0.war

EXPOSE 8080
CMD ["java","-jar","hello-jhipster-2.23.0.war"]
