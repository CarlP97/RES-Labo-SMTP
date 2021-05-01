package ch.heigvd.res.pranksender.model.prank;

import ch.heigvd.res.pranksender.model.mail.Message;
import ch.heigvd.res.pranksender.model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {


    private Person sender;
    private List<Person> victims = new ArrayList<Person>();
    private List<Person> witnesses = new ArrayList<Person>();
    private String message;


    public void addVictim(List<Person> people){
        victims.addAll(people);
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public void addWitnesses(List<Person> people) {
        witnesses.addAll(people);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message generateMessage(){
        Message msg = new Message();

        msg.setBody(this.message + "\r\n" + sender.getFirstname());
        msg.setTo(victims.stream().map(Person::getAddress).collect(Collectors.toList()).toArray(new String[]{}));
        msg.setCc(witnesses.stream().map(Person::getAddress).collect(Collectors.toList()).toArray(new String[]{}));
        msg.setFrom(sender.getAddress());

        return msg;
    }
}
