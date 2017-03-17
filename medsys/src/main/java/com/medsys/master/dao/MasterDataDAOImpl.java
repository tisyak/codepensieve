package com.medsys.master.dao;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.medsys.master.model.MasterData;

@Repository
public class MasterDataDAOImpl implements MasterDataDAO {

	static Logger logger = LoggerFactory.getLogger(MasterDataDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void add(MasterData masterData) throws DuplicateKeyException {
		getCurrentSession().save(masterData);

	}

	@Override
	public MasterData get(Class subClass, Integer id) throws EmptyResultDataAccessException {
		logger.debug("MasterConfigDAOImpl.getMasterData() - for " + subClass.getName() + "[" + id + "]");
		MasterData masterData = null;

		masterData = (MasterData) getCurrentSession().get(subClass, id);

		if (masterData == null) {
			logger.debug("No masterData found.");
			throw new EmptyResultDataAccessException("masterData paraname [" + id + "] not found", 1);
		} else {
			logger.debug("returning masterData object: " + masterData.toString());
			return masterData;
		}
	}

	@Override
	public void update(Class subClass, MasterData masterData) throws EmptyResultDataAccessException {
		logger.debug("MasterConfigDAOImpl.update() - for " + subClass.getName() + " masterData :" + masterData);
		MasterData masterDataToUpdate = get(subClass, masterData.getUniqueId());
		if (masterDataToUpdate != null) {

			Field[] fields = subClass.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				try {
					field.set(masterDataToUpdate, field.get(masterData));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.debug("unable to set value for field: " + field.getName());
				}
			}
			getCurrentSession().update(masterDataToUpdate);
		} else {
			throw new EmptyResultDataAccessException(
					"MasterData  - for " + subClass.getName() + "[" + masterData.getUniqueId() + "] not found", 1);
		}
	}

	@Override
	public void delete(Class subClass, Integer id) throws EmptyResultDataAccessException {
		MasterData masterData = get(subClass, id);
		if (masterData != null) {
			getCurrentSession().delete(masterData);
		} else {
			throw new EmptyResultDataAccessException(
					"MasterData  - for " + subClass.getName() + "[" + id + "] not found", 1);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterData> getAll(String modelName) {
		return getCurrentSession().createQuery("from " + modelName).getResultList();
	}

}
