package com.haulmont.testtask.dao.daoimpl;

import com.haulmont.testtask.dao.ResipeDAO;
import com.haulmont.testtask.entity.Resipe;
import com.haulmont.testtask.entity.Patient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResipeDAOImpl implements ResipeDAO {
    private static final Logger log = LogManager.getLogger(ResipeDAOImpl.class);
    private Transactional<Resipe> transactional;

    public ResipeDAOImpl() {
        this.transactional = new Transactional<>(log);
    }

    @Override
    public void insertOrUpdate(Resipe resipe) {
        String logMessage = "Add/update resipe: " + resipe;
        this.transactional.transaction((x -> this.transactional.getEntityManager().merge(x)), resipe, logMessage);
        log.info(logMessage);
    }

    @Override
    public Collection<Resipe> findByDescriptionAndPatientAndDoctor(Map<String, Object> params) {
        StringBuilder logMessage = new StringBuilder("Find resipe by:");
        params.forEach((key, value) -> logMessage.append(key).append("=").append(value).append(" "));

        return this.transactional.transaction(() -> {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Resipe.class);

            if (params.get("description") != null) {
                detachedCriteria.add(Restrictions.like("description", (String) params.get("description"),
                        MatchMode.START).ignoreCase());
            }

            if (params.get("doctor") != null) {
                detachedCriteria.add(Restrictions.eq("doctor", params.get("doctor")));
            }

            if (params.get("patient") != null) {
                detachedCriteria.add(Restrictions.eq("patient", params.get("patient")));
            }

            Session session = this.transactional.getEntityManager().unwrap(Session.class);
            List result = detachedCriteria.getExecutableCriteria(session).list();
            log.info(logMessage.toString());
            return result;
        }, logMessage.toString());
    }

    @Override
    public Collection<Resipe> findByPatient(Patient patient) {
        Map<String, Object> params = new HashMap<>();
        params.put("patient",patient);
        StringBuilder logMessage = new StringBuilder("Find resipe by:");
        params.forEach((key, value) -> logMessage.append(key).append("=").append(value).append(" "));

        return this.transactional.transaction(() -> {
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Resipe.class);

            if (params.get("patient") != null) {
                detachedCriteria.add(Restrictions.eq("patient", params.get("patient")));
            }

            Session session = this.transactional.getEntityManager().unwrap(Session.class);
            List result = detachedCriteria.getExecutableCriteria(session).list();
            log.info(logMessage.toString());
            return result;
        }, logMessage.toString());
    }

    @Override
    public void remove(Resipe resipe) throws Exception {
        String logMessage = "Remove resipe: " + resipe;
        this.transactional.transaction((x -> {
            Resipe removableResipe = this.transactional.getEntityManager().find(Resipe.class, resipe.getId());
            if (removableResipe != null) {
                this.transactional.getEntityManager().remove(removableResipe);
            }
            this.transactional.getEntityManager().flush();
            log.info(logMessage);
        }), resipe, logMessage);
    }

    @Override
    public Resipe findByID(Long id) {
        String logMessage = "Find resipe by id: " + id;
        return this.transactional.transaction(() -> {
            Resipe result = this.transactional.getEntityManager().createNamedQuery("Resipe.findById", Resipe.class)
                    .setParameter("id", id).getSingleResult();
            log.info(logMessage);
            return result;
        }, logMessage);
    }

    @Override
    public Collection<Resipe> getAll() {
        String logMessage = "Get all Resipe";
        return this.transactional.transaction((() -> {
            Collection<Resipe> result = this.transactional.getEntityManager().createNamedQuery("Resipe.findAll", Resipe.class).getResultList();
            log.info(logMessage);
            return result;
        }), logMessage);
    }
}
