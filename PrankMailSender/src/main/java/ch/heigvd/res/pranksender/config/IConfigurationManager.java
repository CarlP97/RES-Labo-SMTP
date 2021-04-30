package ch.heigvd.res.pranksender.config;

import ch.heigvd.res.pranksender.model.mail.Person;

import java.util.List;

public interface IConfigurationManager {
    List<Person> getVictims();
    List<String> getMessages();
    int getNumberOfGroups();
    String getSmtpServerAddress();
    int getSmtpServerPort();
    List<Person> getCcs();
}
