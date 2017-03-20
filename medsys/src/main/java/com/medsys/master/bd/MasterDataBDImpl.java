/*
 * 
 */
package com.medsys.master.bd;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medsys.common.model.Response;
import com.medsys.master.dao.MasterDataDAO;
import com.medsys.master.model.MasterData;
import com.medsys.util.EpSystemError;

/**
 * The Class MasterConfigBDImpl.
 */
@Service
@Transactional
public class MasterDataBDImpl implements MasterDataBD {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(MasterDataBDImpl.class);

	/** The master config dao. */
	@Autowired
	MasterDataDAO masterDataDAO;

	@Override
	public MasterData get(Class subClass, Integer id) {

		try {
			return masterDataDAO.get(subClass, id);
		} catch (EmptyResultDataAccessException e) {
			logger.error("MasterData: " + id + " does not exist!");
			return null;
		}
	}

	@Override
	public Response add(MasterData masterData) {
		try {
			masterDataDAO.add(masterData);
			return new Response(true, null);
		} catch (DuplicateKeyException e) {
			logger.error("MasterData: " + masterData.getUniqueId() + " already exists. Cannot create duplicate!");
			return new Response(false, EpSystemError.DUPLICATE_RECORD);
		}

	}

	@Override
	public Response update(Class subClass, MasterData masterData) {
		try {
			masterDataDAO.update(subClass, masterData);
			return new Response(true, null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("MasterData: " + masterData.getUniqueId() + " does not exist!");
			return new Response(false, EpSystemError.NO_RECORD_FOUND);
		}

	}

	@Override
	public Response delete(Class subClass, Integer id) {
		try {
			masterDataDAO.delete(subClass, id);
			return new Response(true, null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("MasterData: " + id + " does not exist!");
			return new Response(false, EpSystemError.NO_RECORD_FOUND);
		}

	}

	@Override
	public List<MasterData> getAll(String modelName) {
		return masterDataDAO.getAll(modelName);
	}

	@Override
	public MasterData getbyCode(Class subClass, String code) {

		try {
			return masterDataDAO.getbyCode(subClass, code);
		} catch (EmptyResultDataAccessException e) {
			logger.error("MasterData: " + code + " does not exist!");
			return null;
		}

	}

}
