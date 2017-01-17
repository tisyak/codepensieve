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

import com.medsys.master.dao.ConfigParaDAO;
import com.medsys.master.model.ConfigPara;

import com.medsys.common.model.Response;
import com.medsys.util.EpSystemError;


/**
 * The Class MasterConfigBDImpl.
 */
@Service
@Transactional
public class ConfigParaBDImpl implements ConfigParaBD {
	
	 
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory
            .getLogger(ConfigParaBDImpl.class);
    
    /** The master config dao. */
    @Autowired
    ConfigParaDAO masterConfigDAO;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.medsys.common.bd.MasterConfigBD#getConfigPara(in.cdac.epramaan
     * .util.ConfigParaKey)
     */
    @Override
    public ConfigPara getConfigPara(String paraname) {

    	try{
    		return masterConfigDAO.getConfigPara(paraname);
    	}catch(EmptyResultDataAccessException e){
    		logger.error("ConfigPara: " + paraname + " does not exist!");
            return null;
    	}
    }

	/* (non-Javadoc)
	 * @see com.medsys.master.bd.MasterConfigBD#addConfigPara(com.medsys.master.model.ConfigPara)
	 */
	@Override
	public Response addConfigPara(ConfigPara configPara) {
		try{
    		masterConfigDAO.addConfigPara(configPara);
    		return new Response(true, null);
    	}catch(DuplicateKeyException e){
    		logger.error("ConfigPara: " + configPara.getParaname() + " already exists. Cannot create duplicate!");
            return new Response(false, EpSystemError.DUPLICATE_RECORD);
    	}
		
	}

	/* (non-Javadoc)
	 * @see com.medsys.master.bd.MasterConfigBD#updateConfigPara(com.medsys.master.model.ConfigPara)
	 */
	@Override
	public Response updateConfigPara(ConfigPara configPara) {
		try{
    		masterConfigDAO.updateConfigPara(configPara);
    		return new Response(true, null);
    	}catch(EmptyResultDataAccessException e){
    		logger.error("ConfigPara: " + configPara.getParaname() + " does not exist!");
            return new Response(false, EpSystemError.NO_RECORD_FOUND);
    	}
		
	}

	/* (non-Javadoc)
	 * @see com.medsys.master.bd.MasterConfigBD#deleteConfigPara(java.lang.String)
	 */
	@Override
	public Response deleteConfigPara(String paraname) {
		try{
    		masterConfigDAO.deleteConfigPara(paraname);
    		return new Response(true, null);
    	}catch(EmptyResultDataAccessException e){
    		logger.error("ConfigPara: " + paraname + " does not exist!");
            return new Response(false, EpSystemError.NO_RECORD_FOUND);
    	}
		
	}

	/* (non-Javadoc)
	 * @see com.medsys.master.bd.MasterConfigBD#getConfigParas()
	 */
	@Override
	public List<ConfigPara> getConfigParas() {
		return masterConfigDAO.getConfigParas();
	}
    
  
    
    
}
