package ch.heigvd.res.pranksender.model.prank;

import ch.heigvd.res.pranksender.model.mail.Message;
import ch.heigvd.res.pranksender.model.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {


    private Person sender;
    private final List<Person> victims = new ArrayList<Person>();
    private final List<Person> witnesses = new ArrayList<Person>();
    private final List<Person> witnessesB = new ArrayList<Person>();
    private String message;


    /**
     * Adds a list of Person to the victims
     * @param people
     */
    public void addVictim(List<Person> people){
        victims.addAll(people);
    }

    /**
     * Adds a list of Person to the witnesses
     * @param people
     */
    public void addWitnesses(List<Person> people) {
        witnesses.addAll(people);
    }

    /**
     * Set a sender of the prank
     * @param sender the sender of the prank
     */
    public void setSender(Person sender) {
        this.sender = sender;
    }

    /**
     * Set the message of the prank
     * @param message the message of the prank
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Generate the message to be sent, based on the sender, the victims and witnesses
     * @return the message to be sent
     */
    public Message generateMessage(){
        Message msg = new Message();
        msg.setSubject(message.lines().findFirst().get().substring(9));

        msg.setBody(message.substring(message.indexOf("\n")) + "\r\n");
        msg.setTo(victims.stream().map(Person::getAddress).collect(Collectors.toList()).toArray(new String[]{}));
        msg.setCc(witnesses.stream().map(Person::getAddress).collect(Collectors.toList()).toArray(new String[]{}));
        msg.setBcc(witnessesB.stream().map(Person::getAddress).collect(Collectors.toList()).toArray(new String[]{}));
        msg.setFrom(sender.getAddress());

        return msg;
    }
}
