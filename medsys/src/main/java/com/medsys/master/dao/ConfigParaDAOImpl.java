package com.medsys.master.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.medsys.adminuser.dao.AdminUserDAOImpl;
import com.medsys.master.model.ConfigPara;

@Repository
public class ConfigParaDAOImpl implements ConfigParaDAO {

	static Logger logger = LoggerFactory.getLogger(AdminUserDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void addConfigPara(ConfigPara configPara)
			throws DuplicateKeyException {
		getCurrentSession().save(configPara);

	}

	@Override
	public ConfigPara getConfigPara(String paraname)
			throws EmptyResultDataAccessException {
		logger.debug("MasterConfigDAOImpl.getConfigPara() - [" + paraname + "]");
		ConfigPara configPara = (ConfigPara) getCurrentSession().get(
				ConfigPara.class, paraname);

		if (configPara == null) {
			logger.debug("No configPara found.");
			throw new EmptyResultDataAccessException("configPara paraname ["
					+ paraname + "] not found", 1);
		} else {
			logger.debug("returning configPara object");
			return configPara;
		}
	}

	@Override
	public void updateConfigPara(ConfigPara configPara)
			throws EmptyResultDataAccessException {
		ConfigPara configParaToUpdate = getConfigPara(configPara.getParaname());
		if (configParaToUpdate != null) {
			configParaToUpdate.setParavalue(configPara.getParavalue());
			configParaToUpdate.setParadesc(configPara.getParadesc());
			configParaToUpdate.setParabytevalue(configPara.getParabytevalue());
			configParaToUpdate.setUpdateBy(configPara.getUpdateBy());
			configParaToUpdate.setUpdateTimestamp(new Timestamp(System
					.currentTimeMillis()));
			getCurrentSession().update(configParaToUpdate);
		} else {
			throw new EmptyResultDataAccessException("ConfigPara ID : ["
					+ configPara.getParaname() + "] not found", 1);
		}
	}

	@Override
	public void deleteConfigPara(String paraname)
			throws EmptyResultDataAccessException {
		ConfigPara configPara = getConfigPara(paraname);
		if (configPara != null) {
			getCurrentSession().delete(configPara);
		} else {
			throw new EmptyResultDataAccessException("ConfigPara ID : ["
					+ paraname + "] not found", 1);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigPara> getConfigParas() {
		return getCurrentSession().createQuery("from ConfigPara").list();
	}

}
