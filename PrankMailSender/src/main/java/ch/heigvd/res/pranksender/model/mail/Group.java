package ch.heigvd.res.pranksender.model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private  final List<Person> members = new ArrayList<>();

    public List<Person> getMembers(){
        return new ArrayList<>(members);
    }

    public void addMember(Person member){
        members.add(member);
    }
}
