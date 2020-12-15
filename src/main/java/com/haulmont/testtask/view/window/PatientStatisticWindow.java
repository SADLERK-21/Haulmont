package com.haulmont.testtask.view.window;

import com.haulmont.testtask.dao.ResipeDAO;
import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.view.util.PatientStatisticEntity;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;

import java.util.*;

public class PatientStatisticWindow extends AbstractWindow {
    private ResipeDAO resipeDAO;
    private PatientDAO patientDAO;

    private Collection<PatientStatisticEntity> patientStatisticEntities;

    private Grid grid = new Grid();

    private static final String WINDOW_WIDTH = "600px";


    public PatientStatisticWindow( PatientDAO patientDAO, ResipeDAO resipeDAO) {
        this.resipeDAO = resipeDAO;
        this.patientDAO = patientDAO;
        this.patientStatisticEntities = new LinkedHashSet<>();
        this.patientDAO.getAll().forEach(patient -> patientStatisticEntities.add(
                new PatientStatisticEntity(patient,this.resipeDAO)));

        initComponents();
    }

    private void initComponents() {
        initCancelButton();
        configMainLayout();
        this.buttons.removeComponent(this.okButton);
        this.grid.setContainerDataSource(new BeanItemContainer<>(PatientStatisticEntity.class, patientStatisticEntities));
        this.grid.setWidth(WINDOW_WIDTH);
        this.grid.setColumnOrder("patient","count");

    }

    private void configMainLayout(){
        configBasicLayout();
        this.main.addComponent(grid);
        this.main.addComponent(this.buttons);

        setContent(this.main);
    }
}
