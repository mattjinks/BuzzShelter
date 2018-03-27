package com.example.hp.buzzshelter;

import java.io.Serializable;

/**
 * Created by YizraGhebre on 3/5/18.
 */

public class Shelter implements Serializable {

    private String name;
    private String capacity;
    private String vacancies;
    private String restrictions;
    private String longitude;
    private String latitude;
    private String address;
    private String specialNotes;
    private String phoneNumber;


    public Shelter (String name, String capacity, String restrictions, String longitude, String latitude, String address, String specialNotes, String phoneNumber) {

        this.name = name;
        this.capacity = capacity;
        this.vacancies = capacity;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.specialNotes = specialNotes;
        this.phoneNumber = phoneNumber;


    }

    public String getName () {
        return name;
    }
    public void setName (String newName) {
        name = newName;
    }


    public String getCapacity () {
        return capacity;
    }
    public void setCapacity (String newCapacity) {
        capacity = newCapacity;
    }

    public String getVacancies () {
        return vacancies;
    }
    public void setVacancies (String newVacancies) {
        this.vacancies = newVacancies;
    }


    public String getRestrictions () {return restrictions;}
    public void setRestrictions (String newRestrictions) {
        restrictions = newRestrictions;
    }


    public String getLongitude () {
        return longitude;
    }
    public void setLongitude (String newLongitude) {
        longitude = newLongitude;
    }


    public String getLatitude () {
        return latitude;
    }
    public void setLatitude (String newLatitude) {
        latitude = newLatitude;
    }


    public String getAddress () {
        return address;
    }
    public void setAddress (String newAddress) {
        address = newAddress;
    }


    public String getSpecialNotes () {
        return specialNotes;
    }
    public void setSpecialNotes (String newNotes) {
        specialNotes = newNotes;
    }


    public String getPhoneNumber () {
        return phoneNumber;
    }
    public void setPhoneNumber (String newNumber) {
        phoneNumber = newNumber;
    }


}
