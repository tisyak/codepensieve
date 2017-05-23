package com.medsys.master.dao;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
	public MasterData get(Class<? extends MasterData> subClass, Integer id) throws EmptyResultDataAccessException {
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
	public void update(Class<? extends MasterData> subClass, MasterData masterData) throws EmptyResultDataAccessException {
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
	public void delete(Class<? extends MasterData> subClass, Integer id) throws EmptyResultDataAccessException {
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
	public List<MasterData> getAll(Class<? extends MasterData> subClass) {
		return getCurrentSession().createQuery("from " + subClass.getName()).getResultList();
	}

	@Override
	public MasterData getbyCode(Class<? extends MasterData> subClass, String code) {
		logger.debug("MasterDataDAOImpl.getbyCode() - [" + code + "]");
		MasterData masterdata;
		try {
			masterdata = (MasterData) subClass.newInstance();

			Query<MasterData> query = getCurrentSession().createQuery(
					"from " + subClass.getName() + " where " + masterdata.getKeyColumnName() + " = '" + code + "'",MasterData.class);

			logger.debug(query.toString());
			if (query.getResultList().size() == 0) {
				logger.debug("No masterdata found.");
				throw new EmptyResultDataAccessException(
						"MasterData [" + subClass.getName() + "] with code [" + code + "]+ not found", 1);
			} else {

				logger.debug("MasterData found. ");
				MasterData masterData = (MasterData) query.getSingleResult();

				return masterData;
			}

		} catch (InstantiationException e) {
			throw new EmptyResultDataAccessException("MasterData [" + subClass.getName() + "] with code [" + code
					+ "]+ not found. InstantiationException: " + e.getMessage(), 1);
		} catch (IllegalAccessException e) {
			throw new EmptyResultDataAccessException("MasterData [" + subClass.getName() + "] with code [" + code
					+ "]+ not found. IllegalAccessException: " + e.getMessage(), 1);
		}
	}

}
