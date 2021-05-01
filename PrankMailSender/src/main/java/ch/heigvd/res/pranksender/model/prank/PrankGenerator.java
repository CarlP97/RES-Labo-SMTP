package ch.heigvd.res.pranksender.model.prank;

import ch.heigvd.res.pranksender.config.IConfigurationManager;
import ch.heigvd.res.pranksender.model.mail.Group;
import ch.heigvd.res.pranksender.model.mail.Message;
import ch.heigvd.res.pranksender.model.mail.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrankGenerator {

    private IConfigurationManager configurationManager;


    public PrankGenerator(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public List<Prank> generatePrank(){
        List<Prank> pranks = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        int index = 0;

        int nbOfGroups = 0;

        List<Group> groups = generateGroups(configurationManager.getVictims(), nbOfGroups);

        for(Group group : groups){
            Prank prank = new Prank();
            List<Person> victims = group.getMembers();
            Collections.shuffle(victims);
            Person sender = victims.remove(0);
            prank.setSender(sender);
            prank.addVictim(victims);
            prank.addWitnesses(configurationManager.getCcs());

            String message = messages.get(index);
            index = (index + 1) % messages.size();
            prank.setMessage(message);

            pranks.add(prank);
        }
        return pranks;
    }

    public List<Group> generateGroups(List<Person> victims, int nbOfGroups){
        if((victims.size() / nbOfGroups) < 3){
            nbOfGroups = victims.size() / 3;
            System.out.println("Not enough victims for this number of groups");
        }
        List<Person> availabeVictims = new ArrayList<>(victims);
        List<Group> groups = new ArrayList<>();
        for(int i = 0; i < nbOfGroups; ++i){
            groups.add(new Group());
        }
        int index = 0;
        while(availabeVictims.size() > 0){
            groups.get(index).addMember(availabeVictims.remove(0));
            index = (index + 1) % groups.size();
        }
        return groups;
    }
}
