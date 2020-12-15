package com.haulmont.testtask.view.window;

import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.entity.Patient;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class PatientWindow extends AbstractWindow {

    private Patient patient;
    private PatientDAO patientDAO;
    private Grid grid;

    private TextField nameField = new TextField("Name");
    private TextField secondNameField = new TextField("Second Name");
    private TextField lastNameField = new TextField("Last Name");
    private TextField phoneNumberField = new TextField("Phone Number");

    public PatientWindow(PatientDAO patientDAO, Grid grid) {
        this(patientDAO, null, grid);
    }

    public PatientWindow(PatientDAO patientDAO, Patient patient, Grid grid) {

        this.patientDAO = patientDAO;
        this.patient = patient;
        this.grid = grid;

        initComponents();
    }

    private void initComponents() {
        initFields();
        initButtons();
        configMainLayout();
    }

    private void configMainLayout() {
        configBasicLayout();

        this.main.addComponent(this.nameField);
        this.main.addComponent(this.secondNameField);
        this.main.addComponent(this.lastNameField);
        this.main.addComponent(this.phoneNumberField);

        this.main.addComponent(this.buttons);

        setContent(this.main);
    }

    private void initFields() {
        if (this.patient != null) {
            this.nameField.setValue(this.patient.getFirstName());//TODO Ниже доделай: Done
            this.secondNameField.setValue(this.patient.getSecondName());
            this.lastNameField.setValue(this.patient.getLastName());
            this.phoneNumberField.setValue(this.patient.getPhoneNumber());
        } else {
            this.patient = new Patient();
        }
    }

    private void initOkButton() {
        this.okButton.addClickListener((Button.ClickListener) clickEvent -> {
            updatePatient();
            refreshGrid();
            this.close();
        });
    }

    private void initButtons() {
        initCancelButton();
        initOkButton();
    }

    private void refreshGrid() {
        this.grid.removeAllColumns();
        this.grid.setContainerDataSource(
                new BeanItemContainer<>(Patient.class, this.patientDAO.getAll()));
    }

    private void updatePatient() {
        this.patient.setFirstName(this.nameField.getValue());
        this.patient.setSecondName(this.secondNameField.getValue());
        this.patient.setLastName(this.lastNameField.getValue());
        this.patient.setPhoneNumber(this.phoneNumberField.getValue());

        this.patientDAO.insertOrUpdate(patient);
    }


}
