package ch.heigvd.res.pranksender.model.mail;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    private String firstname;
    private String lastname;
    private String address;

    public Person(String address){
        this.address = address;
        Pattern pattern = Pattern.compile("(.*)\\.(.*)@");
        Matcher matcher = pattern.matcher(address);
        boolean found = matcher.find();
        if(found) {
            firstname = matcher.group(1);
            firstname = firstname.substring(0,1).toUpperCase() + firstname.substring(1);
            lastname = matcher.group(2);
            lastname = lastname.substring(0,1).toUpperCase() + lastname.substring(1);
        }

    }
}
