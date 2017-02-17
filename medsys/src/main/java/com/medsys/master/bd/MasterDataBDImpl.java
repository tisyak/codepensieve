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
    private static final Logger logger = LoggerFactory
            .getLogger(MasterDataBDImpl.class);
    
    /** The master config dao. */
    @Autowired
    MasterDataDAO masterDataDAO;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.medsys.common.bd.MasterConfigBD#getConfigPara(in.cdac.epramaan
     * .util.ConfigParaKey)
     */
    @Override
    public MasterData get(Class subClass,String id) {

    	try{
    		return masterDataDAO.get(subClass,id);
    	}catch(EmptyResultDataAccessException e){
    		logger.error("ConfigPara: " + id + " does not exist!");
            return null;
    	}
    }

	/* (non-Javadoc)
	 * @see com.medsys.master.bd.MasterConfigBD#addConfigPara(com.medsys.master.model.ConfigPara)
	 */
	@Override
	public Response add(MasterData masterData) {
		try{
    		masterDataDAO.add(masterData);
    		return new Response(true, null);
    	}catch(DuplicateKeyException e){
    		logger.error("ConfigPara: " + masterData.getUniqueId() + " already exists. Cannot create duplicate!");
            return new Response(false, EpSystemError.DUPLICATE_RECORD);
    	}
		
	}

	/* (non-Javadoc)
	 * @see com.medsys.master.bd.MasterConfigBD#updateConfigPara(com.medsys.master.model.ConfigPara)
	 */
	@Override
	public Response update(Class subClass,MasterData masterData) {
		try{
    		masterDataDAO.update(subClass,masterData);
    		return new Response(true, null);
    	}catch(EmptyResultDataAccessException e){
    		logger.error("ConfigPara: " + masterData.getUniqueId() + " does not exist!");
            return new Response(false, EpSystemError.NO_RECORD_FOUND);
    	}
		
	}

	/* (non-Javadoc)
	 * @see com.medsys.master.bd.MasterConfigBD#deleteConfigPara(java.lang.String)
	 */
	@Override
	public Response delete(Class subClass,String id) {
		try{
    		masterDataDAO.delete(subClass,id);
    		return new Response(true, null);
    	}catch(EmptyResultDataAccessException e){
    		logger.error("ConfigPara: " + id + " does not exist!");
            return new Response(false, EpSystemError.NO_RECORD_FOUND);
    	}
		
	}

	/* (non-Javadoc)
	 * @see com.medsys.master.bd.MasterConfigBD#getConfigParas()
	 */
	@Override
	public List<MasterData> getAll(String modelName) {
		return masterDataDAO.getAll(modelName);
	}
    
  
    
    
}
