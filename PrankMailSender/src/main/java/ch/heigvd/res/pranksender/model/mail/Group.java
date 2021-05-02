package ch.heigvd.res.pranksender.model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private  final List<Person> members = new ArrayList<>();

    /**
     * Get a list of the members of the group
     * @return
     */
    public List<Person> getMembers(){
        return new ArrayList<>(members);
    }

    public void addMember(Person member){
        members.add(member);
    }
}
