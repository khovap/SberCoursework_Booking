FROM openjdk:17

ARG version=0.0.1-SNAPSHOT

COPY ./target/BookingSource_main-$version.jar /usr/app/BookingSource_main.jar

WORKDIR /usr/app
USER root


#RUN java BookingSource_main.jar

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

ENTRYPOINT ["java", "-jar", "/usr/app/BookingSource_main.jar"]

ENV RUN_DEBUG=true;
EXPOSE 8080
