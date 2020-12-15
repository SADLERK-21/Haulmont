package com.haulmont.testtask.entity;

import javax.persistence.*;


@Entity
@Table(name = "patient")
@NamedQueries({
        @NamedQuery(name = "Patient.findAll", query = "select s from Patient s"),
        @NamedQuery(name = "Patient.findById", query = "select s from Patient s where id = :id")
})
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name_p")
    private String firstName;
    @Column(name = "second_name_p")
    private String secondName;
    @Column(name = "patronymic_p")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;

    public Patient(){
    }

    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public  String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Long getId() { return id; }

    public String toString() { return id + " " + firstName + " " + secondName + " " + lastName + " " + phoneNumber; }
}
