package ch.heigvd.res.pranksender.model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private  final List<Person> members = new ArrayList<>();

    /**
     * Get the members of the group
     * @return a list of the members of the group
     */
    public List<Person> getMembers(){
        return new ArrayList<>(members);
    }

    public void addMember(Person member){
        members.add(member);
    }
}
