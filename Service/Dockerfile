FROM openjdk:8

WORKDIR /service/bin

ENV PORT 6000

COPY /target/classes /service/bin/classes
COPY /target/dependency /service/bin/dependency
RUN mkdir keystores
COPY keystores/* /service/bin/keystores/

CMD ["java","-cp","./classes:./dependency/*","edu.escuelaing.arep.SparkWebApp"]