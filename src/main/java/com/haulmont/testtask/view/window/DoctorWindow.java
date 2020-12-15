package com.haulmont.testtask.view.window;

import com.haulmont.testtask.dao.DoctorDAO;
import com.haulmont.testtask.entity.Doctor;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class DoctorWindow extends AbstractWindow{
    private static final int MAX_NAME_LENGTH = 50;

    private Doctor doctor;
    private DoctorDAO doctorDAO;
    private Grid grid;

    private TextField firstNameField = new TextField("FirstName");
    private TextField surnameField = new TextField("SurnameField");
    private TextField lastNameField = new TextField("LastName");
    private TextField proficiency = new TextField("Proficiency");

    public DoctorWindow(DoctorDAO doctorDAO, Grid grid){
        this(doctorDAO,null,grid);
    }

    public DoctorWindow(DoctorDAO doctorDAO, Doctor doctor,Grid grid){
        this.doctorDAO = doctorDAO;
        this.doctor = doctor;
        this.grid = grid;

        initComponents();
    }

    private void initComponents(){
        initField();
        initButtons();
        configMainLayout();
    }

    private void initField(){
        configFields();
        if (this.doctor != null){
            this.firstNameField.setValue(this.doctor.getFirstName());
            this.surnameField.setValue(this.doctor.getSecondName());
            this.lastNameField.setValue(this.doctor.getLastName());
            this.proficiency.setValue(this.doctor.getSpecialization());
        }
        else{
            this.doctor = new Doctor();
        }

    }

    private void refreshGrid() {
        this.grid.removeAllColumns();
        this.grid.setContainerDataSource(
                new BeanItemContainer<>(Doctor.class, this.doctorDAO.getAll()));
    }

    private void updateAuthor(){
        this.doctor.setFirstName(this.firstNameField.getValue());
        this.doctor.setSecondName(this.surnameField.getValue());
        this.doctor.setLastName(this.lastNameField.getValue());
        this.doctor.setSpecialization(this.proficiency.getValue());
        this.doctorDAO.insertOrUpdate(doctor);
    }

    private void configFields(){
        this.firstNameField.setMaxLength(MAX_NAME_LENGTH);
        this.surnameField.setMaxLength(MAX_NAME_LENGTH);
        this.lastNameField.setMaxLength(MAX_NAME_LENGTH);
        this.proficiency.setMaxLength(MAX_NAME_LENGTH);
    }

    private void initButtons(){
        initCancelButton();
        initOkButton();

    }

    private void initOkButton(){
        this.okButton.addClickListener((Button.ClickListener) clickEvent -> {//Make some thing
            updateAuthor();
            refreshGrid();
            this.close();
        });
    }

    private void configMainLayout(){
        configBasicLayout();

        this.main.addComponent(this.firstNameField);
        this.main.addComponent(this.surnameField);
        this.main.addComponent(this.lastNameField);
        this.main.addComponent(this.proficiency);

        this.main.addComponent(buttons);


        setContent(this.main);
    }
}
