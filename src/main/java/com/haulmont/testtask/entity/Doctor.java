package com.haulmont.testtask.entity;

import javax.persistence.*;


@Entity
@Table(name = "doctor")
@NamedQueries({
        @NamedQuery(name = "Doctor.findAll", query = "select s from Doctor s"),
        @NamedQuery(name = "Doctor.findById", query = "select s from Doctor s where id = :id")
})
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "patronymic")
    private String lastName;
    @Column(name = "proficiency")
    private String specialization;

    public Doctor(){
    }
    public Long getId() { return id; }

    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public  String getSecondName() { return secondName; }
    public void setSecondName(String secondName) { this.secondName = secondName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String toString() { return id + " " + firstName + " " + secondName + " " + lastName + " " + specialization; }
}
