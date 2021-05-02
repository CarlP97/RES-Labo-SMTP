# RES-Labo-SMTP

## Description

This project is a tool for running a prank campaign by email. It gives you a java client that sends fake messages to a list of victims. The content of the messages, the list of victims, the CCs and BCCs are all configurable.

The campaign is run like this : you make a list of victims, a list of prank to send, a list of CCs and BCCs that will be witnesses to your prank and you choose a number of groups. The victims are placed in the different groups. Among the members of each group, one is chosen to be the sender of the prank. A prank from the list is then sent  from the sender to the other members of his group, with all of the CCs and BCCs.

## Installation of a mock SMTP server

The client needs a connection to a SMTP server to be working. If you want to test your pranks before sending them for real  or just have fun with the tool without consequences, you should use a [mock](https://en.wikipedia.org/wiki/Mock_object) SMTP server. A docker image of the project [MockMock](https://github.com/tweakers/MockMock) is included for this purpose. In the directory `docker` you need to first run the script `build-image.sh` then `run-container.sh`. By default, the image makes the SMTP server run on port 2525 and the web interface on port 8083. You can change that by modifying the command in the script `run-container.sh` like that :

`docker run -p <SMTP_port>:2525 -p <web_port>:8083 mockmock-server`

Learn how to use the MockMock server on their page : [MockMock](https://github.com/tweakers/MockMock).

## Configuration

Before running the prank campaign, you must make some configurations in the PrankMailSender/config files :

* config.properties :

  in this file you can set the address and port of the smtp server to connect to, the number of groups and the addresses to CC and BCC.

  The names of the properties must not be changed.

* messages.utf8 :

  In this file you write all the pranks to send. Pranks are delimited by `---` and they should  start with `Subject: <mysubject>`  :

  ```
  Subject: <my subject>
  
  content of the email
  
  ---
  ```

* victims.utf8 :

  In this file you write all the addresses that will send or receive pranks. One address by line. Keep in mind that each group is composed of at minimum 3 people, so numberOfVictims / numberOfGroups must be equal or greater than 3.

## Implementation

