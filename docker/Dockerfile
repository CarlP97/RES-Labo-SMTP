FROM openjdk:8-jre
  
#
# When we build the image, we copy the executable jar in the image file system. 
#
COPY MockMock-1.4.0.one-jar.jar /opt/app/smtpmockserver.jar


EXPOSE 2525 8083

#
# This is the command that is executed when the Docker container starts
#

CMD ["java", "-jar", "/opt/app/smtpmockserver.jar", "-p", "2525", "-h", "8083"]
