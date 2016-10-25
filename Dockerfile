FROM java:8
VOLUME /tmp
ADD build/libs/itest.jar itest.jar
RUN bash -c 'touch /itest.jar'
ENTRYPOINT ["java","-jar","/itest.jar"]