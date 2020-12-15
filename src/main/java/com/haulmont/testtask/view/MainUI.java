package com.haulmont.testtask.view;

import com.haulmont.testtask.DAOFactory;
import com.haulmont.testtask.dao.DoctorDAO;
import com.haulmont.testtask.dao.ResipeDAO;
import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.entity.Doctor;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Precedence;
import com.haulmont.testtask.entity.Resipe;
import com.haulmont.testtask.view.window.PatientStatisticWindow;
import com.haulmont.testtask.view.window.DoctorWindow;
import com.haulmont.testtask.view.window.PatientWindow;
import com.haulmont.testtask.view.window.ResipeWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.*;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    HorizontalLayout layout = new HorizontalLayout();

    private TabSheet tabSheet = new TabSheet();

    private VerticalLayout doctorTab = new VerticalLayout();
    private VerticalLayout resipeTab = new VerticalLayout();
    private VerticalLayout patientTab = new VerticalLayout();

    private VerticalLayout controlButtonLayout = new VerticalLayout();

    private Grid doctorGrid = new Grid();
    private Grid resipeGrid = new Grid();
    private Grid patientGrid = new Grid();

    private Button addButton = new Button("Add object");
    private Button changeButton = new Button("Change selected");
    private Button deleteButton = new Button("Delete selected");
    private Button refreshButton = new Button("Refresh grid");

    private Button patientStatisticButton = new Button("Get recipe statistics");


    private DoctorDAO doctorDAO;
    private ResipeDAO resipeDAO;
    private PatientDAO patientDAO;

    private static final String COMPONENT_WIDTH = "230px";
    private static final String TAB_WIDTH = "100%";
    private static final String GRID_WIDTH = "100%";


    private static final Logger log = LogManager.getLogger(MainUI.class);

    @Override
    protected void init(VaadinRequest request) {
        this.doctorDAO = DAOFactory.getDoctorDAO();
        this.resipeDAO = DAOFactory.getResipeDAO();
        this.patientDAO = DAOFactory.getPatientDAO();
        layout.setSizeFull();
        layout.setMargin(true);
        initControlButtonsLayout();
        initTabsWithGrid();
        this.layout.addComponent(tabSheet);
        this.layout.addComponent(this.controlButtonLayout);
        this.layout.setMargin(true);
        setContent(layout);
    }

    private void initControlButtonsLayout() {
        this.controlButtonLayout.setWidthUndefined();
        this.controlButtonLayout.setSpacing(true);
        this.controlButtonLayout.setMargin(true);
        this.controlButtonLayout.addComponent(addButton);
        this.controlButtonLayout.addComponent(changeButton);
        this.controlButtonLayout.addComponent(deleteButton);
        this.controlButtonLayout.addComponent(refreshButton);

        initAddButton();
        initChangeButton();
        initDeleteButton();
        initRefreshButton();
        initGenreStatisticsButton();
    }

    private void initTabsWithGrid() {
        initTabSheetTabs();
        updateDoctorGrid();
        updateResipeGrid();
        updatePatientGrid();
    }

    private void updateDoctorGrid() {
        this.doctorGrid.removeAllColumns();
        Collection<Doctor> collection = this.doctorDAO.getAll();
        BeanItemContainer<Doctor> container = new BeanItemContainer<>(Doctor.class, collection);
        this.doctorGrid.setContainerDataSource(Objects.requireNonNull(container));
        doctorGrid.setColumnOrder("id", "firstName", "secondName", "lastName", "specialization");
    }

    private void updateResipeGrid() {
        this.resipeGrid.removeAllColumns();
        Collection<Resipe> collection = this.resipeDAO.getAll();
        BeanItemContainer<Resipe> container = new BeanItemContainer<>(Resipe.class, collection);
        this.resipeGrid.setContainerDataSource(Objects.requireNonNull(container));
        resipeGrid.setColumnOrder("id", "definition", "patient", "doctor", "creationDate", "duration", "precedence");
    }

    private void updatePatientGrid() {
        this.patientGrid.removeAllColumns();
        Collection<Patient> collection = this.patientDAO.getAll();
        BeanItemContainer<Patient> container = new BeanItemContainer<>(Patient.class, collection);
        this.patientGrid.setContainerDataSource(Objects.requireNonNull(container));
        patientGrid.setColumnOrder("id", "firstName", "secondName", "lastName", "phoneNumber");
    }

    private void initTabSheetTabs() {
        this.doctorGrid.setWidth(GRID_WIDTH);
        this.doctorTab.addComponent(this.doctorGrid);
        this.resipeTab.addComponent(this.resipeGrid);
        this.resipeTab.addComponent(getFilterPanel());
        this.resipeGrid.setWidth(GRID_WIDTH);
        this.patientTab.addComponent(this.patientGrid);
        this.resipeTab.addComponent(this.patientStatisticButton);
        this.doctorTab.setWidth(TAB_WIDTH);
        this.resipeTab.setWidth(TAB_WIDTH);
        this.patientGrid.setWidth(GRID_WIDTH);
        this.tabSheet.setWidth(TAB_WIDTH);
        this.tabSheet.addTab(doctorTab, "Doctor", null);
        this.tabSheet.addTab(resipeTab, "Recipe", null);
        this.tabSheet.addTab(patientTab, "Patient", null);
        this.tabSheet.setWidth("100%");

    }

    private void initAddButton() {
        this.addButton.getListeners(Button.ClickEvent.class)
                .forEach(listener -> this.addButton.removeListener(Button.ClickEvent.class, listener));
        this.addButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (this.tabSheet.getSelectedTab().equals(this.doctorTab)) {
                addWindow(new DoctorWindow(doctorDAO, patientGrid));
            } else if (this.tabSheet.getSelectedTab().equals(this.resipeTab)) {
                addWindow(new ResipeWindow(resipeDAO, doctorDAO, patientDAO, patientGrid));
            } else if (this.tabSheet.getSelectedTab().equals(this.patientTab)) {
                addWindow(new PatientWindow(patientDAO, patientGrid));
            }
            log.info("Click event");
        });
    }

    private void initChangeButton() {
        this.changeButton.getListeners(Button.ClickEvent.class)
                .forEach(listener -> this.changeButton.removeListener(Button.ClickEvent.class, listener));
        this.changeButton.addClickListener((Button.ClickListener) clickEvent -> {

            if (this.tabSheet.getSelectedTab().equals(this.doctorTab)) {
                if (doctorGrid.getSelectedRow() == null) {
                    Notification.show("Doctor not selected");
                } else {
                    addWindow(new DoctorWindow(doctorDAO, (Doctor) doctorGrid.getSelectedRow(), patientGrid));
                }
            } else if (this.tabSheet.getSelectedTab().equals(this.resipeTab)) {
                if (resipeGrid.getSelectedRow() == null) {
                    Notification.show("Recipe not selected");
                } else {
                    addWindow(new ResipeWindow((Resipe) resipeGrid.getSelectedRow(), resipeDAO, doctorDAO, patientDAO, patientGrid));
                }
            } else if (this.tabSheet.getSelectedTab().equals(this.patientTab)) {
                if (patientGrid.getSelectedRow() == null) {
                    Notification.show("Patient not selected");
                } else {
                    addWindow(new PatientWindow(patientDAO, (Patient) this.patientGrid.getSelectedRow(), patientGrid));
                }
            }
            log.info("Click event");
        });
    }

    private void initDeleteButton() {
        this.deleteButton.getListeners(Button.ClickEvent.class)
                .forEach(listener -> this.deleteButton.removeListener(Button.ClickEvent.class, listener));
        this.deleteButton.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                if (this.tabSheet.getSelectedTab().equals(this.doctorTab)) {
                    if (doctorGrid.getSelectedRow() != null) {
                        doctorDAO.remove((Doctor) doctorGrid.getSelectedRow());
                        updateDoctorGrid();
                    } else {
                        Notification.show("Doctor not selected");
                    }
                } else if (this.tabSheet.getSelectedTab().equals(this.patientTab)) {
                    if (patientGrid.getSelectedRow() != null) {
                        patientDAO.remove((Patient) patientGrid.getSelectedRow());
                        updatePatientGrid();
                    } else {
                        Notification.show("Patient not selected");
                    }
                } else if (this.tabSheet.getSelectedTab().equals(this.resipeTab)) {
                    if (resipeGrid.getSelectedRow() != null) {
                        resipeDAO.remove((Resipe) resipeGrid.getSelectedRow());
                        updateResipeGrid();
                    } else {
                        Notification.show("Recipe not selected");
                    }
                }
            } catch (Exception exception) {
                Notification.show("Can't remove selected object");
                return;
            }
            Notification.show("Entity successfully removed");

        });
    }

    private void initRefreshButton() {
        this.refreshButton.getListeners(Button.ClickEvent.class)
                .forEach(listener -> this.refreshButton.removeListener(Button.ClickEvent.class, listener));
        this.refreshButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (this.tabSheet.getSelectedTab().equals(this.doctorTab)) {
                updateDoctorGrid();
            } else if (this.tabSheet.getSelectedTab().equals(this.patientTab)) {
                updatePatientGrid();
            } else if (this.tabSheet.getSelectedTab().equals(this.resipeTab)) {
                updateResipeGrid();
            }
        });
    }

    private void initGenreStatisticsButton() {
        this.patientStatisticButton.getListeners(Button.ClickEvent.class)
                .forEach(listener -> this.patientStatisticButton.removeListener(Button.ClickEvent.class, listener));
        this.patientStatisticButton.addClickListener((Button.ClickListener) clickEvent ->
                addWindow(new PatientStatisticWindow(patientDAO, resipeDAO)));
    }

    private Layout getFilterPanel() {
        TextField descriptionInput = new TextField("Recipe description");
        ComboBox doctorBox = new ComboBox("Doctor");
        ComboBox priorityBox = new ComboBox();
        descriptionInput.setWidth(COMPONENT_WIDTH);
        doctorBox.setWidth(COMPONENT_WIDTH);
        priorityBox.setWidth(COMPONENT_WIDTH);

        doctorDAO.getAll().forEach(doctorBox::addItem);

        Arrays.stream(Precedence.values()).forEach(priorityBox::addItem);

        Button filterButton = new Button("Filter");
        filterButton.addClickListener((Button.ClickListener) clickEvent -> {
            Map<String, Object> filterMap = new HashMap<>();
            filterMap.put("description", descriptionInput.getValue().isEmpty() ? null : descriptionInput.getValue());
            filterMap.put("doctor", doctorBox.getValue() == null ? null : doctorBox.getValue());
            filterMap.put("priority", priorityBox.getValue());

            this.resipeGrid.removeAllColumns();
            this.resipeGrid.setContainerDataSource(
                    new BeanItemContainer<>(Resipe.class, this.resipeDAO.findByDescriptionAndPatientAndDoctor(filterMap))
            );
            resipeGrid.setColumnOrder("id", "definition", "patient", "doctor", "creationDate", "duration", "precedence");
        });
        Button removeFilterButton = new Button("Remove filter");
        filterButton.setWidth(COMPONENT_WIDTH);
        removeFilterButton.setWidth(COMPONENT_WIDTH);

        removeFilterButton.addClickListener((Button.ClickListener) clickEvent -> {
            descriptionInput.clear();
            doctorBox.clear();
            priorityBox.clear();
            updateResipeGrid();
        });

        VerticalLayout filterPanel = new VerticalLayout();
        filterPanel.setSpacing(true);
        filterPanel.setMargin(true);
        filterPanel.addComponent(descriptionInput);
        filterPanel.addComponent(doctorBox);
        filterPanel.addComponent(priorityBox);
        VerticalLayout filterButtonLayout = new VerticalLayout();
        filterButtonLayout.setSpacing(true);
        filterButtonLayout.addComponent(filterButton);
        filterButtonLayout.addComponent(removeFilterButton);
        filterPanel.addComponent(filterButtonLayout);
        return filterPanel;
    }
}
