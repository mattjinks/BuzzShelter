package com.example.hp.buzzshelter;

import java.io.Serializable;

/**
 * Created by HP on 3/20/2018.
 */

public class HomelessPerson extends User implements Serializable {

    private String gender;
    private String age;
    private Boolean reserved = false;

    public HomelessPerson(String name, String loginEmail,
                          String password, String gender, String age) {
        super(name, loginEmail, password);
        this.age = age;
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setReservation(Boolean reservation) { this.reserved = reservation; }

    public Boolean hasReservation() { return reserved; }

    public String getGender() { return gender; }

    public String getAge() { return age; }


}
