FROM tomcat:10.1-jdk22-temurin

# envs var config
ENV DB_URL=jdbc:postgresql://db:5432/sgt_db
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=secret

RUN apt-get update && apt-get install -y curl

# db driver
RUN curl -L https://jdbc.postgresql.org/download/postgresql-42.7.3.jar -o /usr/local/tomcat/lib/postgresql-42.7.3.jar

# dependencies
RUN curl -L https://repo1.maven.org/maven2/org/glassfish/jakarta.faces/4.1.0/jakarta.faces-4.1.0.jar -o /usr/local/tomcat/lib/jakarta.faces-4.1.0.jar
RUN curl -L https://repo1.maven.org/maven2/org/primefaces/primefaces/14.0.0/primefaces-14.0.0-jakarta.jar -o /usr/local/tomcat/lib/primefaces-14.0.0-jakarta.jar
RUN curl -L https://repo1.maven.org/maven2/org/hibernate/orm/hibernate-core/7.0.0.Alpha3/hibernate-core-7.0.0.Alpha3.jar -o /usr/local/tomcat/lib/hibernate-core-7.0.0.Alpha3.jar
RUN curl -L https://repo1.maven.org/maven2/org/hibernate/validator/hibernate-validator/8.0.1.Final/hibernate-validator-8.0.1.Final.jar -o /usr/local/tomcat/lib/hibernate-validator-8.0.1.Final.jar
RUN curl -L https://repo1.maven.org/maven2/org/jboss/weld/weld-servlet-core/6.0.0.Beta4/weld-servlet-core-6.0.0.Beta4.jar -o /usr/local/tomcat/lib/weld-servlet-core-6.0.0.Beta4.jar


COPY target/sgt-esig.war /usr/local/tomcat/webapps/sgt.war


EXPOSE 8080

# run tomcat
CMD ["catalina.sh", "run"]
