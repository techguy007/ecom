FROM openjdk:17

COPY target/ecommerce-0.0.1-SNAPSHOT.jar ecommerce-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar", "/ecommerce-0.0.1-SNAPSHOT.jar"]
