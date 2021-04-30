FROM openjdk:8-jre
MAINTAINER Carl Penalva <carl.penalva@heig-vd.ch>
  
#
# When we build the image, we copy the executable jar in the image file system. 
#
COPY MockMock-1.4.0.one-jar.jar /opt/app/smtpmockserver.jar

#
# Our application will accept TCP connections on port 2205. Note that the EXPOSE statement
# does not make the container accessible via the host. For the container to really be accessible,
# we must either use the -p or the -P flag when using the docker run command. With -p, we can
# specify an explicit port mapping (and EXPOSE is not even required). With -P, we let Docker 
# assign random ports for each EXPOSEd port. We can then use the docker port command to know the port
# numbers that have been selected.
#
EXPOSE 2525 8083

#
# This is the command that is executed when the Docker container starts
#

CMD ["java", "-jar", "/opt/app/smtpmockserver.jar", "-p", "2525", "-h", "8083"]
