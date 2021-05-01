package ch.heigvd.res.pranksender.config;

import ch.heigvd.res.pranksender.model.mail.Person;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
public class ConfigurationManager implements IConfigurationManager {
    private String smtpServerAddress;
    private int smtpServerPort;
    private final List<Person> victims;
    private final List<String> messages;
    private int numberOfGroups;
    private List<Person> ccs;

    public ConfigurationManager() throws IOException {
        victims = loadAddressesFromFile("./config/victims.utf8");
        messages = loadMessagesFromFile("./config/messages.utf8");
        loadProperties("./config/config.properties");
    }

    private void loadProperties(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        Properties props = new Properties();
        props.load(fis);
        smtpServerAddress = props.getProperty("smtpServerAddress");
        smtpServerPort = Integer.parseInt(props.getProperty("smtpServerPort"));
        numberOfGroups = Integer.parseInt(props.getProperty("numberOfGroups"));

        ccs = new ArrayList<>();
        String witnessesToCC = props.getProperty("witnessesToCC");
        String[] witnessesAdresses = witnessesToCC.split(",");
        for(String s : witnessesAdresses){
            ccs.add(new Person(s));
        }

    }

    private List<Person> loadAddressesFromFile(String filename) throws IOException {
        List<Person> result;
        try (FileInputStream fis = new FileInputStream(filename)){
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(isr)){
                result = new ArrayList<>();
                String address = reader.readLine();
                while(address != null){
                    result.add(new Person(address));
                    address = reader.readLine();
                }
            }
        }
        return result;
    }

    private List<String> loadMessagesFromFile(String filename) throws IOException {
        List<String> result;
        try (FileInputStream fis = new FileInputStream(filename)){
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(isr)){
                result = new ArrayList<>();
                String line = reader.readLine();
                while(line != null){
                    StringBuilder body = new StringBuilder();
                    while((line != null) && (!line.equals("---"))){
                        body.append(line);
                        body.append(System.lineSeparator());
                        line = reader.readLine();
                    }
                    result.add(body.toString());
                    line = reader.readLine();
                }
            }
        }
        return result;
    }

}
