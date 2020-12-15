package com.haulmont.testtask.dao.daoimpl;

import com.haulmont.testtask.dao.PatientDAO;
import com.haulmont.testtask.entity.Patient;
import com.haulmont.testtask.entity.Resipe;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientDAOImpl implements PatientDAO {

    private static final Logger log = LogManager.getLogger(PatientDAOImpl.class);
    private Transactional<Patient> transactional;

    public PatientDAOImpl() {
        this.transactional = new Transactional<>(log);
    }

    @Override
    public void insertOrUpdate(Patient patient) {
        String logMessage = "Add/update patient: " + patient;
        this.transactional.transaction((x -> this.transactional.getEntityManager().merge(x)), patient, logMessage);
        log.info(logMessage);
    }

    @Override
    public void remove(Patient patient) throws Exception {
        String logMessage = "Remove patient: " + patient;
        this.transactional.transaction((x -> {
            Patient removableGenre = this.transactional.getEntityManager().find(Patient.class, patient.getId());
            if (removableGenre != null) {
                this.transactional.getEntityManager().remove(removableGenre);
            }
            this.transactional.getEntityManager().flush();
            log.info(logMessage);
        }), patient, logMessage);
    }

    @Override
    public Patient findByID(Long id) {
        String logMessage = "Find patient by id: " + id;
        return this.transactional.transaction(() -> {
            Patient result = this.transactional.getEntityManager().createNamedQuery("Patient.findById", Patient.class)
                    .setParameter("id", id).getSingleResult();
            log.info(logMessage);
            return result;
        }, logMessage);
    }

    @Override
    public Collection<Patient> getAll() {
        String logMessage = "Get all Patients";
        return this.transactional.transaction((() -> {
            Collection<Patient> result = this.transactional.getEntityManager()
                    .createNamedQuery("Patient.findAll", Patient.class).getResultList();
            log.info(logMessage);
            return result;
        }), logMessage);
    }

    @Override
    public Collection<Patient> findByResipe(Resipe resipe){
        Map<String, Object> params = new HashMap<>();
        params.put("resipe",resipe);
        StringBuilder logMessage = new StringBuilder("Find patient by:");
        params.forEach((key, value) -> logMessage.append(key).append("=").append(value).append(" "));

        return this.transactional.transaction(() -> {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Patient.class);

            if (params.get("resipe") != null) {
                detachedCriteria.add(Restrictions.eq("resipe", params.get("resipe")));
            }

            Session session = this.transactional.getEntityManager().unwrap(Session.class);
            List result = detachedCriteria.getExecutableCriteria(session).list();
            log.info(logMessage.toString());
            return result;
        }, logMessage.toString());
    }
}