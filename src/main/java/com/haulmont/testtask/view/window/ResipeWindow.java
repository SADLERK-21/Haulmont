package com.haulmont.testtask.view.window;

import com.haulmont.testtask.dao.DoctorDAO;
import com.haulmont.testtask.dao.ResipeDAO;
import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Precedence;
import com.haulmont.testtask.entity.Resipe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;

import java.util.Arrays;

public class ResipeWindow extends AbstractWindow {

    private Resipe resipe;
    private ResipeDAO resipeDAO;
    private Grid grid;

    private DoctorDAO doctorDAO;
    private PatientDAO patientDAO;

    private TextField descriptionField = new TextField("Description");
    private TextField validityField = new TextField("Validity");
    private TextField dateField = new TextField("Date");

    private ComboBox listDoctor = new ComboBox("Doctor");
    private ComboBox listPatient = new ComboBox("Patient");
    private ComboBox listPrecedence = new ComboBox("Priority");


    public ResipeWindow(ResipeDAO resipeDAO, DoctorDAO doctorDAO, PatientDAO patientDAO, Grid grid) {
        this(null, resipeDAO, doctorDAO, patientDAO, grid);
    }

    public ResipeWindow(Resipe resipe, ResipeDAO resipeDAO, DoctorDAO doctorDAO, PatientDAO patientDAO, Grid grid) {
        this.resipeDAO = resipeDAO;
        this.doctorDAO = doctorDAO;
        this.patientDAO = patientDAO;
        this.grid = grid;
        this.resipe = resipe;

        initComponents();
    }

    private void initComponents() {
        initButtons();
        initFields();
        configMainLayout();

    }

    private void initFields() {
        configBasicLayout();
        configFields();

        if (this.resipe != null) {

            this.descriptionField.setValue(this.resipe.getDefinition());
            this.validityField.setValue(Integer.toString(this.resipe.getDuration()));
            this.dateField.setValue(this.resipe.getCreationDate());

            this.listDoctor.select(this.resipe.getDoctor());
            this.listPatient.select(this.resipe.getPatient());
            this.listPrecedence.select(this.resipe.getPrecedence());

        } else {
            this.resipe = new Resipe();
        }
    }

    private void configMainLayout() {
        configBasicLayout();

        this.main.addComponent(descriptionField);
        this.main.addComponent(validityField);
        this.main.addComponent(dateField);
        this.main.addComponent(listDoctor);
        this.main.addComponent(listPatient);
        this.main.addComponent(listPrecedence);
        this.main.addComponent(buttons);

        setContent(this.main);

    }

    private void configFields() {
        this.listDoctor.setNullSelectionAllowed(false);
        this.listDoctor.setFilteringMode(FilteringMode.CONTAINS);

        this.listPatient.setNullSelectionAllowed(false);
        this.listPatient.setFilteringMode(FilteringMode.CONTAINS);

        this.listPrecedence.setNullSelectionAllowed(false);

        this.validityField.addValidator(new RegexpValidator("^[1-9]+[0-9]*$", "Validity invalid"));

        doctorDAO.getAll().forEach(doctor -> listDoctor.addItem(doctor));

        patientDAO.getAll().forEach(patient -> listPatient.addItem(patient));

        Arrays.stream(Precedence.values()).forEach(precedence -> listPrecedence.addItem(precedence));

    }

    private void initButtons() {
        initCancelButton();
        initOkButton();
    }

    private void initOkButton() {
        this.okButton.addClickListener((Button.ClickListener) clickEvent -> {
            this.validityField.validate();
            if (!this.validityField.isValid()) {
                Notification.show("Validity is invalid");
                return;
            }

            updateResipe();
            refreshGrid();
            this.close();
        });
    }

    private void updateResipe() {
        this.resipe.setDoctor((Doctor) this.listDoctor.getValue());
        this.resipe.setCreationDate(this.dateField.getValue());
        this.resipe.setPatient((Patient) this.listPatient.getValue());
        this.resipe.setDefinition(this.descriptionField.getValue());
        this.resipe.setDuration(Integer.parseInt(this.validityField.getValue()));
        this.resipe.setPatient((Patient) this.listPatient.getValue());

        this.resipeDAO.insertOrUpdate(resipe);

    }

    private void refreshGrid() {
        this.grid.removeAllColumns();
        this.grid.setContainerDataSource(
                new BeanItemContainer<>(Resipe.class, this.resipeDAO.getAll()));
    }
}