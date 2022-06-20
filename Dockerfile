FROM tomcat:8.0.51-jre8-alpine

RUN rm -rf /usr/local/tomcat/webapps/*

COPY . .

EXPOSE 8080

CMD ["catalina.sh", "run"]

ENTRYPOINT ["java","-jar","./target/REGISTRATION-0.0.1-SNAPSHOT.war"]