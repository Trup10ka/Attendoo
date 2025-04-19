FROM openjdk:17-jdk-slim

WORKDIR /attendoo

COPY dist/attendoo-0.0.1.jar /attendoo/attendoo-0.0.1.jar
COPY dist/resources /attendoo/resources

EXPOSE 5000

COPY dist/application.conf /attendoo/application.conf

CMD ["java", "-jar", "/attendoo/attendoo-0.0.1.jar"]
