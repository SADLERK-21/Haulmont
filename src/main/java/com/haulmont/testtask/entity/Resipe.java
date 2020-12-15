package com.haulmont.testtask.entity;

import javax.persistence.*;


@Entity
@Table(name = "resipe")
@NamedQueries({
        @NamedQuery(name = "Resipe.findAll", query = "select s from Resipe s"),
        @NamedQuery(name = "Resipe.findById", query = "select s from Resipe s where id = :id")
})
public class Resipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String definition;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "creation_date")
    private String creationDate;

    @Column(name = "validity")
    private int duration;

    @Column(name= "priority")
    @Enumerated(EnumType.STRING)
    private Precedence precedence;

    public Resipe(){
    }

    public String getDefinition(){ return definition; }
    public void setDefinition(String definition) { this.definition = definition; }

    public  Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public String getCreationDate() { return creationDate; }
    public void setCreationDate(String creationDate) { this.creationDate = creationDate; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public Precedence getPrecedence() { return precedence; }
    public void setPrecedence(Precedence precedence) { this.precedence = precedence; }

    public Long getId() { return  id; }

    public String toString() { return id + " " + definition + " " + patient + " " + doctor + " " + creationDate + " " + duration + " " + precedence; }
}
